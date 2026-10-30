package um.modelum.migration

import uschema.USchema
import documentschema.DocumentSchema
import documentschema.DocumentschemaFactory
import uschema.PList
import uschema.PSet
import uschema.PTuple
import uschema.PMap
import documentschema.Type
import uschema.Key
import java.util.List
import java.util.HashMap
import documentschema.PrimitiveType
import documentschema.DocumentType
import documentschema.Field
import documentschema.DataType
import documentschema.Reference
import documentschema.Array
import uschema.RelationshipType
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import uschema.UschemaPackage
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import um.modelum.migration.Trace
import uschema.EntityType
import documentschema.DocumentschemaPackage
import documentschema.Embedded
import uschema.Attribute
import uschema.Aggregate

class Uschema2Document {
	
	var USchema uSchema;
	var DocumentSchema documentSchema;
	val HashMap<DataType, PrimitiveType> docTypes;
	val HashMap<DataType, Array> docArrayTypes;
	val DocumentschemaFactory dsFactory;
	val Trace trace;
	
	new() {
		this.docTypes = new HashMap
		this.docArrayTypes = new HashMap
		this.dsFactory = DocumentschemaFactory.eINSTANCE
		this.trace = new Trace()
	}
	
	// Creation of the mapping
	def DocumentSchema executeMapping() {
		//esquema
        uSchema2DocumentSchema() // R1
		
        //entidades root
		uSchema.entities.filter[ e | e.root ].forEach[ e | entityType2DocumentType(e) ] // R2 
        //atributos y claves de entidades root
		uSchema.entities.filter[ e | e.root ].forEach[ e |
			val DocumentType d = trace.getTargetInstance(e.name).head as DocumentType 
			
			e.features.filter[ f | f.isAttributeNotRef].forEach[ at |	//don't transform attributes from references
				attribute2Field(at as uschema.Attribute, d) // R3 
			]
			e.features.filter[ f | f instanceof uschema.Key 
									&& (f as uschema.Key).isID && (f as uschema.Key).attributes.size > 1 //only applies to keyIDs (primary keys) with 2 or more attributes
			].forEach[ k |
				compositeKey2Field(k as uschema.Key, d) // R4 
			]
		]
        //agregados y referencias dentro de entidades root
		uSchema.entities.filter[ e | e.root ].forEach[ e |
			val d = trace.getTargetInstance(e.name).head as DocumentType
			
			e.features.filter[ f | f instanceof uschema.Reference ].forEach[ rf |
				reference2Reference(rf as uschema.Reference, d) // R5
			]
			
			e.features.filter[ f | f instanceof uschema.Aggregate ].forEach[ ag |
				aggregate2Embedded(ag as uschema.Aggregate, d) // R6
			]
		]
		
        //relationships
		uSchema.relationships.forEach[ rt | 
			relationshipType2DocumentType(rt) // R7
		]
		
		trace.printDirectTraceTypes
		
		return documentSchema
	}
	
	// filter attributes from references and primary keys (unique keys are allowed)
	def boolean isAttributeNotRef(uschema.Feature f){ 
		if (!(f instanceof uschema.Attribute)) 	return false
		var att= f as uschema.Attribute
		if (att.references === null || att.references.empty) return true
		return false
	}

	// R1: USchema to DocumentSchema
	def void uSchema2DocumentSchema() {
		documentSchema = dsFactory.createDocumentSchema
		createPrimitiveTypes()
		createArrays()
		
		documentSchema.name = uSchema.name
		
		trace.addTrace(uSchema.name, uSchema, documentSchema.name, documentSchema)
	}
	
	// R2
	def void entityType2DocumentType(EntityType e) { 
		val d = dsFactory.createDocumentType 	   
		
		d.name = e.name
		documentSchema.documents.add(d)
//System.out.println("Creado document:"+d.name);		
		trace.addTrace(e.name, e, d.name, d)
	}

