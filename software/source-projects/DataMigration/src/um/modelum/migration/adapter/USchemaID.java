package um.modelum.migration.adapter;

import java.util.ArrayList;
import uschema.Attribute;

/**
 * returns a USchema EntityType identifier (relationships don't have identifier or key) 
 * It has a list of attributes with values and the name of the entity
 * */
public class USchemaID {
	private String entityName; //entidad a la que se refiere esta ID (en rel dónde están esas columnas que hay que consultar)
	private String constraintName; //key/reference constraint name
	private ArrayList<Attribute> attributes=new ArrayList<Attribute>();
	private ArrayList<Object> values=new ArrayList<Object>();
	public USchemaID (String schemaTypeName, String constraintName, ArrayList<Attribute> attributes, ArrayList<Object> values ) {
		this.entityName=schemaTypeName;
		this.constraintName=constraintName;
		this.attributes=attributes;
		this.values=values;
	}
	
	public ArrayList<Attribute> getAttributes() { return attributes; }
	public ArrayList<Object> getValues() { return values; }
	public String getEntityName() { return entityName; }
	public String getConstraintName() {return constraintName;}
	public String printedValue() { //return a string in the form [ att1 = x att2 = y att3 = z ... ]
		String result="[ ";
		for(int i=0;i<attributes.size();i++) {
			result+=attributes.get(i).getName()+" = "+values.get(i)+" ";
		}
		result+="]";
		return result;
	}
	
}
