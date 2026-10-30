package um.modelum.migration.adapter;

import uschema.EntityType;
import uschema.SchemaType;

public interface USchemaAdapter {
	//connect to database using ID of database
	public String openConnectionID(String server,int portNumber, String id, String username, String password);
	public String openConnectionID(String server,String id, String username, String password); //standard port
	//connect to database using name of the service
	public String openConnectionService(String server,int portNumber, String service, String username, String password);
	public String openConnectionService(String server, String service, String username, String password);  //standard port
	
	public void closeConnection();			//close the conection to the database
	
	public USchemaCursor readEntityAll(EntityType st);
}
