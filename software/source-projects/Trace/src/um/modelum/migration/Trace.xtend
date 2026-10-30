package um.modelum.migration

import java.util.TreeMap
import java.util.List
import java.util.Arrays
import java.util.ArrayList
import java.util.SortedMap
import java.util.Set

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI
import java.io.IOException
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import java.io.BufferedReader
import java.io.FileReader
import java.io.BufferedWriter
import java.io.FileWriter
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import java.util.Map

class Trace {
	//T_sourceElement (destination, sources)->devuelve el/los elementos origen del elemento destino
	//T_targetElement (origin, targets)->devuelve el/los elementos destino del elemento origen
	SortedMap <String,ArrayList<String>> T_sourceElement=new TreeMap<String,ArrayList<String>>(); 
	SortedMap <String,ArrayList<String>> T_targetElement=new TreeMap<String,ArrayList<String>>();
	SortedMap <String,ArrayList<Object>> T_sourceInstance=new TreeMap<String,ArrayList<Object>>();
	SortedMap <String,ArrayList<Object>> T_targetInstance=new TreeMap<String,ArrayList<Object>>();
	//NUEVO --trazas para obtener una instancia del origen a partir de un id de origen
	//      --y trazas para obtener una instancia del destino a partir de un id de destino
	SortedMap <String,Object> T_sourceInstanceFromSourceId=new TreeMap<String,Object>();
	SortedMap <String,Object> T_targetInstanceFromTargetId=new TreeMap<String,Object>();
	
	//Si es la primera vez que se añade a esa key crea la lista y si no, lo añade a la lista
	def void addTrace(String sourceElement, Object sourceInstance, String targetElement, Object targetInstance) { 
			//--Origen a partir de identificador de origen
			T_sourceInstanceFromSourceId.put(sourceElement,sourceInstance)
			//--Destino a partir de identificador de destino
			T_targetInstanceFromTargetId.put(targetElement,targetInstance)
			//--Destinos a partir del origen
			//String
			var List<String> auxTargetE=T_targetElement.get(sourceElement)
			if (auxTargetE===null)  
				T_targetElement.put(sourceElement, new ArrayList<String>(Arrays.asList(targetElement)))
			else auxTargetE.add(targetElement)
			//objetos
		    var List<Object> auxTargetI=T_targetInstance.get(sourceElement)
		    if (auxTargetI===null)  
				T_targetInstance.put(sourceElement, new ArrayList<Object> (Arrays.asList(targetInstance)))
			else auxTargetI.add(targetInstance)
		    
		    //--Origen a partir del destino (le pasas el targetElement y obtienes el source string u objeto
		    var List<String> auxSourceE=T_sourceElement.get(targetElement)
			if (auxSourceE===null)  
				T_sourceElement.put(targetElement, new ArrayList<String> (Arrays.asList(sourceElement)))
			else auxSourceE.add(sourceElement)
			//objetos
		    var List<Object> auxSourceI=T_sourceInstance.get(targetElement)
		    if (auxSourceI===null)  
				T_sourceInstance.put(targetElement, new ArrayList<Object>(Arrays.asList(sourceInstance)))
			else auxSourceI.add(sourceInstance)
	}
	/**
	 * obtener origen a partir del identificador de destino
	 * @param target: id del elemento del modelo target
	 * @return Lista de identificadores del modelo origen que lo crearon
	 */
	def ArrayList<String> getSourceElement(String target) {
		return T_sourceElement.get(target)
	}
	/**
	 * obtener objeto origen a partir del identificador de destino
	 * @param target: id del elemento del modelo target
	 * @return Lista de objetos del modelo origen que lo crearon
	 */
	def ArrayList<Object> getSourceInstance(String target) {
		return T_sourceInstance.get(target)
	}
	/**
	 * obtener destino a partir del identificador de origen
	 * @param source: id del elemento del modelo source (e.g. app_user.name)
	 * @return Lista de identificadores de los objetos del modelo destino que ha creado
	 */
	def ArrayList<String> getTargetElement(String source) {
		return T_targetElement.get(source)
	}
	/**
	 * obtener objetos del destino a partir del identificador de origen
	 * @param source: id del elemento del modelo source (e.g. app_user.name)
	 * @return Lista de objetos del modelo destino que ha creado
	 */
	def ArrayList<Object> getTargetInstance(String source) {
		return T_targetInstance.get(source)
	}
	/**
	 * obtener un objeto del modelo source a partir de su identificador
	 * @param id: id del elemento del modelo source (e.g. app_user.name)
	 * @return El objeto del modelo source que tiene ese id
	 */
	def Object getSourceInstanceFromSourceId(String id) {
		 return T_sourceInstanceFromSourceId.get(id)
	}
	/**
	 * obtener un objeto del modelo destino a partir de su identificador
	 * @param id: id del elemento del modelo target (e.g. app_user.name)
	 * @return El objeto del modelo destino que tiene ese id
	 */
	def Object getTargetInstanceFromTargetId(String id) {
		 return T_targetInstanceFromTargetId.get(id)
	}
	/**
	 * obtener un identificador de un objeto del modelo destino a partir del objeto
	 * @param targetInstance - the target object
	 * @return Id or null if not found
	 */
	def String getIdFromTargetInstance(Object targetInstance) {
    	for (Map.Entry<String, Object> e : T_targetInstanceFromTargetId.entrySet()) {
        	if (e.getValue() == targetInstance) {
            	return e.getKey();
        	}
    	}
    	return null;
	}
	/**
	 * Devuelve el tipo de un objeto del metamodelo recuperado de la traza
	 * @return un String con el nombre de la metaclase
	 */
	def String getType(Object o) {
		o.class.interfaces.head.name
	}
	
