package IngenicoSDK;

import java.util.ArrayList;
import java.util.List;

public class SimpleElement extends BaseElement
{
	private String value;
	
	@Override
	public String serialize() {
		return String.format("<%1$s>%2$s</%1$s>", this.getName(), value);
	}	
	
	void trim(int maxLength){
		if (value.length() > maxLength){
			value = value.substring(0, maxLength);
		}
	}
	
	private static List<BaseElement> emptyChildren = new ArrayList<BaseElement>();
	
	@Override
	List<BaseElement> getChildren() {
		return emptyChildren;
	}
	
	
	@Override
	String getConcatenatedValues() {
		return this.value;
	}  
	
    public SimpleElement(String name, String value) {
		this.setName(name);
		this.value = value;
	}
  
}

	