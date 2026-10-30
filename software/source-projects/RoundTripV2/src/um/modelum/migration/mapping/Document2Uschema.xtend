package um.modelum.migration.mapping

import documentschema.DocumentSchema

import uschema.USchema
import uschema.PrimitiveType
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import uschema.UschemaFactory
import um.modelum.migration.Trace
import documentschema.Field
import documentschema.DocumentType
import documentschema.Embedded
import documentschema.Property
import documentschema.DocumentschemaPackage
import org.eclipse.emf.ecore.EObject
import uschema.UschemaPackage
import java.util.Collections
import java.util.List

class Document2Uschema {

	var USchema uSchema
	var DocumentSchema documentSchema
	val UschemaFactory uschemaFactory;
	val Trace trace

	new() {
		uschemaFactory = UschemaFactory.eINSTANCE
		trace = new Trace()
	}

	def USchema transformacion() {
		// R1
		documentSchema2USchema()
		// R2
		documentSchema.documents.forEach[e|documentType2entityType(e)] 
		documentSchema.documents.forEach [ e | 
			e.properties.forEach [ p |
				switch p {
					// R3
					Field: field2attribute(p as Field)
					// R4
					documentschema.Reference: reference2reference(p as documentschema.Reference)
				}
			]
		]
		documentSchema.documents.forEach [ e |
			e.properties.forEach [ p |
				if (p instanceof Embedded) 
					// R5
					embedded2entityType(p as Embedded)
			]
		]
		//trace.printDirectTraceTypes
		return uSchema
	}

	// R1: DocumentSchema to USchema 
	def void documentSchema2USchema() {
		uSchema = uschemaFactory.createUSchema
		uSchema.name = documentSchema.name
		trace.addTrace(documentSchema.name, documentSchema, uSchema.name, uSchema)
	}

	// R2: DocumentSchema DocumentType to USchema EntityType 
	def uschema.EntityType documentType2entityType(DocumentType d) { 
		if (trace.directTraceString.containsKey(d.name)) // if Document have already mapped, don't create new EntityType
			return trace.getTargetInstance(d.name).head as uschema.EntityType

		val ed = uschemaFactory.createEntityType
		ed.name = d.name
		ed.root = true
		uSchema.entities.add(ed)
		trace.addTrace(d.name, d, ed.name, ed)
		return ed
	}

	// R3: Field to Attribute or Attribute + Key 
	def void field2attribute(Field f) { 
		val ef = uschemaFactory.createAttribute
		
		val uschema.EntityType ed = trace.getTargetInstance(f.ownerName).head as uschema.EntityType
		ef.name = f.name
		ef.type = type2datatype(f.type)
		if (f.isKey) {
			var k = uschemaFactory.createKey
			k.name = f.name
			k.isID = true
			k.attributes.add(ef)
			ef.key = k
			ed.features.add(k)
			trace.addTrace(f.ownerName + "." + f.name, f, k.owner.name + "." + k.name + ".key", k)
		}
		ed.features.add(ef)
		trace.addTrace(f.ownerName + "." + f.name, f, ef.owner.name + "." + ef.name + ".attribute", ef)
	}

	// R4: Reference to Reference + Attribute
	def void reference2reference(documentschema.Reference r) {
		val er = uschemaFactory.createReference
		val ea = uschemaFactory.createAttribute
		
        if (r.type instanceof documentschema.PrimitiveType) {
        	ea.type = type2datatype(r.type)
        } else {
        	val arrayaux=r.type as documentschema.Array
        	ea.type = type2datatype(arrayaux.type)
        }
        			
        
		
		val uschema.EntityType ed = trace.getTargetInstance(r.ownerName).head as uschema.EntityType
		er.name = r.name
		ea.name = r.name
		er.lowerBound = 1
		er.upperBound = r.type instanceof documentschema.PrimitiveType ? 1 : -1
		er.attributes.add(ea)
		ea.references.add(er)
		er.refsTo = trace.getTargetInstance(r.target.name).head as uschema.EntityType
		ed.features.add(er)
		trace.addTrace(r.ownerName + "." + r.name, r, er.owner.name + "." + er.name, er)
		ed.features.add(ea)
		trace.addTrace(r.ownerName + "." + r.name, r, ea.owner.name + "." + ea.name, ea)
	}

	// R5: Embedded to EntityType + Aggregate 
	def void embedded2entityType(Embedded g) { 
		val uschema.EntityType ed = trace.getTargetInstance(g.ownerName).head as uschema.EntityType
		// Comprobamos si el agregado ya se mapeo
		val eg = trace.directTraceString.containsKey(g.ownerName + "." + g.name) // *MJ* Si el embebido ya ha sido mapeado, no crear nueva EntityType
			? trace.getTargetInstance(g.ownerName + "." + g.name).head as uschema.EntityType
			: uschemaFactory.createEntityType
		eg.name = g.name
		eg.root = false
		if (!trace.directTraceString.containsKey(g.ownerName + "." + g.name)) { //*MJ* Si el embebido no habia sido mapeado ya, annadir nueva EntityType al uSchema
			uSchema.entities.add(eg)
			trace.addTrace(g.ownerName + "." + g.name, g, eg.name, eg)
		}
		
		if (g.aggregates !== null) {
			loadAggregateFeatures(g.aggregates)
		}
		//addAggregateFeatures(g, eg) //*MJ* las properties agregadas por g ya habran sido traducidas o lo seran
		val ag = uschemaFactory.createAggregate
		ag.name = g.name
		ag.lowerBound = 1
		ag.upperBound = g.isMany ? -1 : 1
		ag.specifiedBy = eg
		ed.features.add(ag)
		//trace.addTrace(g.ownerName + "." + g.name, g, ag.owner.name + "." + ag.name + ".aggregate", ag)
		trace.addTrace(g.ownerName + "." + g.name, g, ag.owner.name + "." + ag.name, ag)
	}

