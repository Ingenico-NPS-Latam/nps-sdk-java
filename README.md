# Java SDK



## Availability
Supports Java 6, 7 and 8


## How to install

```
mvn install:install-file -Dfile=path/to/your/nps_sdk.jar -DgroupId=ar.com.nps -DartifactId=nps_sdk -Dpackaging=jar
```

## Configuration

It's a basic configuration of the SDK

```Java
import NpsSDK.ConsoleLogger;
import NpsSDK.ILogger;
import NpsSDK.NpsSdk;
import NpsSDK.WsdlHandlerConfiguration;
import NpsSDK.ILogger.LogLevel;
import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;
import NpsSDK.WsdlHandlerException;

ILogger consoleLogger = new ConsoleLogger();
WsdlHandlerConfiguration wsdlHandlerConfiguration = new WsdlHandlerConfiguration(LogLevel.DEBUG, NpsEnvironment.sandbox, "_YOUR_SECRET_KEY_", consoleLogger);      
NpsSdk npsSdk = null;
try {
	npsSdk = new NpsSdk(wsdlHandlerConfiguration);
} 
catch (WsdlHandlerException ex) {	
	//Code to handle error
}  
```


Here is an simple example request:

```Java
import NpsSDK.ComplexElement;
import NpsSDK.ComplexElementArray;
import NpsSDK.ComplexElementArrayItem;
import NpsSDK.WsdlHandlerException;

RootElement data = null;
RootElement response = null;

try {
	data = new RootElement();
	data.add("psp_Version", "2.2" );
	data.add("psp_MerchantId", PSPMERCHANTID );
	data.add("psp_TxSource", "WEB" );
	data.add("psp_MerchTxRef", "ORDER69461-3" );
	data.add("psp_MerchOrderId", "ORDER69461" );
	data.add("psp_Amount", "15050" );
	data.add("psp_NumPayments", "1" );
	data.add("psp_Currency", "032" );
	data.add("psp_Country", "ARG" );
	data.add("psp_Product", "14" );
	data.add("psp_CardNumber", "4507990000000010" );
	data.add("psp_CardExpDate", "1612" );
	data.add("psp_PosDateTime", "2016-12-01 12:00:00");
	data.add("psp_CardSecurityCode", "325" );	

	response = npsSdk.payOnLine_2p(data);
} 
catch (WsdlHandlerException ex) {	
	//Code to handle error
}  

```

## Environments

```Java

import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;

```

## Error handling

WsdlHandlerException: This exception is thrown when occurs any error. 

```Java
import NpsSDK.WsdlHandlerException;

//Code
try {
	//code or sdk call
} 
catch (WsdlHandlerException ex) {	
	//Code to handle error
}  
```

## Advanced configurations

Nps SDK allows you to log what’s happening with you request inside of our SDK, it logs by default to the java console

```Java
import NpsSDK.ConsoleLogger;
import NpsSDK.ILogger;
import NpsSDK.WsdlHandlerConfiguration;
import NpsSDK.ILogger.LogLevel;
import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;

ILogger consoleLogger = new ConsoleLogger();
WsdlHandlerConfiguration wsdlHandlerConfiguration = new WsdlHandlerConfiguration(LogLevel.DEBUG, NpsEnvironment.sandbox, "_YOUR_SECRET_KEY_", consoleLogger);      

```
If you prefer the sdk can write the output generated from the logger to the file you provided.
```Java
import NpsSDK.FileLogger;
import NpsSDK.ILogger;
import NpsSDK.WsdlHandlerConfiguration;
import NpsSDK.ILogger.LogLevel;
import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;

ILogger fileLogger = new FileLogger("path/to/your/file.log");
WsdlHandlerConfiguration wsdlHandlerConfiguration = new WsdlHandlerConfiguration(LogLevel.DEBUG, NpsEnvironment.sandbox, "_YOUR_SECRET_KEY_", fileLogger);      
```

The LogLevel.Info level will write concise information of the request and will mask sensitive data of the request. 
The LogLevel.Debug level will write information about the request to let developers debug it in a more detailed way.

```

you can change the open timeout and the read timeout of the request.

```Java
import NpsSDK.ConsoleLogger;
import NpsSDK.ILogger;
import NpsSDK.WsdlHandlerConfiguration;
import NpsSDK.ILogger.LogLevel;
import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;

ILogger consoleLogger = new ConsoleLogger();

int openTimeOut = 60;
int readTimeOut = 60;
WsdlHandlerConfiguration wsdlHandlerConfiguration = new WsdlHandlerConfiguration(LogLevel.DEBUG, NpsEnvironment.sandbox, "_YOUR_SECRET_KEY_", consoleLogger,openTimeOut,readTimeOut);      
```

Proxy configuration

```Java
import NpsSDK.ConsoleLogger;
import NpsSDK.ILogger;
import NpsSDK.WsdlHandlerConfiguration;
import NpsSDK.ILogger.LogLevel;
import NpsSDK.WsdlHandlerConfiguration.NpsEnvironment;

ILogger consoleLogger = new ConsoleLogger();

int openTimeOut = 60;
int readTimeOut = 60;
HttpHost proxy = new HttpHost("PROXY HOST", 8080);
WsdlHandlerConfiguration wsdlHandlerConfiguration = new WsdlHandlerConfiguration(LogLevel.DEBUG, NpsEnvironment.sandbox, "_YOUR_SECRET_KEY_", consoleLogger,openTimeOut,readTimeOut,proxy);      
```
