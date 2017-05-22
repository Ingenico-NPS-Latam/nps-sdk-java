package IngenicoSDK;

import java.util.ArrayList;
import java.util.List;

public class ComplexType {

	
	public ComplexType(){
		attributes = new ArrayList<Attribute>();		
	}
	
	private String typeName;
	private Boolean isArray;
	private Boolean isMandatory;
	private List<Attribute> attributes;
	
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	public Boolean isArray() {
		return isArray;
	}
	public void setIsArray(Boolean isArray) {
		this.isArray = isArray;
	}
	
	public Boolean isMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(Boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	
	
	
}
