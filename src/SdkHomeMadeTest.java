import java.text.SimpleDateFormat;
import java.util.Date;

import NpsSDK.ComplexElement;
import NpsSDK.ComplexElementArray;
import NpsSDK.ComplexElementArrayItem;
import NpsSDK.ConsoleLogger;
import NpsSDK.ILogger;
import NpsSDK.NpsSdk;
import NpsSDK.RootElement;
import NpsSDK.WsdlHandlerConfiguration;
import NpsSDK.WsdlHandlerException;
import NpsSDK.ILogger.LogLevel;
import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;

public class SdkHomeMadeTest {

	String PSPVERSION = "2.2";
	String PSPMERCHANTID = "psp_test";
	private static String posDateTime;
	NpsSdk sdk = null;
	
	
	public SdkHomeMadeTest() throws WsdlHandlerException {
		Date dateTimeNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        posDateTime = formatter.format(dateTimeNow);
        ILogger consoleLogger = new ConsoleLogger();
        WsdlHandlerConfiguration wsdlHandlerConfiguration = new WsdlHandlerConfiguration(LogLevel.INFO, 
        		NpsEnvironment.sandbox, 
        		"swGYxNeehNO8fS1zgwvCICevqjHbXcwPWAvTVZ5CuULZwKWaGPmXbPSP8i1fKv2q", consoleLogger);
        sdk = new NpsSdk(wsdlHandlerConfiguration);
	}


