package NpsSDKTests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

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

public class NpsSdkDeveloperPageTests {

	
	private static final String PSPVERSION = "2.2";
	private static final String PSPMERCHANTID = "sdk_test";
	private static String posDateTime;
	private static NpsSdk npsSdk;
	
	
	@BeforeClass
    public static void setUp() throws WsdlHandlerException {
		
		Date dateTimeNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        posDateTime = formatter.format(dateTimeNow);		
        
        ILogger consoleLogger = new ConsoleLogger();
        WsdlHandlerConfiguration wsdlHandlerConfiguration = new WsdlHandlerConfiguration(LogLevel.DEBUG, NpsEnvironment.sandbox, "swGYxNeehNO8fS1zgwvCICevqjHbXcwPWAvTVZ5CuULZwKWaGPmXbPSP8i1fKv2q", consoleLogger);
        
        npsSdk = new NpsSdk(wsdlHandlerConfiguration);  	
	}
	
	
	@Test
	public void secureHashTest() throws WsdlHandlerException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,2017);
		cal.set(Calendar.MONTH,2);
		cal.set(Calendar.DAY_OF_MONTH,14);
		cal.set(Calendar.HOUR_OF_DAY, 13);
		cal.set(Calendar.MINUTE, 3);
		cal.set(Calendar.SECOND, 5);
		cal.set(Calendar.MILLISECOND, 0);
	
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(cal.getTime());
		
		
		RootElement rootElement = new RootElement();
		rootElement.add("psp_Version", PSPVERSION);
        rootElement.add("psp_MerchantId", PSPMERCHANTID);
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER1000-2" );
        rootElement.add("psp_MerchOrderId", "ORDER1000" );
        rootElement.add("psp_MerchAdditionalRef", "ADDITIONAL-REF1234" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_PaymentAmount", "15050" );
        rootElement.add("psp_Recurrent", "1" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_CardNumber", "4507990000000010" );
        rootElement.add("psp_CardExpDate", "1506" );
        rootElement.add("psp_CardSecurityCode", "324" );
        rootElement.add("psp_CardHolderName", "JUAN PEREZ" );
        rootElement.add("psp_Plan", "PlanZ" );
        rootElement.add("psp_CustomerMail", "client@client.com.ar" );
        rootElement.add("psp_CustomerId", "P123765" );
        rootElement.add("psp_MerchantMail", "merchant@merchant.com.ar" );
        rootElement.add("psp_PurchaseDescription", "Com. X Art 1555" );
        rootElement.add("psp_SoftDescriptor", "Alphanumeric" );
        rootElement.add("psp_PosDateTime",  date);
        
        assertEquals("0d02b8515770a23f5e6791f190aba4b8",rootElement.secureHash("123456789"));
        
        
        rootElement = new RootElement(); 
        rootElement.add("psp_Version", PSPVERSION);
        rootElement.add("psp_MerchantId", PSPMERCHANTID);
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER1000-2" );
        rootElement.add("psp_MerchOrderId", "ORDER1000" );
        rootElement.add("psp_MerchAdditionalRef", "ADDITIONAL-REF1234" );
        rootElement.add("psp_ReturnURL", "https://sitio_de_comercio/recive.php" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_FrmBackButtonURL", "https://sitio_de_comercio/backurl.php" );
        rootElement.add("psp_FrmTimeout", "720" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_PaymentAmount", "15050" );
        rootElement.add("psp_Plan", "PlanZ" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_CustomerMail", "client@client.com.ar" );
        rootElement.add("psp_CustomerId", "P123765" );
        rootElement.add("psp_MerchantMail", "merchant@merchant.com.ar" );
        rootElement.add("psp_PurchaseDescription", "Com. X Art 1555" );
        rootElement.add("psp_SoftDescriptor", "Company Inc" );
        rootElement.add("psp_PromotionCode", "VISA_SANTANDER" );
        rootElement.add("psp_PosDateTime",  date);
        
        assertEquals("a7e9944e1e9c8295e01a821342f2dc70",rootElement.secureHash("123456789"));
        
        
        rootElement = new RootElement(); 
        rootElement.add("psp_Version", PSPVERSION);
        rootElement.add("psp_MerchantId", PSPMERCHANTID);
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER1000-2" );
        rootElement.add("psp_MerchOrderId", "ORDER1000" );
        rootElement.add("psp_MerchAdditionalRef", "ADDITIONAL-REF1234" );
        rootElement.add("psp_ReturnURL", "https://merchantsite/recive.php" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_FrmBackButtonURL", "https://merchantsite/back.php" );
        rootElement.add("psp_FrmTimeout", "720" );
        rootElement.add("psp_ScreenDescription", "Cod de factura" );
        rootElement.add("psp_TicketDescription", "Venta de ticket aéreo 54872487" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "320" );
        rootElement.add("psp_Amount1", "15050" );
        rootElement.add("psp_ExpDate1", "2010-10-01" );
        rootElement.add("psp_Amount2", "17050" );
        rootElement.add("psp_ExpDate2", "2010-10-15" );
        rootElement.add("psp_Amount3", "19050" );
        rootElement.add("psp_ExpDate3", "2010-10-20" );
        rootElement.add("psp_MinAmount", "10050" );
        rootElement.add("psp_ExpMark", "0" );
        rootElement.add("psp_ExpTime", "14:00:00" );
        rootElement.add("psp_CustomerMail", "client@client.com.ar" );
        rootElement.add("psp_CustomerId", "P123765" );
        rootElement.add("psp_MerchantMail", "merchant@merchant.com.ar" );
        rootElement.add("psp_PosDateTime",  date);
        
        assertEquals("186619d58c8bf753c919291fd3f92aec",rootElement.secureHash("123456789"));
	}
	
	@Test
    public void noMatchingFieldTest() throws WsdlHandlerException {
		String noMatchingFieldName = "aasdfasdfasdf";
		String noMatchingFieldValue = "1234132";		
		
		RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION);
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER66666-3" );
        rootElement.add("psp_MerchOrderId", "ORDER66666" );
        rootElement.add("psp_ReturnURL", "http://localhost/" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_PosDateTime", posDateTime);
        rootElement.add(noMatchingFieldName, noMatchingFieldValue);
        
        RootElement response = npsSdk.payOnLine_3p(rootElement);
        
        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER66666-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDER66666",response.getValue("psp_MerchOrderId"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("1",response.getValue("psp_NumPayments"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
	}
	
	@Test
    public void arrayResponseTest() throws WsdlHandlerException {	
		
		RootElement rootElement = new RootElement();
        rootElement.add("psp_CustomerId", "hCggS9d6m9aBHcUmDZB0gg8PfxdEL8j1");
        rootElement.add("psp_MerchantId", "sdk_test" );
        rootElement.add("psp_PosDateTime", "2008-01-12 13:05:00" );
        rootElement.add("psp_Version", "2.2" );
        
        RootElement response = npsSdk.retrieveCustomer(rootElement);
        
        assertNotNull(response.getComplexElementArray("psp_PaymentMethods"));
        assertNotNull(response.getComplexElementArray("psp_PaymentMethods").get(0).getComplexElement("CardOutputDetails"));
       
        
        for(ComplexElementArrayItem complexElementArrayItem : response.getComplexElementArray("psp_PaymentMethods")){
        	assertEquals("1909",complexElementArrayItem.getComplexElement("CardOutputDetails").getValue("ExpirationDate"));
        }
	
	}	

	@Test
    public void payOnLine_3pTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION);
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER66666-3" );
        rootElement.add("psp_MerchOrderId", "ORDER66666" );
        rootElement.add("psp_ReturnURL", "http://localhost/" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.payOnLine_3p(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER66666-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDER66666",response.getValue("psp_MerchOrderId"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("1",response.getValue("psp_NumPayments"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


	//warning: psp_NumPayments not found in documentation as parameter and it is required.
    @Test
    public void authorize_3pTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER76666-3" );
        rootElement.add("psp_MerchOrderId", "ORDER76666" );
        rootElement.add("psp_ReturnURL", "http://localhost/" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.authorize_3p(rootElement);

        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER76666-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDER76666",response.getValue("psp_MerchOrderId"));
        assertEquals("1",response.getValue("psp_NumPayments"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void splitPayOnLine_3pTest() throws WsdlHandlerException {
    	
    	String pspMerchOrderId =  UUID.randomUUID().toString();
        RootElement request = new RootElement();
        request.add("psp_Version", PSPVERSION );
        request.add("psp_MerchantId", PSPMERCHANTID );
        request.add("psp_TxSource", "WEB" );
        request.add("psp_MerchOrderId", pspMerchOrderId );
        request.add("psp_ReturnURL", "http://localhost/" );
        request.add("psp_FrmLanguage", "es_AR" );
        request.add("psp_Amount", "15050" );
        request.add("psp_Currency", "032" );
        request.add("psp_Country", "ARG" );
        request.add("psp_Product", "14" );
        request.add("psp_PosDateTime", posDateTime );
        request.add("psp_MerchantAdditionalDetails", new ComplexElement());

        ComplexElementArray complexElementArray = new ComplexElementArray();
        ComplexElementArrayItem complexElementArrayItem1 = new ComplexElementArrayItem();
        complexElementArrayItem1.add("psp_MerchantId", PSPMERCHANTID );
        complexElementArrayItem1.add("psp_MerchTxRef", UUID.randomUUID().toString() );
        complexElementArrayItem1.add("psp_Product", "14" );
        complexElementArrayItem1.add("psp_Amount", "10000" );
        complexElementArrayItem1.add("psp_NumPayments", "1" );
        
        ComplexElementArrayItem complexElementArrayItem2 = new ComplexElementArrayItem();
        complexElementArrayItem2.add("psp_MerchantId", PSPMERCHANTID );
        complexElementArrayItem2.add("psp_MerchTxRef", UUID.randomUUID().toString() );
        complexElementArrayItem2.add("psp_Product", "14" );
        complexElementArrayItem2.add("psp_Amount", "15050" );
        complexElementArrayItem2.add("psp_NumPayments", "1" );
        
        complexElementArray.add(complexElementArrayItem1);
        complexElementArray.add(complexElementArrayItem2);
        
        request.add("psp_Transactions",complexElementArray);        
        
        
        RootElement response = npsSdk.splitPayOnLine_3p(request);
        
        assertNotNull(request.getComplexElementArray("psp_Transactions"));
        assertNotNull(request.getComplexElementArray("psp_Transactions").get(0));
        assertNotNull(request.getComplexElementArray("psp_Transactions").get(1));
        assertEquals("10000", request.getComplexElementArray("psp_Transactions").get(0).getValue("psp_Amount"));
        
        assertNotNull(response.getComplexElementArray("psp_Transactions"));
        assertNotNull(response.getComplexElementArray("psp_Transactions").get(0));
        assertNotNull(response.getComplexElementArray("psp_Transactions").get(1));
        assertEquals("10000",request.getComplexElementArray("psp_Transactions").get(0).getValue("psp_Amount"));
        
        for(int i = 0; i < response.getComplexElementArray("psp_Transactions").size();i++){
        	assertEquals(request.getComplexElementArray("psp_Transactions").get(i).getValue("psp_Amount"),response.getComplexElementArray("psp_Transactions").get(i).getValue("psp_Amount"));
        }
        
        
        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals(pspMerchOrderId,response.getValue("psp_MerchOrderId"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));

    }


    @Test
    public void splitAuthorize_3pTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchOrderId", "ORDER66666" );
        rootElement.add("psp_ReturnURL", "http://localhost/" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_PosDateTime", posDateTime );

        ComplexElementArray complexElementArray = new ComplexElementArray();
        ComplexElementArrayItem complexElementArrayItem1 = new ComplexElementArrayItem();
        complexElementArrayItem1.add("psp_MerchantId", PSPMERCHANTID );
        complexElementArrayItem1.add("psp_MerchTxRef", UUID.randomUUID().toString() );
        complexElementArrayItem1.add("psp_Product", "14" );
        complexElementArrayItem1.add("psp_Amount", "10000" );
        complexElementArrayItem1.add("psp_NumPayments", "1" );
        
        ComplexElementArrayItem complexElementArrayItem2 = new ComplexElementArrayItem();
        complexElementArrayItem2.add("psp_MerchantId", PSPMERCHANTID );
        complexElementArrayItem2.add("psp_MerchTxRef", UUID.randomUUID().toString() );
        complexElementArrayItem2.add("psp_Product", "14" );
        complexElementArrayItem2.add("psp_Amount", "5050" );
        complexElementArrayItem2.add("psp_NumPayments", "1" );
        
        complexElementArray.add(complexElementArrayItem1);
        complexElementArray.add(complexElementArrayItem2);
        
        rootElement.add("psp_Transactions", complexElementArray);
        
        
        RootElement response = npsSdk.splitAuthorize_3p(rootElement);

        String psp_ResponseExtended = "";
        if (response.getValue("psp_ResponseExtended") != null){
        	psp_ResponseExtended = response.getValue("psp_ResponseExtended");
        }
        assertFalse(psp_ResponseExtended.contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER66666",response.getValue("psp_MerchOrderId"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void cashPayment_3pTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER32145-3" );
        rootElement.add("psp_MerchOrderId", "ORDER32145" );
        rootElement.add("psp_ReturnURL", "http://localhost/" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "301" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.cashPayment_3p(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER32145-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDER32145",response.getValue("psp_MerchOrderId"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("301",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void bankPayment_3pTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER66666-3" );
        rootElement.add("psp_MerchOrderId", "ORDER66666" );
        rootElement.add("psp_ReturnURL", "http://localhost/" );
        rootElement.add("psp_FrmLanguage", "es_AR" );
        rootElement.add("psp_ScreenDescription", "Descripcion" );
        rootElement.add("psp_TicketDescription", "Descripcion" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "320" );
        rootElement.add("psp_ExpDate1", "2016-12-01" );
        rootElement.add("psp_Amount1", "15050" );
        rootElement.add("psp_ExpMark", "0" );
        rootElement.add("psp_ExpTime", "14:00:00" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.bankPayment_3p(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER66666-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDER66666",response.getValue("psp_MerchOrderId"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("320",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void payOnLine_2pTest() throws WsdlHandlerException {
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

        RootElement response = npsSdk.payOnLine_2p(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER69461-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDER69461",response.getValue("psp_MerchOrderId"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("1",response.getValue("psp_NumPayments"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals("4507990000000010",response.getValue("psp_CardNumber"));
        assertEquals("1612",response.getValue("psp_CardExpDate"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void authorize_2pTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDERX1466Xz-3" );
        rootElement.add("psp_MerchOrderId", "ORDERX1466Xz" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_CardNumber", "4507990000000010" );
        rootElement.add("psp_CardExpDate", "1712" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.authorize_2p(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDERX1466Xz-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDERX1466Xz",response.getValue("psp_MerchOrderId"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("1",response.getValue("psp_NumPayments"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals("4507990000000010",response.getValue("psp_CardNumber"));
        assertEquals("1712",response.getValue("psp_CardExpDate"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void captureTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER66666-3" );
        rootElement.add("psp_TransactionId_Orig", "2693348" );
        rootElement.add("psp_AmountToCapture", "15050" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.capture(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER66666-3",response.getValue("psp_MerchTxRef"));
        assertEquals("2693348",response.getValue("psp_TransactionId_Orig"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void refundTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER66666-3" );
        rootElement.add("psp_TransactionId_Orig", "2693348" );
        rootElement.add("psp_AmountToRefund", "15050" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.refund(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER66666-3",response.getValue("psp_MerchTxRef"));
        assertEquals("2693348",response.getValue("psp_TransactionId_Orig"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void simpleQueryTxTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_QueryCriteria", "M" );
        rootElement.add("psp_QueryCriteriaId", "ORDER69461-3" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.simpleQueryTx(rootElement);

        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("M",response.getValue("psp_QueryCriteria"));
        assertEquals("ORDER69461-3",response.getValue("psp_QueryCriteriaId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void queryTxsTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_QueryCriteria", "O" );
        rootElement.add("psp_QueryCriteriaId", "ORDER69461-3" );
        rootElement.add("psp_PosDateTime", posDateTime);

    	RootElement response = npsSdk.queryTxs(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("O",response.getValue("psp_QueryCriteria"));
        assertEquals("ORDER69461-3",response.getValue("psp_QueryCriteriaId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


	//warning: psp_Amount not found in documentation as parameter and it is required.
	//warning: psp_Country not found in documentation as parameter and it is required.
	//warning: psp_Currency not found in documentation as parameter and it is required. 
	//warning: psp_IIN is in documentation but the wsdl doesn't have this field.
	//warning: psp_Product not found in documentation as parameter and it is required.
	//warning: No response documentation
    @Test
    public void getInstallmentsOptionsTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.getInstallmentsOptions(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }

    @Test
    public void queryCardNumberTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", "1" );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_QueryCriteria", "T" );
        rootElement.add("psp_QueryCriteriaId", "76577" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.queryCardNumber(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }

    @Test
    public void createClientSessionTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.createClientSession(rootElement);

        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }

    @Test
    public void createPaymentMethodTokenTest() throws WsdlHandlerException {
        RootElement request = new RootElement();
        request.add("psp_Version", PSPVERSION );
        request.add("psp_MerchantId", PSPMERCHANTID );
        request.add("psp_Product", "14" );       
        
        ComplexElement complexElement1 = new ComplexElement();
        complexElement1.add("Number", "4507990000000010");
        complexElement1.add("ExpirationDate", "1902");
        complexElement1.add("SecurityCode", "123");
        complexElement1.add("HolderName", "JOHN DOE");
        
        ComplexElement complexElement2 = new ComplexElement();
        complexElement2.add("FirstName", "John");
        complexElement2.add("LastName", "Doe");
        complexElement2.add("MiddleName", "Michael");
        complexElement2.add("DateOfBirth", "1979-01-12");
        complexElement2.add("PhoneNumber1", "+1 011 11111111");
        complexElement2.add("PhoneNumber2", "+1 011 22222222");
        complexElement2.add("Gender", "M");
        complexElement2.add("Nationality",  "ARG" );
        complexElement2.add("IDNumber", "54111111");
        complexElement2.add("IDType", "200");
        
        ComplexElement complexElement3 = new ComplexElement();
        complexElement3.add("Street", "Av. Collins");
        complexElement3.add("HouseNumber", "4702");
        complexElement3.add("AdditionalInfo", "2 A");
        complexElement3.add("City", "Buenos Aires");
        complexElement3.add("StateProvince", "CABA");
        complexElement3.add("Country", "ARG");
        complexElement3.add("ZipCode", "1425");
        
        request.add("psp_CardInputDetails",complexElement1);
        request.add("psp_Person",complexElement2);
        request.add("psp_Address",complexElement3);
        
        request.add("psp_ClientSession", "C5jwwbyAYneLbvZe0IYPHTvn7ODMb3vG8ZqCYaYIioUmWUbcgKscGpg8WhXrspRs" );      
        
        RootElement response = npsSdk.createPaymentMethodToken(request);
        
        assertEquals("JOHN DOE", request.getComplexElement("psp_CardInputDetails").getValue("HolderName"));
        assertNull(request.getValue("psp_CardInputDetails"));
        assertNotNull(response.getComplexElement("psp_Person"));

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("14",response.getValue("psp_Product"));
    }

    @Test
    public void recachePaymentMethodTokenTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_PaymentMethodId", "jGW24iDaoMBzfKHViL18TmHo9sHBgW4J" );
        rootElement.add("psp_CardSecurityCode", "123" );
        
        
        ComplexElement complexElement1 = new ComplexElement();
        complexElement1.add("FirstName", "John");
        complexElement1.add("DateOfBirth", "1979-01-12");        
        complexElement1.add("LastName", "Doe");
        complexElement1.add("MiddleName", "Michael");       
        complexElement1.add("PhoneNumber1", "+1 011 11111111");
        complexElement1.add("PhoneNumber2", "+1 011 22222222");
        complexElement1.add("Gender", "M");
        complexElement1.add("Nationality",  "ARG" );
        complexElement1.add("IDNumber", "54111111");
        complexElement1.add("IDType", "200");
        
        ComplexElement complexElement2 = new ComplexElement();
        complexElement2.add("Street", "Av. Collins");
        complexElement2.add("HouseNumber", "4702");
        complexElement2.add("AdditionalInfo", "2 A");
        complexElement2.add("City", "Buenos Aires");
        complexElement2.add("StateProvince", "CABA");
        complexElement2.add("Country", "ARG");
        complexElement2.add("ZipCode", "1425");
        
        rootElement.add("psp_Person",complexElement1);
        rootElement.add("psp_Address",complexElement2);
        
        rootElement.add("psp_ClientSession", "C5jwwbyAYneLbvZe0IYPHTvn7ODMb3vG8ZqCYaYIioUmWUbcgKscGpg8WhXrspRs" );

        RootElement response = npsSdk.recachePaymentMethodToken(rootElement);
        
        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));    
    
    }

    @Test
    public void retrievePaymentMethodTokenTest() throws WsdlHandlerException {
    	RootElement rootElement = new RootElement();
    	rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_PaymentMethodToken", "KCVMXsbue5fUKOoAxvp1PTJ94gxv2dQM" );
        rootElement.add("psp_ClientSession", "C5jwwbyAYneLbvZe0IYPHTvn7ODMb3vG8ZqCYaYIioUmWUbcgKscGpg8WhXrspRs" );    	
    	
        RootElement response = npsSdk.retrievePaymentMethodToken(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId")); 
        assertEquals("KCVMXsbue5fUKOoAxvp1PTJ94gxv2dQM",response.getValue("psp_PaymentMethodToken")); 
    }

    @Test
    public void createPaymentMethodTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );


        ComplexElement complexElement1 = new ComplexElement();
        complexElement1.add("PaymentMethodToken", "HGIYeXKJpQyBvO3pHuZTN9JZiVPnzQaQ");
        complexElement1.add("Product", "14"); 
        
        ComplexElement complexElement11 = new ComplexElement();  
        complexElement11.add("FirstName", "John");
        complexElement11.add("LastName", "Doe");
        complexElement11.add("MiddleName", "Michael");       
        complexElement11.add("PhoneNumber1", "+1 011 11111111");
        complexElement11.add("PhoneNumber2", "+1 011 22222222");
        complexElement11.add("DateOfBirth", "1979-01-12");
        complexElement11.add("Gender", "M");
        complexElement11.add("Nationality",  "ARG" );
        complexElement11.add("IDNumber", "54111111");
        complexElement11.add("IDType", "200");
        
        ComplexElement complexElement12 = new ComplexElement();
        complexElement12.add("Street", "Av. Collins");
        complexElement12.add("HouseNumber", "4702");
        complexElement12.add("AdditionalInfo", "2 A");
        complexElement12.add("City", "Buenos Aires");
        complexElement12.add("StateProvince", "CABA");
        complexElement12.add("Country", "ARG");
        complexElement12.add("ZipCode", "1425");
        
        complexElement1.add("Person",complexElement11);
        complexElement1.add("Address",complexElement12);
        
        rootElement.add("psp_PaymentMethod",complexElement1);
        
        rootElement.add("psp_SetAsCustomerDefault","1");
        rootElement.add("psp_PosDateTime",posDateTime);     
                
        RootElement response = npsSdk.createPaymentMethod(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));  
        assertEquals(posDateTime,response.getValue("psp_PosDateTime")); 
    }

    @Test
    public void createPaymentMethodFromPaymentTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TransactionId", "65340022" );
        rootElement.add("psp_CustomerId", "bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b" );
        rootElement.add("psp_SetAsCustomerDefault", "1" );
        rootElement.add("psp_PosDateTime", posDateTime);

    	RootElement response = npsSdk.createPaymentMethodFromPayment(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
    }
    
    
    //warning: psp_PaymentMethodId not found as echo. Internal error 1206 - PaymentMethod
    @Test
    public void updatePaymentMethodTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_PaymentMethodId", "jGW24iDaoMBzfKHViL18TmHo9sHBgW4J" );

        ComplexElement complexElement1 = new ComplexElement();
        complexElement1.add("ExpirationDate", "1909");
        complexElement1.add("HolderName", "JOHN DOE"); 
        
        ComplexElement complexElement2 = new ComplexElement();
        complexElement2.add("FirstName", "John");
        complexElement2.add("LastName", "Doe");
        complexElement2.add("MiddleName", "Michael");
        complexElement2.add("PhoneNumber1", "+1 011 11111111");
        complexElement2.add("PhoneNumber2", "+1 011 22222222");
        complexElement2.add("DateOfBirth", "1979-01-12");
        complexElement2.add("Nationality",  "ARG" );
        complexElement2.add("Gender", "M");
        complexElement2.add("IDNumber", "54111111");
        complexElement2.add("IDType", "200");
        
        ComplexElement complexElement3 = new ComplexElement();
        complexElement3.add("Street", "Av. Collins");
        complexElement3.add("HouseNumber", "4702");
        complexElement3.add("AdditionalInfo", "2 A");
        complexElement3.add("StateProvince", "CABA");
        complexElement3.add("City", "Buenos Aires");
        complexElement3.add("Country", "ARG");
        complexElement3.add("ZipCode", "1425");        
        
        rootElement.add("psp_CardInputDetails",complexElement1);
        rootElement.add("psp_Person",complexElement2);
        rootElement.add("psp_Address",complexElement3);
        
        rootElement.add("psp_PosDateTime",posDateTime);     
        
        
        RootElement response = npsSdk.updatePaymentMethod(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId")); 
        //assertEquals("jGW24iDaoMBzfKHViL18TmHo9sHBgW4J",response.getValue("psp_PaymentMethodId")); 
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));   
    
    }

//warning: psp_PaymentMethodId not found as echo. Internal error 1206 - PaymentMethod
    @Test
    public void deletePaymentMethodTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        
        rootElement.add("psp_Version", PSPVERSION);
        rootElement.add("psp_MerchantId", PSPMERCHANTID);
        rootElement.add("psp_PaymentMethodId", "Nzmd7DyGytXXC5PtKwnCsXXuQWHJB9EE");
        rootElement.add("psp_PosDateTime", posDateTime);                
            
        RootElement response = npsSdk.deletePaymentMethod(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        //assertEquals("Nzmd7DyGytXXC5PtKwnCsXXuQWHJB9EE",response.getValue("psp_PaymentMethodId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }

//warning: psp_PaymentMethodId not found as echo. Internal error 1206 - PaymentMethod
    @Test
    public void retrievePaymentMethodTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        
        rootElement.add("psp_Version", PSPVERSION);
        rootElement.add("psp_MerchantId", PSPMERCHANTID);
        rootElement.add("psp_PaymentMethodId", "jGW24iDaoMBzfKHViL18TmHo9sHBgW4J");
        rootElement.add("psp_PosDateTime", posDateTime);                
            
        RootElement response = npsSdk.retrievePaymentMethod(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        //assertEquals("jGW24iDaoMBzfKHViL18TmHo9sHBgW4J",response.getValue("psp_PaymentMethodId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void createCustomerTest() throws WsdlHandlerException {
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
        
        ComplexElement complexElement3 = new ComplexElement();
        complexElement3.add("PaymentMethodToken", "KCVMXsbue5fUKOoAxvp1PTJ94gxv2dQM");
        complexElement3.add("Product", "14");
        
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
        
        complexElement3.add("Address",complexElement31);
        complexElement3.add("Person",complexElement32);
        
        rootElement.add("psp_Person",complexElement1);
        rootElement.add("psp_Address",complexElement2);
        rootElement.add("psp_PaymentMethod",complexElement3);        
       
        rootElement.add("psp_PosDateTime", posDateTime);

                
        RootElement response = npsSdk.createCustomer(rootElement);
                
        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("jhon.doe@example.com",response.getValue("psp_EmailAddress"));
        assertEquals("jdoe@example.com",response.getValue("psp_AlternativeEmailAddress"));
        assertEquals("jdoe78",response.getValue("psp_AccountID"));
        assertEquals("2010-10-23",response.getValue("psp_AccountCreatedAt"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));

    }

    @Test
    public void updateCustomerTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_CustomerId", "bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b" );
        rootElement.add("psp_EmailAddress", "jhon.doe@example.com" );
        rootElement.add("psp_AlternativeEmailAddress", "jdoe@example.com" );
        rootElement.add("psp_AccountCreatedAt", "2010-10-23" );
        rootElement.add("psp_AccountID", "jdoe78" );
        
        
        ComplexElement complexElement1 = new ComplexElement();
        complexElement1.add("FirstName", "John");
        complexElement1.add("LastName", "Doe");
        complexElement1.add("MiddleName", "Michael");
        complexElement1.add("PhoneNumber1", "+1 011 11111111");
        complexElement1.add("PhoneNumber2", "+1 011 22222222");
        complexElement1.add("Gender", "M");
        complexElement1.add("DateOfBirth", "1979-01-12");
        complexElement1.add("Nationality",  "ARG" );
        complexElement1.add("IDNumber", "54111111");
        complexElement1.add("IDType", "200");
        
        
        ComplexElement complexElement2 = new ComplexElement();
        complexElement2.add("Street", "Av. Collins");
        complexElement2.add("HouseNumber", "1245");
        complexElement2.add("AdditionalInfo", "2 A");
        complexElement2.add("StateProvince", "Miami");
        complexElement2.add("City", "Florida");
        complexElement2.add("Country", "USA");
        complexElement2.add("ZipCode", "33140");       
        
        
        ComplexElement complexElement3 = new ComplexElement();
        complexElement3.add("PaymentMethodToken", "KCVMXsbue5fUKOoAxvp1PTJ94gxv2dQM");
          
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
        
        complexElement3.add("Person",complexElement32);
        complexElement3.add("Address",complexElement31);
        complexElement3.add("Product", "14");
        
        rootElement.add("psp_Person",complexElement1);
        rootElement.add("psp_Address",complexElement2);
        rootElement.add("psp_PaymentMethod",complexElement3);        
       
        rootElement.add("psp_DefaultPaymentMethodId", "Nzmd7DyGytXXC5PtKwnCsXXuQWHJB9EE" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.updateCustomer(rootElement);
                
        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b",response.getValue("psp_CustomerId"));
        assertEquals("jhon.doe@example.com",response.getValue("psp_EmailAddress"));
        assertEquals("jdoe@example.com",response.getValue("psp_AlternativeEmailAddress"));
        assertEquals("2010-10-23",response.getValue("psp_AccountCreatedAt"));
        assertEquals("jdoe78",response.getValue("psp_AccountID"));
        assertEquals("bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b",response.getValue("psp_CustomerId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void deleteCustomerTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_CustomerId", "bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b" );
        rootElement.add("psp_PosDateTime", posDateTime);

    	RootElement response = npsSdk.deleteCustomer(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b",response.getValue("psp_CustomerId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }

    @Test
    public void retrieveCustomerTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_CustomerId", "bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.retrieveCustomer(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("bMnVPbNgX55oUz1VLgC41E5iD2rYRo2b",response.getValue("psp_CustomerId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void fraudScreeningTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_TxSource", "WEB" );
        rootElement.add("psp_MerchTxRef", "ORDER66666-3" );
        rootElement.add("psp_MerchOrderId", "ORDER66666" );
        rootElement.add("psp_Amount", "15050" );
        rootElement.add("psp_NumPayments", "1" );
        rootElement.add("psp_Currency", "032" );
        rootElement.add("psp_Country", "ARG" );
        rootElement.add("psp_Product", "14" );
        rootElement.add("psp_CardNumber", "4507990000000010" );
        rootElement.add("psp_CardExpDate", "1612" );
        rootElement.add("psp_PosDateTime", posDateTime);

    	RootElement response = npsSdk.fraudScreening(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals("ORDER66666-3",response.getValue("psp_MerchTxRef"));
        assertEquals("ORDER66666",response.getValue("psp_MerchOrderId"));
        assertEquals("15050",response.getValue("psp_Amount"));
        assertEquals("1",response.getValue("psp_NumPayments"));
        assertEquals("032",response.getValue("psp_Currency"));
        assertEquals("ARG",response.getValue("psp_Country"));
        assertEquals("14",response.getValue("psp_Product"));
        assertEquals("4507990000000010",response.getValue("psp_CardNumber"));
        assertEquals("1612",response.getValue("psp_CardExpDate"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }


    @Test
    public void notifyFraudScreeningReviewTest() throws WsdlHandlerException {
        RootElement rootElement = new RootElement();
        rootElement.add("psp_Version", PSPVERSION );
        rootElement.add("psp_MerchantId", PSPMERCHANTID );
        rootElement.add("psp_Criteria", "T" );
        rootElement.add("psp_CriteriaId", "ORDER69461-3" );
        rootElement.add("psp_ReviewResult", "A" );
        rootElement.add("psp_PosDateTime", posDateTime);

        RootElement response = npsSdk.notifyFraudScreeningReview(rootElement);

        assertFalse(response.getValue("psp_ResponseExtended").contains("Error Interno 1003 - Campo psp_SecureHash invalido"));
        assertNotNull(response.getValue("psp_ResponseCod"));
        assertNotNull(response.getValue("psp_ResponseMsg"));
        assertEquals(PSPMERCHANTID,response.getValue("psp_MerchantId"));
        assertEquals(posDateTime,response.getValue("psp_PosDateTime"));
    }
	
	
	
	
	
}
