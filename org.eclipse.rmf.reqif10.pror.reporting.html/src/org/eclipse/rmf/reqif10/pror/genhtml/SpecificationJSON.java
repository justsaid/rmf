package org.eclipse.rmf.reqif10.pror.genhtml;

import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "specification", "relations" })
public class SpecificationJSON {

	private List<SpecObjectJSON> specification;
	private List<SpecRelationJSON> relations;
	

	public void setSpecification(List<SpecObjectJSON> specification) {
		this.specification = specification;
	}
	public List<SpecObjectJSON> getSpecification() {
		return specification;
	}
	public void setRelations(List<SpecRelationJSON> relations) {
		this.relations = relations;
	}
	public List<SpecRelationJSON> getRelations() {
		return relations;
	}
	
}
