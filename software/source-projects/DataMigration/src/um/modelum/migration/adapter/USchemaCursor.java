package um.modelum.migration.adapter;



import uschema.Aggregate;
import uschema.Attribute;
import uschema.Reference;

//Iterate entity values
public interface USchemaCursor {							
	public Object			getAttributeValue(Attribute att);
	public String			getAttributeType(Attribute att);	
	public USchemaID 		getID();							//get the ID values for the current instance of this entity
	
	public String 			getEntityName();
	public USchemaCursor 	getReference(Reference ref); 
	public USchemaCursor	getAggregate(Aggregate agg);
	

	
	public boolean		hasData();
	public void			next();
	public void			close();
	
}
