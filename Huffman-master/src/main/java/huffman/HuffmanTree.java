package huffman;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import queue.CustomPriorityQueue;
import tree.BinaryTree;
import tree.Node;

@Getter
@Setter
@NoArgsConstructor
public class HuffmanTree {
	protected final int ENCODING_TABLE_SIZE = 127;
	protected String originalString;
	protected BinaryTree huffmanTree;
	protected int[] frequencyArray;
	protected String[] encodingArray;
	
	public HuffmanTree(String originalString) {
		this.originalString = originalString;
		frequencyArray = new int[ENCODING_TABLE_SIZE];
		fillFrequencyArray();
		huffmanTree = createHuffmanTree();
		encodingArray = new String[ENCODING_TABLE_SIZE];
		fillEncodingArray(huffmanTree.getRootNode(), "", "");
	}
	
	private void fillFrequencyArray() {
		for (int i = 0; i < originalString.length(); i++) {
			frequencyArray[(int) originalString.charAt(i)]++;
		}
	}
	
	private BinaryTree createHuffmanTree() {
		CustomPriorityQueue queue = new CustomPriorityQueue();
		for (int i = 0; i < ENCODING_TABLE_SIZE; i++) {
			if (frequencyArray[i] != 0) {
				Node newNode = new Node(frequencyArray[i], (char) i);
				BinaryTree newTree = new BinaryTree(newNode);
				queue.insert(newTree);
			}
		}
		
		while (true) {
			BinaryTree leftTree = queue.remove();
			try {
				BinaryTree rightTree = queue.remove();
				Node newNode = new Node();
				newNode.addChild(leftTree.getRootNode());
				newNode.addChild(rightTree.getRootNode());
				queue.insert(new BinaryTree(newNode));
			} catch (Exception e) {
				return leftTree;
			}
		}
	}
	
	private void fillEncodingArray(Node node, String codeBefore, String direction) {
		if (node.isLeaf()) {
			encodingArray[(int) node.getSymbol()] = codeBefore + direction;
		} else {
			fillEncodingArray(node.getLeftChild(), codeBefore + direction, "0");
			fillEncodingArray(node.getRightChild(), codeBefore + direction, "1");
		}
	}
	
	public String getEncodingTable() {
		StringBuilder enc = new StringBuilder();
		for (int i = 0; i < encodingArray.length; i++) {
			if (frequencyArray[i] != 0)
				enc.append((char) i).append(encodingArray[i]).append('\n');
		}
		return enc.toString();
	}
	
}
