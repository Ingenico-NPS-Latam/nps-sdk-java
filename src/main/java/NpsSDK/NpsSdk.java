package NpsSDK;

import java.io.InputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.predic8.schema.ComplexContent;
import com.predic8.schema.ModelGroup;
import com.predic8.schema.SchemaComponent;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.Input;
import com.predic8.wsdl.Message;
import com.predic8.wsdl.Operation;
import com.predic8.wsdl.Output;
import com.predic8.wsdl.PortType;
import com.predic8.wsdl.WSDLParser;

import NpsSDK.ILogger.LogLevel;
import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;
import groovy.xml.QName;

public class NpsSdk {

	static final String sdkVersion = "Java 1.0.0";

	// Sanitize

	static Map<String, Integer> fieldsMaxLength = new HashMap<String, Integer>() {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		{
			put("psp_Person.FirstName", 128);
			put("psp_Person.LastName", 64);
			put("psp_Person.MiddleName", 64);
			put("psp_Person.PhoneNumber1", 32);
			put("psp_Person.PhoneNumber2", 32);
			put("psp_Person.Gender", 1);
			put("psp_Person.Nationality", 3);
			put("psp_Person.IDNumber", 40);
			put("psp_Person.IDType", 5);
			put("psp_Address.Street", 128);
			put("psp_Address.HouseNumber", 32);
			put("psp_Address.AdditionalInfo", 128);
			put("psp_Address.City", 40);
			put("psp_Address.StateProvince", 40);
			put("psp_Address.Country", 3);
			put("psp_Address.ZipCode", 10);
			put("psp_OrderItem.Description", 127);
			put("psp_OrderItem.Type", 20);
			put("psp_OrderItem.SkuCode", 48);
			put("psp_OrderItem.ManufacturerPartNumber", 30);
			put("psp_OrderItem.Risk", 1);
			put("psp_Leg.DepartureAirport", 3);
			put("psp_Leg.ArrivalAirport", 3);
			put("psp_Leg.CarrierCode", 2);
			put("psp_Leg.FlightNumber", 5);
			put("psp_Leg.FareBasisCode", 15);
			put("psp_Leg.FareClassCode", 3);
			put("psp_Leg.BaseFareCurrency", 3);
			put("psp_Passenger.FirstName", 50);
			put("psp_Passenger.LastName", 30);
			put("psp_Passenger.MiddleName", 30);
			put("psp_Passenger.Type", 1);
			put("psp_Passenger.Nationality", 3);
			put("psp_Passenger.IDNumber", 40);
			put("psp_Passenger.IDType", 10);
			put("psp_Passenger.IDCountry", 3);
			put("psp_Passenger.LoyaltyNumber", 20);
			put("psp_SellerDetails.IDNumber", 40);
			put("psp_SellerDetails.IDType", 10);
			put("psp_SellerDetails.Name", 128);
			put("psp_SellerDetails.Invoice", 32);
			put("psp_SellerDetails.PurchaseDescription", 32);
			put("psp_SellerDetails.MCC", 5);
			put("psp_SellerDetails.ChannelCode", 3);
			put("psp_SellerDetails.GeoCode", 5);
			put("psp_TaxesRequest.TypeId", 5);
			put("psp_MerchantAdditionalDetails.Type", 1);
			put("psp_MerchantAdditionalDetails.SdkInfo", 48);
			put("psp_MerchantAdditionalDetails.ShoppingCartInfo", 48);
			put("psp_MerchantAdditionalDetails.ShoppingCartPluginInfo", 48);
			put("psp_CustomerAdditionalDetails.IPAddress", 45);
			put("psp_CustomerAdditionalDetails.AccountID", 128);
			put("psp_CustomerAdditionalDetails.DeviceFingerPrint", 4000);
			put("psp_CustomerAdditionalDetails.BrowserLanguage", 2);
			put("psp_CustomerAdditionalDetails.HttpUserAgent", 255);
			put("psp_BillingDetails.Invoice", 32);
			put("psp_BillingDetails.InvoiceCurrency", 3);
			put("psp_ShippingDetails.TrackingNumber", 24);
			put("psp_ShippingDetails.Method", 3);
			put("psp_ShippingDetails.Carrier", 3);
			put("psp_ShippingDetails.GiftMessage", 200);
			put("psp_AirlineDetails.TicketNumber", 14);
			put("psp_AirlineDetails.PNR", 10);
			put("psp_VaultReference.PaymentMethodToken", 64);
			put("psp_VaultReference.PaymentMethodId", 64);
			put("psp_VaultReference.CustomerId", 64);
		}
	};

