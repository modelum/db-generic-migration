package um.modelum.migration.mapping

import relationalschema.RelationalschemaFactory
import relationalschema.RelationalSchema
import relationalschema.Table
import relationalschema.Column
import relationalschema.FKey
import relationalschema.ReferentialAction
import uschema.EntityType
import uschema.USchema
import uschema.Attribute
import uschema.Reference
import uschema.Aggregate
import uschema.RelationshipType
import uschema.DataType
import uschema.PrimitiveType
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import uschema.UschemaPackage
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import java.util.List
import java.util.ArrayList
import uschema.Key
import um.modelum.migration.Trace
import java.util.Collections

class USchema2Relational {

	val RelationalschemaFactory relationalFactory;
	var USchema uSchema
	var RelationalSchema relationalSchema
	val Trace trace
	List<String> do_not_tableMN

	new() {
		relationalFactory = RelationalschemaFactory.eINSTANCE
		trace = new Trace
		do_not_tableMN=new ArrayList<String>();
	}
	
	def void do_not_tableMN(String ref) {do_not_tableMN.add(ref)}
	
	def RelationalSchema transformacion()
	{
		// R1
		uschema2relational()
		
		// R2
		uSchema.entities.forEach [ e | entityType2Table(e) ]
		
		uSchema.entities.forEach [ e |
	        e.features.filter(typeof(Attribute)).forEach [ attribute2Column(it) ] // R3
	        e.features.filter(typeof(Key)).forEach [ key2key(it) ] // R4
	    ]
		
		uSchema.entities.forEach [ e |
	        e.features.filter(typeof(Reference)).forEach [ reference2fkey(it) ] // R5
	        e.features.filter(typeof(Aggregate)).forEach [ aggregate2weakTable(it) ] // R6
	    ]
		
		uSchema.relationships.forEach [ relationshipType2Table(it) ] // R7
		
		//trace.printDirectTraceTypes
    	relationalSchema
	}

	// R1: USchema to Relational Schema
	def void uschema2relational() {
		relationalSchema = relationalFactory.createRelationalSchema
		relationalSchema.name = uSchema.name
		trace.addTrace(uSchema.name, uSchema, relationalSchema.name, relationalSchema)
	}

	// R2: EntityType to Table
	def Table entityType2Table(EntityType e) {
		if (trace.directTraceString.containsKey(e.name)) { // if EntityType have already mapped, don't create new table 
			return getTable(e.name) 
		}
		val t = relationalFactory.createTable
		t.name = e.name
		relationalSchema.tables.add(t) 
		trace.addTrace(e.name, e, t.name, t)
		return t
	}

	// R3: Attribute to Column
	def Column attribute2Column(Attribute at) {
		if(!at.references.empty && at.key === null) return null // to avoid prints attributes repeatedly (attribute + reference)
			// <-- *MJ* Ok. Si el atributo esta asociado a una reference NO debe ser mapeado hasta
			// que la Reference sea mapeada (R5)
			// ?POR QUE' SE PREGUNTA SI NO ES PARTE DE UNA CLAVE?? --- preguntar[]
			 
		if (trace.directTraceString.containsKey(at.owner.name + "." + at.name)) 
		    // If attribute have already mapped, don't create new column
			return trace.getTargetInstance(at.owner.name + "." + at.name).head as Column
		val Table t = getTable(at.owner.name)
		
		var type = mapType2String(at.type)
		val col = createColumn(at.name, type, getSizeForDatatype(type), true, null) 
		t.columns.add(col)
		trace.addTrace(at.owner.name + "." + at.name, at, col.owner.name + "." + col.name, col)
		return col
	}

	// R4: uschema.Key to relationalschema.Key
	def void key2key(uschema.Key k) {
		val Table t = getTable(k.owner.name)
		val relationalschema.Key pk = relationalFactory.createKey
		pk.constraintname = k.isID ? t.name + "_pk" : k.name + "_ak"
		pk.owner = t // [MJ] hace falta? Abajo se hace t.keys.add(pk) que es el oppositem o no?
		pk.isPK = k.isID
		//k.attributes.forEach[pk.columns.add(attribute2Column(it))]
		k.attributes.forEach[pk.columns.add(trace.getTargetInstance(it.owner.name + "." + it.name).head as Column)]
		/* [MJ] Mejor si se accede a la traza directamente para sacar las columnas map(it) de cada it de k.attributes
		k.attributes.forEach[pk.columns.add(trace.getTargetInstance(it.owner.name + "." + it.name).head as Column)]
		*/
		t.keys.add(pk)
		trace.addTrace(k.owner.name + "." + k.name, k, pk.owner.name + "." + pk.constraintname, pk)
	}

