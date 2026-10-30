package um.modelum.migration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import documentschema.DocumentSchema;
import documentschema.DocumentType;
import documentschema.Embedded;
import documentschema.Field;
import relationalschema.RelationalSchema;
import uschema.Attribute;
import uschema.EntityType;
import uschema.RelationshipType;
import uschema.USchema;
import um.modelum.migration.adapter.*;
import um.modelum.migration.data.DocumentAPI;
import um.modelum.migration.data.DocumentBrowser;

public class Principal {

	public static void main(String[] args) {
		File jarFile=null;
		File jarFolder=null;
		try {
			//jarFile = new File(Principal.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
			jarFile = new File(Principal.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			//new File(jarDir, "miModelo.xmi");
		} catch (URISyntaxException e) {e.printStackTrace();}
		System.out.println("El nombre del archivo es:"+jarFile.getName());
		if (args.length!=9) {
			//System.out.println("Usage: java -jar "+jarFile.getName()+" relational(xmi without extension) BDrelational BDMongo");
			//System.out.println("Usage: java -jar "+jarFile.getName()+" relationalModel(xmi without extension) IP_postgreSQL Port_postgreSQL postgreSQL_user postgreSQL_password postgresSQL_database IP_mongoDB Port_mongoDB mongoDB_database");
			System.out.println("Usage: java -jar "+jarFile.getName()+" a1 a2 a3 a4 a5 a6 a7 a8 a9");
			System.out.println("   a1 = relationalModel(xmi without extension)"); 
			System.out.println("   a2 = PostgreSQL_IP_address");
			System.out.println("   a3 = PostgreSQL_Port");
			System.out.println("   a4 = PostgreSQL_user");
			System.out.println("   a5 = PostgreSQL_user_password");
			System.out.println("   a6 = PostgreSQL_database_name");
			System.out.println("   a7 = MongoDB_IP_address");
			System.out.println("   a8 = MongoDB_Port");
			System.out.println("   a9 = MongoDB_database_name");
			System.out.println("e.g:");
			System.out.println("java -jar migrator.jar MusicService-relational localhost 5432 user user_pwd sourceDatabase localhost 27017 targetDatabase");
			System.exit(0);
		}
		//obtener la carpeta donde está contenido el jar ejecutable
		//getParentFile obtiene el padre del archivo jar (e.g. la carpeta /home/user)
		jarFolder=jarFile.getParentFile();
		Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);

		// rutas de creaciones
		String RELATIONAL_MODEL_XMI_PATH = new File(jarFolder, args[0]+".xmi").getAbsolutePath();
		String USCHEMA_MODEL_XMI_PATH = new File(jarFolder, args[0]+"-uschema.xmi").getAbsolutePath();
		String DOCUMENT_MODEL_XMI_PATH = new File(jarFolder, args[0]+"-document.xmi").getAbsolutePath();
		String DOCUMENT_MODEL_JSON_PATH = new File(jarFolder, args[0]+"-mongoose.js").getAbsolutePath();

		Trace trace1, trace2;
		final String SERVER_MONGODB =  "mongodb://"+args[6]+":"+args[7];
		//final String SERVER_MONGODB =  "mongodb://localhost:27017";
		//final String SERVER_MONGODB =  "mongodb://51.49.50.59:27017";

		// Relational to USchema
		Relational2USchema m2mRel = new Relational2USchema();
		RelationalSchema relationalModel = m2mRel.loadSourceModel(RELATIONAL_MODEL_XMI_PATH);
		USchema uschemaModel = m2mRel.createTargetModel(USCHEMA_MODEL_XMI_PATH);
		//no agregar en TPC-C estos dos porque tienen referencias desde otras tablas fuertes
		m2mRel.filter_do_not_aggregate("customer.c_fk_d");
		m2mRel.filter_do_not_aggregate("district.d_fk_w");
	    //m2mRel.filter_do_not_aggregate("Item.order_fk");
		//m2mRel.filter_do_not_move("Product.productCategory_fk");
		//m2mRel.filter_do_not_move("Item.includeProduct_fk");
		//m2mRel.filter_do_not_aggregate("playlist.playlist_fk_user");
		//m2mRel.filter_do_not_aggregate("playlist_song.playlist_song_fk_playlist");
		m2mRel.transformar(relationalModel, uschemaModel);
		trace1 = m2mRel.getTrace();

//Update uschema model test - only on MusicService
if (args[0].equals("MusicService-relational")) {
	System.out.println("Updating MusicService uschema");
	System.out.print("Saving trace...");
	try { trace1.saveTrace("MusicService.trace", RELATIONAL_MODEL_XMI_PATH, USCHEMA_MODEL_XMI_PATH);
	} catch (IOException e) { e.printStackTrace(); }
	System.out.println("OK");
	TraceChanger tchan=new TraceChanger();
	System.out.print("TraceChanger crated. Loading trace file...");
	tchan.loadTrace("MusicService.trace", RELATIONAL_MODEL_XMI_PATH,USCHEMA_MODEL_XMI_PATH);
	System.out.println("OK");
	System.out.print("Disaggregating app_user.playlist....");
	//desagregar
	//System.out.println(t.getTargetInstanceFromTargetId("app_user.playlist"));
	tchan.disaggregate("app_user.playlist");
	System.out.println("OK");
	//rename
	System.out.print("Renaming app_user to user_data....");
	tchan.rename("app_user", "user_data");
	System.out.println("OK");
	//borrar atributo
	System.out.print("Removing user_data.is_premium attribute....");
	tchan.removeAttribute("user_data.is_premium");
	System.out.println("OK");
	System.out.print("Saving uschema updated model....");
	tchan.saveUpdateTargetModel();
	System.out.println("OK");
	System.out.print("Saving updated trace file....");
	tchan.saveUpdateTrace();
	System.out.println("OK");
	System.out.print("Loading updated trace...");
	try {trace1.loadTrace("MusicService.trace", RELATIONAL_MODEL_XMI_PATH, USCHEMA_MODEL_XMI_PATH);
	} catch (IOException e) { e.printStackTrace(); }
	System.out.println("OK");
	System.out.println("Continuing migration process!");
}

//-------------end test---------------------------
		
		
		//relational.generateScript(relationalModel, "models/createMusicArt.sql");
		//relational.generateRemoveScript(relationalModel, "models/deleteMusicArt.sql");

		// USchema to Document
		Uschema2Document m2mDoc = new Uschema2Document();
		m2mDoc.loadSchema(USCHEMA_MODEL_XMI_PATH);
		m2mDoc.executeMapping();
		m2mDoc.saveSchema(DOCUMENT_MODEL_XMI_PATH);
		DocumentSchema documentModel = m2mDoc.getDocumentSchema();
		trace2 = m2mDoc.getTrace();

		// Document to JSON
		Document2Text m2Text = new Document2Text();
		m2Text.loadSchema(DOCUMENT_MODEL_XMI_PATH);
		System.out.println("Starting DocumentSchema to JavaScript file...");
		m2Text.mappingDocument2Text(DOCUMENT_MODEL_JSON_PATH);
		System.out.println("JavaScript file has been created at '" + DOCUMENT_MODEL_JSON_PATH + "'");

//trace2.printFullTrace();
		// ---------------Data migration----------------
		// ---------------------------------------------
		// Este ejemplo recorre directamente el modelo de USchema.
		// En vez de eso debería de recorrer el modelo de documentos de la base de datos
		// destino y a partir de la traza2
		// recuperar las entidades de USchema que se deben consultar
		// para poder usarlo hay que cambiar el xmi del relacional, comentar uno de los dos sysout de abajo, tener la base mongo abierta en localhost
		// y poner los mismo credenciales (usuario, pass) que el sysout en el validador de abajo

		USchemaAdapter uadapter = new USchemaRelAdapter(trace1);
		// musicart database
		//System.out.println(uadapter.openConnectionService(SERVER_SQL, 1521, "ORCLPDB", "DSI02", "modelum"));
		
		// metashop database
		//System.out.println(uadapter.openConnectionService("sgbd.inf.um.es", 1521, "ORCLPDB", "DSI00", "fargo"));
		
		// ec2 instance
		//System.out.println(	uadapter.openConnectionService("51.49.50.59", 5432, "musicdb", "music", "music"));
		//System.out.println(	uadapter.openConnectionService("localhost", 5432, args[1], "music", "music"));
		System.out.println(	uadapter.openConnectionService(args[1], Integer.parseInt(args[2]), args[5], args[3], args[4]));
		
		// connection mongoDB
		MongoClient mongoClient = MongoClients.create(SERVER_MONGODB);
		MongoDatabase database = mongoClient.getDatabase(args[8]);
		MongoCollection<Document> collection = null;
		
		
		USchemaBrowser ub = new USchemaBrowser(uschemaModel, trace2);
		// documentAPI contains the code to entityType and relationshipType to document
		DocumentAPI docAPI = new DocumentAPI(trace2,ub);
		DocumentBrowser db = new DocumentBrowser(trace2);
		
		//Guardar tiempos en un archivo
		BufferedWriter writer = null;
		try {

            writer = new BufferedWriter(new FileWriter("Migration_times.txt"));
		  } catch (Exception e) {
	            e.printStackTrace();
	        }
            //variables para tiempos en milisegundos
            long inicioTotal=0;
            long inicio=0;
            long finTotal=0;
            long fin=0;
            
            inicioTotal = System.nanoTime();
            try {
				writer.write("***Entities***");
				writer.newLine();
			} catch (Exception e) { e.printStackTrace();}
			
		// for each entity
		for (DocumentType entity : documentModel.getDocuments()) {
			inicio = System.nanoTime();
System.out.println("===entidades===");	
			Object targetInstance = trace2.getSourceInstance(entity.getName()).get(0);
			
			if (targetInstance instanceof EntityType) {
				int numInstances=0;		
				database.createCollection(entity.getName());
				// get collection
				collection = database.getCollection(entity.getName());
System.out.println("*****Colección:"+entity.getName()+"*****");
				// get uschema entity and cursor access
				EntityType e = (EntityType) trace2.getSourceInstance(entity.getName()).get(0);
System.out.println("Leyendo datos de la entidad de USchema:"+e.getName());
				// read de entity's rows
				USchemaCursor cursor = uadapter.readEntityAll(e);
//NUEVO: Obtener previamente todos los datos de la entidad de Documentos
				List<Attribute> keys=null;
				if (cursor.hasData() && cursor.getID()!=null) { //sólo si tiene key en uschema la entidad
					keys= cursor.getID().getAttributes();
				}
				List<Field> attributes = db.getAttributes(entity.getName(), false);
				List<Attribute> uSchemaAttributes = new ArrayList<Attribute>();
				for (Field docAtt : attributes) {
					Attribute att = (Attribute) trace2.getSourceInstance(entity.getName() + "." + docAtt.getName()).get(0);
					uSchemaAttributes.add(att);
				}
				List<documentschema.Reference> references = db.getReferences(entity.getName(), false);
				List<Embedded> aggregates = db.getAggregates(entity.getName(), false);
				int BATCH_SIZE=1000;
				
				List<Document> batch = new ArrayList<>(BATCH_SIZE);
				while (cursor.hasData()) {
					// return a document with one parsed row
					Document document = docAPI.EntityType2Document(cursor, e.getName(), keys, uSchemaAttributes, references, aggregates, false);
					batch.add(document);
					if (batch.size() == BATCH_SIZE) {
						numInstances=numInstances+batch.size();
				        collection.insertMany(batch);
				        batch.clear();
				    }
				    cursor.next();
				    
				}
				// insertar los documentos restantes
				if (!batch.isEmpty()) {
					numInstances=numInstances+batch.size();
				    collection.insertMany(batch);
				}
				fin=System.nanoTime();
				cursor.close();
				System.out.println(numInstances+" instances writed");
				try {
				   writer.write(entity.getName()+", "+numInstances+" instances, " + calculaTiempo(inicio,fin));
				   writer.newLine();
				} catch (Exception e2) { e2.printStackTrace();}
			}
		}

System.out.println("===relationship types===");
		try {
		   writer.write("***Relationships***");
		   writer.newLine();
		} catch (Exception e) { e.printStackTrace();}
		// relationshipType, similar to the last forEach
		for (DocumentType entity : documentModel.getDocuments()) {
			
			Object targetInstance = trace2.getSourceInstance(entity.getName()).get(0);
			if (targetInstance instanceof RelationshipType)
			{
				inicio = System.nanoTime();
System.out.println("Relationship: "+entity.getName());
				database.createCollection(entity.getName());
				// get collection
				collection = database.getCollection(entity.getName());
				// get the fks and the attributes
				int numInstances=docAPI.RelationshipType2Document(entity.getName(), uadapter, collection);
				fin=System.nanoTime();				
				System.out.println(numInstances+" instances writed");
				try {
				   writer.write(entity.getName()+", "+numInstances+" instances, " + calculaTiempo(inicio,fin));
				   writer.newLine();
				} catch (Exception e) { e.printStackTrace();}
			}
			
		}
		finTotal=System.nanoTime();
		try {
		   writer.newLine();
		   writer.write("Total time: "+ calculaTiempo(inicioTotal,finTotal));
		   writer.newLine();
		   writer.close();
		} catch (Exception e) { e.printStackTrace();}
		
		mongoClient.close();
		uadapter.closeConnection();

	}
	private static String calculaTiempo(long ini, long fin) {
		long duracionNano = fin - ini;
		long milisegundos = duracionNano / 1_000_000;
		long horas = milisegundos / 3_600_000;
		long minutos = (milisegundos % 3_600_000) / 60_000;
		long segundos = (milisegundos % 60_000) / 1_000;
		//long ms = milisegundos % 1_000;
        //String tiempo = String.format("%02d:%02d:%02d.%03d", horas, minutos, segundos, ms);
		String tiempo = String.format("%02d:%02d:%02d", horas, minutos, segundos);
		return tiempo;
	}
	
}