	// Attributes
	private List<ServiceDefinition> _services;
	private Map<String, ComplexType> _types;
	private WsdlHandlerConfiguration _wsdlHandlerConfiguration;

	// Constructor

	public NpsSdk(WsdlHandlerConfiguration wsdlHandlerConfiguration) throws WsdlHandlerException {

		try {
			if (wsdlHandlerConfiguration.getLogLevel() == LogLevel.DEBUG
					&& wsdlHandlerConfiguration.getNpsEnvironment() == NpsEnvironment.production) {
				throw new WsdlHandlerException("LogLevel can't be set to Debug on Production environment");
			}

			_wsdlHandlerConfiguration = wsdlHandlerConfiguration;
			
			String wsdlPath = String.format("%1$s.wsdl", _wsdlHandlerConfiguration.getNpsEnvironment().toString());
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream wsdlStream = classLoader.getResourceAsStream(wsdlPath);

			if (wsdlStream == null) {
				throw new WsdlHandlerException("Missing local WSDL");
			}

			WSDLParser parser = new WSDLParser();			
			Definitions defs = parser.parse(wsdlStream);
			
			_types = getTypes(defs);
			_services = getServices(defs, wsdlHandlerConfiguration);
			
			Security.insertProviderAt(new BouncyCastleProvider(),1);			

		} catch (WsdlHandlerException ex) {
			throw ex;
		}

		catch (Exception ex) {
			_wsdlHandlerConfiguration.getLogger().log(LogLevel.DEBUG, ex.getMessage());
			throw new WsdlHandlerException(ex);
		}

	}
	
	private Map<String, ComplexType> getTypes(Definitions defs){
		Map<String,ComplexType> types = new LinkedHashMap<String, ComplexType>();

		List<com.predic8.schema.ComplexType> complexTypesRead = defs.getTypes().get(0).getAllSchemas().get(0)
				.getComplexTypes();
		for (com.predic8.schema.ComplexType complexTypeRead : complexTypesRead) {
			NpsSDK.ComplexType complexType = new ComplexType();
			complexType.setTypeName(complexTypeRead.getName());
			Boolean isArray = false;
			if (complexTypeRead.getModel() instanceof ComplexContent) {
				String typeModel = ((ComplexContent) complexTypeRead.getModel()).getDerivation().getBasePN()
						.getLocalName();
				if (typeModel.equals("Array")) {
					isArray = true;
				}
			}
			complexType.setIsArray(isArray);
			complexType.setIsMandatory(false);

			if (complexType.isArray() && complexTypeRead.getModel() instanceof ComplexContent) {
				String typeName = complexTypeRead.getName().replaceAll("ArrayOf_", "");
				complexType.setTypeName(typeName);
			}
			outputElements(complexType, complexTypeRead);
			types.put(complexTypeRead.getName(), complexType);
		}
		return types;
	}

	private List<ServiceDefinition> getServices(Definitions defs, WsdlHandlerConfiguration wsdlHandlerConfiguration) {
		List<ServiceDefinition> services = new ArrayList<ServiceDefinition>();
		List<PortType> portTypes = defs.getPortTypes();		
		for (PortType portType : portTypes) {
			List<Operation> operations = portType.getOperations();
			for (Operation operation : operations) {

				ServiceDefinition serviceDefinition = new ServiceDefinition(_wsdlHandlerConfiguration);
				serviceDefinition.setServiceName(operation.getName());

				Input input = operation.getInput();

				if (input != null) {
					String messageName = input.getMessagePrefixedName().getLocalName();
					Message message = defs.getMessage(messageName);
					if (message != null) {
						serviceDefinition.setInputParameterName(message.getParts().get(0).getName());
						serviceDefinition.setInputType(message.getParts().get(0).getTypePN().getLocalName());
					}

				}

				Output output = operation.getOutput();
				if (output != null) {
					String messageName = output.getMessagePrefixedName().getLocalName();
					Message message = defs.getMessage(messageName);
					if (message != null) {
						serviceDefinition.setOutputParameterName(message.getParts().get(0).getName());
						serviceDefinition.setOutputType(message.getParts().get(0).getTypePN().getLocalName());
					}

				}

				serviceDefinition.setInput(getTypeDefinition(serviceDefinition.getInputType()));
				serviceDefinition.setOutput(getTypeDefinition(serviceDefinition.getOutputType()));

				services.add(serviceDefinition);
			}
		}
		return services;
	}

