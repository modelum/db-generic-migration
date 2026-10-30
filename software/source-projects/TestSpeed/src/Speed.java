import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Speed {
	private static Connection connection=null; //conexión a PostgreSQL
	private static int postgresqlPortNumber=5432; //puerto por defecto de postgresql
	private static String postgresqlUser="music";
	private static String postgresqlPassword="music";
	private static Statement stmt=null; //statement para sql
	private static ResultSet rs;		//result set para sql
	private static ResultSetMetaData rsmd; //datos de columnas leidas de la tabla
	private static int numCol; //número de columnas que tiene la tabla
	
	private static final String SERVER_MONGODB =  "mongodb://localhost:27017";
	private static MongoClient mongoClient;
	private static MongoDatabase database;
	private MongoCollection<Document> collection;
	
	private static int BATCH_SIZE=1000;
	
	private static long numInstances=0; //number of instances (records) migrated
	
	
	//variables para tiempos en milisegundos
    private static long inicioTotal=0;
    private static long finTotal=0;

		public static void main(String[] args) {
			File jarFile=null;
			File jarFolder=null;
			try {
				//jarFile = new File(Principal.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
				jarFile = new File(Speed.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			} catch (URISyntaxException e) {e.printStackTrace();}
			System.out.println("El nombre del archivo es:"+jarFile.getName());
			if (args.length!=3) {
				System.out.println("Usage: java -jar "+jarFile.getName()+" BDrelational BDMongo Table_name");
				System.exit(0);
			}
			//obtener la carpeta donde está contenido el jar ejecutable
			//getParentFile obtiene el padre del archivo jar (e.g. la carpeta /home/user)
			jarFolder=jarFile.getParentFile();
			Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);

			

			//Connect to Postgresql database
			//String server, int portNumber, String dbname, String username, String password) {
				try {
					// Load the JDBC driver
					String driverName = "org.postgresql.Driver";
					Class.forName(driverName);				
					//OJO:Postgres no tiene servicio y usa siempre el nombre de la base de datos
					//service = "id de la bd" en postgres
					String url = "jdbc:postgresql://" + "localhost" + ":" + postgresqlPortNumber + "/" + args[0];
System.out.println(url);
System.out.println("usuario:'"+postgresqlUser+"'");
					connection = DriverManager.getConnection(url, postgresqlUser,postgresqlPassword);
				} catch (ClassNotFoundException e) {
					System.out.println(e.toString());// Could not find the database driver
				} catch (SQLException e) {
					System.out.println(e.toString());
					// Could not connect to the database
				}
				System.out.println(connection.toString());

				//Connect to MongoDB databas
				mongoClient = MongoClients.create(SERVER_MONGODB);
				database = mongoClient.getDatabase(args[1]);
				MongoCollection<Document> collection = null;
				
				
				
				//query the postgresql table
				String query="SELECT * FROM "+args[2];
				try { 
					// 1. OBLIGATORIO en Postgres para habilitar FetchSize: desactivar autocommit
				    connection.setAutoCommit(false);
					stmt = connection.createStatement(
					        ResultSet.TYPE_FORWARD_ONLY, 
					        ResultSet.CONCUR_READ_ONLY
					    );
					stmt.setFetchSize(5000); //para que no reviente con tablas de 30.000.000 de registros
					rs = stmt.executeQuery(query);
					rsmd = rs.getMetaData(); //datos de las columnas obtenidas
					numCol = rsmd.getColumnCount();
		            System.out.println("Total de columnas de la tabla '"+args[2]+"': " + numCol);
		            for (int i = 1; i <= numCol; i++) {
		                System.out.print(rsmd.getColumnName(i)+" ");
		            }
		            System.out.println();
		            System.out.println();
				} catch (Exception e) {
					System.out.println(e.toString());
					// Could not connect to the database
				}
				
				//create mongo collection
				database.createCollection(args[2]);
				// get mongo collection
				collection = database.getCollection(args[2]);
				
				List<Document> batch = new ArrayList<>(BATCH_SIZE); //buffer
				
				
				//migrate records from table
				System.out.println("Migrando filas de tabla...");
				inicioTotal = System.nanoTime();
				try { 
					while(rs.next()) { //while cursor has data
						Document document=new Document();
						for (int i = 1; i <= numCol; i++) {
							Object value = rs.getObject(i);
							//convertir tipo de dato
							Object convertedValue=maptodoc(rsmd.getColumnTypeName(i),value);
							document.append(rsmd.getColumnName(i), convertedValue);
						}
						batch.add(document);
						if (batch.size() >= BATCH_SIZE) {
							numInstances=numInstances+batch.size();
					        collection.insertMany(batch);
					        batch.clear();
					    }
			        } //while records
					// insert remaining documents
					if (!batch.isEmpty()) {
						numInstances=numInstances+batch.size();
					    collection.insertMany(batch);
					}
				} catch (SQLException e) {	e.printStackTrace(); }
				finTotal=System.nanoTime();
		
				//--close MongoDB connection
				mongoClient.close();
				
		//---close postgresql connection
		/**
		 * close the connection to the postgresql database
		 */
			try {
				rs.close();
				if (stmt!=null) stmt.close();
				connection.close();
			} catch (SQLException e) {e.printStackTrace();}
			
			//--report
			System.out.println(numInstances+" instances writed");
			System.out.println("Total time: "+ calculaTiempo(inicioTotal,finTotal));
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
		
	public static String maptouschema(String sqlType) {

	        if (sqlType == null) {
	            return "VARCHAR";
	        }

	        String t = sqlType.toUpperCase(Locale.ROOT).trim();

	        // eliminar parámetros
	        int p = t.indexOf('(');
	        if (p >= 0) {
	            t = t.substring(0, p).trim();
	        }

	        // Oracle

	        if (t.equals("VARCHAR2"))
	            return "VARCHAR";

	        if (t.equals("NVARCHAR2"))
	            return "VARCHAR";

	        if (t.equals("VARCHAR"))
	            return "VARCHAR";

	        if (t.equals("CHAR"))
	            return "CHAR";

	        if (t.equals("NCHAR"))
	            return "CHAR";

	        if (t.equals("CLOB"))
	            return "VARCHAR";

	        if (t.equals("NCLOB"))
	            return "VARCHAR";

	        if (t.equals("LONG"))
	            return "VARCHAR";

	        if (t.equals("LONG RAW"))
	            return "VARCHAR";

	        if (t.equals("RAW"))
	            return "VARCHAR";

	        if (t.equals("BLOB"))
	            return "VARCHAR";

	        if (t.equals("XMLTYPE"))
	            return "VARCHAR";

	        if (t.equals("ROWID"))
	            return "VARCHAR";

	        if (t.equals("UROWID"))
	            return "VARCHAR";

	        // Numéricos Oracle

	        if (t.equals("NUMBER"))
	            return "NUMBER";

	        if (t.equals("FLOAT"))
	            return "DECIMAL";

	        if (t.equals("BINARY_FLOAT"))
	            return "DECIMAL";

	        if (t.equals("BINARY_DOUBLE"))
	            return "DECIMAL";

	        // PostgreSQL

	        if (t.equals("INTEGER"))
	            return "INTEGER";

	        if (t.equals("INT"))
	            return "INTEGER";

	        if (t.equals("INT2"))
	            return "INTEGER";

	        if (t.equals("INT4"))
	            return "INTEGER";

	        if (t.equals("INT8"))
	            return "INTEGER";

	        if (t.equals("SMALLINT"))
	            return "INTEGER";

	        if (t.equals("BIGINT"))
	            return "INTEGER";

	        if (t.equals("SERIAL"))
	            return "INTEGER";

	        if (t.equals("BIGSERIAL"))
	            return "INTEGER";

	        if (t.equals("NUMERIC"))
	            return "NUMBER";

	        if (t.equals("DECIMAL"))
	            return "DECIMAL";

	        if (t.equals("REAL"))
	            return "DECIMAL";

	        if (t.equals("DOUBLE"))
	            return "DECIMAL";

	        if (t.equals("DOUBLE PRECISION"))
	            return "DECIMAL";

	        if (t.equals("MONEY"))
	            return "DECIMAL";

	        // Fecha

	        if (t.equals("DATE"))
	            return "DATE";

	        if (t.equals("TIMESTAMP"))
	            return "DATE";

	        if (t.equals("TIMESTAMP WITH TIME ZONE"))
	            return "DATE";

	        if (t.equals("TIMESTAMP WITH LOCAL TIME ZONE"))
	            return "DATE";

	        if (t.equals("TIME"))
	            return "DATE";

	        if (t.equals("TIME WITH TIME ZONE"))
	            return "DATE";

	        if (t.equals("INTERVAL"))
	            return "DATE";

	        // Boolean

	        if (t.equals("BOOLEAN"))
	            return "NUMBER";

	        // Otros PostgreSQL

	        if (t.equals("TEXT"))
	            return "VARCHAR";

	        if (t.equals("JSON"))
	            return "VARCHAR";

	        if (t.equals("JSONB"))
	            return "VARCHAR";

	        if (t.equals("UUID"))
	            return "VARCHAR";

	        if (t.equals("BYTEA"))
	            return "VARCHAR";

	        if (t.equals("CHARACTER"))
	        	return "VARCHAR";
	        
	        // Fallback

	        return "VARCHAR";
	    }
	public static Object maptodoc(String tipo, Object value) {
		
		if (value == null) {
		    return "";
		}
		Object docDt = value.toString(); //por defecto se convierte a String
		
		String uDtUp = tipo.toUpperCase();
		switch (uDtUp) {
			case "INT":
			case "INTEGER":
				docDt = (Integer) value;
				break;
			case "DOUBLE":
			case "DECIMAL":
			case "NUMBER":
				docDt = (Double) value;
				break;
			case "BOOLEAN": 
				docDt = (Boolean) value;
				break;
			case "DATE": 
				docDt = value.toString();
				break;
		}
		return docDt;
	}
		
}