	// R3: Attribute to Field from DocumentType	
	def void attribute2Field(Attribute f_at, DocumentType d) { 
		val p_at = dsFactory.createField 
		
		p_at.name = f_at.name
		p_at.type = datatype2Type(f_at.type) // p_at.type = f_at.type (R8)
		d.properties.add(p_at)
		
		if (f_at.key !== null && f_at.key.attributes.size == 1 && f_at.key.isID) {
			p_at.name = p_at.name
			p_at.isKey = true
		}
		
		trace.addTrace(f_at.owner.name+"."+f_at.name, f_at, d.name+"."+p_at.name, p_at)
	}
	
	// R3: Attribute to Field from Embedded 
	def void attribute2Field(Attribute f_at, Embedded ag) {
		val p_at = dsFactory.createField
		
		p_at.name = f_at.name
		p_at.type = datatype2Type(f_at.type) // p_at.type = f_at.type
		ag.aggregates.add(p_at)
		
		// (r4)
		if (f_at.key !== null && f_at.key.attributes.size == 1 && f_at.key.isID) {
			p_at.name = p_at.name //<- No tiene sentido esto
			p_at.isKey = true
		}
		
		var String p_atTraceName = getAggregateRecursiveTraceName(ag)+ "." + p_at.name
		
		trace.addTrace(f_at.owner.name+"."+f_at.name, f_at, p_atTraceName, p_at)
	}
	
	// Create an field in the document entity owner (DocumentType or Embedded). 
	// Create a type from an uschema dt datatype 
	// It does not register transformation in the trace. It must be done in the calling method
	def Field createField(String attName, Object owner, Type t) { 
		val p_at = dsFactory.createField 
		p_at.name = attName
		p_at.type = t
		if (owner instanceof Embedded) {
			(owner as Embedded).aggregates.add(p_at) 
		} else {
			(owner as DocumentType).properties.add(p_at) 
		}
		return p_at
	}
		
	// R4: Key (isKey=true) to Field from DocumentType 
	def void compositeKey2Field(Key f_key, DocumentType d) { 
		var Field att
		att=createField(d.name + "_id",d,docTypes.get(DataType::STRING))
		att.isKey = true
		
		trace.addTrace(f_key.owner.name+"."+f_key.name, f_key, d.name+"."+att.name, att)
	}
	
	// R4: Key (isKey=true) to Field from Embedded 
	def void compositeKey2Field(Key f_key, Embedded ag) { 
		var Field att
		
		att=createField(ag.name + "_id",ag,docTypes.get(DataType::STRING))
		 
		att.isKey = true
			
		var String p_atTraceName = getAggregateRecursiveTraceName(ag) + "." + att.name 
		trace.addTrace(f_key.owner.name+"."+f_key.name, f_key, p_atTraceName, att)
	}
	
	// R5: uschema.Reference to Reference from DocumentType 
	def void reference2Reference(uschema.Reference f_ref, DocumentType d) { 
		if (f_ref.isFeaturedBy !== null)
			return 
			
		val Reference p_ref = dsFactory.createReference
		val targets = trace.getTargetInstance(f_ref.refsTo.name)
		if (targets === null || targets.empty) {
		    throw new IllegalStateException(
		        "No hay DocumentType en la traza para refsTo='" + f_ref.refsTo.name +
		        "' al mapear " + f_ref.owner.name + "." + f_ref.name
		    )
		}
		val target = targets.head as DocumentType
		var PrimitiveType primitiveType
		
		if (f_ref.attributes.size > 1 || f_ref.attributes.empty) {
		  primitiveType = docTypes.get(DataType::STRING)
		} else {
		  primitiveType = datatype2Primitive(f_ref.attributes.head.type)
		}	
		p_ref.name = f_ref.name
		p_ref.target = target
		
		// Reference.type
		if (f_ref.upperBound == 1) {
			p_ref.type = primitiveType
		} else if (f_ref.upperBound == -1 || f_ref.upperBound > 1) {
			val Array array = docArrayTypes.get(primitiveType.datatype)
			p_ref.type = array
		} 
		
		d.properties.add(p_ref)
		
		trace.addTrace(f_ref.owner.name+"."+f_ref.name, f_ref, d.name+"."+p_ref.name, p_ref) 
	}
	
