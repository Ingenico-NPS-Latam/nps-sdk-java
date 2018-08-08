package NpsSDK;

import org.bouncycastle.jcajce.provider.symmetric.ARC4;

import java.util.*;


public class ComplexElement extends BaseElement
{
    private Map<String, BaseElement> _childrenHash;
    private List<BaseElement> _children;
    
	@Override
	List<BaseElement> getChildren() {
		return _children;
	}
    
	@Override
	public String serialize() {
		String serial = "";
		for(BaseElement child : this.getChildren()){
			serial += child.serialize();
		}		
		return String.format("<%1$s>%2$s</%1$s>", this.getName(), serial);
	
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
		for(BaseElement element : elements){
			concatenatedValuesList.add(element.getConcatenatedValues());
		}
		return joinList("", concatenatedValuesList);
	}
	
	protected String joinList(String delimiter, List<String> stringList){
		StringBuilder result = new StringBuilder();
		for(int i = 0; i<stringList.size()-1;i++){
			result.append(stringList.get(i));
			result.append(delimiter);
		}
		result.append(stringList.get(stringList.size()-1));
		return result.toString();
	}

    public ComplexElement() {
    	_childrenHash = new HashMap<String,BaseElement>();
		_children = new ArrayList<BaseElement>();
	}
   

    //public void add(BaseElement baseElement) { this.getChildren().add(baseElement); }
    public void add(String name, String value) { 
    	SimpleElement simpleElement = new SimpleElement(name, value);
    	_childrenHash.put(name, simpleElement);
    	_children.add(simpleElement); 
    }
    
    public void add(String name, BaseElement baseElement){
    	baseElement.setName(name);
    	_childrenHash.put(name, baseElement);
    	_children.add(baseElement);
    }
	
    
    public BaseElement getElement(String index){
    	if (_childrenHash.containsKey(index)){
    		return _childrenHash.get(index);
    	}
    	return null;
    }
    
    public String getValue(String index){
    	BaseElement child = this.getElement(index);
    	if (child != null && child instanceof SimpleElement){
    		return child.getConcatenatedValues();
    	}
    	return null;
    }

	private void removeElementFromMap(String index) {
		if(_childrenHash.containsKey(index)) {
			_childrenHash.remove(index);
		}
	}

	private Boolean isElementInList(String index) {
		for(BaseElement child: this.getChildren()) {
			if(child.getName().equals(index)) {
				return true;
			}
		}
		return false;
	}

	private void removeElementFromList(String index) {
		if(isElementInList(index)) {
			for (Iterator<BaseElement> iterator = this.getChildren().listIterator(); iterator.hasNext(); ) {
				if (iterator.next().getName().equals(index)) {
					iterator.remove();
				}
			}
		}
	}


	public void removeSecureHash(){
		for(Iterator<String> iterator = _childrenHash.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			if(key.toLowerCase().equals("psp_securehash")) {
				this.removeElementFromList(key);
				iterator.remove();
			}
		}
	}

    public <T extends BaseElement> T getChild(String index, Class<T> clazz){
    	BaseElement child = this.getElement(index); 
    	
    	if (clazz.isAssignableFrom(child.getClass())){
    		return clazz.cast(child);
    	}
    	return null;
		
    }    
    
    public ComplexElement getComplexElement(String index){
    	return getChild(index,ComplexElement.class);
    }
    
    public List<ComplexElementArrayItem> getComplexElementArray(String index){
    	return getChild(index,ComplexElementArray.class).getTypedChildren();
    }
    
    public SimpleElementArray getSimpleElementArray(String index){
    	return getChild(index,SimpleElementArray.class);
    }

    

}