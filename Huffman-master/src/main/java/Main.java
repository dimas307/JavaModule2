import huffman.HuffmanCompressionTool;
import huffman.HuffmanDecompressionTool;
import huffman.HuffmanTree;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner consoleScanner = new Scanner(System.in);
		System.out.println("Enter the absolute path to your file:");
		String filePath = consoleScanner.nextLine();
		try {
			if (!filePath.endsWith(".hf")) {
				compress(filePath);
				System.out.println("Done!");
			} else if (filePath.endsWith(".hf")) {
				extract(filePath);
				System.out.println("Done!");
			} else {
				throw new IllegalArgumentException();
			}
		} catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private static void compress(String stringPath) throws IOException {
		List<String> stringList;
		File inputFile = new File(stringPath);
		String compressedFileName = stringPath.substring(0, stringPath.indexOf('.')) + ".hf";
		String tableFileName = inputFile.getParent() + "\\table.txt";
		StringBuilder originalString = new StringBuilder();
		
		try {
			stringList = Files.readAllLines(Paths.get(inputFile.getAbsolutePath()));
		} catch (NoSuchFileException | MalformedInputException e) {
			e.printStackTrace();
			return;
		}
		
		for (String item : stringList) {
			originalString.append(item).append("\n");
		}
		HuffmanCompressionTool huffmanCompressionTool = new HuffmanCompressionTool(new HuffmanTree(originalString.toString()));
		
		FileUtils compressedFileUtils = new FileUtils().createFileOutputStream(compressedFileName);
		FileUtils tableUtils = new FileUtils().createFileOutputStream(tableFileName);
		compressedFileUtils.write(huffmanCompressionTool.getCompressedMsg());
		tableUtils.write(huffmanCompressionTool.getEncodingTable());
	}
	
	private static void extract(String filePath) throws IOException {
		HuffmanDecompressionTool huffmanDecompressionTool = new HuffmanDecompressionTool();
		String[] encodingArray = new String[huffmanDecompressionTool.getENCODING_TABLE_SIZE()];
		String extractedFilePath = filePath.substring(0, filePath.indexOf('.')) + ".txt";
		File file = new File(filePath);
		String tablePath = file.getParent() + "\\table.txt";
		StringBuilder compressed = new StringBuilder();
		FileUtils compressedFileUtils = new FileUtils().createFileInputStream(filePath);
		FileUtils tableFileUtils = new FileUtils().createFileInputStream(tablePath).createBufferedReader(tablePath);
		
		byte b;
		while (true) {
			try {
				b = compressedFileUtils.readByte();
			} catch (Exception e) {
				break;
			}
			compressed.append(String.format("%8s", Integer.toBinaryString(b & 0xff)).replace(" ", "0"));
		}
		tableFileUtils.readLine();
		encodingArray[(byte) '\n'] = tableFileUtils.readLine();
		while (true) {
			String s = tableFileUtils.readLine();
			if (s == null) {
				break;
			}
			encodingArray[(byte) s.charAt(0)] = s.substring(1);
		}
		
		FileUtils decompressedFileUtils = new FileUtils().createFileOutputStream(extractedFilePath);
		decompressedFileUtils.write(huffmanDecompressionTool.extract(compressed.toString(), encodingArray));
	}
}
