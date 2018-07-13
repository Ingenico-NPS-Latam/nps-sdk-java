package NpsSDK;

import java.util.ArrayList;
import java.util.List;

public class SimpleElement extends BaseElement
{
	private String value;

	private String escapeSpecialCharacters(String value){
		value = value.replace("&", "&amp;");
		value = value.replace("<", "&lt;");
		value = value.replace(">", "&gt;");
		return value;
	}

	@Override
	public String serialize() {
		return String.format("<%1$s>%2$s</%1$s>", this.getName(), this.escapeSpecialCharacters(value));
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

	