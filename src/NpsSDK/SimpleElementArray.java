package NpsSDK;

import java.util.ArrayList;
import java.util.List;

public class SimpleElementArray extends BaseElement{
	
	private List<String> values;
	
	@Override
	public String serialize() {
		return String.format("<%1$s>%2$s</%1$s>", this.getName(), String.join(",", values)); 
	}
	
	@Override
	String getConcatenatedValues() {
		return String.join(",", this.values);
	}
	
	private static List<BaseElement> emptyChildren = new ArrayList<BaseElement>();

	@Override
	List<BaseElement> getChildren() {
		return emptyChildren;
	}
	
	
	/*public SimpleElementArray(String name) {
		super(name);
	}*/
	
	public void add(String value) { values.add(value); }
	
    public String getValue(int index){
    	return values.get(index);
    }

}
