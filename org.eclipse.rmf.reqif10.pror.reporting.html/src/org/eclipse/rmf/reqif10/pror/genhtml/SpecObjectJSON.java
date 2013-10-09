package org.eclipse.rmf.reqif10.pror.genhtml;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "parentId", "attributes" })
public class SpecObjectJSON {

	private String id;
	private String parentId;
	private List<SpecAttributeJSON> attributes;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public static SpecObjectJSON createSpecObject(String specObjId, String parentId, List<SpecAttributeJSON> attributes){
		SpecObjectJSON specObj = new SpecObjectJSON();
		specObj.setId(specObjId);
		specObj.setParentId(parentId);
		specObj.setAttributes(attributes);
		return specObj;
	}
	public static SpecObjectJSON createDefaultSpecObject(){
		SpecObjectJSON specObj = new SpecObjectJSON();
		specObj.setId("123");
		specObj.setParentId("-1");
		specObj.setAttributes(createDefaultAttributes());

		return specObj;
	}
	
	public static List<SpecAttributeJSON> createDefaultAttributes(){
		List<SpecAttributeJSON> attributes = new ArrayList<SpecAttributeJSON>();
		attributes.add(new SpecAttributeJSON("Name", "Req 1"));
		attributes.add(new SpecAttributeJSON("Desc.", "Req description "));
		attributes.add(new SpecAttributeJSON("Priority", "5"));
		
		return attributes;
	}
}
