package org.eclipse.rmf.reqif10.pror.genhtml;

import java.util.ArrayList;
import java.util.List;

public class SpecRelationJSON {

	private String id;
	private String source;
	private String target;
	private List<SpecAttributeJSON> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAttributes(List<SpecAttributeJSON> attributes) {
		this.attributes = attributes;
	}

	public List<SpecAttributeJSON> getAttributes() {
		return attributes;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public static SpecRelationJSON createSpecObject(String specObjId,
			String parentId, List<SpecAttributeJSON> attributes) {
		SpecRelationJSON specObj = new SpecRelationJSON();
		specObj.setId(specObjId);
		specObj.setAttributes(attributes);
		return specObj;
	}

	public static SpecRelationJSON createDefaultSpecObject() {
		SpecRelationJSON specObj = new SpecRelationJSON();
		specObj.setId("123");
		specObj.setAttributes(createDefaultAttributes());

		return specObj;
	}

	public static List<SpecAttributeJSON> createDefaultAttributes() {
		List<SpecAttributeJSON> attributes = new ArrayList<SpecAttributeJSON>();
		attributes.add(new SpecAttributeJSON("Name", "Req 1"));
		attributes.add(new SpecAttributeJSON("Desc.", "Req description "));
		attributes.add(new SpecAttributeJSON("Priority", "5"));

		return attributes;

	}
}