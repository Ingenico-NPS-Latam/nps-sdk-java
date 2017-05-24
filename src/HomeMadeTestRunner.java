import NpsSDK.WsdlHandlerException;


public class HomeMadeTestRunner {

	public static void main(String[] args) throws WsdlHandlerException {
		
       SdkHomeMadeTest t = new SdkHomeMadeTest(); 
       //t.payOnline_2p(sdk); // el getValue de psp_fraudScreening devuelve cosas concatenadas
       //t.createClientSession(); // funciona de forma esperada
       //t.createPaymentMethodToken();
       //t.createPaymentMethod();
       //t.splitPayOnline_3p();
       
       //t.createCustomer();
       
       t.retrieveCustomer();
       
       
       
	}
	
}
