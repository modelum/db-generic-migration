package um.modelum.migration.data;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import documentschema.DocumentType;
import documentschema.Embedded;
import documentschema.Field;
import documentschema.Reference;
import um.modelum.migration.Trace;

public class DocumentBrowser {

	private Trace trace;

	public DocumentBrowser() {

	}

	public DocumentBrowser(Trace trace) {
		this.trace = trace;
	}

	// this methods are using the generic method, to better access out
	public List<Field> getAttributes(String parentName, boolean isAgg) {

		return getPropertiesByParentName(parentName, Field.class, isAgg, attr -> !((Field) attr).isIsKey());
	}
	
	public List<Field> getKey(String parentName, boolean isAgg)
	{
		return getPropertiesByParentName(parentName, Field.class, isAgg, attr -> ((Field) attr).isIsKey());
	}
	
	// with the keys too
	public List<Field> getAllAtributes(String parentName, boolean isAgg)
	{
		return getPropertiesByParentName(parentName, Field.class, isAgg, attr -> true);
	}

	public List<Reference> getReferences(String parentName, boolean isAgg) {
		return getPropertiesByParentName(parentName, Reference.class, isAgg, ref -> true);
	}

	public List<Embedded> getAggregates(String parentName, boolean isAgg) {
		return getPropertiesByParentName(parentName, Embedded.class, isAgg, agg -> true);
	}

	// get the propertie selected(attribute, reference or aggregate), aditionalfilter is for attribute.
	public <T> List<T> getPropertiesByParentName(String parentName, Class<T> type, boolean isAgg,
			Predicate<T> aditionalFilter) {
		List<T> list;
		// if isAgg is false, then cast to entity, else to aggregate, is the same but the access change (getProperties or getAggregates)
		if (!isAgg) {
			DocumentType entity = (DocumentType) trace.getTargetInstance(parentName).get(0);
			list = entity.getProperties().stream()
					.filter(p -> type.isInstance(p))
					.map(type::cast)
					.filter(aditionalFilter)
					.collect(Collectors.toList());
		} else {
			Embedded agg = (Embedded) trace.getTargetInstance(parentName).get(0);
			list = agg.getAggregates().stream()
					.filter(p -> type.isInstance(p))
					.map(type::cast)
					.filter(aditionalFilter)
					.collect(Collectors.toList());
		}

		return list;
	}

}