	/**
	 * Remove target element from trace ( a partir del identificador del elemento target)
	 * como si no se hubiera generado el elemento
	 * Y se reajusta toda la traza
	 */
	def boolean removeTargetElementFromTrace(String target) {
		var Object targetInstance=getTargetInstanceFromTargetId(target); //instancia correspondiente a este target id
		if (targetInstance==null) return false;	//Si no se ha encontrado ese target
		
		//lo primero es obtener todos los elementos source que generaron este elemento target
		var ArrayList<String> sourceElements=getSourceElement(target);
		
		T_sourceElement.remove(target)		//se borran las entradas de ese target como si no existiera para poder consultar a la inversa
		T_sourceInstance.remove(target)
		//Ahora para cada source key que hubiera generado este target, se elimina como valor de ese source
		for (String s:sourceElements) {
			var ArrayList<String> values=this.getTargetElement(s) //borra ids generados de la traza
			values.remove(target)
			T_targetElement.put(s,values) //sobreescribe la clave de nuevo pero sin ese valor
			var ArrayList<Object> valueInstances=this.getTargetInstance(s) //borra instancias generadas de la traza
			valueInstances.remove(targetInstance)
			T_targetInstance.put(s,valueInstances) //sobreescribe la clave de nuevo pero sin ese valor
		}
		//por último se elimina de su traza individual
		T_targetInstanceFromTargetId.remove(target)
		return true
	}
	
