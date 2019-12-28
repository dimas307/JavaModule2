package utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.*;

@NoArgsConstructor
@AllArgsConstructor
public class FileUtils {
	private FileInputStream fileInputStream;
	private FileOutputStream fileOutputStream;
	private BufferedReader bufferedReader;
	
	public FileUtils createFileOutputStream(String fileName) throws FileNotFoundException {
		fileOutputStream = new FileOutputStream(fileName);
		return this;
	}
	
	public FileUtils createFileInputStream(String fileName) throws FileNotFoundException {
		fileInputStream = new FileInputStream(fileName);
		return this;
	}
	
	public FileUtils createBufferedReader(String fileName) {
		bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
		return this;
	}
	
	public void write(byte[] msg) throws IOException {
		fileOutputStream.write(msg);
	}
	
	public void write(String msg) {
		try (PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
			printWriter.write(msg);
		}
	}
	
	public byte readByte() throws IOException {
		int data;
		data = fileInputStream.read();
		if (data == -1) {
			throw new EOFException();
		} else {
			return (byte) data;
		}
	}
	
	public String readLine() throws IOException {
		return bufferedReader.readLine();
	}
	
}
