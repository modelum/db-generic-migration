package um.modelum.migration.data

import com.mongodb.client.MongoDatabase
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import documentschema.DocumentSchema
import documentschema.DocumentType
import java.util.TreeMap
import um.modelum.migration.Trace
import um.modelum.migration.adapter.USchemaAdapter
import java.util.ArrayList
import java.util.LinkedList
import documentschema.Reference
import relationalschema.Table
import org.bson.Document
import java.util.List
import relationalschema.Column
import java.util.Collections
import java.util.Arrays
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import java.util.stream.Collectors
import com.mongodb.client.model.Projections
import documentschema.Embedded
import com.mongodb.client.model.Field

class DocumentData2text {

	MongoDatabase mongoDB
	DocumentSchema documentModel
	String outputFile
	BufferedWriter writer
	List<TreeMap<String, String>> entity
	List<String> aggregates
	DocumentBrowser db
	Trace traceU2D
	Trace traceR2U
	USchemaAdapter uadapter

	new(MongoDatabase mongoDB, DocumentSchema documentModel, String namefile, Trace traceR2U, Trace traceU2D,
		USchemaAdapter uadapter) throws IOException{
		this.mongoDB = mongoDB
		outputFile = namefile
		this.documentModel = documentModel
		writer = new BufferedWriter(new FileWriter(outputFile))
		entity = new LinkedList<TreeMap<String, String>>
		aggregates = new LinkedList();
		this.traceU2D = traceU2D
		this.traceR2U = traceR2U
		db = new DocumentBrowser(traceU2D)
		this.uadapter = uadapter

	}

	def void printToFile() {

		writer.append("---------------------------------------------------------------------------------------\n")
		for (DocumentType entity : documentModel.documents) {

			var targetInstance = traceU2D.getSourceInstance(entity.getName()).get(0);
			// different methods to entityType, relationshipType and aggregate
			if (targetInstance instanceof uschema.EntityType) {
				// search all the aggregate recursively
				searchAggregate(entity.name, true)
				// get the data to the entity
				sortedEntity(entity.name)
			} else {
				// the same with a relationship
				sortedRelationship(entity.name)
			}

			printEntity(entity.name)
			writer.append("---------------------------------------------------------------------------------------\n")

		}

		// the same function as the last forEach but with all the aggregates
		printAggregates()

		writer.close()

	}

	def printAggregates() {
		for (String agg : aggregates) {
			sortedAggregate(agg)
			printEntity(getNameAggregate(agg))
		}
	}

	def void sortedRelationship(String entityName) {

		entity.clear()

		// get the atts and rels
		var atts = db.getAttributes(entityName, false)
		var rels = db.getReferences(entityName, false)
		// get the collection and open the cursor
		var collection = mongoDB.getCollection(entityName)
		var cursor = collection.find.iterator

		// get the table
		var entityTable = traceR2U.getSourceInstance(entityName).get(0) as Table
		// select the pk and fk keys
		val pksName = entityTable.keys.stream.filter[k|k.isIsPK].flatMap[k|k.columns.stream].map[c|c.name.toLowerCase].
			toList
		val fksName = entityTable.fks.stream.map[c|c.constraintname.toLowerCase].toList

		while (cursor.hasNext) {
			var aux = new TreeMap<String, String>();
			var doc = cursor.next;

			// attributes
			writeAttributes(atts, aux, doc)

			// references/foreign keys and primaryKeys
			for (Reference r : rels) {
				// get the pkName through fkname
				aux.put(pksName.get(fksName.indexOf(r.name.toLowerCase)), doc.get(r.name).toString)
			}

			entity.add(aux);
		}

		cursor.close

		Collections.sort(entity, ComparatorKeys.INSTANCE.getComparator(entityName))

	}

	def void sortedEntity(String entityName) {
		// clear the last table
		entity.clear()

		// entity, extract the attributes
		var atts = db.getAttributes(entityName, false)
		var collection = mongoDB.getCollection(entityName)

		// create a cursor to access collection
		var cursor = collection.find.iterator

		while (cursor.hasNext) {
			// treemap to sort the attributes
			var aux = new TreeMap<String, String>();
			var doc = cursor.next;

			// adjust the key
			adjustKey(entityName, aux, doc)

			// attributes
			writeAttributes(atts, aux, doc)

			// insert with the other rows
			entity.add(aux);

		}

		cursor.close

		// use the comparator with the keys chosen as criteria in the relational
		Collections.sort(entity, ComparatorKeys.INSTANCE.getComparator(entityName))

	}