	//modifica el valor de una clave que ya existe (mueve un elemento del destino a otro sitio del destino manteniendo los mismos orígenes)
	//PREREQUISITO: EL ELEMENTO DEL DESTINO YA ESTÁ AÑADIDO A LA TRAZA. La instancia ya se ha movido al nuevo sitio
	//los elementos que dieron origen siguen siendo los mismos aunque cambie de sitio
	def boolean changeTargetKeyTo(String target, String newTarget) {
		var Object targetInstance=getTargetInstanceFromTargetId(target); //instancia correspondiente a este target id
		if (targetInstance==null) return false;	//Si no se ha encontrado ese target
		
		//lo primero es obtener todos los elementos source que generaron este elemento target
		var ArrayList<String> sourceElements=getSourceElement(target);
		var ArrayList<Object> sourceInstances=getSourceInstance(target);
		T_sourceElement.put(newTarget,sourceElements)
		T_sourceElement.remove(target)		//se borran las entradas de ese target como si no existiera para poder consultar a la inversa
		T_sourceInstance.put(newTarget,sourceInstances)
		T_sourceInstance.remove(target)
		//Ahora para cada source key que hubiera generado este target, se cambia por el nuevo target para esa source
		for (String s:sourceElements) {
			var ArrayList<String> values=this.getTargetElement(s) 
			for(var int i=0;i<values.size;i++) {
				if (values.get(i)==target) values.set(i,newTarget) //modifica el target string por el nuevo target string
			}
			//T_targetInstance no hace falt tocarla porque la instancia target sigue siendo la misma. Sólo han cambiado valores en el objeto como el owner
		}
		//por último se modifica su traza individual
		T_targetInstanceFromTargetId.put(newTarget,targetInstance)
		T_targetInstanceFromTargetId.remove(target)
		return true
	}
	
	//----rename en target------
	//ej: 'user.name' -> 'user.nombre'
	//busca todos los descendientes que empiezan por ese prefijo para cambiarles su identificador también
	//ej:user.nombre.string
	//usa changeTargetKeyTo() para cada prefijo que encuentra
	def void renameTargetPrefix(String oldId, String newId) {
    
    // 1) Obtener todos los id afectados (los que tienen el mismo prefijo)
    val Set<String> afectados = new java.util.TreeSet<String>()
    for (k : T_sourceElement.keySet()) if (esDescendiente(k, oldId)) afectados.add(k)
    //los de targetInstance deberían ser iguales
    //for (k : T_targetInstanceFromTargetId.keySet()) if (esDescendiente(k, oldId)) afectados.add(k)

    // 2) Renombrar cada uno. El orden no importa porque:
    for (idViejo : afectados) {
        val String idNuevo = newId + idViejo.substring(oldId.length)
        changeTargetKeyTo(idViejo, idNuevo)
    }
}

def private boolean esDescendiente(String id, String prefix) {
    if (id === null || prefix === null) return false
    return id == prefix || id.startsWith(prefix + ".")
}
	
	//-----fin rename-----------
	
	def SortedMap<String,ArrayList<String>> getDirectTraceString() { T_targetElement } //obtener la tabla hash
	
	def void printDirectTraceTypes(){
		var SortedMap<String,ArrayList<String>> resultado=T_targetElement
		var Set<String> entradas=resultado.keySet
		var iterador=entradas.iterator
		System.out.println
		System.out.println("Traza")
		System.out.println("-----")
		while(iterador.hasNext){
			var String entrada=iterador.next
			var ArrayList<String> salida=resultado.get(entrada)
			var String auxSal=String.join(", ",salida)
			entrada=entrada+"("+this.getType(this.getSourceInstance(salida.head).head)+")"
			System.out.println(String::format("%60s",entrada)+" -> "+auxSal)
		}
	}
	
	def void printFullTrace(){
		val ancho=72
		var SortedMap<String,ArrayList<String>> resultado=T_targetElement
		var Set<String> entradas=resultado.keySet
		var iterador=entradas.iterator
		System.out.println
		System.out.println("Traza")
		System.out.println("-----")
		while(iterador.hasNext){
			var String entrada=iterador.next
			var auxEnt=entrada+"("+this.getType(this.getSourceInstanceFromSourceId(entrada))+")"
			System.out.print(String::format("%72s",auxEnt)+" -> ");
			
			var ArrayList<String> salida=resultado.get(entrada)
			var String auxSal=""
			for(var i=0;i<salida.length;i++) {
				var String s=salida.get(i)
				auxSal=s+"("+getType(this.getTargetInstanceFromTargetId(s))+")"
				System.out.println(auxSal)
				if (i<salida.length-1) {
					System.out.print(String::format("%72s","")+"    ")
				}
			}
			if(salida.empty) System.out.println();			
		}
	}
	
