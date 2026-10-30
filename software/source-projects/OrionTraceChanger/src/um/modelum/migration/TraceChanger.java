package um.modelum.migration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import documentschema.DocumentschemaPackage;
import relationalschema.RelationalschemaPackage;
import uschema.Aggregate;
import uschema.Attribute;
import uschema.DataType;
import uschema.EntityType;
import uschema.Feature;
import uschema.Reference;
import uschema.SchemaType;
import uschema.USchema;
import uschema.UschemaFactory;
import uschema.UschemaPackage;
/**
 * IMPORTANTE: saveUpdateModel machaca con las modificaciones el archivo .xmi original del modelo uschema
 * IMPORTANTE: saveUpdateTrace machaca con la traza actualizada el archivo de la traza original
 */

/*-OJO!!!!!  Falta añadir una operación DELETE??------------------------------------------------------------------*/

public class TraceChanger {
	private Trace trace=new Trace();
	private Resource sResource,tResource;
	private String traceFile;
	private boolean targetModelUpdate=false;
	
	public TraceChanger() {
		// Registra los ecore de los metamodelos en el EPackage Registry global de EMF
		//A partir del código de las clases generadas en genmodel
		RelationalschemaPackage.eINSTANCE.eClass();
		UschemaPackage.eINSTANCE.eClass();
		DocumentschemaPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Carga la traza y los modelos en memoria
	 * Actualiza los recursos con los EObject en sResource para source model y tResource para target model
	 * @param traceFile
	 * @param sourceModelFile
	 * @param targetModelFile
	 */
	
	public void loadTrace(String traceFile, String sourceModelFile, String targetModelFile) {
		this.traceFile=traceFile;
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		ResourceSet srcRS = new ResourceSetImpl();
		ResourceSet tgtRS = new ResourceSetImpl();
		sResource = srcRS.getResource(URI.createFileURI(sourceModelFile), true);
		tResource = tgtRS.getResource(URI.createFileURI(targetModelFile), true);
		try {
			trace.loadTrace(traceFile, sResource, tResource);
		} catch (IOException e) { e.printStackTrace(); }
		targetModelUpdate=false;
	}
	
	/**
	 * Disaggregate an Entitytype non-root from another EntityType
	 * @param idParent - identifier of Parent (e.g. app_user)
	 * @param idAggregate - identifier of Child (e.g. 'app_user.playlists')
	 * 
	 * Mark Child as a root entity and creates a reference in Parent to the child
	 * update all necessary properties in the model
	 * update trace
	 */
	public void disaggregate(String idAggregate) {
		//-->1)obtener el objeto fuente y su id
		
		//Retrieve from trace the source elements from which was created the aggregate
		//Ej:playlist.playlist_fk_user(relationalschema.FKey) -> app_user.playlist(uschema.Aggregate)
		//Guardar los identificadores origen y destino "playlist.playlist_fk_user" y "app_user.playlist"
		List<String> origenIds=trace.getSourceElement(idAggregate); //lista de identificadores del modelo origen que lo crearon
																	//ej: playlist.playlist_fk_user
		List<Object> sourceInstances=new ArrayList<Object>();
		//obtiene los EObject del modelo source que tienen esos Id
		//(ej:obtiene el EObject de la fk) 
		for(String id:origenIds) {
			Object origenInstance=trace.getSourceInstanceFromSourceId(id);
			sourceInstances.add(origenInstance);
		}
		
		//-->2) obtener el objeto Aggregate
		Aggregate ag=(Aggregate) trace.getTargetInstanceFromTargetId(idAggregate);
		
		//-->3) obtener el entitytype del agregado
		EntityType child=(EntityType) ag.getSpecifiedBy();
		
		//-->4) Convertir en root entity
		child.setRoot(true);
		
		//-->5) Obtener el entitytype del padre
		EntityType parent=(EntityType) ag.getOwner();
		
		//-->6) Crear una referencia en el padre hacia la nueva entidad root del hijo
		UschemaFactory factory = UschemaFactory.eINSTANCE;
		Reference ref=factory.createReference();
		ref.setRefsTo(child);
		ref.setUpperBound(ag.getUpperBound());
		ref.setLowerBound(ag.getLowerBound());
		ref.setName(child.getName()+"s");    //ej:playlists
		parent.getFeatures().add(ref);
		
		//-->7) Borra de la traza el aggregate
		trace.removeTargetElementFromTrace(idAggregate);
		
		//->8) Eliminar del modelo target el EObject del aggregate
		parent.getFeatures().remove(ag);
		child.setAggregate(null); //elimina la referencia inversa también
		//(si no funciona usar EcoreUtil.delete(ag, true);  que elimina el Aggregate y cualquier otra referencia residual 
		
		//->9) Añadir en la traza la referencia nueva
		//se hace un add para cada uno de los elementos del fuente
		//(normalmente sólo habrá uno)
		for (int i=0;i<origenIds.size();i++) {
			trace.addTrace(origenIds.get(i), sourceInstances.get(i), parent.getName()+"."+ref.getName(), ref);
		}	
	}
	
	/**
	 * renombra un objeto en Uschema. Sólo funciona con named elements
	 * @param oldId- identificador antiguo ej:user.name
	 * @param newId- identificador nuevo ej:user.nombre
	 */
	public void rename(String oldId, String newId) {
		//-->1) Recupera el objeto a renombrar
		Object elemento=trace.getTargetInstanceFromTargetId(oldId);
		String nuevoNombre=lastName(newId);
		
		//-->2) Comprueba que es un namedElement
		if (elemento instanceof Feature ) {
			System.out.println("renombrar feature");
			Feature f=(Feature) elemento;
			f.setName(nuevoNombre);
		} else if (elemento instanceof SchemaType) {
			System.out.println("renombrar SchemaType");
			SchemaType s=(SchemaType) elemento;
			s.setName(nuevoNombre);
		} else if (elemento instanceof USchema) {
			System.out.println("renombrar USchema");
			USchema u=(USchema) elemento;
			u.setName(nuevoNombre);
		} else {
			System.out.println("No se puede renombrar un "+trace.getType(elemento));
			return;
		}
		trace.renameTargetPrefix(oldId, newId);
	}
	
	/**
	 * Delete an attribute from model
	 * It must have no references or key to be deleted
	 * @param attributeId- id of the attribute (e.g. user.name)
	 */
	public void removeAttribute(String attributeId) {
		//-->1) Recupera el attributo a borrar
				Object elemento=trace.getTargetInstanceFromTargetId(attributeId);
				if (!(elemento instanceof Attribute)) {
					System.out.println("'"+attributeId+"' is not an attribute: ("+trace.getType(elemento));
					return;
				}
		//-->2) Comprueba si tiene referencias
				Attribute at=(Attribute) elemento;
				if (at.getReferences()!=null && at.getReferences().size()>0) {
					System.out.println("'"+attributeId+"' can't be delete because it has references");
					return;
				}
		//-->2b) Comprueba si pertenece a una key
				if (at.getKey()!=null ) {
					System.out.println("'"+attributeId+"' can't be delete because it has a key");
					return;
				}
		//-->3) Borra su tipo primitivo del modelo y lo quita de la traza
				DataType dt=at.getType();
				String dtType=trace.getIdFromTargetInstance(dt); //obtener su id en la traza
				//Si no es un tipo primitivo tendrá otros hijos, que al ser relación de contenido se borrarán también
				EcoreUtil.delete(dt); //borra el objeto y desconecta todas sus referencias
				trace.removeTargetElementFromTrace(dtType);
				
		//-->4) Borra el atributo del modelo y lo quita de la traza
				EcoreUtil.delete(at);
				trace.removeTargetElementFromTrace(attributeId);				
	}
	
	private static String lastName(String text) {
	    
	    int ultimoPunto = text.lastIndexOf('.');
	    
	    // Si no contiene puntos o el punto es el último carácter, no busca y seguirá
	    if (ultimoPunto != -1 && ultimoPunto < text.length() - 1) {
	        return text.substring(ultimoPunto + 1);
	    }
	    
	    return text; //si no tiene puntos lo devuelve completo
	}
			
	public void saveUpdateTargetModel() {
		try {
			tResource.save(null);
		} catch (IOException e) { e.printStackTrace(); }
        System.out.println("Se ha actualizado el modelo uschema: "+tResource.getURI());
        targetModelUpdate=true;
	}
	
	public void saveUpdateTrace() {
		if (!targetModelUpdate) {
			System.out.println("Updated target model must be saved first!!!");
			return;
		}
		try {
			trace.saveTrace(traceFile, sResource, tResource);
		} catch (IOException e) { e.printStackTrace(); }
		System.out.println("Se ha actualizado la traza: "+traceFile);
	}
	
	public Trace getTrace() {return trace;}
	
}
