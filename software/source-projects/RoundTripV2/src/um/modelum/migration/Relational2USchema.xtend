package um.modelum.migration
/*La version 3 incorpora dos listas defiltros */
import relationalschema.RelationalSchema
import uschema.USchema
import uschema.SchemaType
import uschema.RelationshipType
import uschema.UschemaFactory
import uschema.EntityType
import uschema.Attribute
import relationalschema.Table
import relationalschema.Column
import java.util.List
import uschema.PrimitiveType
import java.util.ArrayList

import um.modelum.migration.Trace

class Relational2USchema {

	Boolean 		no_aggregation=false;	//true=no crear agregados y poner relaciones 1:N en su lugar
	RelationalAPI 	relationalAPI;
	USchemaAPI 		uschemaAPI;
	Trace 			trace; 			//TRAZA
	
	//FK FILTERS
	List<String> do_not_aggregate;	//any FK in this list will not create an aggregate. They will create a one to many reference in the parent(strong) entity
	List<String> do_not_move;		//any FK in this list must stay as reference in its entity (for references one to Many) (don't reverse) 
									//En su lugar la convierte en una referencia 1:1 en la propia entidad en la que está
	
	new(){
		relationalAPI=new RelationalAPI(); 	//crea el api relacional y registra el metamodelo ecore en memoria
		uschemaAPI=new USchemaAPI();		//crea el api uschema y registra su metamodelo ecore en memoria
		trace=new Trace();					//inicializa la traza
		do_not_aggregate=new ArrayList<String>();
		do_not_move=new ArrayList<String>();				
	}
	
	def void filter_do_not_aggregate(String fk) {do_not_aggregate.add(fk)}
	
	def void filter_do_not_move(String fk) {do_not_move.add(fk)}
	
	//open source model and return rootElement of the loaded model
	def RelationalSchema loadSourceModel(String path) { //delega en el relationalAPI
		return relationalAPI.loadSourceModel(path); 	//retorna el root del modelo cargado que es el RelationalSchema
	}

	//create target model and return rootElement of it
	def USchema createTargetModel(String path) { 	//delega en uschemaAPI
		return uschemaAPI.createTargetModel(path)	//retorna el root del modelo creado que es USchema (unico elemento)
	}
	
	// R1 RelationalSchema -> USchema
    def void toUSchema(RelationalSchema rschema, USchema uschema){
    	uschema.name=rschema.name;
        trace.addTrace(rschema.name,rschema,uschema.name,uschema)
    }
 
	// R2 Table -> SchemaType
	def void tableToSchemaType(Table table, USchema uschema ) {
		if (relationalAPI.is_relationship(table)) {
    		var RelationshipType rtype=UschemaFactory.eINSTANCE.createRelationshipType
    		rtype.name=table.name
    		uschema.relationships.add(rtype)
    		
    		trace.addTrace(table.name,table,rtype.name,rtype)
        } 
        else {
    		var EntityType etype=UschemaFactory.eINSTANCE.createEntityType 
    		etype.name=table.name
    		etype.root=true							
    		uschema.entities.add(etype)
    		
    		trace.addTrace(table.name,table,etype.name,etype)
    	}
	} 
	
	// R3 Column -> Attribute 
	def void columnsToAttributes(Table table) {
		val entity = trace.getTargetInstance(table.name).head as SchemaType
		table.columns.forEach[c |
		   if (!relationalAPI.isFkColumn(table, c)) c.columnToAttribute(entity)
		]		
	}
	
