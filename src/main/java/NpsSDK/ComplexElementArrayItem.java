package NpsSDK;

public class ComplexElementArrayItem extends ComplexElement{

	/*public ComplexElementArrayItem() {
		super("");
	}*/
	
	@Override
	public String serialize() {
		String serial = "";
		for(BaseElement child : this.getChildren()){
			serial += child.serialize();
		}		
		return serial;
	}

}