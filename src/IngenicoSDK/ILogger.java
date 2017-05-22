package IngenicoSDK;

public interface ILogger {
	
	public enum LogLevel {
		DEBUG,
		INFO
	}
	
	void log(String message);
	
}
