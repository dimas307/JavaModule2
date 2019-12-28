package tree;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Node {
	private int frequency;
	
	private char symbol;
	
	private Node leftChild;
	
	private Node rightChild;
	
	public Node(int frequency, char symbol) {
		this.frequency = frequency;
		this.symbol = symbol;
	}
	
	public void addChild(Node newChild) {
		if (leftChild == null) {
			leftChild = newChild;
		} else {
			if (leftChild.getFrequency() <= newChild.getFrequency())
				rightChild = newChild;
			else {
				rightChild = leftChild;
				leftChild = newChild;
			}
		}
		
		frequency += newChild.getFrequency();
	}
	
	public boolean isLeaf() {
		return this.leftChild == null && this.rightChild == null;
	}
	
}
