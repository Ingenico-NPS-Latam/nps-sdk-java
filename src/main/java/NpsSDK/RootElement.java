package NpsSDK;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
    	
    	return joinList("", concatenatedValues);
    }   
	
	
	
	@Override
	public String serialize() {
		String serial = "";
		for(BaseElement child : this.getChildren()){
			serial += child.serialize();
		}		
		return serial;
	}

	public String secureHash(String secretKey) throws WsdlHandlerException {
		char[] secure_hash = this.getHmac256Hash(secretKey);
		return String.valueOf(secure_hash);
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

    private char[] getHmac256Hash(String secretKey) {
    	byte[] hash = null;
    	try {
    		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			hash = sha256_HMAC.doFinal(this.getSecureHashConcatenatedValues().getBytes());
		} catch (Exception e) {
    		System.out.println(e);
		}
		return Hex.encodeHex(hash);
	}

	private char[] getHmac512Hash(String secretKey) {
		byte[] hash = null;
		try {
			Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
			SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA512");
			sha512_HMAC.init(secret_key);
			hash = sha512_HMAC.doFinal(this.getSecureHashConcatenatedValues().getBytes());
		} catch (Exception e) {
			System.out.println(e);
		}
		return Hex.encodeHex(hash);
	}
}
