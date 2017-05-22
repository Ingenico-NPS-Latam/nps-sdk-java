package IngenicoSDK;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RootElement extends ComplexElement{

	/*public RootElement() {
		super("");
	}*/
	
	
    private String getSecureHashConcatenatedValues(){
    	
    	List<SimpleElement> simpleElements = new ArrayList<SimpleElement>();

    	for(BaseElement element : this.getChildren()){
    		if (element instanceof SimpleElement){
    			simpleElements.add((SimpleElement) element); 
    		}    		
    	}
    	
    	Collections.sort(simpleElements, new Comparator<SimpleElement>() {
		      @Override
		      public int compare(final SimpleElement element1, final SimpleElement element2) {
		          return element1.getName().compareTo(element2.getName());
		      }
		  });
    	
    	List<String> concatenatedValues = new ArrayList<String>();
    	for(SimpleElement simpleElement : simpleElements){
    		concatenatedValues.add(simpleElement.getConcatenatedValues());
    	}
    	
    	return String.join("", concatenatedValues);
    }   
	
	
	
	@Override
	public String serialize() {
		String serial = "";
		for(BaseElement child : this.getChildren()){
			serial += child.serialize();
		}		
		return String.join("", serial);
	}

	
	public String secureHash(String secretKey) throws WsdlHandlerException { 
		return this.getMd5Hash(this.getSecureHashConcatenatedValues() + secretKey); 
	}

    private static MessageDigest getMessageDigest() throws WsdlHandlerException{
    	MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new WsdlHandlerException(e);
		}

    	return md5;
    }
    
    private String getMd5Hash(String input) throws WsdlHandlerException
    {    	
	    byte[] data = null;
		try {
			data = getMessageDigest().digest(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new WsdlHandlerException(e);
		}
        StringBuilder sBuilder = new StringBuilder();
        for (byte b : data) { sBuilder.append(String.format("%02X", b)); }
        return sBuilder.toString().toLowerCase();
    }  

}