	// R5: Reference to Fkey [+ columns]
	def void reference2fkey(Reference ref) {
		if(ref.isFeaturedBy === null) // Reference no associated to RelationshipType
		{
			val t = getTable(ref.owner.name) 
			val tr = getTable(ref.refsTo.name)
			if(ref.upperBound == 1) // Case 1:1. FKey maintain the direction of the Reference
			{
				buildForeignKey(t, tr, ref, tr.name + "_fk")
			}else
			{
				// Case 1:N. FKey in opposite direction to Reference
				// It only makes sense if each child can bond with only one parent.
				//decision tomada (pendiente de revisar lowerbound o flag targetshared)
				buildForeignKey(tr, t, ref, t.name + "_fk") 
				
				//if(do_not_tableMN.contains(ref.name)) // or ref.lowerBound <=1) //[MJ]********AQUI******************
				//{   // Case 1:N. FKey in opposite direction to Reference
				//   	// It only makes sense if each child can bond with only one parent.
				//	buildForeignKey(tr, t, ref, t.name + "_fk")
				//}
				//else // Case M:N. It should create a new Table with two FKey
				//{
				//	ref2Table(ref)
				//}
			}
		}
	}

	// R6: Aggregate [+ EntityType non root] to WeakTable
	def void aggregate2weakTable(Aggregate ag) {
		val tg = getTable(ag.specifiedBy.name)
		val ts = getTable(ag.owner.name)
		// Create Foreign Key to strong Table 
		val fk = relationalFactory.createFKey
		fk.constraintname = ts.name + "_fk"
		fk.refsTo = ts.keys.findFirst[pk|pk.isPK]
		fk.refsTo.columns.forEach [ column | // FK create for referenced PK's colum 
			//var columna = createColumn(column) //[MJ] Debe cambiarle el nombre a ag.name+"_"+colum.name
			var columna= createColumn(ag.name+"_"+column.name, column.datatype, column.size, column.nullable, column.defaultvalue)
			tg.columns.add(columna)
			fk.columns.add(columna)
			trace.addTrace(ag.owner.name + "." + ag.name, ag, columna.owner.name + "." + columna.name, columna)
		]
		fk.onDelete = ReferentialAction.NO_ACTION
		fk.onUpdate = ReferentialAction.CASCADE
		fk.owner = tg // Antonio add this line [MJ] hace falta?
		tg.fks.add(fk)
		trace.addTrace(ag.owner.name + "." + ag.name, ag, fk.owner.name + "." + fk.constraintname, fk)
		
		/*
		//Create composite Primary Key
		// [MJ] ERROR. No debe crear la Key ni sus columnas: ya existen!
		val k = relationalFactory.createKey // [MJ] QUITAR
		k.constraintname = tg.name + "_pk"  // [MJ] QUITAR
		k.isPK = true                       // [MJ] QUITAR  
		val col = createColumn("id", "NUMERIC", 38, false, "1") // [MJ] QUITAR
		fk.columns.forEach [ column | // Add FKey columns as part of the key // [MJ] QUITAR
			k.columns.add(column)           // [MJ] QUITAR
		]                                   // [MJ] QUITAR
      	k.columns.add(col) // Add discriminator o partial key //[MJ] QUITAR
		tg.keys.add(k)                      // [MJ] QUITAR
		tg.columns.add(col)                 // [MJ] QUITAR
		trace.addTrace(ag.owner.name + "." + ag.name, ag, k.owner.name + "." + k.constraintname, k)  // [MJ] QUITAR
		trace.addTrace(ag.owner.name + "." + ag.name, ag, col.owner.name + "." + col.name, col)      // [MJ] QUITAR
	  */

/*NUEVO - Si la tabla weak no tiene PK la crea. Puede ocurrir si no tiene discriminante*/	 
	    if (tg.keys.findFirst[pk|pk.isPK]==null) {
			val auxk = relationalFactory.createKey
			auxk.constraintname = tg.name + "_pk"
			auxk.isPK=true
			tg.keys.add(auxk)
		}
/*FIN NUEVO */
		val k = tg.keys.findFirst[pk|pk.isPK]

		fk.columns.forEach [ column | // Add FKey columns as part of the key
			k.columns.add(column)
		]
	  
	}

