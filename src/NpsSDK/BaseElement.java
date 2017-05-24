package NpsSDK;

import java.util.List;

public abstract class BaseElement
{		
	 
    //protected BaseElement(String name) { this.name = name; }    
    
    private String name;
    String getName() {
    	return this.name;
    }
    void setName(String name){
    	this.name = name;
    }  
    public abstract String serialize();
    abstract List<BaseElement> getChildren();
    
    abstract String getConcatenatedValues(); 
    
    
    
}


