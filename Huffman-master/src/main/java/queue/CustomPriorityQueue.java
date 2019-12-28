package queue;

import lombok.Getter;
import lombok.Setter;
import tree.BinaryTree;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomPriorityQueue {
	private List<BinaryTree> queueData;
	private int nElem;
	
	public CustomPriorityQueue() {
		queueData = new ArrayList<>();
		nElem = 0;
	}
	
	public void insert(BinaryTree newTree) {
		if (nElem == 0) {
			queueData.add(newTree);
		} else {
			for (int i = 0; i < nElem; i++) {
				if (queueData.get(i).getRootNode().getFrequency() > newTree.getRootNode().getFrequency()) {
					queueData.add(i, newTree);
					break;
				}
				if (i == nElem - 1) {
					queueData.add(newTree);
				}
			}
		}
		nElem++;
	}
	
	public BinaryTree remove() {
		nElem--;
		return queueData.remove(0);
	}
}
