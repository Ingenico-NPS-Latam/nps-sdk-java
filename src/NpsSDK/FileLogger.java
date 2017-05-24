package NpsSDK;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger {

	private String _filePath;
	
	public FileLogger(String filePath){
		_filePath = filePath;
	}
	
	@Override
	public void log(String message) {
		File file = new File(_filePath);
		FileWriter writer;
		try {
			file.createNewFile();
			writer = new FileWriter(file,true);
			writer.append(message);
			writer.close();
		} catch (IOException e) {
		} 
	}
}
