package um.modelum.migration.data;

import documentschema.DocumentSchema;
import relationalschema.RelationalSchema;
import um.modelum.migration.Relational2USchema;
import um.modelum.migration.Trace;
import um.modelum.migration.Uschema2Document;
import um.modelum.migration.adapter.USchemaAdapter;
import um.modelum.migration.adapter.USchemaRelAdapter;
import uschema.USchema;

public class Test {

	public static final int USER_ID = 4;

	public static void main(String[] args) {
		String RELATIONAL_MODEL_XMI_PATH = "models/relational2.xmi";
		String RELATIONAL_EXAMPLE_MODEL_XMI_PATCH = "models/MusicArtRModel.xmi";
		String USCHEMA_MODEL_XMI_PATH = "models/uschema2.xmi";
		String DOCUMENT_MODEL_XMI_PATH = "models/document2.xmi";
		String DOCUMENT_MODEL_JSON_PATH = "models/mongoose.js";
		Trace trace1, trace2;
		final String SERVER_SQL = "sgbd.inf.um.es";
		final String SERVER_MONGODB = "mongodb://localhost:27017";

		// Relational to USchema
		Relational2USchema m2mRel = new Relational2USchema();
		RelationalSchema relationalModel = m2mRel.loadSourceModel(RELATIONAL_MODEL_XMI_PATH);
		USchema uschemaModel = m2mRel.createTargetModel(USCHEMA_MODEL_XMI_PATH);
		m2mRel.filter_do_not_aggregate("Item.order_fk");
		// m2mRel.filter_do_not_move("Product.productCategory_fk");
		// m2mRel.filter_do_not_move("Item.includeProduct_fk");
		m2mRel.transformar(relationalModel, uschemaModel);
		trace1 = m2mRel.getTrace();

		// USchema to Document
		Uschema2Document m2mDoc = new Uschema2Document();
		m2mDoc.loadSchema(USCHEMA_MODEL_XMI_PATH);
		m2mDoc.executeMapping();
		m2mDoc.saveSchema(DOCUMENT_MODEL_XMI_PATH);
		DocumentSchema documentModel = m2mDoc.getDocumentSchema();
		trace2 = m2mDoc.getTrace();

		USchemaAdapter uadapter = new USchemaRelAdapter(trace1);

		// musicart database
		//System.out.println(uadapter.openConnectionService(SERVER_SQL, 1521, "ORCLPDB", "DSI02", "modelum"));

		// metashop database
		System.out.println(uadapter.openConnectionService("sgbd.inf.um.es", 1521, "ORCLPDB", "DSI00", "fargo"));

		// only change the 2 last fields (user, password)
		DocumentValidator dv = new DocumentValidator(SERVER_MONGODB, SERVER_SQL, documentModel.getName(), "DSI00", "fargo");

		// relationalSquema, documentSquema, traces and adapter
		dv.validateDocumentAndRelational(relationalModel, documentModel, trace1, trace2, uadapter);

		uadapter.closeConnection();

	}

}
