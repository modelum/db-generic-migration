package um.modelum.migration.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

import documentschema.Embedded;
import documentschema.Field;
import um.modelum.migration.Trace;
import um.modelum.migration.adapter.USchemaAdapter;
import um.modelum.migration.adapter.USchemaBrowser;
import um.modelum.migration.adapter.USchemaCursor;
import uschema.Attribute;
import uschema.Reference;
import uschema.Aggregate;
import uschema.RelationshipType;

public class DocumentAPI {

	private static final String SEPARATOR_KEYS = "_";
	private Trace trace;
	private USchemaBrowser ub;
	private DocumentBrowser db;
	public DocumentAPI() {

	}

	public DocumentAPI(Trace trace, USchemaBrowser ub) {
		this.trace = trace;
		this.ub = ub;
		db = new DocumentBrowser(trace);
	}

	// select the new name for entity's id
	public String getNameKey(List<Attribute> keys) {
		return keys.size() == 1 ? keys.get(0).getName().toString() + "_id" : keys.get(0).getOwner().getName() + "_id";
	}

	// if the key contains 2 or more values, it will form with the concatenation the
	// values with _
	// TODO: revisar object
	public Object getValueKey(List<Object> keyValues) {
		return keyValues.size() == 1 ? keyValues.get(0)
				: keyValues.stream().map(Object::toString).collect(Collectors.joining(SEPARATOR_KEYS));
	}

	// Recursive method for traversing aggregates and entities
	public Document EntityType2Document(USchemaCursor cursor, String entity, List<Attribute> keys, List<Attribute> attributes, List<documentschema.Reference> references, List<Embedded> aggregates, boolean isAgg) {
		// keys
		// delimiter _
//System.out.println("Convierto entidad "+entity+ "en documento -Agregado= "+isAgg);
		Document document;
		//comprobar si documento tiene clave o si no tiene atributos (caso de agregado sin discriminante)
		if (keys!=null && keys.size()>0) {
//System.out.println("Los atributos de la key son ("+keys.size()+"):"+keys);
			List<Object> keyValues = cursor.getID().getValues();
			document = new Document(getNameKey(keys), getValueKey(keyValues));
		} else {
			document =new Document(); //usará un _id interno como identificador
		}
//System.out.println("Nuevo documento id:"+getNameKey(keys)+"="+getValueKey(keyValues));
		String entitySelected = isAgg ? lastTwoFields(entity) : entity;

		writeAttributes(attributes, cursor, document, entity);
//System.out.println("Referencias:"+references);
		// select the references

		writeReferences(references, cursor, document, entity);
//System.out.println("Agregados:");
		// select the aggregates

		writeAggregates(aggregates, cursor, document, entity);

		return document;
	}
	
	public String lastTwoFields(String path) {
	    return Arrays.stream(path.split("\\."))
	                 .skip(Math.max(0, path.split("\\.").length - 2))
	                 .collect(Collectors.joining("."));
	}

	public int RelationshipType2Document(String relationshipType, USchemaAdapter uadapter,
			MongoCollection<Document> collection) {
		Object fk1_value = null, fk2_value = null;
		// get the relationship and any random reference (the size of getReference is 2)
		RelationshipType rt = (RelationshipType) trace.getSourceInstance(relationshipType).get(0);
		int numInstances=0;	
		if (rt.getReferences().size() > 1) {
			Reference r1 = rt.getReferences().get(0);
			Reference r2 = rt.getReferences().get(1);

			// get cursor to the owner's reference and a additional cursor to read the
			// relationship fields
			uschema.EntityType e = (uschema.EntityType) trace.getSourceInstance(r1.getOwner().getName()).get(0);
			USchemaCursor cursor = uadapter.readEntityAll(e);

			// select the attributes
			List<Attribute> attributes = rt.getFeatures().stream().filter(f -> f instanceof Attribute)
					.map(attr -> (Attribute) attr).toList();
			
			int BATCH_SIZE=1000;
			List<Document> batch = new ArrayList<>(BATCH_SIZE);
			
			while (cursor.hasData()) {

				USchemaCursor cursorRef = cursor.getReference(r1);
				// select the first key
				fk2_value = getValueKey(cursor.getID().getValues());
				while (cursorRef.hasData()) {
					// autoincrement id (rt.getName() + "_id", autoIncrement++) not necessary
					Document document = new Document();
					// selects the second key reference for the first
					fk1_value = getValueKey(cursorRef.getID().getValues());
					
					document.append(r1.getName(), fk1_value);
					document.append(r2.getName(), fk2_value);

					// write the attributes read since cursorRt
					for (Attribute a : attributes) {
						//NO->Attribute att = (Attribute) trace.getSourceInstance(rt.getName() + "." + a.getName()).get(0);
						Attribute att = (Attribute) trace.getSourceInstanceFromSourceId(rt.getName() + "." + a.getName());
			
						Object value = cursorRef.getAttributeValue(att);
						document.append(att.getName(), value == null ? null : value);
					}
					batch.add(document);
					if (batch.size() == BATCH_SIZE) {
						numInstances=numInstances+batch.size();
				        collection.insertMany(batch);
				        batch.clear();
				    }
					cursorRef.next();
				}
				// insertar los documentos restantes
				if (!batch.isEmpty()) {
					numInstances=numInstances+batch.size();
				    collection.insertMany(batch);
				    batch.clear();
				}
				cursorRef.close();

				cursor.next();
			}
			cursor.close();
		}
		return numInstances;
	}

