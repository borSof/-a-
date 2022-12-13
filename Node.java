package zadanie;

class Node implements Comparable<Node> {
	private char character;
	private int frequency;
	private Node leftChild;
	private Node rightChild;

	Node(char character, int frequency, Node leftChild, Node rightCild) {
		this.character = character;
		this.frequency = frequency;
		this.leftChild = leftChild;
		this.rightChild = rightCild;

	}

	// ako nqma deca zadavame this.node kato leaf
	boolean isLeaf() {
		return this.leftChild == null && this.rightChild == null;
	}

	// sravnenie na chestoti
	@Override
	public int compareTo(Node that) {
		int frequencyComparison = Integer.compare(this.frequency, that.frequency);
		if (frequencyComparison != 0) {
			return frequencyComparison;
		}
		return Integer.compare(this.character, that.character);
	}
	
	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

}
