package NpsSDK;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String nodeName;
	private String nodeType;
	private Node arrayBaseType;
	private Boolean isArray;
	private Boolean isMandatory;
	public List<Node> children = new ArrayList<Node>();
	public Boolean isSimpleType;

	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	public Node getArrayBaseType() {
		return arrayBaseType;
	}
	public void setArrayBaseType(Node arrayBaseType) {
		this.arrayBaseType = arrayBaseType;
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
	
	public List<Node> getChildren() {
		return children;
	}
	public void addChild(Node child) {		
		children.add(child);
	}
	
	public Boolean isSimpleType() {
		return isSimpleType;
	}
	public void setIsSimpleType(Boolean isSimpleType) {
		this.isSimpleType = isSimpleType;
	}



}
