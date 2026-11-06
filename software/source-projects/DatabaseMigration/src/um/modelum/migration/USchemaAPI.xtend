package um.modelum.migration;

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import uschema.USchema
import uschema.UschemaPackage
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import uschema.UschemaFactory
import org.eclipse.emf.common.util.URI
import uschema.EntityType
import uschema.RelationshipType
import uschema.SchemaType
import uschema.Attribute
import uschema.Key

class USchemaAPI {
	ResourceSet usResourceSet;
	Resource usResource; // Contains all target elements 
	USchema uschema=null; 
	
	new(){
        UschemaPackage.eINSTANCE.eClass()
	}

	def USchema createTargetModel(String path) {
        usResourceSet = new ResourceSetImpl();
        usResourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("xmi", new XMIResourceFactoryImpl());
        usResource = usResourceSet.createResource(URI.createFileURI(path));
        uschema = UschemaFactory.eINSTANCE.createUSchema; 
        usResource.getContents().add(uschema);
        return uschema;
	}
	
	def EntityType getEntityType (String name) {
		var result = uschema.entities.stream.filter[e|e.name==name].findFirst()
		result.isPresent() ? return result.get() : return null
	}
	
	def RelationshipType getRelationshipType (String name) {
		var result = uschema.relationships.stream.filter[r|r.name.equals(name)].findFirst()
		result.isPresent() ? return result.get() : return null
	}
	
	def SchemaType getSchemaType(String name) {
		var SchemaType result=null;
		result = getEntityType(name);
		result===null ? return getRelationshipType(name)
		return result
	}
	
	def Attribute getAttribute(SchemaType type, String name) {
		var result=type.features.stream.filter[f|f.name.equals(name) && f instanceof Attribute].findFirst
		result.isPresent() ? return result.get() as Attribute : return null
	}
	
	def Key getEntityKeyID(SchemaType type) {
		var keyID=type.features.stream.filter[f| f instanceof Key && (f as Key).isID].findFirst
		keyID.isPresent() ? keyID.get() as Key : return null
	}
	
	//get the position order of an attribute inside a Key
	def int getAttributePosinKey(Attribute att, Key key) {
		key.attributes.indexOf(att); //0 for first position, -1 if not found
	}
	
	// Guarda el modelo en el archivo XMI
	def void saveModel(){
        try {
            usResource.save(null);
            System.out.println("Modelo uschema creado en formato XMI.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }		
    
    
}