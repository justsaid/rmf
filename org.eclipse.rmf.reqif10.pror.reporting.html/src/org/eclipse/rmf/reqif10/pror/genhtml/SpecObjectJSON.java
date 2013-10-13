package org.eclipse.rmf.reqif10.pror.genhtml;

import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "hierarchyId","objectId", "parentId", "attributes" })
public class SpecObjectJSON {

	private String objectId;
	private String hierarchyId;
	private String parentId;
	private String level;
	private List<SpecAttributeJSON> attributes;
	
	
	public void setLevel(String level) {
		this.level = level;
	}
	public String getLevel() {
		return level;
	}
	public void setHierarchyId(String hierarchyId) {
		this.hierarchyId = hierarchyId;
	}
	public String getHierarchyId() {
		return hierarchyId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setAttributes(List<SpecAttributeJSON> attributes) {
		this.attributes = attributes;
	}
	public List<SpecAttributeJSON> getAttributes() {
		return attributes;
	}
	
//	public static SpecObjectJSON createDefaultSpecObject(){
//		SpecObjectJSON specObj = new SpecObjectJSON();
//		specObj.setObjectId("123");
//		specObj.setParentId("-1");
//		specObj.setAttributes(createDefaultAttributes());
//
//		return specObj;
//	}
	
//	public static List<SpecAttributeJSON> createDefaultAttributes(){
//		List<SpecAttributeJSON> attributes = new ArrayList<SpecAttributeJSON>();
//		attributes.add(new SpecAttributeJSON("Name", "Req 1",FormatType.TEXT));
//		attributes.add(new SpecAttributeJSON("Desc.", "Req description ",FormatType.TEXT));
//		attributes.add(new SpecAttributeJSON("Priority", "5",FormatType.INTEGER));
//		return attributes;
//	}
}
