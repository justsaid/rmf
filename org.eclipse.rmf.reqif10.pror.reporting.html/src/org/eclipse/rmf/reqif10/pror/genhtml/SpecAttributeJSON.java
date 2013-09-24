package org.eclipse.rmf.reqif10.pror.genhtml;

public class SpecAttributeJSON {
	private String attrName;
	private String attrValue;
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getAttrName() {
		return attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	
	public SpecAttributeJSON(String attrName, String attrValue) {
		this.attrName = attrName;
		this.attrValue = attrValue;
	}
	
}