	// R6
	def uschema.DataType type2datatype(documentschema.Type type) {
//System.out.println(type);
//System.out.println("-->"+type.getClass().getInterfaces().get(0).simpleName)
		var typename =type.getClass().getInterfaces().get(0).simpleName
	/* 
		switch type {
			//cambio el documentschema.PrimitiveType y documentschema.Array por:
			case documentschema.PrimitiveType: return createPrimitiveType(type as documentschema.PrimitiveType)
			case documentschema.Array: return createArrayType(type as documentschema.Array)
		}
		*/
		switch typename {
			//cambio el documentschema.PrimitiveType y documentschema.Array por:
			case "PrimitiveType": return createPrimitiveType(type as documentschema.PrimitiveType)
			case "Array": return createArrayType(type as documentschema.Array)
		}
	}
	
	def void loadAggregateFeatures(List<Property> properties)
	{
		for (prop : properties) {
		    switch prop {
		      Field: {
		        val key = prop.ownerName + "." + prop.name
		        if (!trace.directTraceString.containsKey(key)) {
		          field2attribute(prop as Field)
		        }
		      }
		      documentschema.Reference: {
		        val key = prop.ownerName + "." + prop.name
		        if (!trace.directTraceString.containsKey(key)) {
		          reference2reference(prop as documentschema.Reference)
		        }
		      }
		      Embedded: {
		        embedded2entityType(prop as Embedded)
		      }
		    }
		}
	}
	
	// Forma parte de R5 Embedded to EntityType + Aggregate
	private def void addAggregateFeatures(Embedded g, uschema.EntityType eg) { 
		if (g.aggregates === null) return;
		for (prop : g.aggregates) {
		    switch prop {
		      Field: {
		        val key = prop.ownerName + "." + prop.name
		        val atributo = trace.getTargetInstance(key).findFirst[it instanceof uschema.Attribute] as uschema.Attribute
		        if (atributo !== null) eg.features.add(atributo)
		      }
		      documentschema.Reference: {
		        val key = prop.ownerName + "." + prop.name
		        val referencia = trace.getTargetInstance(key).findFirst[it instanceof uschema.Reference] as uschema.Reference
		        if (referencia !== null) eg.features.add(referencia)
		      }
		      Embedded: {
		        embedded2entityType(prop as Embedded)
		      }
		   	}
	  	}
	}

	private def uschema.PrimitiveType createPrimitiveType(documentschema.PrimitiveType type) {
		val et = uschemaFactory.createPrimitiveType
		et.name = mapPrimitiveTypeName(type.datatype)
		trace.addTrace(type.datatype.getName, type, et.name, et)
		return et
	}

	private def uschema.PList createArrayType(documentschema.Array type) {
		val plist = uschemaFactory.createPList
		plist.elementType = type2datatype(type.type)
		val primitivo = plist.elementType as PrimitiveType
		trace.addTrace(type.type.datatype.getName, type, primitivo.name, plist)
		return plist
	}

	def private String mapPrimitiveTypeName(documentschema.DataType datatype) {
//System.out.println("Tipo primmitivo->"+datatype)
		switch (datatype) {			
			case documentschema.DataType::BOOLEAN: return "BOOLEAN"
			case documentschema.DataType::INTEGER: return "INTEGER"
			case documentschema.DataType::DOUBLE: return "DOUBLE"
			case documentschema.DataType::STRING: return "STRING"
			
		}
	}

	def void loadSchema(String path) {
		var ResourceSet resourceSet
		var Resource docResource
		var URI docUri = URI.createFileURI(path)
		DocumentschemaPackage.eINSTANCE.eClass()
		resourceSet = new ResourceSetImpl()
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl())
		docResource = resourceSet.getResource(docUri, true)
		documentSchema = docResource.contents.head as DocumentSchema
	}
	
	def void saveSchema(String path)
	{
		val reg = Resource.Factory.Registry.INSTANCE;
		val map = reg.getExtensionToFactoryMap();
		map.put("xmi", new XMIResourceFactoryImpl());

		val resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(UschemaPackage.eNS_URI, UschemaPackage.eINSTANCE);

		val resource = resourceSet.createResource(URI.createFileURI(path));
		resource.getContents().add(uSchema);

		resource.save(Collections.EMPTY_MAP);
	}
	
	def String getOwnerName(EObject obj) {
	  val parent = obj.eContainer
	  switch parent {
	    Embedded: (parent as Embedded).ownerName + "." + (parent as Embedded).name
	    DocumentType: (parent as DocumentType).name
	    default: null
	  }
	}

	def void imprimirTraza() {
		trace.printDirectTraceTypes
	}

	def USchema getUSchema() {
		return this.uSchema
	}

	def DocumentSchema getDocumentSchema() {
		return this.documentSchema
	}
}