	// R7: RelationshipType to Table (+2FKey + Key)
	def void relationshipType2Table(RelationshipType rt) {
		val t = relationalFactory.createTable
		t.name = rt.name
		trace.addTrace(rt.name, rt, t.name, t)
		rt.features.forEach [ feature |
			if (feature instanceof Attribute) {
				// R3
				t.columns.add(attribute2Column(feature as Attribute))
			}
		]
		
		val ref = rt.references.findFirst[ isFeaturedBy == rt]
		if(ref === null) return
		
		val td = getTable(ref.refsTo.name)
		val to = getTable(ref.owner.name)
		
		val fk1 = newFkTo(td, t, rt, null) //[MJ] destino, origen, relationshiptype, reference_origen_destino
		trace.addTrace(rt.name, rt, fk1.owner.name + "." + fk1.constraintname, fk1)

		val fk2 = newFkTo(to, t, rt, null)
		trace.addTrace(rt.name, rt, fk2.owner.name + "." + fk2.constraintname, fk2)
		
		val pk = makeCompositePkFrom(fk1, fk2, t)
		trace.addTrace(rt.name, rt, pk.owner.name + "." + pk.constraintname, pk)

		relationalSchema.tables.add(t)
	}

	// R8
	def String mapType2String(DataType datatype) {
		if ((datatype as PrimitiveType) !== null) {
			switch ((datatype as PrimitiveType).name.toUpperCase) {
				case "STRING": return "VARCHAR"
				case "INTEGER": return "NUMERIC"
				case "BOOLEAN": return "BOOLEAN"
				case "DOUBLE": return "NUMERIC"
				case "DATE": return "DATE"
				// case "DECIMAL": return "DECIMAL" [MJ] 
			}
		}
	}

	def void imprimirTraza() {
		trace.printDirectTraceTypes
	}

	private def void setFk(FKey fk, Table table, Table join, RelationshipType rt, Reference ref) {
		fk.constraintname = table.name + "_fk"
		fk.refsTo = table.keys.findFirst [ pk | pk.isPK ]
		if (fk.refsTo !== null) {
			fk.refsTo.columns.forEach [ column |
				var columna = createColumn(join.name+"_"+column.name, column.datatype, column.size, column.nullable, column.defaultvalue)
				//var columna = createColumn(column) // [MJ] Crea col pero NO cambia el nombre: machaca o duplica. Debe admitir parametro para el nombre
				fk.columns.add(columna)            // [MJ] columna.name = join.name + "_" + column.name
				fk.owner = join
				join.columns.add(columna)
				rt !== null ? trace.addTrace(rt.name, rt, fk.constraintname + "." + columna.name, columna)
					: trace.addTrace(ref.name, ref, fk.constraintname + "." + columna.name, columna)
			]
		}
	}

	private def void addColumns(FKey fk, relationalschema.Key pk) {
		fk.columns.forEach [ column |
			pk.columns.add(column)
		]
	}

	def int getSizeForDatatype(String datatype) {
		switch (datatype) {
			case "VARCHAR": return 255
			case "NUMERIC": return 38
			case "BOOLEAN": return 1
			case "DATE": return 10
			// case "DECIMAL": return ??
			default: return 0
		}
	}