	// R4 PK->Key, UK -> Key
	//def void relationalKeysToUSchemaKeys(Table table) {
    //    if (table.keys.size==0) {
    //    	System.out.println("tabla "+table.name+" sin Primary key"); 
    //    	System.exit(1)
    //    }
    //    table.keys.stream.forEach[rkey|rkey.toUSchemaKey]
    //}
	def void relationalKeysToUSchemaKeys(Table table) {
        if (table.keys.size==0) {
        	System.out.println("tabla "+table.name+" sin Primary key"); 	
        } else {
        	table.keys.stream.forEach[rkey|rkey.toUSchemaKey]
        }       	
    }
    //R7 & R5 foreign keys-> references & aggregates
    def void foreignKeysToReferencesAndAggregates(Table table) {
	  val pk = relationalAPI.getTablePK(table)
//System.out.println("FksToReferencesAndAggreg-Tabla:"+table.name);	
	  for (fk : table.fks) {
//System.out.println("FK:"+fk.constraintname);	
	    val aggregate =
	      relationalAPI.is_weakWith(table, fk.refsTo.owner) &&
	      !relationalAPI.tableReferencesToTable(fk.refsTo.owner, table) && !do_not_aggregate.contains(table.name + "." + fk.constraintname)

	    val isOneToOne = relationalAPI.isUnique(fk) 

	    val isInPK = relationalAPI.primaryKeyContainsFK(pk, fk)

	    val oneToOne_or_relationship = isOneToOne || isInPK || pk==null

	    val oneToMany = !isOneToOne
//System.out.println("fk (aggregate:"+aggregate+") oneToOne_or_relationship:"+oneToOne_or_relationship+") oneToMany:"+oneToMany+")");	
	    switch fk {
	      case aggregate                 : fk.to_Aggregate
	      case oneToOne_or_relationship  : fk.to_Reference_OneToOne
	      case oneToMany                 : fk.to_Reference_OneToMany
	      default                        : System.out.println("ERROR en FK")
	    }
	  }
	}
	
	def Attribute columnToAttribute(Column column, SchemaType entity){
       	return column.columnToAttribute(entity, column.name)		
	}
	
	def Attribute columnToAttribute(Column column, SchemaType entity, String attrName){	
		val uSchemaAttr = uschemaAPI.getAttribute(entity, attrName);
		if (uSchemaAttr!==null ) {
       		System.out.println("No se puede crear. Ya existe un atributo "+entity.name+"."+attrName);
       		return uSchemaAttr;
       	}

       	return createAttributeFromColumn(column, entity, attrName)		
	}

	def void toUSchemaKey(relationalschema.Key rkey) {                                                          
		if (relationalAPI.is_relationship(rkey.owner)) return;
		
		var uschema.Key key=UschemaFactory.eINSTANCE.createKey	
		val entity = uschemaAPI.getEntityType(rkey.owner.name)
		
		val isAgg = isAggregable(rkey.owner)
		// Only translate the local key part in weak tables
		val fks = rkey.owner.fks
		val columnsToUse = (isAgg && rkey.isPK) ? rkey.columns.filter[c | !isColumnInFKs(fks, c)] : rkey.columns
		
		for(column:columnsToUse) {	
			if(isColumnInFKs(fks, column))
				column.createAttributeFromColumn(entity, column.name)
	 		key.addKeyAttribute(column.owner.name+"."+column.name)
        }
        
		key.name=rkey.constraintname
        key.isID=rkey.isPK
        key.owner=trace.getTargetInstance(rkey.owner.name).head as SchemaType 
        
        trace.addTrace(rkey.owner.name+"."+rkey.constraintname,rkey,key.owner.name+"."+rkey.constraintname,key)
	}
	

    def void to_Aggregate(relationalschema.FKey fk) {   
    	if(no_aggregation||do_not_aggregate.contains(fk.owner.name+"."+fk.constraintname)) {
    		System.out.println("FILTRO AGREGADO:"+fk.owner.name+"."+fk.constraintname);
    		to_Reference_OneToMany(fk); // not aggregate, 1:M conversion
    		return;
    	}
    	
        var thisEntity=trace.getTargetInstance(fk.owner.name).head as EntityType 
    	var refEntity=trace.getTargetInstance(fk.refsTo.owner.name).head as SchemaType 

		var uschema.Aggregate aggregate=UschemaFactory.eINSTANCE.createAggregate
		aggregate.name=fk.owner.name
		aggregate.lowerBound=0
		//si no existe discriminante, la tabla hija sólo tiene asociada una linea suya con el padre
		if (relationalAPI.isAlsoPK(fk)) {aggregate.upperBound = 1} else {aggregate.upperBound = -1}
		aggregate.specifiedBy=thisEntity as EntityType
		thisEntity.root=false
		refEntity.features.add(aggregate) 

        trace.addTrace(fk.owner.name+"."+fk.constraintname,fk,aggregate.owner.name+"."+aggregate.name,aggregate)		    	  
    }
    
    def void to_Reference_OneToOne(relationalschema.FKey fk){

    	val pk = relationalAPI.getTablePK(fk.owner)
	  	val isRelationship = relationalAPI.is_relationship(fk.owner)
	
	  	val reference = isRelationship ? fk.createRelationshipReference(pk) : fk.createOneToOneReference
        
        trace.addTrace(fk.owner.name+"."+fk.constraintname,fk,reference.owner.name+"."+reference.name,reference)	    	
    }
    
    
    