	// R5: uschema.Reference to Reference from Embedded
	def void reference2Reference(uschema.Reference f_ref, Embedded ag) {  
		if (f_ref.isFeaturedBy !== null)
			return
		
		val Reference p_ref = dsFactory.createReference
		val target = trace.getTargetInstance(f_ref.refsTo.name).head as DocumentType 
		var PrimitiveType primitiveType
		//val PrimitiveType primitiveType = findAttributeKey(target).type as PrimitiveType

		if (f_ref.attributes.size > 1 || f_ref.attributes.empty) {
		  primitiveType = docTypes.get(DataType::STRING)
		} else {
		  primitiveType = datatype2Primitive(f_ref.attributes.head.type)
		}	
		p_ref.name = f_ref.name
		p_ref.target = target
		
		// Reference.type
		if (f_ref.upperBound == 1) {
			p_ref.type = primitiveType
		} else if (f_ref.upperBound == -1 || f_ref.upperBound > 1) {
			val Array array = docArrayTypes.get(primitiveType.datatype)
			p_ref.type = array
		}
		
		ag.aggregates.add(p_ref)
		
		val String p_refTraceName = getAggregateRecursiveTraceName(ag)+ "." + p_ref.name
		
		trace.addTrace(f_ref.owner.name+"."+f_ref.name, f_ref, p_refTraceName, p_ref)
	}
	

	// R6: Aggregate to Embedded from DocumentType 
	def void aggregate2Embedded(Aggregate f_ag, DocumentType d) { 
		val Embedded p_ag = dsFactory.createEmbedded 
		val uschema.EntityType uet = f_ag.specifiedBy as uschema.EntityType
		
		d.properties.add(p_ag)
		p_ag.name = f_ag.name
		if (f_ag.upperBound > 1 || f_ag.upperBound == -1) {
			p_ag.isMany = true
		} else if (f_ag.upperBound == 1) {
			p_ag.isMany = false
		}
		
		trace.addTrace(f_ag.owner.name+"."+f_ag.name, f_ag, d.name+"."+p_ag.name, p_ag)
		
		// EntityType non-root features
		features2Properties(uet, p_ag)
	}
	
	// R6: Aggregate to Embedded from Embedded 
	def void aggregate2Embedded(Aggregate f_ag, Embedded ag) {
		val Embedded p_ag = dsFactory.createEmbedded 
		val uet = f_ag.specifiedBy as EntityType
		
		ag.aggregates.add(p_ag)
		p_ag.name = f_ag.name
		if (f_ag.upperBound > 1 || f_ag.upperBound == -1) {
			p_ag.isMany = true
		} else if (f_ag.upperBound == 1) {
			p_ag.isMany = false
		}
		
		val String p_agTraceName = getAggregateRecursiveTraceName(p_ag)
		
		trace.addTrace(f_ag.owner.name+"."+f_ag.name, f_ag, p_agTraceName, p_ag)
		
		// EntityType non-root features
		features2Properties(uet, p_ag)
	}

	// R7: RelationshipType to DocumentType 
	def void relationshipType2DocumentType(RelationshipType rt) { 
//System.out.println("Relationship: "+rt.name);
		val c = dsFactory.createDocumentType 
		
		// New DocumentType from RelationshipType 
		c.name = rt.name
		documentSchema.documents.add(c) 
		
		trace.addTrace(rt.name, rt, c.name, c)
//System.out.println("--Atributos");		
		// Map Attributes
		for (f : rt.features) {
//System.out.println("feature:"+f.name);
			switch f {
				uschema.Attribute: attribute2Field(f, c) 
			}
		}
//System.out.println("Creando Id falso 'id'");		
		// New Field (id) 
		val at = dsFactory.createField 
		at.name = c.name
		at.isKey = true
		// at.type = docTypes.get(DataType::STRING)
		at.type = docTypes.get(DataType::INTEGER)
		c.properties.add(at)
		
		trace.addTrace(rt.name, rt, c.name+"."+at.name, at) 
//System.out.println("--mirando referencias");		
		// New References
		// *MJ* rt.references es el opuesto del IsFeaturedBy y pueden ser una referencia (grafos) o dos (relacional).
		for (r : rt.references) { 
//System.out.println("referencia: "+r.name+" owner:"+r.owner.name+ " refsTo:"+r.refsTo.name);
			val Reference rf = dsFactory.createReference
			val refsTo = trace.getTargetInstance(r.refsTo.name).head as DocumentType

			rf.name = r.name
			rf.target = refsTo
			var PrimitiveType primitiveType = (r.attributes.size > 1 || r.attributes.empty) ? docTypes.get(DataType::STRING)
    																						: datatype2Primitive(r.attributes.head.type)
			rf.type = primitiveType
						
			c.properties.add(rf)
			
			trace.addTrace(r.owner.name+"."+r.name, r, c.name+"."+rf.name, rf) 
		}
	}
	
