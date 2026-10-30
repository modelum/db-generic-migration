package um.modelum.migration.data

import java.util.List
import java.util.TreeMap
import java.util.Comparator
import java.util.Map
import java.util.HashMap

class ComparatorKeys {
	
	// singleton
	public static final ComparatorKeys INSTANCE = new ComparatorKeys()
	
	// keys added since relationalSquema
	Map<String, List<String>> keys = new HashMap<String, List<String>>()
	
	def ComparatorKeys()
	{
		
	}
	
	def void addKeys(String entityName, List<String> keys)
	{
		this.keys.put(entityName, keys)
	}
	
	def getComparator(String entityName)
	{
		return new Comparator<TreeMap<String, String>>() {
			override int compare(TreeMap<String, String> map1, TreeMap<String, String> map2) {
				// compare with the columns of entity's pks
				for (pkName : keys.get(entityName)) {
					val value1 = map1.get(pkName)
					val value2 = map2.get(pkName)

					
					val comparison = value1.compareTo(value2)
					if (comparison != 0) {
						return comparison // if are the same, next, otherwhise select this result
					}
				}
				return 0 // imposible case
			}
		}
	}
}