package um.modelum.migration.adapter;
import java.sql.*;

import um.modelum.migration.Trace;
import uschema.EntityType;
import uschema.SchemaType;

public class USchemaRelAdapter implements USchemaAdapter{

	private Connection connection=null;
	private Trace trace;
	
	public USchemaRelAdapter(Trace t) {
		trace=t;
		
	}
	
	/**
	 * open a connection to an oracle database using the database sid
	 * Can throw an exception if wrong driver or database
	 * @param  server the IP or DNS name of the server
	 * @param portNumer port of the server to connect
	 * @param id the sid of the database
	 * @param username the user
	 * @param password the password to be used
	 * @return an string with the data of the established connection
	 */
	@Override
	public String openConnectionID(String server, int portNumber, String id, String username, String password) {
		try {
			// Load the JDBC driver
			//String driverName = "oracle.jdbc.driver.OracleDriver";
			String driverName = "org.postgresql.Driver";
			Class.forName(driverName);

			// Create a connection to the database
			//String url = "jdbc:oracle:thin:@" + server + ":" + portNumber + ":" + id;
			String url = "jdbc:postgresql://" + server + ":" + portNumber + "/" + id;
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());// Could not find the database driver
		} catch (SQLException e) {
			System.out.println(e.toString());
			// Could not connect to the database
		}
		//		System.out.println(connection.toString());
		return connection.toString();
	}

	/**
	 * open a connection to an oracle database using the database sid and the standard port 1521
	 * Can throw an exception if wrong driver or database
	 * @param  server the IP or DNS name of the server
	 * @param id the sid of the database
	 * @param username the user
	 * @param password the password to be used
	 * @return an string with the data of the established connection
	 */
	@Override
	public String openConnectionID(String server, String id, String username, String password) {
		//return openConnectionID(server,1521,id,username,password); //use standard port for Oracle 1521
		return openConnectionID(server,5432,id,username,password); //use standard port for Postgres 5432
	}

	/**
	 * open a connection to an oracle database using the Oracle Service Name
	 * Can throw an exception if wrong driver or database
	 * @param  server the IP or DNS name of the server
	 * @param portNumer port of the server to connect
	 * @param service the service name
	 * @param username the user
	 * @param password the password to be used
	 * @return an string with the data of the established connection
	 */
	@Override
	public String openConnectionService(String server, int portNumber, String service, String username, String password) {
		try {
			// Load the JDBC driver
			//String driverName = "oracle.jdbc.driver.OracleDriver";
			String driverName = "org.postgresql.Driver";
			Class.forName(driverName);
			
			//String url = "jdbc:oracle:thin:@" + server+ ":" + portNumber+"/"+service;
			
			//OJO:Postgres no tiene servicio y usa siempre el nombre de la base de datos
			//service = "id de la bd" en postgres
			String url = "jdbc:postgresql://" + server + ":" + portNumber + "/" + service;

			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());// Could not find the database driver
		} catch (SQLException e) {
			System.out.println(e.toString());
			// Could not connect to the database
		}
		return connection.toString();
	}

	/**
	 * open a connection to an oracle database using the Oracle Service Name and the standard port 1521
	 * Can throw an exception if wrong driver or database
	 * @param  server the IP or DNS name of the server
	 * @param service the service name
	 * @param username the user
	 * @param password the password to be used
	 * @return an string with the data of the established
	 */
	@Override
	public String openConnectionService(String server, String service, String username, String password) {		
		return openConnectionService(server, 5432, service, username, password);
	}

	/**
	 * close the connection to the oracle database
	 */
	@Override
	public void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Returns a USchemaCursor with all data from an USchema Entity
	 * @param name the name of the USchema entity
	 * @return an USchemaCursor to iterate the instances of the entity
	 */
	@Override
	public USchemaCursor readEntityAll(EntityType st) {
		String tableName=trace.getSourceElement(st.getName()).get(0);
		String query="SELECT * FROM "+tableName;
		return new USchemaRelCursor(connection,st.getName(),query,trace);
	}

}
