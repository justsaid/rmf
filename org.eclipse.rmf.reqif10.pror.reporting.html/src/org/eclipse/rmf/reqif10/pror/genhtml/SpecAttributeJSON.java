package org.eclipse.rmf.reqif10.pror.genhtml;

import org.eclipse.rmf.reqif10.pror.genexcel.FormatType;

public class SpecAttributeJSON {
	private String attrName;
	private String attrValue;
	private FormatType attrType;
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
	
	public void setAttrType(FormatType attrType) {
		this.attrType = attrType;
	}
	public FormatType getAttrType() {
		return attrType;
	}
	
	public SpecAttributeJSON(String attrName, String attrValue, FormatType attrType) {
		this.attrName = attrName;
		this.attrValue = attrValue;
		this.attrType = attrType;
	}
	
}
