package zadanie;

class HuffmanEncodedResult {
	Node root;
	String encodedData;

	HuffmanEncodedResult(String encodetData, Node root) {
		this.encodedData = encodetData;
		this.root = root;
	}

	public String getEncodedData() {
		return this.encodedData;
	}

	public Node getRoot() {
		return this.root;
	}
}
