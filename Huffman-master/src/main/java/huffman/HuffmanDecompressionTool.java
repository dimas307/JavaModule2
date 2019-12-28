package huffman;

public class HuffmanDecompressionTool extends HuffmanTree {
	public String extract(String compressed, String[] newEncodingArray) {
		StringBuilder decompressed = new StringBuilder();
		StringBuilder current = new StringBuilder();
		StringBuilder delta = new StringBuilder();
		encodingArray = newEncodingArray;
		
		for (int i = 0; i < 8; i++) {
			delta.append(compressed.charAt(i));
		}
		int zeroes = Integer.parseInt(delta.toString(), 2);
		
		for (int i = 8, l = compressed.length() - zeroes; i < l; i++) {
			current.append(compressed.charAt(i));
			for (int j = 0; j < ENCODING_TABLE_SIZE; j++) {
				if (current.toString().equals(encodingArray[j])) {
					decompressed.append((char) j);
					current = new StringBuilder();
				}
			}
		}
		return decompressed.toString();
	}
}