	// R8: Datatype to Type
	def Type datatype2Type(uschema.DataType dt) {
		if(dt instanceof uschema.PrimitiveType)
			return primitiveTypeConversionUsc2Doc(dt)
		else {
			var uschema.PrimitiveType upt
			
			switch dt {	
				PList: {
					upt = dt.elementType as uschema.PrimitiveType
				}
				PSet: {
					upt = dt.elementType as uschema.PrimitiveType
				}
				PTuple: {
					upt = dt.elements.head as uschema.PrimitiveType
				}
				PMap: {
					upt = dt.valueType as uschema.PrimitiveType
				}
			}
			val PrimitiveType pt = primitiveTypeConversionUsc2Doc(upt)
			
			return docArrayTypes.get(pt.datatype)
		}
	}
	
	def PrimitiveType datatype2Primitive(uschema.DataType dt) {
	  if (dt === null) return docTypes.get(DataType::STRING)
	
	  switch dt {
	    uschema.PrimitiveType: return primitiveTypeConversionUsc2Doc(dt as uschema.PrimitiveType)
	    PList:  return datatype2Primitive(dt.elementType)
	    PSet:   return datatype2Primitive(dt.elementType)
	    PTuple: return dt.elements.empty ? docTypes.get(DataType::STRING) : datatype2Primitive(dt.elements.head)
	    PMap:   return datatype2Primitive(dt.valueType)
	    default: return docTypes.get(DataType::STRING)
	  }
	}
	
	// Creates the mapped properties from non-root uschema.EntityType.features and asign them to Embedded 
	def void features2Properties(uschema.EntityType uet, Embedded ag) { 
		for (f : uet.features) {
			switch f {
				uschema.Attribute: f.isAttributeNotRef ? attribute2Field(f, ag) // R3
				uschema.Aggregate: aggregate2Embedded(f, ag) // R6 
			}
		}
		
		for (f: uet.features) {
			switch f {
				uschema.Key: { 
					if (f.isID && f.attributes.size > 1)
						compositeKey2Field(f, ag) // R4 
				}
			}
		}
		
		for (f: uet.features) {
			switch f {
				uschema.Reference: reference2Reference(f, ag) // R5
			}
		}
	}
	
	//    Obtains the recursive name for Aggregate that could be aggregatedBy another Aggregate [ANTONIO] como
	def String getAggregateRecursiveTraceName(Embedded ag) {
		var eb = ag 
		var String docAgTraceName = eb.name
		var boolean exit = false
		
		while (!exit) {
			if (eb.owner !== null) {
				docAgTraceName = eb.owner.name+"."+docAgTraceName
				exit = true
			} else {
				docAgTraceName = eb.aggregatedBy.name+"."+docAgTraceName
				eb = eb.aggregatedBy
			}
		}
		
		return docAgTraceName
	}
	
	def aggregatedBy(Embedded embedded)
	{
		return (embedded.eContainer as Embedded)
	}
	
	def getOwner(Embedded embedded)
	{
		if(embedded.eContainer instanceof DocumentType)
		{
			return (embedded.eContainer as DocumentType)
		}
		return null
	}
	
