package NpsSDK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ComplexElementArray extends BaseElement {

	private String _childType;
	
	void setChildType(String childType){ this._childType = childType; }
	 

	public ComplexElementArray() {
		this._children = new ArrayList<BaseElement>();
		this._typedChildren = new ArrayList<ComplexElementArrayItem>();
	}

	private List<BaseElement> _children;

	@Override
	List<BaseElement> getChildren() {
		return _children;
	}
	
	private List<ComplexElementArrayItem> _typedChildren;
	
	List<ComplexElementArrayItem> getTypedChildren(){
		return _typedChildren;
	}

	@Override
	public String serialize() {
		String serial = "";
		for (BaseElement child : this.getChildren()) {
			serial += String.format("<item xsi:type=\"q1:%1$s\">%2$s</item>", this._childType,child.serialize());
		}

		return String.format("<%1$s q2:arrayType=\"q3:%2$s[%3$s]\">%4$s</%1$s>", this.getName(), this._childType, this._children.size(), serial);
	}

	@Override
	String getConcatenatedValues() {
		List<BaseElement> elements = new ArrayList<BaseElement>(this.getChildren());

		Collections.sort(elements, new Comparator<BaseElement>() {
			@Override
			public int compare(final BaseElement element1, final BaseElement element2) {
				return element1.getName().compareTo(element2.getName());
			}
		});

		List<String> concatenatedValuesList = new ArrayList<String>();
		for (BaseElement element : elements) {
			concatenatedValuesList.add(element.getConcatenatedValues());
		}
		return joinList("", concatenatedValuesList);
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

	public void add(ComplexElementArrayItem complexElementArrayItem) {
		this._typedChildren.add(complexElementArrayItem);
		this.getChildren().add(complexElementArrayItem);
	}

}