	def void sortedAggregate(String aggregateName) {

		entity.clear()
		// get the agg in usquema
		var aggUschema = traceU2D.getSourceInstance(aggregateName).get(0) as uschema.Aggregate

		// weak entity
		var entityOwner = traceR2U.getSourceInstance(getNameAggregate(aggregateName)).get(0) as Table
		// get the fk that reference at the aggregate's owner
		val pk = entityOwner.keys.stream.flatMap[k|k.columns.stream].map[c|c.name].toList
		val fk = entityOwner.fks.stream.filter[f|f.columns.stream.allMatch[c|pk.contains(c.name)]].toList
		var fkWeak = fk.get(0)

		// select the attributes en usquema
		var attributes = aggUschema.specifiedBy.features.stream.filter[f|f instanceof uschema.Attribute].map [ f |
			f as uschema.Attribute
		].toList

		// get the atts and keys in usquema aggregate
		var atts = attributes.filter[a|a.key === null].toList
		var keys = attributes.filter[a|a.key !== null].toList

		var collection = mongoDB.getCollection(getNameOwnerAggregate(aggregateName))

		// create a cursor to access collection
		var cursor = collection.find.iterator

		while (cursor.hasNext) {
			// treemap to sort the attributes
			var doc = cursor.next;

			var docAggs = doc.get(getNameAggregate(aggregateName)) as List<Document>
			for (docAux : docAggs) {
				var aux = new TreeMap<String, String>();
				// adjust the key
				adjustKeyAggregate(keys, aux, docAux)

				// attributes
				for (uschema.Attribute a : atts) {
					aux.put(a.name, docAux.get(a.name) === null ? "" : docAux.get(a.name).toString)
				}

				// weak entity, if the column is 1, the id have a _id with the field's name which the aggregate's owner's id
				if (fkWeak.columns.size == 1) {
					aux.put(fkWeak.columns.get(0).name, doc.get(fkWeak.refsTo.columns.get(0).name + "_id").toString)
				} else {
					// the colunm > 1, the owner's id is EntityName + "_id" and the data is join with a "_" limiter
					var values = doc.get(getNameOwnerAggregate(aggregateName) + "_id").toString.split("\\_")
					var i = 0
					for (Column c : fkWeak.columns) {
						aux.put(c.name, values.get(i))
						i++
					}
				}

				entity.add(aux);
			}
		}
		cursor.close

		Collections.sort(entity, ComparatorKeys.INSTANCE.getComparator(getNameAggregate(aggregateName)))

	}

	// print the struct
	def void printEntity(String entityName) {
		writer.append("\nEntity: " + entityName + "\n\n")

		for (TreeMap<String,String> properties : entity) {
			for (String key : properties.keySet) {
				writer.append(key.toLowerCase + ": " + properties.get(key) + "\n")
			}
			writer.newLine
		}

	}

	// method inverse to recovery the original name key
	def processKey(ArrayList<uschema.Attribute> keys) {
		var keyName = ""
		if (keys.size == 1)
			keyName = keys.get(0).name
		else
			keyName = keys.get(0).owner.name
		keyName + "_id"
	}

	// assign the original values a her original key
	def void adjustKey(String entityName, TreeMap<String, String> aux, Document doc) {
		var e = traceU2D.getSourceInstance(entityName).get(0) as uschema.EntityType
		var ucursor = uadapter.readEntityAll(e)
		var keys = ucursor.ID.attributes
		var key = processKey(keys)

		// return to the original key values, key1_key2 = key1, key2
		if (keys.size == 1) {
			aux.put(key.substring(0, key.length() - 3), doc.get(key).toString)
		} else {
			var compositeKey = doc.get(key).toString
			var keyValues = compositeKey.split("_")
			var i = 0
			for (uschema.Attribute k : keys) {
				aux.put(k.name, keyValues.get(i))
				i++
			}
		}
		ucursor.close
	}