	private static void outputElements(NpsSDK.ComplexType complexType, com.predic8.schema.ComplexType complexTypeRead) {

		if (!(complexTypeRead.getModel() instanceof ModelGroup)) {
			return;
		}
		List<SchemaComponent> components = ((ModelGroup) complexTypeRead.getModel()).getParticles();
		for (SchemaComponent component : components) {
			if (component.getName() == null || component.getName().isEmpty()) {
				continue;
			}

			NpsSDK.Attribute attribute = new Attribute();
			attribute.setAttributeName(component.getName());
			attribute.setAttributeType(((QName) component.getProperty("type")).getLocalPart());
			Boolean isMandatory = Integer.parseInt(((String) component.getProperty("minOccurs"))) > 0;
			attribute.setIsMandatory(isMandatory);
			complexType.addAttribute(attribute);
		}

	}

	private Node getTypeDefinition(String typeName) {
		return getTypeDefinition("", typeName);
	}

	private Node getTypeDefinition(String nodeName, String nodeType) {
		ComplexType complexType = getComplexType(nodeType);
		if (complexType == null) {
			return null;
		}

		Node node = new Node();
		node.setIsArray(complexType.isArray());
		node.setIsMandatory(complexType.isMandatory());
		node.setNodeName(nodeName);
		node.setNodeType(nodeType);
		node.setArrayBaseType(complexType.isArray() ? getTypeDefinition(complexType.getTypeName()) : null);
		node.setIsSimpleType(false);

		for (Attribute attribute : complexType.getAttributes()) {
			Node attributeDefinition = getTypeDefinition(attribute.getAttributeName(), attribute.getAttributeType());
			if (attributeDefinition != null) {
				node.addChild(attributeDefinition);
			} else {
				Node attributeNode = new Node();
				attributeNode.setIsMandatory(attribute.isMandatory());
				attributeNode.setNodeName(attribute.getAttributeName());
				attributeNode.setIsArray(false);
				attributeNode.setNodeType(attribute.getAttributeType());
				attributeNode.setArrayBaseType(null);
				attributeNode.setIsSimpleType(true);

				node.addChild(attributeNode);
			}
		}
		return node;
	}

	private ComplexType getComplexType(String complexTypeName) {
		return _types.containsKey(complexTypeName) ? _types.get(complexTypeName) : null;
	}

	// Call web service

	private RootElement call(RootElement data, String serviceName) throws WsdlHandlerException {

		ServiceDefinition serviceDefinition = this.getService(serviceName);
		if (serviceDefinition == null) {
			throw new WsdlHandlerException("Invalid service: " + serviceName);
		}

		try {
			return serviceDefinition.call(data);
		} catch (Exception ex) {
			_wsdlHandlerConfiguration.getLogger().log(LogLevel.DEBUG, ex.getMessage());
			throw new WsdlHandlerException(ex);
		}
	}

	// Services

	private ServiceDefinition getService(String serviceName) {
		for (ServiceDefinition service : _services) {
			if (service.getServiceName().equals(serviceName)) {
				return service;
			}
		}
		return null;
	}

	public RootElement authorize_2p(RootElement data) throws WsdlHandlerException {
		return call(data, "Authorize_2p");
	}

	public RootElement authorize_3p(RootElement data) throws WsdlHandlerException {
		return call(data, "Authorize_3p");
	}

	public RootElement bankPayment_2p(RootElement data) throws WsdlHandlerException {
		return call(data, "BankPayment_2p");
	}

	public RootElement bankPayment_3p(RootElement data) throws WsdlHandlerException {
		return call(data, "BankPayment_3p");
	}

