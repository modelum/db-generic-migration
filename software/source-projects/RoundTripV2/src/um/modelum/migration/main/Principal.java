package um.modelum.migration.main;

import java.io.IOException;

import documentschema.DocumentSchema;
import relationalschema.RelationalSchema;
import um.modelum.migration.Document2Text;
import um.modelum.migration.Relational2USchema;
import um.modelum.migration.RelationalAPI;
import um.modelum.migration.Trace;
import um.modelum.migration.Uschema2Document;
import um.modelum.migration.mapping.Document2Uschema;
import um.modelum.migration.mapping.USchema2Relational;
import uschema.USchema;

public class Principal {
	private static String DIRECT_DIR = "roundtrip/direct/"; //entradas relacional genera salidas uschema y doc
	private static String REVERSE_DIR = "roundtrip/reverse/"; //toma como entradas las salidas del direct
	
	public static void main(String[] args) {
		Trace directTrace1, directTrace2;
		RelationalAPI relationalAPI=new RelationalAPI();
//DIRECT --------------------------------------------------------------------------------
		// Relational to USchema
		System.out.println("-Relacional a USchema de Music Service");
		Relational2USchema m2mRel2Uschema = new Relational2USchema();
		RelationalSchema relationalModel = m2mRel2Uschema.loadSourceModel(DIRECT_DIR+"MusicService-relational.xmi");
		USchema directUschemaModel = m2mRel2Uschema.createTargetModel(DIRECT_DIR+"MusicService-uschema-generated.xmi");
		//m2mRel2Uschema.filter_do_not_aggregate("playlist.playlist_fk_user");
	    //m2mRel.filter_do_not_aggregate("Item.order_fk");
		//m2mRel.filter_do_not_move("Product.productCategory_fk");
		//m2mRel.filter_do_not_move("Item.includeProduct_fk");
		//m2mRel.filter_do_not_aggregate("playlist.playlist_fk_user");
		//m2mRel.filter_do_not_aggregate("playlist_song.playlist_song_fk_playlist");
		m2mRel2Uschema.transformar(relationalModel, directUschemaModel);
		directTrace1 = m2mRel2Uschema.getTrace();
//---prueba de persistencia de la traza
directTrace1.printFullTrace();
try {
	directTrace1.saveTrace(DIRECT_DIR+"MusicServiceTrace.tra", DIRECT_DIR+"MusicService-relational.xmi", DIRECT_DIR+"MusicService-uschema-generated.xmi");
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
directTrace1=null;
directTrace1=new Trace();
try {
	directTrace1.loadTrace(DIRECT_DIR+"MusicServiceTrace.tra", DIRECT_DIR+"MusicService-relational.xmi", DIRECT_DIR+"MusicService-uschema-generated.xmi");
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
directTrace1.printFullTrace();
directTrace1.printFullReverseTrace();
System.exit(0);
//----fin prueba de persistencia de la traza

		System.out.println("-Relacional a USchema de Northwind");
		Relational2USchema m2mRel2UschemaN = new Relational2USchema();
		RelationalSchema relationalModelN = m2mRel2UschemaN.loadSourceModel(DIRECT_DIR+"Northwind-relational.xmi");
		USchema directUschemaModelN = m2mRel2UschemaN.createTargetModel(DIRECT_DIR+"Northwind-uschema-generated.xmi");
		m2mRel2UschemaN.transformar(relationalModelN, directUschemaModelN);		
		System.out.println("-Relacional a USchema de TPC-C");		
		// Relational to USchema
		Relational2USchema m2mRel2UschemaT = new Relational2USchema();
		RelationalSchema relationalModelT = m2mRel2UschemaT.loadSourceModel(DIRECT_DIR+"TPC-C-relational-sinPK.xmi");
		USchema directUschemaModelT = m2mRel2UschemaT.createTargetModel(DIRECT_DIR+"TPC-C-uschema-sinPK-generated.xmi");
		//no agregar en TPC-C estos dos porque tienen referencias desde otras tablas fuertes
		m2mRel2UschemaT.filter_do_not_aggregate("customer.c_fk_d");
		m2mRel2UschemaT.filter_do_not_aggregate("district.d_fk_w");
		m2mRel2UschemaT.transformar(relationalModelT, directUschemaModelT);

		/*
		//Generate SQL DDL
		System.out.println("-Generando scripts sql para MusicServcie y NorthWind");
		relationalAPI.generateScript(relationalModel, DIRECT_DIR+"MusicService-relational-generated.sql");
		relationalAPI.generateScript(relationalModelN, DIRECT_DIR+"Northwind-relational-generated.sql");
		*/
		// USchema to Document
		System.out.println("USchema de Music-Service a documentos");
		Uschema2Document m2mUschema2Doc = new Uschema2Document();
		m2mUschema2Doc.loadSchema(DIRECT_DIR+"MusicService-uschema-generated.xmi");
		m2mUschema2Doc.executeMapping();
		m2mUschema2Doc.saveSchema(DIRECT_DIR+"MusicService-document-generated.xmi");
		DocumentSchema directDocumentModel = m2mUschema2Doc.getDocumentSchema();
		directTrace2 = m2mUschema2Doc.getTrace();
		
		System.out.println("USchema de Northwind a documentos");
		Uschema2Document m2mUschema2DocN = new Uschema2Document();
		m2mUschema2DocN.loadSchema(DIRECT_DIR+"Northwind-uschema-generated.xmi");
		m2mUschema2DocN.executeMapping();
		m2mUschema2DocN.saveSchema(DIRECT_DIR+"Northwind-document-generated.xmi");
		DocumentSchema directDocumentModelN = m2mUschema2DocN.getDocumentSchema();
		
		System.out.println("USchema de TPC-C a documentos");
		Uschema2Document m2mUschema2DocT = new Uschema2Document();
		m2mUschema2DocT.loadSchema(DIRECT_DIR+"TPC-C-uschema-sinPK-generated.xmi");
		m2mUschema2DocT.executeMapping();
		m2mUschema2DocT.saveSchema(DIRECT_DIR+"TPC-C-document-sinPK-generated.xmi");
		DocumentSchema directDocumentModelT = m2mUschema2DocT.getDocumentSchema();
		
		/*
		// Document to JSON
		System.out.println("Generando JavaScript de documentos Music-Service");
		Document2Text m2Text = new Document2Text();
		m2Text.loadSchema(DIRECT_DIR+"MusicService-document-generated.xmi");
		m2Text.mappingDocument2Text(DIRECT_DIR+"MusicService-document-generated.js");
		System.out.println("Generando JavaScript de documentos Northwind");
		Document2Text m2TextN = new Document2Text();
		m2TextN.loadSchema(DIRECT_DIR+"Northwind-document-generated.xmi");
		m2TextN.mappingDocument2Text(DIRECT_DIR+"Northwind-document-generated.js");
		 */
		
//REVERSE --------------------------------------------------------------------------------
		System.out.println("================ ROUND-TRIP====================");
		System.out.println("***Doc to Uschema:");
		System.out.println("Generando USchema de 'Music-Service documentos generated'");
		Document2Uschema m2mDoc2Uschema = new Document2Uschema();
		m2mDoc2Uschema.loadSchema(DIRECT_DIR+"MusicService-document-generated.xmi");
		m2mDoc2Uschema.transformacion();
		m2mDoc2Uschema.saveSchema(REVERSE_DIR+"MusicServiceDoc-uschema-generated.xmi");
		
		System.out.println("Generando USchema de 'Northwind documentos generated'");
		Document2Uschema m2mDoc2UschemaN = new Document2Uschema();
		m2mDoc2UschemaN.loadSchema(DIRECT_DIR+"Northwind-document-generated.xmi");
		m2mDoc2UschemaN.transformacion();
		m2mDoc2UschemaN.saveSchema(REVERSE_DIR+"NorthwindDoc-uschema-generated.xmi");
		
		System.out.println("Generando USchema de 'TPC-C documentos sinPK generated'");
		Document2Uschema m2mDoc2UschemaT = new Document2Uschema();
		m2mDoc2UschemaT.loadSchema(DIRECT_DIR+"TPC-C-document-sinPK-generated.xmi");
		m2mDoc2UschemaT.transformacion();
		m2mDoc2UschemaT.saveSchema(REVERSE_DIR+"TPC-CDoc-uschema-generated.xmi");
		
		System.out.println("***Uschema to Rel:");
		System.out.println("Generando Relational de 'MusicServiceDoc-uschema-generated");			
		USchema2Relational entities2sql = new USchema2Relational();
		entities2sql.loadSchema(REVERSE_DIR+"MusicServiceDoc-uschema-generated.xmi");
		RelationalSchema reverseRelationalModel = entities2sql.transformacion();
		entities2sql.writeModel(REVERSE_DIR+"MusicServiceDoc-relational-generated.xmi");
		
		System.out.println("Generando Relational de 'NorthwindDoc-uschema-generated");			
		USchema2Relational entities2sqlN = new USchema2Relational();
		entities2sqlN.loadSchema(REVERSE_DIR+"NorthwindDoc-uschema-generated.xmi");
		RelationalSchema reverseRelationalModelN = entities2sqlN.transformacion();
		entities2sqlN.writeModel(REVERSE_DIR+"NorthwindDoc-relational-generated.xmi");

		System.out.println("Generando Relational de 'TPC-CDoc-uschema-generated");			
		USchema2Relational entities2sqlT = new USchema2Relational();
		entities2sqlT.loadSchema(REVERSE_DIR+"TPC-CDoc-uschema-generated.xmi");
		RelationalSchema reverseRelationalModelT = entities2sqlT.transformacion();
		entities2sqlT.writeModel(REVERSE_DIR+"TPC-CDoc-relational-generated.xmi");
		/*
		//Generate SQL DDL
		System.out.println("-Generando scripts inversos sql para MusicServcie y NorthWind");
		relationalAPI.generateScript(reverseRelationalModel, REVERSE_DIR+"MusicServiceDoc-relational-generated.sql");
		relationalAPI.generateScript(reverseRelationalModelN, REVERSE_DIR+"NorthwindDoc-relational-generated.sql");
		*/
	}

}
