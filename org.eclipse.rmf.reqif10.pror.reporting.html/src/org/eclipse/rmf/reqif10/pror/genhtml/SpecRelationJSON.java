package org.eclipse.rmf.reqif10.pror.genhtml;

import java.util.List;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "label", "target", "source", "attributes" })
public class SpecRelationJSON {

	private String id;
	private String source;
	private String target;
	private String label;
	private List<SpecAttributeJSON> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setAttributes(List<SpecAttributeJSON> attributes) {
		this.attributes = attributes;
	}

	public List<SpecAttributeJSON> getAttributes() {
		return attributes;
	}

}