package um.modelum.migration;

public class Prueba {

	public static void main(String[] args) {
		String relational="C:\\eclipse2025-12\\workspace-GIT\\OrionTraceChanger\\Pruebas\\MusicService-relational.xmi";
		String uschema="C:\\eclipse2025-12\\workspace-GIT\\OrionTraceChanger\\Pruebas\\MusicService-uschema-generated.xmi";
		String traceFile="C:\\eclipse2025-12\\workspace-GIT\\OrionTraceChanger\\Pruebas\\MusicServiceTrace.tra";
		
		TraceChanger tchan=new TraceChanger();
		tchan.loadTrace(traceFile, relational, uschema);
		Trace t=tchan.getTrace();
		t.printFullTrace();
		
		//desagregar
		tchan.disaggregate("app_user.playlist");
		
		//rename
		tchan.rename("app_user", "user_data");
		
		//borrar
		tchan.removeAttribute("user_data.is_premium");
		
		tchan.saveUpdateTargetModel();
		tchan.saveUpdateTrace();
		System.out.println("trace actualizada");
		System.out.println("-----------------");
		t.printFullTrace();
	}

}
