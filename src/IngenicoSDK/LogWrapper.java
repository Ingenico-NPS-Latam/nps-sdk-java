package IngenicoSDK;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import IngenicoSDK.ILogger.LogLevel;

class LogWrapper {

	private LogLevel _minimumLevel;
	private ILogger _logger;

	public LogWrapper(LogLevel minimumLevel, ILogger logger) {
		_logger = logger;
		_minimumLevel = minimumLevel;
	}

	private static Pattern[] sanitizerRegularExpressions = {
			Pattern.compile("(<psp_CardExpDate[^>]*>)(.*)(</psp_CardExpDate>)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
			Pattern.compile("(<psp_CardSecurityCode[^>]*>)(.*)(</psp_CardSecurityCode>)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
			Pattern.compile("(<psp_CardNumber[^>]*>.{6})(.*)(.{4}</psp_CardNumber>)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
			Pattern.compile("(<Number[^>]*>.{6})(.*)(.{4}</Number>)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
			Pattern.compile("(<ExpirationDate[^>]*>)(.*)(</ExpirationDate>)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
			Pattern.compile("(<SecurityCode[^>]*>)(.*)(</SecurityCode>)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE) };

	public void log(LogLevel logLevel, String message) {
		if (_logger == null || logLevel.ordinal() < _minimumLevel.ordinal()) {
			return;
		}

		_logger.log(message);
	}

	public void logRequest(LogLevel logLevel, String request) {
		if (_logger == null || logLevel.ordinal() < _minimumLevel.ordinal()) {
			return;
		}

		String message = Beautify(request);
		if (_minimumLevel.ordinal() > LogLevel.DEBUG.ordinal()) {
			for (int i = 0; i < sanitizerRegularExpressions.length; i++) {
				Pattern regex = sanitizerRegularExpressions[i];
				Matcher matcher = regex.matcher(message);
				while (matcher.find()) {
					message = message.replaceAll(matcher.group(), String.format("%1$s%2$s%3$s", matcher.group(1),
							matcher.group(2).replaceAll(".", "*"), matcher.group(3)));
				}
			}
		}
		log(logLevel, "Request:" + System.getProperty("line.separator") + message);

	}

	public void logResponse(LogLevel logLevel, String response) {
		log(logLevel, "Response: " + System.getProperty("line.separator") + Beautify(response));
	}

	private static String Beautify(String xml) {

		StreamResult result = null;
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = documentBuilder.parse(new InputSource(new StringReader(xml)));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);

		} catch (Exception e) {
		}

		return result.getWriter().toString();

	}

}
