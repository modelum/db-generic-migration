package um.modelum.migration;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import relationalschema.RelationalschemaPackage
import relationalschema.RelationalSchema
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import relationalschema.Table
import relationalschema.FKey
import relationalschema.Column
import relationalschema.Key
import java.util.ArrayList
import java.io.FileWriter
import java.io.PrintWriter
import org.eclipse.emf.common.util.EList

class RelationalAPI {
	ResourceSet relResourceSet;
	Resource relResource; //contiene todos los elementos del modelo origen
	RelationalSchema rootElement=null;
	
	new(){ 
        RelationalschemaPackage.eINSTANCE.eClass()
	}

	def RelationalSchema loadSourceModel(String path) {
        relResourceSet = new ResourceSetImpl();
        relResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl());
        var uri = org.eclipse.emf.common.util.URI.createFileURI(path)
        relResource = relResourceSet.getResource(uri, true)
        rootElement = relResource.contents.head as RelationalSchema
        return rootElement;
	}
	
	//check if a table is a relationship M:N
	def boolean is_relationship(Table t){
		getNumberOfForeignKinPK(t)==2
	}
	
	//check if a table is a weak entity with whatever other table
	def boolean is_weak(Table table) {
		getNumberOfForeignKinPK(table)==1
	}
	
	//check if a table is a weak entity with respect to other
	//una sola clave ajena en la clave primaria de tabla1 y esa fk referencia a la tabla2
	def boolean is_weakWith(Table table, Table table2) {
		val relationalschema.Key pk=getTablePK(table)
		if (pk==null) return false;
		getNumberOfForeignKinPK(table)==1 &&
		table.fks.stream.filter[fk|primaryKeyContainsFK(pk,fk)].anyMatch[fk|fk.refsTo.owner.equals(table2)]
	}
	
	//check whether PK contains FK 
	def boolean primaryKeyContainsFK(relationalschema.Key pk, relationalschema.FKey fk){
		if (pk==null) return false;
		return pk.columns.containsAll(fk.columns)
	}
	
	//count how many FKs are contained in a PK 
	def int getNumberOfForeignKinPK(Table table) {
		val relationalschema.Key pk=getTablePK(table)
		if (pk==null) return 0;
		return table.fks.stream.filter[fk|primaryKeyContainsFK(pk,fk)].count() as int	
	}
	//get the position order of a column inside a Key
	def int getAttributePosinKey(Column col, Key key) {
		key.columns.indexOf(col); //0 for first position, -1 if not found
	}
	
	//get a list of Foreignkeys contained in PK (in order)
	def ArrayList<FKey> getForeignKeysInPrimaryKey(Table table) {
		var result=new ArrayList<FKey>();
		val primaryKey=table.getTablePK
		if (primaryKey!=null) {
			//recorrer las columnas de la PK
			for (col : primaryKey.columns){
				//encontrar una FK que tenga esa columna y que esa FK esté contenida entera en la primaryKey
				var aux=table.fks.stream.filter(fk|fk.columns.contains(col)&& primaryKey.primaryKeyContainsFK(fk)).findFirst()
				//añade la fk al resultado si no se ha añadido ya
				if (aux.isPresent() && !result.contains(aux.get())) result.add(aux.get()) //si no la ha añadido antes la añade
			}
		} //if	
		return result
	}
	
	def boolean tableReferencesToTable(Table table, Table refTable) {
		return table.fks.exists[fk | fk.refsTo.owner.equals(refTable)]
	}
	
	//get the PK of a table 
	def relationalschema.Key getTablePK(Table table) {
		var pk=table.keys.stream.filter[k|k.isPK].findFirst()
		pk.isPresent() ? pk.get() : return null
	}
	
	//check if Foreign has unique values (unique) (es clave alternativa)
	def boolean isUnique(FKey fk){
		fk.owner.keys.stream.filter[key|!key.isPK]
		.anyMatch[key|key.columns.containsAll(fk.columns)&&key.columns.size()==fk.columns.size()]
	}  
	
	def boolean isFkColumn(Table t, Column c) {
	  t.fks.exists[fk | fk.columns.contains(c)]
	}
	
	 //check if a Foreign key is also a primary key (weak table without partial key)
	 //(check if a primarykey is a foreign key)
    def boolean isAlsoPK(FKey fk){
        fk.owner.keys.stream.filter[key|key.isPK]
        .anyMatch[key|key.columns.containsAll(fk.columns)&&key.columns.size()==fk.columns.size()]
    }

