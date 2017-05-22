package IngenicoSDK;

import org.apache.http.HttpHost;

public class WsdlHandlerConfiguration {
	
    /// <summary>
    /// 
    /// </summary>
    /// <param name="logLevel"></param>
    /// <param name="ingenicoEnvironment"></param>
    /// <param name="secretKey"></param>
    /// <param name="logger"></param>
    /// <param name="openTimeOut">The number of seconds to wait before the request times out. The default value is 100 seconds</param>
	/// <param name="readTimeOut">The number of seconds to wait before the socket times out. The default value is 0 seconds interpreted as infinite</param>
    /// <param name="proxy"></param> 
	
	
    public enum IngenicoEnvironment
    {
        sandbox,
        staging,
        production
    }
	private IngenicoEnvironment ingenicoEnvironment;
	private IngenicoSDK.ILogger.LogLevel logLevel;
	private String secretKey;
	private LogWrapper logger;
	private int openTimeOut;
	private int readTimeOut;
	private HttpHost proxy;
	
	
	
	public WsdlHandlerConfiguration(IngenicoSDK.ILogger.LogLevel logLevel, IngenicoEnvironment ingenicoEnvironment, String secretKey){
		this.logLevel = logLevel;
		this.ingenicoEnvironment = ingenicoEnvironment;
		this.secretKey = secretKey;	
		this.logger = new LogWrapper(logLevel, new ConsoleLogger());
		this.openTimeOut = 100;
		this.readTimeOut = 0;
		this.proxy = null;
		
	}
	
	
	public WsdlHandlerConfiguration(IngenicoSDK.ILogger.LogLevel logLevel, IngenicoEnvironment ingenicoEnvironment, String secretKey, ILogger logger){
		this.logLevel = logLevel;
		this.ingenicoEnvironment = ingenicoEnvironment;
		this.secretKey = secretKey;	
		this.logger = new LogWrapper(logLevel, logger);
		this.openTimeOut = 100;
		this.readTimeOut = 0;
		this.proxy = null;
		
	}
	
	public WsdlHandlerConfiguration(IngenicoSDK.ILogger.LogLevel logLevel, IngenicoEnvironment ingenicoEnvironment, String secretKey, ILogger logger, int openTimeOut, int readTimeOut){
		this.logLevel = logLevel;
		this.ingenicoEnvironment = ingenicoEnvironment;
		this.secretKey = secretKey;
		this.logger = new LogWrapper(logLevel, logger);
		this.openTimeOut = openTimeOut;
		this.readTimeOut = readTimeOut;
		this.proxy = null;
	}
	
	public WsdlHandlerConfiguration(IngenicoSDK.ILogger.LogLevel logLevel, IngenicoEnvironment ingenicoEnvironment, String secretKey, ILogger logger, int openTimeOut, int readTimeOut, HttpHost proxy){
		this.logLevel = logLevel;
		this.ingenicoEnvironment = ingenicoEnvironment;
		this.secretKey = secretKey;
		this.logger = new LogWrapper(logLevel, logger);
		this.openTimeOut = openTimeOut;
		this.readTimeOut = readTimeOut;
		this.proxy = proxy;
		
	}
	
	
	public IngenicoEnvironment getIngenicoEnvironment(){
		return ingenicoEnvironment;
	}	
	
	public IngenicoSDK.ILogger.LogLevel getLogLevel(){
		return logLevel;
	}

	String getSecretKey(){
		return secretKey;
	}
	
	
	LogWrapper getLogger(){
		return logger;
	}
	
	int getOpenTimeOut(){
		return openTimeOut;
	}
	
	int getReadTimeOut(){
		return readTimeOut;
	}
	
	HttpHost getProxy(){
		return proxy;
	}
		
	String getServiceUrl() throws WsdlHandlerException{
		switch(ingenicoEnvironment){
			case sandbox:
				return "https://sandbox.nps.com.ar/ws.php";
			case staging:
				return "https://implementacion.nps.com.ar/ws.php";
			case production:
				return "https://services2.nps.com.ar/ws.php";		
			default:
				throw new WsdlHandlerException("Wrong ingenico Envrionment");
		}
	}	
	
}
