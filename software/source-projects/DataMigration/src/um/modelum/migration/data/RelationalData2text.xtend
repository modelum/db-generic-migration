package um.modelum.migration.data

import java.sql.Statement
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.IOException
import java.util.TreeMap
import java.util.LinkedList
import relationalschema.RelationalSchema
import relationalschema.Table
import java.util.Collections
import java.util.List
import java.util.stream.Collectors

class RelationalData2text {

	Statement statement
	RelationalSchema relationalModel
	String outputFile = ""
	BufferedWriter writer

	// struct to sort the attributes by natural order, and Comparator Keys to the row order
	LinkedList<TreeMap<String, String>> entity

	new(Statement statement, RelationalSchema relationalModel, String namefile) throws IOException{
		this.statement = statement
		this.relationalModel = relationalModel
		this.outputFile = namefile
		writer = new BufferedWriter(new FileWriter(outputFile))
		entity = new LinkedList<TreeMap<String, String>>()
	}

	def void printToFile() {
		writer.append("---------------------------------------------------------------------------------------\n")

		for (Table table : relationalModel.tables) {

			sortedEntity(table)
			printEntity(table.name)

			writer.append("---------------------------------------------------------------------------------------\n")
		}

		writer.close()
	}

	def void sortedEntity(Table table) {
		entity.clear()
		// generate a query and get the data
		var query = "SELECT * FROM " + table.name
		var rs = statement.executeQuery(query)
		val metaData = rs.metaData
		val columnCount = metaData.columnCount

		// get the pk and fk keys
		val pksName = table.keys.stream.filter[k|k.isIsPK].flatMap[k|k.columns.stream].map[c|c.name].toList
		val fksName = table.fks.stream.flatMap[k|k.columns.stream].map[c|c.name].toList

		// remove the foreign keys that reference other tables and aren't pk 
		var fksNameNoKey = fksName.filter[f|!pksName.contains(f)].map[f|f.toUpperCase].toList

		while (rs.next) {
			var aux = new TreeMap<String, String>()
			for (i : 1 .. columnCount) {
				// get the column
				val columnName = metaData.getColumnName(i)
				val value = rs.getObject(i)
				// filter the last fk
				if (!fksNameNoKey.contains(columnName)) {
					aux.put(columnName.toLowerCase, value === null ? "" : value.toString)
				}

			}
			entity.add(aux)
		}
		rs.close();

		// add for this table the pks, this keys will be use to sort document model
		ComparatorKeys.INSTANCE.addKeys(table.name, pksName)
		// sort the entity with the comparator		
		Collections.sort(entity, ComparatorKeys.INSTANCE.getComparator(table.name))

	}

	// method to print in a file the struct
	def void printEntity(String entityName) {
		writer.append("\nEntity: " + entityName + "\n\n")

		for (TreeMap<String,String> properties : entity) {
			for (String key : properties.keySet) {
				writer.append(key + ": " + properties.get(key) + "\n")
			}
			writer.newLine
		}
	}

	def List<String> testReference(String dbName) {
		switch(dbName)
		{
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
	
	
	def String twoTableQuery(int idUser)
	{
		
		var List<String> results = new LinkedList<String>()
		
		// metodos de pago de un usuario
		var query = "SELECT pm.methodname
					 FROM APPUSER u JOIN PAYMENTMETHOD pm ON u.id = pm.user_id 
					 WHERE u.id = " + idUser
		var rs = statement.executeQuery(query)

		while (rs.next()) {
			results.add(rs.getString("methodname"))
		}
		rs.close();
		results.stream.sorted().collect(Collectors.joining(","))
	}
	
	def String threeTableQuery(int idUser)
	{
		var List<String> results = new LinkedList<String>()
		
		// productos que ha comprado un usuario (nombre del usuario, nombre del producto, cantidad, subtotal)
		var query = "SELECT au.name AS user_name, p.name AS product_name, i.amount AS product_amount, i.subtotal AS total_amount
					 FROM ITEM i JOIN PRODUCT p ON i.product_id = p.id JOIN APPUSER au ON i.user_id = au.id
					 WHERE au.id = " + idUser
    	var rs = statement.executeQuery(query)

		while (rs.next()) {
			var row = "{" + rs.getString("user_name") + "," + rs.getString("product_name") 
							+ "," + rs.getString("product_amount") + "," + rs.getString("total_amount") + "}"
			results.add(row)
		}
		rs.close();
    	results.stream.sorted().collect(Collectors.joining(","))
	}
	
	def String exclusionQuery()
	{
		var List<String> results = new LinkedList<String>()
		
		// productos que no han sido relacionados
		var query = "SELECT p.name AS name_product, p.description AS description
					 FROM PRODUCT p LEFT JOIN RelatedProducts ON id = product_id
					 WHERE product_id IS NULL"
		
		var rs = statement.executeQuery(query)

		while (rs.next()) {
			var row = "{" + rs.getString("name_product") + "," + rs.getString("description") + "}"
			results.add(row)
		}
		rs.close();
    	results.stream.sorted().collect(Collectors.joining(","))
	}

}