//-----------------------------------generate SQL script---------------------------------------
	def String printColumns(EList<Column> lc) {
		var String r=""
		var SEPARADOR=""
		for (co:lc){
      		r=r+SEPARADOR
      		r=r+co.name
      		SEPARADOR=", "
      	}
		return r
	}

	
	def void generateScript(RelationalSchema root, String fileName) {
        var FileWriter fichero = null;
        var PrintWriter pw = null;
        var SEPARADOR=""
		try {
            fichero = new FileWriter(fileName);
            pw = new PrintWriter(fichero);
       		for (table : root.tables) {
       			pw.println
      			var String cs="CREATE TABLE "+table.name+" (" //create statement
       			pw.println(cs);
       			SEPARADOR=""
      			for (col: table.columns) {
      				pw.println(SEPARADOR);
      				cs="   "+col.name+" "+col.datatype
      				if (col.size>1) cs=cs+"("+col.size+")"
      				if (col.nullable) cs +="NOT NULL "
      				pw.print(cs)
      				SEPARADOR=","
      			}
				pw.println
      			pw.println(");")
      		}
      		//Primary keys
      		for (table : root.tables) {
       			pw.println
      			var String cs="ALTER TABLE "+table.name 
       			pw.println(cs);
       			var pk=table.getTablePK()
      			cs="ADD CONSTRAINT "+pk.constraintname+" PRIMARY KEY ("+printColumns(pk.columns)+");"
      			pw.print(cs); //primary key
       		}
       		pw.println
       		//Unique keys
       		for (table : root.tables) {
       			for(uk:table.keys.filter[k|k.isPK==false]){
      				pw.println
      				var cs="ALTER TABLE "+table.name 
      				pw.println(cs);
      				cs="ADD CONSTRAINT "+uk.constraintname+" UNIQUE ("+printColumns(uk.columns)+");"
      				pw.print(cs)     				
      			}
      		}
      		pw.println
      		//foreign keys
      		for (table : root.tables) {
       			for(fk:table.fks){
					pw.println
      				var cs="ALTER TABLE "+table.name 
      				pw.println(cs);
      				cs="ADD CONSTRAINT "+fk.constraintname+" FOREIGN KEY ("+printColumns(fk.columns)+")"
      				cs=cs+" REFERENCES "+fk.refsTo.owner.name+" ("+printColumns(fk.refsTo.columns)+");" 
      				pw.print(cs);	
       			}
       		}
      		System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null !== fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	def void generateRemoveScript(RelationalSchema root, String fileName) {
        var FileWriter fichero = null;
        var PrintWriter pw = null;
		try {
            fichero = new FileWriter(fileName);
            pw = new PrintWriter(fichero);
            //remove foreign keys
      		for (table : root.tables) {
       			for(fk:table.fks){
					pw.println
      				var cs="ALTER TABLE "+table.name 
      				pw.println(cs);
      				cs="DROP CONSTRAINT "+fk.constraintname+";"
      				pw.print(cs);	
       			}
       		}
       		pw.println
            //remove Unique keys
       		for (table : root.tables) {
       			for(uk:table.keys.filter[k|k.isPK==false]){
      				pw.println
      				var cs="ALTER TABLE "+table.name 
      				pw.println(cs);
      				cs="DROP CONSTRAINT "+uk.constraintname+";"
      				pw.print(cs)     				
      			}
      		}
      		pw.println
      		//remove Primary keys
      		for (table : root.tables) {
       			pw.println
      			var String cs="ALTER TABLE "+table.name 
       			pw.println(cs);
       			var pk=table.getTablePK()
      			cs="DROP CONSTRAINT "+pk.constraintname+";"
      			pw.print(cs); 
       		}
       		pw.println
       		//remove tables
       		for (table : root.tables) {
       			pw.println
      			var String cs="DELETE FROM "+table.name+";" //delete records
       			pw.println(cs);
       			cs="DROP TABLE "+table.name+";"
				pw.println(cs);
      		}

			pw.println("COMMIT;")
			pw.println
      		
      		System.out.println("Successfully wrote to the file.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null !== fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
}