	void payOnline_2p() throws WsdlHandlerException{
		RootElement rootElement = new RootElement();
		
		rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER69461-3" );
        rootElement.add("psp_MerchOrderId", "ORDER69461" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_CardNumber", "4507990000000010" );
        rootElement.add("psp_CardExpDate", "1612" );
        rootElement.add("psp_CardSecurityCode", "325" );
        rootElement.add("psp_PosDateTime", posDateTime);
		
		
        RootElement response = sdk.payOnLine_2p(rootElement);
        System.out.println(response.getValue("psp_FraudScreeningResult")); // esto devuelve campos concatenados :S
        
	}
	
	
	RootElement createClientSession() throws WsdlHandlerException{
		
		//Luce bien
		RootElement data = new RootElement();
		data.add("psp_Version", PSPVERSION );
        data.add("psp_MerchantId", PSPMERCHANTID );
        data.add("psp_PosDateTime", "2016-12-01 12:00:00");
              
    	System.out.println(posDateTime);
    	
		RootElement response = sdk.createClientSession(data);
		System.out.println(response.getValue("psp_ClientSession"));
		return response;
        
	}
	
	RootElement createPaymentMethodToken() throws WsdlHandlerException{
		RootElement CCSresponse  = createClientSession();
		
		
		RootElement data = new RootElement();
		data.add("psp_Version", PSPVERSION );
        data.add("psp_MerchantId", PSPMERCHANTID );
        data.add("psp_PosDateTime", "2016-12-01 12:00:00");
        data.add("psp_ClientSession", CCSresponse.getValue("psp_ClientSession"));
        
        ComplexElement cardInputDetails = new ComplexElement();
        cardInputDetails.add("Number", "4507990000000010");
        cardInputDetails.add("ExpirationDate", "1902");
        cardInputDetails.add("SecurityCode", "123");
        cardInputDetails.add("HolderName", "JOHN DOE");
        
        ComplexElement person = new ComplexElement();
        person.add("FirstName", "John");
        person.add("LastName", "Doe");
        person.add("MiddleName", "Michael");
        person.add("DateOfBirth", "1979-01-12");
        person.add("PhoneNumber1", "+1 011 11111111");
        person.add("PhoneNumber2", "+1 011 22222222");
        person.add("Gender", "M");
        person.add("Nationality",  "ARG" );
        person.add("IDNumber", "54111111");
        person.add("IDType", "200");
        
        ComplexElement address = new ComplexElement();
        address.add("Street", "Av. Collins");
        address.add("HouseNumber", "4702");
        address.add("AdditionalInfo", "2 A");
        address.add("City", "Buenos Aires");
        address.add("StateProvince", "CABA");
        address.add("Country", "ARG");
        address.add("ZipCode", "1425");
        
        data.add("psp_CardInputDetails",cardInputDetails);
        data.add("psp_Person",person);
        data.add("psp_Address",address);
        
        
        RootElement response = sdk.createPaymentMethodToken(data);
        
        System.out.println(response.getValue("psp_PaymentMethodToken"));
        
		return response;
	}
	
	RootElement createPaymentMethod() throws WsdlHandlerException{
		RootElement CPPTResponse = createPaymentMethodToken();
		
		RootElement data = new RootElement();
		data.add("psp_Version", PSPVERSION );
        data.add("psp_MerchantId", PSPMERCHANTID );
        data.add("psp_CustomerId", "5hnNzGs2lMPw2ch33Yi0Ep334dDIoStQ");
        ComplexElement paymentMethod = new ComplexElement();
        paymentMethod.add("PaymentMethodToken", CPPTResponse.getValue("psp_PaymentMethodToken"));
        paymentMethod.add("Product", "14"); 
        
        ComplexElement person = new ComplexElement();  
        person.add("FirstName", "John");
        person.add("LastName", "Doe");
        person.add("MiddleName", "Michael");       
        person.add("PhoneNumber1", "+1 011 11111111");
        person.add("PhoneNumber2", "+1 011 22222222");
        person.add("DateOfBirth", "1979-01-12");
        person.add("Gender", "M");
        person.add("Nationality",  "ARG" );
        person.add("IDNumber", "54111111");
        person.add("IDType", "200");
        
        ComplexElement address = new ComplexElement();
        address.add("Street", "Av. Collins");
        address.add("HouseNumber", "4702");
        address.add("AdditionalInfo", "2 A");
        address.add("City", "Buenos Aires");
        address.add("StateProvince", "CABA");
        address.add("Country", "ARG");
        address.add("ZipCode", "1425");
        
        paymentMethod.add("Person",person);
        paymentMethod.add("Address",address);
        
        
        data.add("psp_PaymentMethod",paymentMethod);
        //data.add("psp_SetAsCustomerDefault","1");
        data.add("psp_PosDateTime",posDateTime);    
        
        RootElement response = sdk.createPaymentMethod(data);
        
        System.out.println(response.getValue("psp_ResponseMsg"));
        System.out.println(response.getComplexElement("psp_PaymentMethod").getValue("PaymentMethodId"));
        
        
        
        return response;
		
	}
	
	
	RootElement splitPayOnline_3p() throws WsdlHandlerException{
		
		RootElement data = new RootElement();
        data.add("psp_Version", PSPVERSION );
        data.add("psp_MerchantId", PSPMERCHANTID );
        data.add("psp_TxSource", "WEB" );

        data.add("psp_MerchOrderId", "7da08b23-0d31-473a-9b37-9ecb477d4d44" ); //cada nuevo request esto debe cambiarse aunque sea una letra
        
        data.add("psp_ReturnURL", "http://localhost/" );
        data.add("psp_FrmLanguage", "es_AR" );
        data.add("psp_Amount", "15050" );
        data.add("psp_Currency", "032" );
        data.add("psp_Country", "ARG" );
        data.add("psp_Product", "14" );
        data.add("psp_PosDateTime", posDateTime );

        ComplexElementArray transaction = new ComplexElementArray();
        
        ComplexElementArrayItem transactionitem1 = new ComplexElementArrayItem();
        transactionitem1.add("psp_MerchantId", PSPMERCHANTID );
        transactionitem1.add("psp_MerchTxRef", "26a49b7f-4f5b-44b5-856f-9d6954b79a95" ); //cada nuevo request esto debe cambiarse aunque sea una letra
        transactionitem1.add("psp_Product", "14" );
        transactionitem1.add("psp_Amount", "10000" );
        transactionitem1.add("psp_NumPayments", "1" );
        
        ComplexElementArrayItem transactionitem2 = new ComplexElementArrayItem();
        transactionitem2.add("psp_MerchantId", PSPMERCHANTID );
        transactionitem2.add("psp_MerchTxRef", "5393925-faff-4e72-83b3-8b9ae84c2dfd" ); //cada nuevo request esto debe cambiarse aunque sea una letra
        transactionitem2.add("psp_Product", "14" );
        transactionitem2.add("psp_Amount", "5050" );
        transactionitem2.add("psp_NumPayments", "1" );
        
        transaction.add(transactionitem1);
        transaction.add(transactionitem2);
        
        data.add("psp_Transactions",transaction);        
        
        
        RootElement response = sdk.splitPayOnLine_3p(data);
		
		
		return response;
		
	}
	
	
	RootElement createCustomer() throws WsdlHandlerException{
		RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_EmailAddress", "jhon.doe@example.com" );
        rootElement.add("psp_AlternativeEmailAddress", "jdoe@example.com" );
        rootElement.add("psp_AccountID", "jdoe78" );
        rootElement.add("psp_AccountCreatedAt", "2010-10-23" );
        
        ComplexElement complexElement1 = new ComplexElement();
        complexElement1.add("FirstName", "John");
        complexElement1.add("LastName", "Doe");
        complexElement1.add("MiddleName", "Michael");
        complexElement1.add("PhoneNumber1", "+1 011 11111111");
        complexElement1.add("PhoneNumber2", "+1 011 22222222");
        complexElement1.add("DateOfBirth", "1979-01-12");
        complexElement1.add("Nationality",  "ARG" );
        complexElement1.add("Gender", "M");
        complexElement1.add("IDNumber", "54111111");
        complexElement1.add("IDType", "200");
        
        ComplexElement complexElement2 = new ComplexElement();
        complexElement2.add("Street", "Av. Collins");
        complexElement2.add("HouseNumber", "4702");
        complexElement2.add("AdditionalInfo", "2 A");
        complexElement2.add("StateProvince", "CABA");
        complexElement2.add("City", "Buenos Aires");
        complexElement2.add("Country", "ARG");
        complexElement2.add("ZipCode", "1425");       
        
        //ComplexElement complexElement3 = new ComplexElement();
        //complexElement3.add("PaymentMethodToken", "KCVMXsbue5fUKOoAxvp1PTJ94gxv2dQM");
        //complexElement3.add("Product", "14");
        
        ComplexElement complexElement31 = new ComplexElement();
        complexElement31.add("Street", "Av. Collins");
        complexElement31.add("HouseNumber", "1245");
        complexElement31.add("AdditionalInfo", "2 A");
        complexElement31.add("StateProvince", "Florida");
        complexElement31.add("City", "Miami");
        complexElement31.add("Country", "USA");
        complexElement31.add("ZipCode", "33140"); 
        
        
        ComplexElement complexElement32 = new ComplexElement();
        complexElement32.add("FirstName", "John");
        complexElement32.add("LastName", "Doe");
        complexElement32.add("MiddleName", "Michael");
        complexElement32.add("PhoneNumber1", "+1 011 11111111");
        complexElement32.add("PhoneNumber2", "+1 011 22222222");
        complexElement32.add("DateOfBirth", "1979-01-12");
        complexElement32.add("Gender", "M");
        complexElement32.add("Nationality",  "ARG" );
        complexElement32.add("IDNumber", "54111111");
        complexElement32.add("IDType", "200");
        
        //complexElement3.add("Address",complexElement31);
        //complexElement3.add("Person",complexElement32);
        
        rootElement.add("psp_Person",complexElement1);
        rootElement.add("psp_Address",complexElement2);
        //rootElement.add("psp_PaymentMethod",complexElement3);        
       
        rootElement.add("psp_PosDateTime", posDateTime);
        
        //5hnNzGs2lMPw2ch33Yi0Ep334dDIoStQ
        RootElement response = sdk.createCustomer(rootElement);
        
        System.out.println(response.getValue("psp_CustomerId"));
        System.out.println(response.getComplexElement("psp_Person").getValue("FirstName").length());
        
        return response;
        
        
	}
	
	RootElement retrieveCustomer() throws WsdlHandlerException{
		
		RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_CustomerId", "5hnNzGs2lMPw2ch33Yi0Ep334dDIoStQ" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = sdk.retrieveCustomer(rootElement);
        //System.out.println(response.getComplexElementArray("psp_PaymentMethods").getItem(1).getComplexElement("CardOutputDetails").getValue("Number"));
        System.out.println(response.getComplexElement("psp_Person").getValue("FirstName")); //eeto devuelve un objeto simple element que no da acceso a los valores internos
        System.out.println(response.getComplexElement("psp_Person").getValue("FirstName")); // esto devuelve nulo
        
        System.out.println(response.getComplexElementArray("psp_PaymentMethods").get(1).getComplexElement("CardOutputDetails").getValue("Number"));
        
        System.out.println(response.getComplexElementArray("psp_PaymentMethods"));
        
        
        
        
        
        //No tengo forma de acceder a los datos que estan dentro del nodo "psp_Person" o al menos no se como. en los test no hay anda que acceda a datos complejos
        
        //System.out.println(response.getValue("psp_ResponseCod"));
		
        return response;
	}
	
	

}
