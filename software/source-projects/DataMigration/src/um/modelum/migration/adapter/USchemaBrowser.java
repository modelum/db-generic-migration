package um.modelum.migration.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import um.modelum.migration.Trace;
import uschema.Aggregate;
import uschema.Attribute;
import uschema.EntityType;
import uschema.RelationshipType;
import uschema.SchemaType;
import uschema.USchema;
import uschema.Key;
import uschema.Reference;

public class USchemaBrowser {
	private USchema uschema; //USchema model
	private Trace trace;
	public USchemaBrowser(USchema u, Trace t) {
		trace=t;
		uschema=u;
	}
	
	//get all entities
	public List<EntityType> getAllEntities() {
		return uschema.getEntities();
	}
	
	//get all attributes from an entity which are not part of a reference
	public List<Attribute> getAttributes(SchemaType st) {
		//SchemaType st=(SchemaType) trace.getTargetInstanceFromTargetId(schemaTypeName);
		//recupera todos los atributos que no pertenecen a una referencia o auna primary key
		return st.getFeatures().stream().filter(f->f instanceof Attribute && ((Attribute) f).getReferences().size()==0 
				&& (((Attribute) f).getKey()==null || ((Attribute) f).getKey().isIsID()==false))
				.map(f-> (Attribute) f).collect(Collectors.toList());
	}
	
	//get all references from an entity which are not featuredBy a relationship
	public List<Reference> getReferences(SchemaType st) {
			//SchemaType st=(SchemaType) trace.getTargetInstanceFromTargetId(schemaTypeName);
			//recupera todos los atributos que no pertenecen a una referencia o auna primary key

			/*return st.getFeatures().stream().filter(r->r instanceof Reference && ((Reference) r).getIsFeaturedBy()==null) 
					.map(r-> (Reference) r).collect(Collectors.toList());
			*/
			return st.getFeatures().stream().filter(r->r instanceof Reference) 
					.map(r-> (Reference) r).collect(Collectors.toList());
		}
	
	//get all aggregates from an entity (they will need the parent ID to get a complete ID)
	public List<Aggregate> getAggregates(EntityType et) {	
		return et.getFeatures().stream().filter(a->a instanceof Aggregate) 
				.map(a-> (Aggregate) a).collect(Collectors.toList());
	}

	
	//Identico al del uschemaAPI - get the KEY that is ID from an Entity 
	public Key getEntityKeyID(SchemaType type) {
		Optional<Key> keyID=type.getFeatures().stream().filter(f-> f instanceof Key && ((Key) f).isIsID()).map(k->(Key) k).findFirst();
		if (keyID.isPresent()) return (Key) keyID.get();
		else return null;
}
}