    //FK to reference 1:N
    def void to_Reference_OneToMany(relationalschema.FKey fk){
  		val ownerSt = trace.getTargetInstance(fk.owner.name).head as SchemaType
		// if owner table is already root=false, dont't move 1:N to parent
    	if(do_not_move.contains(fk.owner.name+"."+fk.constraintname) || (ownerSt instanceof EntityType && !(ownerSt as EntityType).root)) {
    		System.out.println("FILTRO NO MOVER:"+fk.owner.name+"."+fk.constraintname);
    		to_Reference_OneToOne(fk); // not move, 1:1 conversion
    		return;
    	}
    	
    	val et = trace.getTargetInstance(fk.owner.name).head as EntityType
    	val es = trace.getTargetInstance(fk.refsTo.owner.name).head as EntityType
    	val pk_t = relationalAPI.getTablePK(fk.owner)
    	
    	val ref = UschemaFactory.eINSTANCE.createReference
	  	ref.name = et.name.toFirstLower
	  	ref.lowerBound = 0
	  	ref.upperBound = -1
	  	ref.refsTo = et
	  	ref.owner = es
	  	es.features.add(ref)
	  	
	  	trace.addTrace(fk.owner.name+"."+fk.constraintname, fk, es.name+"."+ref.name, ref)
		
		//only add attributes if pk exists and has attributes
		if (pk_t!=null && pk_t.columns!=null) {
			for (c : pk_t.columns) {
    			//val attrName = (es === et) ? c.name : (c.name + "_" + et.name).toFirstLower
				val attrName = (es === et) ? (c.name + "_" + et.name).toFirstLower : c.name
		    	//val att = (es === et) ? (trace.getTargetInstance(fk.owner.name+"."+c.name).head as Attribute) 
		    	//    				  : c.columnToAttribute(es, attrName)   
				val att = c.columnToAttribute(es, attrName)
		    	if (att !== null) {
		      		ref.attributes.add(att)
		    	} else {
		      		System.out.println("WARNING to_Reference_OneToMany: missing attribute for "+es.name+"."+attrName)
		    	}
  			}
  		} //if pk!=null
  			
    }
    
    // Que hacer con los atributos :C
    def uschema.Reference createRelationshipReference(relationalschema.FKey fk, relationalschema.Key pk)
    {
    	var reference = UschemaFactory.eINSTANCE.createReference
	  	val rm = trace.getTargetInstance(fk.owner.name).head as SchemaType   
	  	val et1 = trace.getTargetInstance(fk.refsTo.owner.name).head as EntityType
	  	
	  	reference.name = fk.constraintname
  		reference.refsTo = et1
  		reference.lowerBound = 0
		reference.upperBound = -1
		
		var listFKinPK=relationalAPI.getForeignKeysInPrimaryKey(pk.owner)
    	if (listFKinPK.length!=2) {
    			System.out.println("WARNING to_Reference_OneToOne: relationship table "+rm.name+" doesn't have TWO FKs")
    			System.exit(4)	
    	}
    	
    	var secondFK=listFKinPK.stream.filter[f|!f.equals(fk)].findFirst.get
    	var et2=trace.getTargetInstance(secondFK.refsTo.owner.name).head as EntityType
    	
    	reference.owner = et2
		et2.features.add(reference)
		
		val rmt = rm as RelationshipType
	    if (listFKinPK.indexOf(fk) == 0) rmt.references.add(0, reference)
	    else rmt.references.add(reference)
	    
	    return reference
    }
    
