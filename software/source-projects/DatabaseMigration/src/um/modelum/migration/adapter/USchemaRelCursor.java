package um.modelum.migration.adapter;

/*
 * All attributes of an entity comes from the same table in relational
 * except those that are part of a reference of the entity (fk), which could
 * come from other table
 * This class assume you only call attributes which are not part of a reference
 */

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import relationalschema.Column;
import relationalschema.FKey;
import relationalschema.Table;
import um.modelum.migration.Trace;
import uschema.Aggregate;
import uschema.Attribute;
import uschema.EntityType;
import uschema.Key;
import uschema.Reference;
import uschema.RelationshipType;
import uschema.SchemaType;

public class USchemaRelCursor implements USchemaCursor{

	private Connection connection=null;
	private SchemaType schemaType; //schematype que se está consultando
	private String tableName;
	private Key	key;				//primary key de la entity (uschema)
	private String keyConstraintName; //constraint de la key de la tabla
	private ArrayList<String> keyColumns;//nombre de las columnas que forman parte de la key en la tabla
	private Trace trace;
	private Statement stmt=null;
	private PreparedStatement pstmt=null;
	private ResultSet rs;
	boolean hasData=false;
	
	private boolean withoutID=false; //indica que se está recorriendo una tabla sin PK
	private ArrayList<String> alternativeKeyColumns; //columnas a utilizar como ID en lugar de las de la PK (ya que no tiene PK)
	
	
	//sql conexion, entity name, trace
	public USchemaRelCursor (Connection con, String name, String query, Trace t) {
		connection=con;
		trace=t;
		tableName=trace.getSourceElement(name).get(0); //obtiene el nombre de la tabla de la traza
		try {
			schemaType=(SchemaType) t.getTargetInstanceFromTargetId(name); //recupera la uschema entity
			this.getEntityKeyID(schemaType); 
			stmt = connection.createStatement();
			//rs = stmt.executeQuery("SELECT * FROM "+tableName);
			rs = stmt.executeQuery(query);
		} catch (Exception e) {
			System.out.println(e.toString());
			// Could not connect to the database
		}

		//The cursor is before the first record
		//Go to the first record (if any)
		try { hasData=rs.next(); } catch (SQLException e) {	e.printStackTrace(); }
	}

	//Constructor variation -> query with preparestatement
	//sql conexion, entity name, values to include in preparedStatement, trace
	public USchemaRelCursor (Connection con, String name, String query, List<Object> values, Trace t) {
//System.out.println("Se crea un cursor para la entidad "+name);
//System.out.print("que corresponde a la tabla ");
		connection=con;
		trace=t;
		tableName=trace.getSourceElement(name).get(0); //obtiene el nombre de la tabla de la traza
//PEP
//System.out.println("tabla "+tableName);
		try {
			schemaType=(SchemaType) t.getTargetInstanceFromTargetId(name); //recupera la uschema entity
			this.getEntityKeyID(schemaType); 
			pstmt = connection.prepareStatement(query);
			int index=1;
			for (Object obj:values) {
				if (obj != null) { //if value is null is not necessary to prepare value for a ? (IS NULL is used instead)
					pstmt.setObject(index,obj);
					index++;
				}
			}
			//rs = stmt.executeQuery("SELECT * FROM "+tableName);
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			System.out.println(e.toString());
			// Could not connect to the database
		}

		//The cursor is before the first record
		//Go to the first record (if any)
		try { hasData=rs.next(); } catch (SQLException e) {	e.printStackTrace(); }
	}
	
	
	
	//nombre de la tabla que se está recorriendo
	public String getEntityName() {return schemaType.getName();}
	
	/**
	 * return substring after the last '.' (or the whole string if none)
	 * @param ele the string with fields ej: 'user.name'
	 * @return the last field. ej:'name'
	 */
	public String lastField(String ele) {
		int pointPos=ele.lastIndexOf('.');
		return ele.substring(pointPos+1);
	}
	