	def void adjustKeyAggregate(List<uschema.Attribute> keys, TreeMap<String, String> aux, Document doc) {
		if (keys.size == 1) {
			var key = keys.get(0).name
			aux.put(key, doc.get(key + "_id").toString)
		} else {
			// its posible?
		}
	}

	def void writeAttributes(List<documentschema.Field> atts, TreeMap<String, String> aux, Document doc) {
		for (documentschema.Field a : atts) {
			aux.put(a.name, doc.get(a.name) === null ? "" : doc.get(a.name).toString)
		}
	}

	def void searchAggregate(String entityName, boolean isEntity) {
		var List<Embedded> aggs
		if (isEntity)
			aggs = db.getAggregates(entityName, false)
		else
			aggs = db.getAggregates(entityName, true)

		for (Embedded a : aggs) {
			aggregates.add(entityName + "." + a.name)
			searchAggregate(entityName + "." + a.name, false)
		}

	}

	def String getNameAggregate(String aggName) {
		var delimiter = aggName.split("\\.")
		delimiter.get(delimiter.length - 1).substring(0, (delimiter.get(delimiter.length - 1)).length - 1)
	}

	def String getNameOwnerAggregate(String aggName) {
		var delimiter = aggName.split("\\.")
		delimiter.get(0)
	}

	def List<String> testReference(String dbName) {
		switch (dbName) {
			case "MetaShop": referencesMetaShop()
		}
	}

	def List<String> referencesMetaShop() {
		var List<String> results = new LinkedList<String>()

		results.add(twoTableQuery(Test.USER_ID))
		results.add(threeTableQuery(Test.USER_ID))
		results.add(exclusionQuery())
		println(exclusionQuery())

		return results
	}

	def String twoTableQuery(int idUser) {

		var List<String> results = new LinkedList<String>()

		val collection = mongoDB.getCollection("AppUser")
		// filter the user selected
		var user = collection.find(Filters.eq("id_id", idUser.toString)).first
		var paymentMethods = user.get("PaymentMethod") as List<Document>

		for (Document method : paymentMethods) {
			results.add(method.getString("methodName"))
		}

		return results.stream.sorted.collect(Collectors.joining(","))
	}

	def String threeTableQuery(int idUser) {
		var List<String> results = new LinkedList<String>()

		var itemCollection = mongoDB.getCollection("Item");

		var itemsInfo = itemCollection.aggregate(Arrays.asList(
			// filter only the user selected
			Aggregates.match(Filters.regex("Item_id", "^" + idUser + "_.*")),
			// create a aux field to search the user
			Aggregates.addFields(Arrays.asList(new Field<String>("user_id", idUser.toString))),
			// Join with product
			Aggregates.lookup("Product", "Item_id", "items", "productDetails"),
			// Join with appUser
			Aggregates.lookup("AppUser", "user_id", "id_id", "userDetails"),
			// proyections
			Aggregates.project(Projections.fields(
				// include this fields
				Projections.include("amount", "subtotal"),
				// select the field name on product
				Projections.computed("product_name",
					new Document("$arrayElemAt", Arrays.asList("$productDetails.name", 0))),
				// select the field name on user
				Projections.computed("user_name", new Document("$arrayElemAt", Arrays.asList("$userDetails.name", 0)))
			))
		))

		for (Document doc : itemsInfo) {
			var row = "{" + doc.getString("user_name") + "," + doc.getString("product_name") + "," +
				doc.get("amount") + "," + doc.get("subtotal") + "}"
			results.add(row)
		}

		return results.stream.sorted.collect(Collectors.joining(","))
	}

	def String exclusionQuery() {
		var List<String> results = new LinkedList<String>()

		var productsCollection = mongoDB.getCollection("Product");

		var products = productsCollection.aggregate(Arrays.asList(
			// $lookup LEFT JOIN with RelatedProducts
			Aggregates.lookup("RelatedProducts", "id_id", "product_id_fk", "relatedProducts"),
			// when the search is null
			Aggregates.match(Filters.eq("relatedProducts", Arrays.asList())),
			// select only name and description
			Aggregates.project(Projections.fields(Projections.include("name", "description")))
		))

		for (Document doc : products) {
			var row = "{" + doc.getString("name") + "," + doc.getString("description") + "}"
			results.add(row)
		}
		
		return results.stream.sorted.collect(Collectors.joining(","))
	}
}