	// Initialize all PrimitiveType instances for DocumentSchema and add them to the HashMap docTypes
	def createPrimitiveTypes() {
		val string = dsFactory.createPrimitiveType
		val integer = dsFactory.createPrimitiveType
		val doubl = dsFactory.createPrimitiveType
		val bool = dsFactory.createPrimitiveType
		
		string.datatype = DataType::STRING
		integer.datatype = DataType::INTEGER
		doubl.datatype = DataType::DOUBLE
		bool.datatype = DataType::BOOLEAN
		
		documentSchema.types.addAll(List.of(string, integer, doubl, bool))
		
		docTypes.put(string.datatype, string)
		docTypes.put(integer.datatype, integer)
		docTypes.put(doubl.datatype, doubl)
		docTypes.put(bool.datatype, bool)
	}
	
	// Initialize all Array instances for DocumentSchema and add them to the HashMap docArrayTypes
	def createArrays() {
		val string = docTypes.get(DataType::STRING)
		val integer = docTypes.get(DataType::INTEGER)
		val doubl = docTypes.get(DataType::DOUBLE)
		val bool = docTypes.get(DataType::BOOLEAN)
		
		val arrayString = dsFactory.createArray
		val arrayInteger = dsFactory.createArray
		val arrayDouble = dsFactory.createArray
		val arrayBoolean = dsFactory.createArray
		
		arrayString.type = string
		arrayInteger.type = integer
		arrayDouble.type = doubl
		arrayBoolean.type = bool
		
		documentSchema.types.addAll(List.of(arrayString, arrayInteger, arrayDouble, arrayBoolean))
		
		docArrayTypes.put(string.datatype, arrayString)
		docArrayTypes.put(integer.datatype, arrayInteger)
		docArrayTypes.put(doubl.datatype, arrayDouble)
		docArrayTypes.put(bool.datatype, arrayBoolean)
	}
	
	// Types conversion from uschema.PrimitiveType to PrimitiveType
	def PrimitiveType primitiveTypeConversionUsc2Doc(uschema.PrimitiveType uDt) {
		
		if (uDt === null || uDt.name === null) {
		    return docTypes.get(DataType::STRING)
		}
		var DataType docDt = DataType::STRING;
		
		val String uDtUp = uDt.name.toUpperCase
		switch uDtUp {
			case "STRING": docDt = DataType::STRING
			case "INT": docDt = DataType::INTEGER
			case "DOUBLE": docDt = DataType::DOUBLE
			case "BOOLEAN": docDt = DataType::BOOLEAN
			case "DATE": docDt = DataType::STRING
		}
		
		return docTypes.get(docDt)
	}
	
	// Find the Attribute that has isKey==true from an DocumentType 
	def Field findFieldKey(DocumentType et) { 
		return et.properties.findFirst[ p |
			p instanceof Field &&
			(p as Field).isIsKey
		] as Field 
	}
	
	// Loads the USchema from the input path
	def Resource loadSchema(String path) {
		var ResourceSet resourceSet
		var Resource uscResource
		var URI uscUri = URI.createFileURI(path)
		
		UschemaPackage.eINSTANCE.eClass()
		
		resourceSet = new ResourceSetImpl()
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl())
		
		uscResource = resourceSet.getResource(uscUri, true)
		
		uSchema = uscResource.contents.head as USchema
		return uscResource
	}
	
	// Saves the DocumentSchema to the output path
	def Resource saveSchema(String output) {
		if (documentSchema !== null) {
			var ResourceSet resourceSet
			var Resource docResource
			var URI docUri = URI.createFileURI(output)
			
			DocumentschemaPackage.eINSTANCE.eClass()
			
			resourceSet = new ResourceSetImpl()
			resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl())
			
			docResource = resourceSet.createResource(docUri)
			docResource.contents.add(documentSchema)
			docResource.save(null)
			return docResource
		}
	}
	
	// Getters
	def USchema getUSchema() {
		return this.uSchema
	}
	def DocumentSchema getDocumentSchema() {
		return this.documentSchema
	}
	def Trace getTrace() {
		return this.trace
	}
	def HashMap<DataType, Array> getDocArrayTypes() {
		return this.docArrayTypes
	}
	
}