	@Override
	public Object getAttributeValue(Attribute att) {
		Object result=null;
		//En la traza, el primer objeto es siemprela columna de la que viene el atributo. (al no ser FK está en la misma tabla)
		String columna=trace.getSourceElement(att.getOwner().getName()+"."+att.getName()).get(0);
		try { result = rs.getObject(lastField(columna));
		} catch (SQLException e) { e.printStackTrace();	}
		return result;
	}
	
	@Override
	public String getAttributeType(Attribute att) {
		//En la traza, el primer objeto es siemprela columna de la que viene el atributo. (al no ser FK está en la misma tabla)
		String columna=trace.getSourceElement(att.getOwner().getName()+"."+att.getName()).get(0);
		Object result=null;
		try { result = rs.getObject(lastField(columna));
			if (rs.wasNull()) return "null";
		} catch (SQLException e) { e.printStackTrace();	}
		return result.getClass().getName();
	}
	
	//get the ID values for the current instance of this entity
	public USchemaID getID() {
		USchemaID result;
		if (withoutID) { //usar columnas alternativas para valores de ID en tablas sin PK (normalmente valores de columnas de FK)
			ArrayList<Object> values=new ArrayList<Object>();
			try { 
				for (String c:alternativeKeyColumns) {
					Object v = rs.getObject(c); //obtiene los valores concretos de las columnas consultando el cursor
					values.add(v);		
				}
			} catch (SQLException e) { e.printStackTrace();	}
			//devuelve alternative ID (sin atributos en uschema, ya que no hay Key)
			result=new USchemaID (schemaType.getName(), "AlternativeKey."+schemaType.getName(), new ArrayList<Attribute> (), values);
			return result;
		}
		if (key==null) { //tabla sin PK y no se ha especificado que usar como ID
			System.out.println("Warning: getID - "+schemaType.getName()+ " doesn't have an ID (if it is a Relationships it's an error");
			return null;
			//finish();
		}
		//System.out.println("getID()-entidad:"+schemaType.getName());
		//System.out.println("Key columns:");
		//for(String c:keyColumns) System.out.println(c);
		
		ArrayList<Object> values=new ArrayList<Object>();
		try { 
			for (String c:keyColumns) {
				Object v = rs.getObject(c); //obtiene los valores concretos de las columnas consultando el cursor
				values.add(v);		
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
		result=new USchemaID (schemaType.getName(), key.getName(), new ArrayList<Attribute> (key.getAttributes()), values);	
		return result;
	}
	
	
	//Identico al del uschemaAPI - get the KEY that is ID from an Entity
	//null if it is a relationship
	private void getEntityKeyID(SchemaType type) {
		Optional<Key> keyID=type.getFeatures().stream().filter(f-> f instanceof Key && ((Key) f).isIsID()).map(k->(Key) k).findFirst();
		if (keyID.isPresent()) key= keyID.get();
		else key= null;
		
		if (key!=null) {
			//System.out.println("Veo que la key "+key.getName()+" tiene "+key.getAttributes().size()+ " atributos pero ");
			//obtener los atributos de la key
			//List<String> keyAtt=new ArrayList<String>(key.getAttributes().stream().map(a->a.getName()).collect(Collectors.toList()));
			//obtener las columnas de la tabla correspondientes a esos atributos
			keyColumns=new ArrayList<String>(key.getAttributes().stream()
					.map(a -> lastField(trace.getSourceElement(a.getOwner().getName()+"."+a.getName()).get(0)))
				.collect(Collectors.toList()));
			keyConstraintName=key.getName();
			//System.out.println("La key tiene:"+keyColumns.size()+" atributos");
		}
   }
	
	@Override
	public USchemaCursor getReference(Reference ref) {
		
		//----Se comprueba si el owner es una EntityType y no un Relationship----
		EntityType owner=null; //se usa solo para un caso raro luego de referencias externas a agregados internos
		if (ref.getOwner() instanceof EntityType) {
			owner= (EntityType) ref.getOwner();
		}
		//-------------------------fin de la comprobación------------------------
		
		if(ref.getIsFeaturedBy()!=null) {
			//System.out.println("La referencia "+ref.getName()+" es de la relationship "+ref.getIsFeaturedBy().getName());
			//sus valores hay que consultarlos en la tabla m:n de la relationship
			//estaran en la fila en la que la relationship tiene el ID de la entidad actual
			Table tableMN=(Table) trace.getSourceInstance(ref.getIsFeaturedBy().getName()).get(0); //tabla relationship m:n
			relationalschema.Key pkMN=tableMN.getKeys().stream().filter(k-> k.isIsPK()).collect(Collectors.toList()).get(0); //PK de tabla m:n
			List<FKey> listFKinPK=getForeignKeysInPrimaryKey(pkMN);  //foreign keys contenidas en PK en tabla m:n
		    if (listFKinPK.size()!=2) {
		    	System.out.println("ERROR relationship "+ref.getIsFeaturedBy().getName()+" is unidirectional. It can't be managed in relational");
		    	finish();
		    }
		    FKey fk=(FKey) trace.getSourceInstance(ref.getOwner().getName()+"."+ref.getName()).get(0); //fk1 a la tabla referenciada
		    //coge la otra (asume que sólo hay 2 FKs). Esa segunda FK es la que apuntara a la entidad consultada en el cursor
		    FKey secondFK=listFKinPK.stream().filter(f->f!=fk).collect(Collectors.toList()).get(0);
		    String refTableName=trace.getSourceElement(ref.getRefsTo().getName()).get(0); //table referenciada a través de la relationship
//System.out.print("relationship 'ref.getIsFeaturedBy().getName()'");
//System.out.println("is in table "+tableMN.getName());
//System.out.println("This is reference '"+ref.getOwner().getName()+"."+ref.getName()+"'");
//System.out.println("In uschema refsTo '"+ref.getRefsTo().getName()+"'");
//System.out.println("this is in trace refsTo table '"+refTableName+"'");

		    String query="SELECT * FROM "+refTableName;
		    query=query+" LEFT JOIN "+tableMN.getName();
		    query=query+" ON "+compareColumns(fk.getRefsTo().getColumns(),fk.getColumns());
		    query=query+" WHERE ";
		    //query=query+filterColumns(secondFK.getColumns(),this.getID().getValues());
		    query=query+filterColumnsWithValues(secondFK.getColumns(),this.getID().getValues());
		    
		    //System.out.println();
		    //System.out.println("value="+this.getID().getValues().get(0));
		    
		    
		    
		    
		    //JOIN de la tabla referenciada con la tabla relationship
		    //donde la pk de la referenciada coincida con los valores de la fk1 de la relationship (corresponde a columnas de esta ref parámetro)
		    //y además la fk2 (de la primarykey) de la relationship coincida con los valores ID de la entity actual del cursor
//System.out.println();
//System.out.println(query);
		    return new USchemaRelCursor (connection, ref.getRefsTo().getName(), query, this.getID().getValues(), trace);
		}
		
		
		//no está optimizado.->si una referencia es null ejecuta igual la consulta
		String query;
		FKey fk=(FKey) trace.getSourceInstance(ref.getOwner().getName()+"."+ref.getName()).get(0); //foreign key relational object
		//los valores de las columnas de esa fk en la tabla en la que esté deben de coincidir con:
		//1) Si la fk está en esta tabla(entity actual), deben coincidir con la PK de la tabla referenciada
		//   (select a la tabla referenciada where su PK==estos valores de columnas
		//   EXCEPTO si es una autoreferencia, que entonces se trata como si la fk estuviera en una tabla diferente
		//2) Si la fk está en una tabla diferente de esta (entity actual), entonces referenciará a la key de esta (actual)
		///  (select a la tabla de la fk where las columnas de la fk=columnas de la key a la que referencia(valores de USchemaID actual)
		relationalschema.Key refTableKey=fk.getRefsTo(); //referenced relational table key
		List<Object> vals=new ArrayList<Object>();
//System.out.println("Este cursor es de la tabla '"+tableName+"'");
//System.out.println("Esta fk es de la tabla '"+fk.getOwner().getName()+"'");
		if ((fk.getOwner().getName().equals(tableName)) && (!fk.getRefsTo().getOwner().getName().equals(tableName))) {
//System.out.println("La referencia "+ref.getName()+" está en esta tabla");
			//query="SELECT * FROM "+tableName+" WHERE ";
			query="SELECT * FROM "+fk.getRefsTo().getOwner().getName()+" WHERE ";

			//Modificado porque una referencia puede no tener atributos
			//for (Attribute a:ref.getAttributes()) {	
				//vals.add(this.getAttributeValue(a));
//System.out.println(a.getName());
			//	}
			
			//Como la referencia puede no tener atributos, se recuperan los valores de las columnas de la FK
			for (Column c:fk.getColumns()) {
				Object result;
				try { result = rs.getObject(c.getName());
				vals.add(result);
				} catch (SQLException e) { e.printStackTrace();	}
			}
			
			//compara vcolumnas de la key destino con valores de la referencia FK
			query=query+filterColumnsWithValues(fk.getRefsTo().getColumns(),vals);
//System.out.println("Query:");
//System.out.println(query);
			/*
			if (ref.getUpperBound()==1) {
				//si es una relación 1:1, el cursor será de la otra tabla, aunque la referencia esté
				//en esta misma tabla
				return new USchemaRelCursor (connection, fk.getRefsTo().getOwner().getName(), query, vals ,trace);
			} //si no es relación 1:1, el cursor
			*/  
			//return new USchemaRelCursor (connection, fk.getOwner().getName(), query, vals ,trace);
			return new USchemaRelCursor (connection, ref.getRefsTo().getName(), query, vals ,trace);
		} else {
			//La referencia está en otra tabla y referencia a esta 
//System.out.println("La referencia está en otra tabla ("+fk.getOwner().getName()+") y referencia a esta ("+fk.getRefsTo().getOwner().getName());	
			//----NUEVO: la referencia está en otra tabla y referencia a una entidad non-root------
			//Caso raro->se está referenciando desde fuera (alguna tabla) a esta tabla débil (es un agregado)
			//
			if (owner!=null && !owner.isRoot()) {
				Table table=(Table) trace.getSourceInstance(ref.getOwner().getName()).get(0); //otra tabla distinta de owner
				//Si es un agregado en uschema y viene de una BD relacional, su tabla tiene PK seguro
				relationalschema.Key primaryKey=table.getKeys().stream().filter(k->k.isIsPK()).collect(Collectors.toList()).get(0); 
				//la FK en relacional tendrá unas columnas con sus valores (fk.getColumns))
				//asume que las columnas de FK están en el mismo orden que las columnas de la PK de la tabla a la que referencia
				List<Column> fk_columns=fk.getColumns();
				//obtener los VALORES de la PK de la instancia (fila) actual de esta tabla débil
				for (Column co: primaryKey.getColumns()) {
					Object result=null;
					try { result = rs.getObject(co.getName());
					    } catch (SQLException e) { e.printStackTrace();	}
					vals.add(result);
				}
				query="SELECT * FROM "+fk.getOwner().getName()+" WHERE ";
				query=query+filterColumnsWithValues(fk.getColumns(),vals);
				USchemaRelCursor cur= new USchemaRelCursor (connection, ref.getOwner().getName(), query, vals, trace);
				//Si la tabla externa (la de la fk no tiene PK se utilizan las columnas de la FK como ID)
				//hay que comprobar si la tabla de fuera que me referencia no tiene PK y entonces se le
				//pondría una clave alternativa (las columnas que le diga. ej: todas)
				//cur.setAlternativeKeyColumns(fk.getColumns());
				return cur;
			}
			//----fin del caso raro nuevo----------------------------------------------------------
			
			//Caso normal la referencia está en otra tabla y referencia a una entidad root
			
			//System.out.println("La referencia "+ref.getName()+" está en otra tabla ("+fk.getOwner().getName()+")");
			query="SELECT * FROM "+fk.getOwner().getName()+" WHERE ";
			query=query+filterColumnsWithValues(fk.getColumns(),this.getID().getValues());
//System.out.println("Query:");
//System.out.println(query);
//System.out.println("values:"+this.getID().getValues());
			//devuelve cursor (fk.getOwnersirve tanto para otra tabla como la propia tabla)
			return new USchemaRelCursor (connection, ref.getRefsTo().getName(), query, this.getID().getValues(),trace);
		}
	}

	@Override
	public USchemaCursor getAggregate(Aggregate agg) {
		String query;
		EntityType aggEntity= (EntityType) trace.getTargetInstance(agg.getSpecifiedBy().getName()).get(0); //specifiedBy entity
		//el aggregate viene de una FK que apunta al padre en la tabla que tiene los datos de la entidad debil
		//schematype es el padre (la entity en la que esté el agregado)
		FKey fk=(FKey) trace.getSourceInstance(schemaType.getName()+"."+agg.getName()).get(0);
		
		//EntityType ownerEntity= (EntityType) schemaType;		//strong entity (es en la que está el agregado)
		//la uschema.key está ya en atributo key y sus columnas relacionales están en keyColumns

		//Se consultan las filas de la tabla debil cuyas columnas de la fk contienen los valores de la PK del padre en la fila actual
		///  (select a la tabla donde estaba la fk where las columnas de la fk=valores de la ID de la fila actual del cursor
		String tableName= trace.getSourceElement(aggEntity.getName()).get(0); //tabla del agregado
		query="SELECT * FROM "+tableName+" WHERE ";
		/*query=query+filterColumnsWithValues(fk.getColumns(),this.getID().getValues());
		
		System.out.println("Aggregate Query:");
		System.out.println(query);
		
		
		//devuelve cursor (sobre las filas de la tabla débil que tienen el id de la tabla fuerte)
		return new USchemaRelCursor (connection, aggEntity.getName(), query, this.getID().getValues(), trace);*/
		List<Object> ownerKeyValues = getRowValuesForColumns(fk.getRefsTo().getColumns());

		query = query + filterColumnsWithValues(fk.getColumns(), ownerKeyValues);
//System.out.println("getAggregate("+agg.getName()+"):"+query);
		return new USchemaRelCursor(connection, aggEntity.getName(), query, ownerKeyValues, trace);
	}
	
	//Obtiene los valores de la instancia actual correspondientes a las columnas que se le pasan
	private List<Object> getRowValuesForColumns(List<relationalschema.Column> cols) {
	    List<Object> vals = new ArrayList<>();
	    for (relationalschema.Column c : cols) {
	        try {
	            vals.add(rs.getObject(c.getName()));
	        } catch (SQLException e) {
	            throw new IllegalStateException(
	                "No se pudo leer columna '" + c.getName() + "' en " + schemaType.getName() +
	                ". Query probablemente no incluye esa columna o hay colisiones de nombres.", e
	            );
	        }
	    }
	    return vals;
	}

	//this method is used to generate a where clause, it compares each column with a value
	private String filterColumns(List<Column> cols,List<Object> values) {
		String result="";
		String separador="";
		for (int i=0;i<cols.size();i++) {
			result=result+separador;
			result=result+cols.get(i).getName();
			result=result+ ((values.get(i) == null) ? " IS NULL" : "="+values.get(i).toString()); //NO FUNCIONA CON DATE (crear función casting)
			separador=" AND ";
		}
		return result;
	}

	//this method is used to generate a where clause, it compares each column with a value with casting
	//query = sql select with an empty WHERE clause
	//cols=columns to compare with values in where clause
	//values=instances of values to compare with each column
	//CREATE A QUERY WITH '?' FOR A PREPAREDSTATEMENT TO BE COMPLETED WITH VALUES
	private String filterColumnsWithValues(List<Column> cols,List<Object> values) {
		String result="";
		String separador="";
		for (int i=0;i<cols.size();i++) {
			result=result+separador;
			result=result+cols.get(i).getName();
			result=result+((values.get(i) == null) ? " IS NULL" : "= ?");
			separador=" AND ";
		}
		//+values.get(i).toString()); //NO FUNCIONA CON DATE (crear función casting)
		return result;
	}

	//Para que las columnas se comparen de manera correcta con los valores concretos que pueden
    //ser de distintos tipos Java, primero comparamos con '?' y luego hacemos un prepareStatement
    //para que el driver haga las comparaciones concretas (ej: poner '' en los String, o convertir el DATE
	
	//this method is used to generate a where clause, it compares columns from list 1 with columns from list2
	private String compareColumns(List<Column> cols1, List<Column> cols2) {
		String result="";
		String separador="";
		for (int i=0;i<cols1.size();i++) {
			result=result+separador;
			result=result+cols1.get(i).getOwner().getName()+"."+cols1.get(i).getName()+"=";
			result=result+cols2.get(i).getOwner().getName()+"."+cols2.get(i).getName();
			separador=" AND ";
		}
		return result;
	}
	
	//Relational method get a list of Foreignkeys contained in PK (in order)
	private  ArrayList<FKey> getForeignKeysInPrimaryKey(relationalschema.Key pk) {
		ArrayList<FKey> result=new ArrayList<FKey>();
		Table table=pk.getOwner();
		//recorrer las columnas de la PK
		for (Column col : pk.getColumns()){
		    //encontrar una FK que tenga esa columna y que esa FK esté contenida entera en la primaryKey
			Optional<FKey> aux=table.getFks().stream().filter(fk->fk.getColumns().contains(col)
															  && pk.getColumns().containsAll(fk.getColumns())).findFirst();
				//añade la fk al resultado si no se ha añadido ya
				if (aux.isPresent() && !result.contains(aux.get())) result.add(aux.get()); //si no la ha añadido antes la añade
		}
		return result;
	}
	
	//-------------------------------------


	/**
	 * return whether the cursor has data from a current entity read 
	 */
	@Override
	public boolean hasData() {
		return hasData;
	}

	/**
	 * Advances to the next instance of the entity
	 */
	@Override
	public void next() {
		try { hasData=rs.next(); } catch (SQLException e) {	e.printStackTrace(); }
	}

	/**
	 * Close the USchemaCursor
	 */
	@Override
	public void close() {
		try {	 rs.close();
		//close a statement or a prepared statement
				 if (stmt!=null) stmt.close();
				 if (pstmt!=null) pstmt.close();
		} catch (SQLException e) { e.printStackTrace(); }
	}

	//error - close connection and exit
	private void finish() {
		try {
			rs.close();
			if (stmt!=null) stmt.close();
			if (pstmt!=null) pstmt.close();
			connection.close();
		} catch (SQLException e) {e.printStackTrace();}
		System.exit(1);
	}

	//Para tablas sin PK se puede llamar a este método y decirle qué columnas utilizar como id
	public void setAlternativeKeyColumns(List<Column> alternativeCols) {
		alternativeKeyColumns=new ArrayList<String>();
		for (Column c:alternativeCols) { alternativeKeyColumns.add(c.getName());}
		withoutID=false;
	}
}