	// search on the trace the usquema.Attribute and get the value in the cursor
	public void writeAttributes(List<Attribute> attributes, USchemaCursor cursor, Document document,
			String parent) {
		for (Attribute att : attributes) {
			Object value = cursor.getAttributeValue(att);
			document.append(att.getName(), value);
//System.out.println("  "+att.getName()+":"+value);
		}
	}

	public void writeReferences(List<documentschema.Reference> references, USchemaCursor cursor, Document document,
			String parent) {
		for (documentschema.Reference docRef : references) {
//PEP
//System.out.println("Busco la instancia fuente(uschema) de '"+parent + "." + docRef.getName()+"'");			
			Reference ref = (Reference) trace.getSourceInstance(parent + "." + docRef.getName()).get(0);
//System.out.println("La encuentro en (uschema):"+ref.getName()+ "("+ref.getOwner().getName()+"."+ref.getName()+")");
			USchemaCursor cursorRef = cursor.getReference(ref);
			List<Object> listRef = new LinkedList<Object>(); //OJO!! No es una lista si el tipo de la referencia en Documentos es primitivo
			/*if(!cursorRef.hasData())
			{
				Document docAux = new Document("noReferences", "");
				listRef.add(docAux);
			}*/
			while (cursorRef.hasData()) {
				Document docAux = new Document();
				if (ref.getIsFeaturedBy() != null && ref.getIsFeaturedBy().getReferences().size() == 1) {
					// ver la relacion inversa (Reference) y si Reference > 1 no se ejecuta (sin
					// propiedad vacia),
					docAux.append("id", getValueKey(cursorRef.getID().getValues()));
					RelationshipType rt = ref.getIsFeaturedBy();
					List<Attribute> relAtts = ub.getAttributes(rt);
					for (Attribute attAux : relAtts) {
						String valAux = cursorRef.getAttributeValue(attAux).toString();
						docAux.append(attAux.getName(), valAux);

					}
					listRef.add(docAux);
				} else {
					listRef.add(getValueKey(cursorRef.getID().getValues()));
				}
				cursorRef.next();
			}
			cursorRef.close();
			document.append(docRef.getName(), listRef);
//System.out.println("  "+docRef.getName()+":"+ listRef);
		}
	}

	// recursive method
	public void writeAggregates(List<Embedded> aggregates, USchemaCursor cursor, Document document,
			String parent) {
		// for each aggregate
		for (Embedded docAgg : aggregates) {
//PEP
//System.out.println("Writting aggregate:"+docAgg.getName());
			// get the aggregate and cursor
			Aggregate agg = (Aggregate) trace.getSourceInstance(parent + "." + docAgg.getName()).get(0);
			USchemaCursor cursorAgg = cursor.getAggregate(agg);
			List<Attribute> keys = cursorAgg.getID().getAttributes();
			String entitySelected = lastTwoFields(parent+"."+docAgg.getName());
			List<Field> attributes = db.getAttributes(entitySelected, true);
			List<Attribute> uSchemaAttributes = new ArrayList<Attribute>();
			for (Field docAtt : attributes) {
				Attribute att = (Attribute) trace.getSourceInstance(parent + "." + docAgg.getName()+"."+docAtt.getName()).get(0);
				uSchemaAttributes.add(att);
			}
			List<documentschema.Reference> references = db.getReferences(entitySelected, true);
			List<Embedded> subAggregates = db.getAggregates(entitySelected, true);
			// create a empty list for the data
			List<Document> dataAggs = new LinkedList<Document>();
			while (cursorAgg.hasData()) {
				// call recursively the method, the last field this time is true
				Document docAux = EntityType2Document(cursorAgg, parent + "." + docAgg.getName(), keys, uSchemaAttributes, references, subAggregates, true);
				dataAggs.add(docAux);
				cursorAgg.next();
			}
			document.append(cursorAgg.getEntityName(), dataAggs);
//System.out.println("  agg- "+cursorAgg.getEntityName()+";"+dataAggs);
			cursorAgg.close();
		}
	}
}
