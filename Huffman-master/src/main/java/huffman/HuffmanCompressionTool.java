package huffman;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HuffmanCompressionTool extends HuffmanTree {
	private HuffmanTree compressorHuffmanTree;
	
	public HuffmanCompressionTool(HuffmanTree compressorHuffmanTree) {
		this.compressorHuffmanTree = compressorHuffmanTree;
		originalString = compressorHuffmanTree.getOriginalString();
		encodingArray = compressorHuffmanTree.getEncodingArray();
		frequencyArray = compressorHuffmanTree.getFrequencyArray();
	}
	
	private String getCompressedString() {
		StringBuilder intermediate = new StringBuilder();
		for (int i = 0; i < originalString.length(); i++) {
			if (encodingArray[originalString.charAt(i)] != null) {
				intermediate.append(encodingArray[originalString.charAt(i)]);
			}
		}
		byte counter = 0;
		for (int length = intermediate.length(), delta = 8 - length % 8; counter < delta; counter++) {
			intermediate.append("0");
		}
		return String.format("%8s", Integer.toBinaryString(counter & 0xff)).replace(" ", "0") + intermediate.toString();
	}
	
	public byte[] getCompressedMsg() {
		StringBuilder compressedString = new StringBuilder(getCompressedString());
		byte[] compressedBytes = new byte[compressedString.length() / 8];
		for (int i = 0; i < compressedBytes.length; i++) {
			compressedBytes[i] = (byte) Integer.parseInt(compressedString.substring(i * 8, (i + 1) * 8), 2);
		}
		return compressedBytes;
	}
	
	
}