	def void loadSchema(String path) {
		var ResourceSet resourceSet
		var Resource uschemaResource
		var URI docUri = URI.createFileURI(path)
		UschemaPackage.eINSTANCE.eClass()
		resourceSet = new ResourceSetImpl()
		resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl())
		uschemaResource = resourceSet.getResource(docUri, true)
		uSchema = uschemaResource.contents.head as USchema
	}

	def USchema getUschema() {
		return this.uSchema
	}

	def RelationalSchema getRelationalSchema() {
		return this.relationalSchema
	}
	
	def Column createColumn(String name, String datatype, int size, boolean nullable, String defaultValue)
	{
		val col = relationalFactory.createColumn
		col.name = name
		col.datatype = datatype
	    col.size = size
	    col.nullable = nullable
	    col.defaultvalue = defaultValue
	    return col
	}
	
	def Column createColumn(relationalschema.Column source) // [MJ] <-- ESTO debe admitir un nuevo nombre para la columna creada
	{ // Creates and returns a column identical to the one passed as an argument.
		createColumn(source.name, source.datatype, source.size, source.nullable, source.defaultvalue)
	}
	
	def Column createColumn(Attribute at)// [MJ] <-- ESTO debe admitir un nuevo nombre para la columna creada
	{ // [MJ] Creates and returns a column corresponding to the attribute passed as an argument.
		val dtype = mapType2String(at.type)
		createColumn(at.name, dtype, getSizeForDatatype(dtype), true, null)
	}
	
	//R5. Case 1:1 y 1:N. Reference mapped FKey
	// tr = table with FK 
	def void buildForeignKey(Table tr, Table referenced, Reference ref, String constraintName)
	{
		val FKey fk = relationalFactory.createFKey
		fk.constraintname = constraintName
		fk.owner = tr // [MJ] hace falta? Abajo se hace tr.keys.add(fk) que es el opposite o no?
		fk.refsTo = referenced.keys.findFirst[pk|pk.isPK]
		fk.onDelete = ReferentialAction.NO_ACTION
		fk.onUpdate = ReferentialAction.CASCADE
		
		if (ref.attributes.size != 0 && ref.upperBound==1) {// Reference has associated attributes and ref is 1:1
	        ref.attributes.forEach [ attribute |
	        	val dtype = mapType2String(attribute.type)
	        	val col= createColumn(attribute.name+"_fk", dtype, getSizeForDatatype(dtype), true, null)
	            //val col = createColumn(attribute) // [MJ] Crea col pero NO cambia el nombre: machaca o duplica. Debe admitir parametro para el nombre
	            tr.columns.add(col)               // col.name = attribute.name + "_fk"
	            fk.columns.add(col)
	            trace.addTrace(ref.owner.name + "." + ref.name, ref, col.owner.name + "." + col.name, col)
	        ]
	    } else if (fk.refsTo !== null) {
	        fk.refsTo.columns.forEach [ columna | //[MJ] Traverse columns of primary key of referenced table
	        	val col = createColumn(columna.name+"_fk", columna.datatype, columna.size, columna.nullable, columna.defaultvalue)
	            //val col  = createColumn(columna) // [MJ] Crea col pero NO cambia el nombre: machaca o duplica. Debe admitir parametro para el nombre
	            tr.columns.add(col)                // [MJ] col.name = columna.name + "_fk"
	            fk.columns.add(col)
	            trace.addTrace(ref.owner.name + "." + ref.name, ref, col.owner.name + "." + col.name, col)
	        ]
	    }
		
		tr.fks.add(fk)
		trace.addTrace(ref.owner.name + "." + ref.name, ref, fk.owner.name + "." + fk.constraintname, fk)
	}
	
	//R5. Caso M:N. A reference is mapped to a new Table with two FKeys
	def void ref2Table(Reference ref)
	{
		val t = relationalFactory.createTable
		t.name = ref.owner.name + "_" + ref.refsTo.name
		trace.addTrace(ref.name, ref, t.name, t)
		
		val td = getTable(ref.refsTo.name)
		val to = getTable(ref.owner.name)

		val fk1 = newFkTo(td, t, null, ref) //[MJ] destino, origen, relationshiptype, reference_origen_destino
		trace.addTrace(ref.name, ref, fk1.owner.name + "." + fk1.constraintname, fk1)

		val fk2 = newFkTo(to, t, null, ref)
		trace.addTrace(ref.name, ref, fk2.owner.name + "." + fk2.constraintname, fk2)
		
		val pk = makeCompositePkFrom(fk1, fk2, t)
		trace.addTrace(ref.name, ref, pk.owner.name + "." + pk.constraintname, pk)
		relationalSchema.tables.add(t)
	}
	
	def FKey newFkTo(Table referenced, Table join, RelationshipType rt, Reference ref) {
	    val fk = relationalFactory.createFKey
	    setFk(fk, referenced, join, rt, ref) //SOLUCIONADO:[MJ] Crea cols pero NO cambia el nombre: machaca o duplica. Debe admitir parametro para el nombre                
	    join.fks.add(fk) 
	    fk
	}
	
	def relationalschema.Key makeCompositePkFrom(FKey fkA, FKey fkB, Table join) {
	    val pk = relationalFactory.createKey
	    pk.constraintname = join.name + "_pk"
	    pk.isPK = true
	    addColumns(fkA, pk)
	    addColumns(fkB, pk)
	    join.keys.add(pk)
	    pk
	}
	
	def Table getTable(String name)
	{
		trace.getTargetInstance(name).head as Table
	}

	def USchema readModel(String inputFileName) {
	val rSet = new ResourceSetImpl()
	rSet.getPackageRegistry().put(UschemaPackage.eNS_URI, UschemaPackage.eINSTANCE)
	rSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl())
	//val r = rSet.getResource(URI.createURI(inputFileName), true)
	val uri = 'file:///'+inputFileName
	val r = rSet.getResource(URI.createURI(uri),true)
	return r.getContents().get(0) as USchema
	}

	def writeModel(String outputUri) {
		val reg = Resource.Factory.Registry.INSTANCE;
		val map = reg.getExtensionToFactoryMap();
		map.put("xmi", new XMIResourceFactoryImpl());

		val resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(UschemaPackage.eNS_URI, UschemaPackage.eINSTANCE);

		val resource = resourceSet.createResource(URI.createFileURI(outputUri));
		resource.getContents().add(relationalSchema);

		resource.save(Collections.EMPTY_MAP);
	}
}