    def uschema.Reference createOneToOneReference(relationalschema.FKey fk) {

	  	// owner SchemaType (EntityType o RelationshipType)
	  	val ownerSt = trace.getTargetInstance(fk.owner.name).head as SchemaType
	  	val targetEt = trace.getTargetInstance(fk.refsTo.owner.name).head as EntityType
	
	  	val reference = UschemaFactory.eINSTANCE.createReference
	  	reference.name = fk.constraintname
	  	reference.refsTo = targetEt
	  	reference.owner = ownerSt
	  	reference.lowerBound = 0
	  	reference.upperBound = 1
	
	  	ownerSt.features.add(reference)
	
	  	for (c : fk.columns) {
	   		val key = fk.owner.name + "." + c.name
	    	val attTargets = trace.getTargetInstance(key)
	
	    	val Attribute att =
	      	if (attTargets !== null && !attTargets.empty)
	        	attTargets.head as Attribute
	      	else
	        	c.columnToAttribute(ownerSt, c.name) 
	
	    	reference.attributes.add(att)
	  	}
	
	  	return reference
	}
    
    
	def USchema transformar(RelationalSchema rschema, USchema uschema) {        
        // R1	
        rschema.toUSchema(uschema)
        // R2/R6 tables -> schemaTypes (EntityType or RelationshipType)       
        rschema.tables.stream.forEach[table|table.tableToSchemaType(uschema)]
		// R3 	columns -> features (Attributes/PrimitiveTypes)
        rschema.tables.stream.forEach[table|table.columnsToAttributes]
        // R4/R5/R7
        rschema.tables.stream.forEach[table|
        	table.relationalKeysToUSchemaKeys			//R4 	relational keys -> uschema keys
        	table.foreignKeysToReferencesAndAggregates	//R7/R5	foreign keys -> references & aggregates
        ]
      
		uschemaAPI.saveModel()
		trace.printFullTrace
		trace.printFullReverseTrace
		
        return uschema
    }
    

    def Trace getTrace() {
    	return trace
    }
    
	def String convertType(String type) {
		switch (type.toLowerCase)
		{
			case "varchar", case "char": return "string"
			case "decimal", case "number", case "numeric": return "double"
			case "date":	return "date"
			case "tinyint", case "int", case "integer": return "int"
			default: System.out.println("*********TIPO DESCONOCIDO:"+type)
		}
		return "string"
	}
	
	/*def String toPlural(String word) {
		var result=word
		var String last=word.charAt(word.length-1).toString
		if (last == "y"){result=result.substring(0,word.length-1)+"ies"; return result}
		if (last == "s"){result=result.substring(0,word.length-1)+"es"; return result}
		result+="s" 
		return result
	}*/
	
	def void removeAttributeFromSchemaType(Attribute att) {
		var thisEntity=att.owner as SchemaType
    	thisEntity.features.remove(att)
    	trace.removeTargetElementFromTrace(thisEntity.name+"."+att.name) //remove attribute from trace
    	var datatype=att.type
    	if (datatype instanceof PrimitiveType)
    		trace.removeTargetElementFromTrace(thisEntity.name+"."+att.name+"."+(datatype as PrimitiveType).name) //remove primitive type
		else {
			System.out.println("removeAttributeFromSchemaType: You can delete trace datatypes because they are not primitives")
			System.exit(4)
		}
	}
	

	def void addKeyAttribute(uschema.Key key, String nameAttr)
	{
		val targets = trace.getTargetInstance(nameAttr)
		key.attributes.add(targets.head as Attribute)
	}
	
	def boolean isAggregable(Table t) {
//System.out.println("¿isAggregable("+t.name+")?")
	    if (!relationalAPI.is_weak(t) || no_aggregation) return false
//System.out.println("Es weak");	
	    val fksInPk = relationalAPI.getForeignKeysInPrimaryKey(t)
	    if (fksInPk === null || fksInPk.empty) return false
//System.out.println("Compruebo si es do_not_aggregate");		
		for(fk : fksInPk)
	    	if (do_not_aggregate.contains(t.name + "." + fk.constraintname)) return false
//System.out.println(":true");	
	    return true
 	}
 	

	def boolean isColumnInFKs(List<relationalschema.FKey> fks, Column c) {
	  return fks.stream.anyMatch[fk | fk.columns.contains(c)]
	}
	
	def boolean isColumnInFK(relationalschema.FKey fk, Column c)
	{
		return fk.columns.contains(c)
	}
	
	def PrimitiveType createPrimitiveType(String relationalDatatype) {
		val pt = UschemaFactory.eINSTANCE.createPrimitiveType
		pt.name = convertType(relationalDatatype)
	    return pt
	}
	
	def Attribute createAttributeFromColumn(Column column, SchemaType entity, String attrName) {
    	var Attribute attribute = UschemaFactory.eINSTANCE.createAttribute
  		attribute.name = attrName
  		attribute.optional = column.nullable

  		val primitiveType = createPrimitiveType(column.datatype)
  		attribute.type = primitiveType

  		entity.features.add(attribute)

  		trace.addTrace(column.owner.name+"."+column.name, column, entity.name+"."+attribute.name, attribute)
  		trace.addTrace(column.owner.name+"."+column.name, column, entity.name+"."+attribute.name+"."+primitiveType.name, primitiveType)

  		return attribute
	}
	

}