	def void printFullReverseTrace(){
		val ancho=72
		var SortedMap<String,ArrayList<String>> resultado=T_sourceElement //consultar objetos de source
		var Set<String> entradas=resultado.keySet //set de elementos del destino
		var iterador=entradas.iterator
		System.out.println
		System.out.println("Traza inversa")
		System.out.println("-------------")
		while(iterador.hasNext){
			var String entrada=iterador.next
			var auxEnt=entrada+"("+this.getType(this.getTargetInstanceFromTargetId(entrada))+")"
			System.out.print(String::format("%72s",auxEnt)+" -> ");
			
			var ArrayList<String> salida=resultado.get(entrada)
			var String auxSal=""
			for(var i=0;i<salida.length;i++) {
				var String s=salida.get(i)
				auxSal=s+"("+getType(this.getSourceInstanceFromSourceId(s))+")"
				System.out.println(auxSal)
				if (i<salida.length-1) {
					System.out.print(String::format("%72s","")+"    ")
				}
			}
			if(salida.empty) System.out.println();			
		}
	}
	
	 /**
     * Persiste la traza en 'traceFile'.
     * 'sourceModelFile' y 'targetModelFile' son las rutas de los .xmi de los
     * modelos origen y destino. Se cargan en un ResourceSet para poder
     * calcular las URIs de los EObject que están en la traza.
     *
     * Los Object que no sean EObject, o que sean EObject sin Resource,
     * se persisten como "null" (y se avisa por consola).
     */
	def void saveTrace(String traceFile, String sourceModelFile, String targetModelFile) throws IOException {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl())
		val ResourceSetImpl srcRS = new ResourceSetImpl()
		val ResourceSet tgtRS = new ResourceSetImpl()
		val Resource srcRes = srcRS.getResource(URI.createFileURI(sourceModelFile), true)
		val Resource tgtRes = tgtRS.getResource(URI.createFileURI(targetModelFile), true)
		saveTraceInternal(traceFile, srcRes, tgtRes)
	}

	def void loadTrace(String traceFile, String sourceModelFile, String targetModelFile) throws IOException {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl())
		val ResourceSet srcRS = new ResourceSetImpl()
		val ResourceSet tgtRS = new ResourceSetImpl()
		val Resource srcRes = srcRS.getResource(URI.createFileURI(sourceModelFile), true)
		val Resource tgtRes = tgtRS.getResource(URI.createFileURI(targetModelFile), true)
		loadTraceInternal(traceFile, srcRes, tgtRes)
	}
	
	// ==================================================================
	//  SAVE / LOAD  (versión con Resources ya cargados)
	// ==================================================================

	/**
	 * Persiste la traza en 'traceFile' usando Resources ya cargados.
	 * Los Resources se usan solo para poder calcular URIs coherentes;
	 */
	 	def void saveTrace(String traceFile, Resource sourceResource, Resource targetResource) throws IOException {
		saveTraceInternal(traceFile, sourceResource, targetResource)
	}

	def void loadTrace(String traceFile, Resource sourceResource, Resource targetResource) throws IOException {
		loadTraceInternal(traceFile, sourceResource, targetResource)
	}
	
	/**
	 * Guarda la traza. Las instancias del modelo1 (source) se resuelven
	 * contra sourceResource; las del modelo2 (target) contra targetResource.
	 * Al guardar solo necesitamos los Resource para poder calcular URIs
	 * coherentes (en realidad EcoreUtil.getURI no los necesita, pero los
	 * pasamos para mantener la coherencia y por si en el futuro hiciera falta).
	 */
	def private void saveTraceInternal(String traceFile,
	                                   Resource sourceResource,
	                                   Resource targetResource) throws IOException {
		val BufferedWriter w = new BufferedWriter(new FileWriter(traceFile))
		try {
			w.write("# Trace file v1")
			w.newLine()

			// --- T_sourceInstanceFromSourceId : String -> Object (modelo1)
			for (entry : T_sourceInstanceFromSourceId.entrySet()) {
				w.write("SI|" + escape(entry.key) + "|" + escape(toURIFragmentString(entry.value, sourceResource)))
				w.newLine()
			}

			// --- T_targetInstanceFromTargetId : String -> Object (modelo2)
			for (entry : T_targetInstanceFromTargetId.entrySet()) {
				w.write("TI|" + escape(entry.key) + "|" + escape(toURIFragmentString(entry.value, targetResource)))
				w.newLine()
			}

			// --- T_targetElement : String -> List<String> (ids, sin URIs)
			for (entry : T_targetElement.entrySet()) {
				w.write("TE|" + escape(entry.key) + "|" + escape(join(entry.value)))
				w.newLine()
			}

			// --- T_sourceElement : String -> List<String> (ids, sin URIs)
			for (entry : T_sourceElement.entrySet()) {
				w.write("SE|" + escape(entry.key) + "|" + escape(join(entry.value)))
				w.newLine()
			}

			// --- T_targetInstance : String -> List<Object> (modelo2)
			for (entry : T_targetInstance.entrySet()) {
				val List<String> uris = new ArrayList<String>()
				for (o : entry.value) uris.add(toURIFragmentString(o, targetResource))
				w.write("TT|" + escape(entry.key) + "|" + escape(join(uris)))
				w.newLine()
			}

			// --- T_sourceInstance : String -> List<Object> (modelo1)
			for (entry : T_sourceInstance.entrySet()) {
				val List<String> uris = new ArrayList<String>()
				for (o : entry.value) uris.add(toURIFragmentString(o, sourceResource))
				w.write("ST|" + escape(entry.key) + "|" + escape(join(uris)))
				w.newLine()
			}
		} finally {
			w.close()
		}
	}

	/**
	 * Carga la traza. Las URIs de source se resuelven contra sourceResource;
	 * las de target contra targetResource. Cada uno usa su propio ResourceSet.
	 */
	def private void loadTraceInternal(String traceFile,
	                                   Resource sourceResource,
	                                   Resource targetResource) throws IOException {
		// Limpiamos la traza actual
		T_sourceInstanceFromSourceId.clear()
		T_targetInstanceFromTargetId.clear()
		T_sourceElement.clear()
		T_targetElement.clear()
		T_sourceInstance.clear()
		T_targetInstance.clear()

		val BufferedReader r = new BufferedReader(new FileReader(traceFile))
		try {
			var String line = r.readLine()
			while (line !== null) {
				if (!line.isEmpty() && !line.startsWith("#")) {
					val String[] parts = line.split("\\|", -1)
					if (parts.length >= 3) {
						val String tag = parts.get(0)
						val String key = unescape(parts.get(1))
						val String value = unescape(parts.get(2))

						switch (tag) {
							// ---- modelo1 (source) -> sourceResource
							case "SI": {
								val Object obj = fromURIFragmentString(value, sourceResource)
								if (obj !== null) T_sourceInstanceFromSourceId.put(key, obj)
							}
							case "ST": {
								val ArrayList<Object> list = new ArrayList<Object>()
								for (u : split(value)) list.add(fromURIFragmentString(u, sourceResource))
								T_sourceInstance.put(key, list)
							}
							// ---- modelo2 (target) -> targetResource
							case "TI": {
								val Object obj = fromURIFragmentString(value, targetResource)
								if (obj !== null) T_targetInstanceFromTargetId.put(key, obj)
							}
							case "TT": {
								val ArrayList<Object> list = new ArrayList<Object>()
								for (u : split(value)) list.add(fromURIFragmentString(u, targetResource))
								T_targetInstance.put(key, list)
							}
							// ---- ids, no necesitan Resource
							case "TE": {
								T_targetElement.put(key, new ArrayList<String>(split(value)))
							}
							case "SE": {
								T_sourceElement.put(key, new ArrayList<String>(split(value)))
							}
							default: {
								// etiqueta desconocida: ignorar
							}
						}
					}
				}
				line = r.readLine()
			}
		} finally {
			r.close()
		}
	}

	// ==================================================================
	//  Utilidades privadas
	// ==================================================================

	/**
	 * Convierte un Object de la traza en su URI String.
	 * - EObject con Resource -> URI completa (EcoreUtil.getURI).
	 * - EObject sin Resource -> null + aviso por consola.
	 * - Otro Object -> "obj:" + toString().
	 *
	 * 'contextResource' es el Resource del modelo al que pertenece el objeto
	 * (source o target). No se usa para calcular la URI en sí (EcoreUtil.getURI
	 * ya lo hace), pero se recibe para poder comprobar que el objeto pertenece
	 * al recurso esperado y avisar si no.
	 */
	def private String toURIFragmentString(Object o, Resource contextResource) {
    	if (o === null) return null
    	if (o instanceof EObject) {
        	val EObject eo = o as EObject
        	val Resource res = eo.eResource()
        	if (res === null) {
            	System.err.println("[Trace] Aviso: EObject sin Resource, no se puede persistir: " + eo)
            	return null
        	}
        	if (contextResource !== null && res !== contextResource) {
            	//System.err.println("[Trace] Aviso: el EObject pertenece a otro Resource distinto del esperado. " +
                //	"Esperado=" + contextResource.getURI() + " real=" + res.getURI())
        	}
        	return res.getURIFragment(eo)   // <-- fragmento, no URI completa
    	}
    	return "obj:" + o.toString()
	}

	/**
	 * Recupera un Object a partir de la cadena almacenada.
	 * La URI se resuelve contra el ResourceSet del 'contextResource'
	 * (que es el Resource del modelo correspondiente).
	 */
	def private Object fromURIFragmentString(String s, Resource contextResource) {
    	if (s === null || s.isEmpty()) return null
    	if (s.startsWith("obj:")) {
        	return s.substring(4)
    	}
    	if (contextResource === null) {
        	System.err.println("[Trace] Aviso: no hay Resource de contexto para resolver el fragmento: " + s)
        	return null
    	}
    	try {
        	val EObject eo = contextResource.getEObject(s)   // <-- resolver contra el Resource
        	if (eo === null) {
            	System.err.println("[Trace] Aviso: no se pudo resolver el fragmento: " + s)
        	}
        	return eo
    	} catch (Exception ex) {
        	System.err.println("[Trace] Error resolviendo el fragmento: " + s + " -> " + ex.message)
        	return null
    	}
	}

	def private String join(List<String> list) {
		if (list === null || list.isEmpty()) return ""
		val StringBuilder sb = new StringBuilder()
		for (var int i = 0; i < list.size(); i++) {
			if (i > 0) sb.append(',')
			val String s = list.get(i)
			sb.append(if (s === null) "" else s)
		}
		return sb.toString()
	}

	def private List<String> split(String s) {
		if (s === null || s.isEmpty()) return new ArrayList<String>()
		return Arrays.asList(s.split(",", -1))
	}

	def private String escape(String s) {
		if (s === null) return ""
		return s.replace("\\", "\\\\").replace("|", "\\p").replace("\n", "\\n")
	}

	def private String unescape(String s) {
		if (s === null) return ""
		return s.replace("\\n", "\n").replace("\\p", "|").replace("\\\\", "\\")
	}
	
	def static void main(String[] args) {
        println("Hola")
    }
}