	public RootElement capture(RootElement data) throws WsdlHandlerException {
		return call(data, "Capture");
	}

	public RootElement cashPayment_3p(RootElement data) throws WsdlHandlerException {
		return call(data, "CashPayment_3p");
	}

	public RootElement changeSecretKey(RootElement data) throws WsdlHandlerException {
		return call(data, "ChangeSecretKey");
	}

	public RootElement createClientSession(RootElement data) throws WsdlHandlerException {
		return call(data, "CreateClientSession");
	}

	public RootElement createPaymentMethod(RootElement data) throws WsdlHandlerException {
		return call(data, "CreatePaymentMethod");
	}

	public RootElement createPaymentMethodFromPayment(RootElement data) throws WsdlHandlerException {
		return call(data, "CreatePaymentMethodFromPayment");
	}

	public RootElement createPaymentMethodToken(RootElement data) throws WsdlHandlerException {
		return call(data, "CreatePaymentMethodToken");
	}

	public RootElement deletePaymentMethod(RootElement data) throws WsdlHandlerException {
		return call(data, "DeletePaymentMethod");
	}

	public RootElement fraudScreening(RootElement data) throws WsdlHandlerException {
		return call(data, "FraudScreening");
	}

	public RootElement getIINDetails(RootElement data) throws WsdlHandlerException {
		return call(data, "GetIINDetails");
	}

	public RootElement getInstallmentsOptions(RootElement data) throws WsdlHandlerException {
		return call(data, "GetInstallmentsOptions");
	}

	public RootElement notifyFraudScreeningReview(RootElement data) throws WsdlHandlerException {
		return call(data, "NotifyFraudScreeningReview");
	}

	public RootElement payOnLine_2p(RootElement data) throws WsdlHandlerException {
		return call(data, "PayOnLine_2p");
	}

	public RootElement payOnLine_3p(RootElement data) throws WsdlHandlerException {
		return call(data, "PayOnLine_3p");
	}

	public RootElement queryCardNumber(RootElement data) throws WsdlHandlerException {
		return call(data, "QueryCardNumber");
	}

	public RootElement queryTxs(RootElement data) throws WsdlHandlerException {
		return call(data, "QueryTxs");
	}

	public RootElement refund(RootElement data) throws WsdlHandlerException {
		return call(data, "Refund");
	}

	public RootElement retrievePaymentMethod(RootElement data) throws WsdlHandlerException {
		return call(data, "RetrievePaymentMethod");
	}

	public RootElement retrievePaymentMethodToken(RootElement data) throws WsdlHandlerException {
		return call(data, "RetrievePaymentMethodToken");
	}

	public RootElement simpleQueryTx(RootElement data) throws WsdlHandlerException {
		return call(data, "SimpleQueryTx");
	}

	public RootElement splitAuthorize_2p(RootElement data) throws WsdlHandlerException {
		return call(data, "SplitAuthorize_2p");
	}

	public RootElement splitAuthorize_3p(RootElement data) throws WsdlHandlerException {
		return call(data, "SplitAuthorize_3p");
	}

	public RootElement splitPayOnLine_2p(RootElement data) throws WsdlHandlerException {
		return call(data, "SplitPayOnLine_2p");
	}

	public RootElement splitPayOnLine_3p(RootElement data) throws WsdlHandlerException {
		return call(data, "SplitPayOnLine_3p");
	}

	// New services from developer page

	public RootElement recachePaymentMethodToken(RootElement data) throws WsdlHandlerException {
		return call(data, "RecachePaymentMethodToken");
	}

	public RootElement updatePaymentMethod(RootElement data) throws WsdlHandlerException {
		return call(data, "UpdatePaymentMethod");
	}

	public RootElement createCustomer(RootElement data) throws WsdlHandlerException {
		return call(data, "CreateCustomer");
	}

	public RootElement updateCustomer(RootElement data) throws WsdlHandlerException {
		return call(data, "UpdateCustomer");
	}

	public RootElement deleteCustomer(RootElement data) throws WsdlHandlerException {
		return call(data, "DeleteCustomer");
	}

	public RootElement retrieveCustomer(RootElement data) throws WsdlHandlerException {
		return call(data, "RetrieveCustomer");
	}

}