package NpsSDK;

import org.apache.http.*;

public class WsdlHandlerConfiguration {	
	
    public enum NpsEnvironment
    {
        sandbox,
        staging,
        production
    }
	private NpsEnvironment npsEnvironment;
	private NpsSDK.ILogger.LogLevel logLevel;
	private String secretKey;
	private LogWrapper logger;
	private int openTimeOut;
	private int readTimeOut;
	private HttpHost proxy;
	
	
	/**	 
	 * @param  logLevel DEBUG or INFO.
	 * @param  npsEnvironment sandbox, staging or production.
	 * @param  secretKey Secret key provided by Nps.
	 */
	
	public WsdlHandlerConfiguration(NpsSDK.ILogger.LogLevel logLevel, NpsEnvironment npsEnvironment, String secretKey){
		this.logLevel = logLevel;
		this.npsEnvironment = npsEnvironment;
		this.secretKey = secretKey;	
		this.logger = new LogWrapper(logLevel, new ConsoleLogger());
		this.openTimeOut = 100;
		this.readTimeOut = 0;
		this.proxy = null;
		
	}
	
	/**	 
	 * @param  logLevel DEBUG or INFO.
	 * @param  npsEnvironment sandbox, staging or production.
	 * @param  secretKey Secret key provided by Nps.
	 * @param  logger Console logger or File logger.
	 */
	
	public WsdlHandlerConfiguration(NpsSDK.ILogger.LogLevel logLevel, NpsEnvironment npsEnvironment, String secretKey, ILogger logger){
		this.logLevel = logLevel;
		this.npsEnvironment = npsEnvironment;
		this.secretKey = secretKey;	
		this.logger = new LogWrapper(logLevel, logger);
		this.openTimeOut = 100;
		this.readTimeOut = 0;
		this.proxy = null;
		
	}
	
	/**	 
	 * @param  logLevel DEBUG or INFO.
	 * @param  npsEnvironment sandbox, staging or production.
	 * @param  secretKey Secret key provided by Nps.
	 * @param  logger Console logger or File logger.
	 * @param  openTimeOut The number of seconds to wait before the request times out. The default value is 100 seconds.
	 * @param  readTimeOut The number of seconds to wait before the socket times out. The default value is 0 seconds interpreted as infinite.
	 */
	
	public WsdlHandlerConfiguration(NpsSDK.ILogger.LogLevel logLevel, NpsEnvironment npsEnvironment, String secretKey, ILogger logger, int openTimeOut, int readTimeOut){
		this.logLevel = logLevel;
		this.npsEnvironment = npsEnvironment;
		this.secretKey = secretKey;
		this.logger = new LogWrapper(logLevel, logger);
		this.openTimeOut = openTimeOut;
		this.readTimeOut = readTimeOut;
		this.proxy = null;
	}
	
	/**	 
	 * @param  logLevel DEBUG or INFO.
	 * @param  npsEnvironment sandbox, staging or production.
	 * @param  secretKey Secret key provided by Nps.
	 * @param  logger Console logger or File logger.
	 * @param  openTimeOut The number of seconds to wait before the request times out. The default value is 100 seconds.
	 * @param  readTimeOut The number of seconds to wait before the socket times out. The default value is 0 seconds interpreted as infinite.
	 * @param  proxy HttpHost proxy.
	 */
	public WsdlHandlerConfiguration(NpsSDK.ILogger.LogLevel logLevel, NpsEnvironment npsEnvironment, String secretKey, ILogger logger, int openTimeOut, int readTimeOut, HttpHost proxy){
		this.logLevel = logLevel;
		this.npsEnvironment = npsEnvironment;
		this.secretKey = secretKey;
		this.logger = new LogWrapper(logLevel, logger);
		this.openTimeOut = openTimeOut;
		this.readTimeOut = readTimeOut;
		this.proxy = proxy;
		
	}
	
	
	NpsEnvironment getNpsEnvironment(){
		return npsEnvironment;
	}	
	
	NpsSDK.ILogger.LogLevel getLogLevel(){
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
		switch(npsEnvironment){
			case sandbox:
				return "https://sandbox.nps.com.ar/ws.php";
			case staging:
				return "https://implementacion.nps.com.ar/ws.php";
			case production:
				return "https://services2.nps.com.ar/ws.php";		
			default:
				throw new WsdlHandlerException("Wrong nps Environment");
		}
	}	
	
}
