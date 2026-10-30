package um.modelum.migration.data



import um.modelum.migration.Trace
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoClients
import java.sql.Connection
import java.sql.Statement
import java.sql.DriverManager
import documentschema.DocumentSchema
import um.modelum.migration.adapter.USchemaAdapter
import relationalschema.RelationalSchema
import java.util.Map
import java.nio.file.Paths
import java.nio.file.Files
import java.util.TreeMap
import java.io.BufferedWriter
import java.io.FileWriter

class DocumentValidator {

	MongoDatabase mongoDB
	Trace trace
	Connection connection
	Statement statement

	// outputFiles folder
	var outputFileMongo = "validation/mongoData"
	var outputFileSQL = "validation/sqlData"

	String dbName = ""

	new(String server_mongoDB, String server_sql, String dbName, String username, String password) {
		this.trace = trace
		this.dbName = dbName
		mongoDB = createConnectionMongo(server_mongoDB)
		statement = createStatement(server_sql, username, password)
		outputFileMongo += "_" + dbName + ".txt"
		outputFileSQL += "_" + dbName + ".txt"
	}

	def createConnectionMongo(String server_mongoDB) {
		var mongoClient = MongoClients.create(server_mongoDB);
		mongoClient.getDatabase(dbName);
	}
	
	// statement to create the ResultSet
	def createStatement(String server_sql, String username, String password) {

		var driverName = "oracle.jdbc.driver.OracleDriver";
		Class.forName(driverName);

		// Create a connection to the database with a service
		var url = "jdbc:oracle:thin:@" + server_sql+ ":" + 1521+"/"+"ORCLPDB";
		connection = DriverManager.getConnection(url, username, password);
		connection.createStatement
	}

	// print the data in a file, and sort the entitys by name
	def void validateDocumentAndRelational(RelationalSchema relationalModel, DocumentSchema documentModel, Trace trace1, Trace trace2, USchemaAdapter uadapter) {
		
		// relational 
		var rt = new RelationalData2text(statement, relationalModel, outputFileSQL)
		rt.printToFile
		
		// mongo
		var dt = new DocumentData2text(mongoDB, documentModel, outputFileMongo, trace1, trace2, uadapter)
		dt.printToFile
		
		// sort the files
		sortFiles(outputFileMongo, outputFileSQL)
		
		// test the references}
		var relationalResults = rt.testReference(dbName)
		var mongoResults = dt.testReference(dbName)
		if(relationalResults.equals(mongoResults))
			println("Correct References")
		else println("An error was ocurred with the references")
	}
	

	
	def void sortFiles(String ... files)
	{
		for(String file : files)
		{
			sortFile(file)
		}
	}
	
	def sortFile(String file) {
		val Map<String, String> entityMap = new TreeMap<String, String>()
		var BufferedWriter writer
		
		// select the path
        val filePath = Paths.get(file)

        // read all the content in a same line
        val lines = Files.readAllLines(filePath)
        val inputText = lines.join("\n") 

        // get the entitys with the separator
        val blocks = inputText.split("---------------------------------------------------------------------------------------")

        // for each block
        for (block : blocks) {
            val linesInBlock = block.trim.split("\n").filter[!it.trim.isEmpty] // Remove empty lines
            if (linesInBlock.size > 0 && linesInBlock.get(0).startsWith("Entity:")) { // search the name of the entity
                val entityName = linesInBlock.get(0).substring("Entity:".length).trim // select the name of the entity as the key
                val entityContent = block.trim // delete the spaces begin and last
                entityMap.put(entityName, entityContent)
            }
        }

		writer = new BufferedWriter(new FileWriter(file))
        // writing the content in the file
        for (entry : entityMap.entrySet) {
            writer.append(entry.value)
            writer.append("\n---------------------------------------------------------------------------------------\n")
        }
        
        writer.close
		
	}

}
