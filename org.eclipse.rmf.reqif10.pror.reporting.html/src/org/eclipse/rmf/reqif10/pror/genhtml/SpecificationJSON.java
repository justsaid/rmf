package org.eclipse.rmf.reqif10.pror.genhtml;

import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "specObjects", "specRelations" })
public class SpecificationJSON {

	private List<SpecObjectJSON> specObjects;
	private List<SpecRelationJSON> specRelations;
	
	public List<SpecObjectJSON> getSpecObjects() {
		return specObjects;
	}
	public void setSpecObjects(List<SpecObjectJSON> specObjects) {
		this.specObjects = specObjects;
	}
	public List<SpecRelationJSON> getSpecRelations() {
		return specRelations;
	}
	public void setSpecRelations(List<SpecRelationJSON> specRelations) {
		this.specRelations = specRelations;
	}
	
	
}
