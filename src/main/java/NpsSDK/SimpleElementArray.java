package NpsSDK;

import java.util.ArrayList;
import java.util.List;

public class SimpleElementArray extends BaseElement{
	
	private List<String> values;
	
	@Override
	public String serialize() {
		return String.format("<%1$s>%2$s</%1$s>", this.getName(), joinList(",", this.values)); 
	}
	
	@Override
	String getConcatenatedValues() {
		return joinList(",", this.values);
	}
	
	private String joinList(String delimiter, List<String> stringList){
		StringBuilder result = new StringBuilder();
		for(int i = 0; i<stringList.size()-1;i++){
			result.append(stringList.get(i));
			result.append(delimiter);
		}
		result.append(stringList.get(stringList.size()-1));
		return result.toString();
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
