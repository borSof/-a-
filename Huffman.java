package zadanie;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
	// брой на знаци в аски
	private static int ASCII_SIZE = 256;

	//
	public HuffmanEncodedResult compress(String data) {
		int[] freq = buildFrequencyTable(data);
		Node root = buildHuffmanTree(freq);
		Map<Character, String> lookupTable = buildLookupTable(root);
		System.out.println("Char codes: " + lookupTable + "\n");
		return new HuffmanEncodedResult(generateEncodetData(data, lookupTable), root);
	}

	// suzdavame masiv ot char-ovete na stringa za zapisvane na chestotata na
	// vseki simvol ot ascii tablicata
	// =====================================================

	private static int[] buildFrequencyTable(String data) {
		// масив за честота на знаци
		int[] freq = new int[ASCII_SIZE];
		// zadavane na indexi po kodovete na char-ovete
		for (char charecter : data.toCharArray()) {
			freq[charecter]++;
		}
		return freq;
	}
	// =====================================================
	// vrushtane na korena na dyrvoto

	private static Node buildHuffmanTree(int[] freq) {
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		// vseki index na masiva koito e po golqm ot 0 se polzva kato pole za obekt Node
		for (char i = 0; i < ASCII_SIZE; i++) {
			if (freq[i] > 0) {
				// suzdavame Node
				priorityQueue.add(new Node(i, freq[i], null, null));
			}
		}
		// ako ima samo 1 element dobavqm nov zashtoto .pol mi vrushta " " pri samo edin
		// element v opashkata
		if (priorityQueue.size() == 1) {
			priorityQueue.add(new Node('\0', 0, null, null));
		}

		// subira chestotoata na vsichki nodove do dostigane na korena
		while (priorityQueue.size() > 1) {
			Node left = priorityQueue.poll();
			Node right = priorityQueue.poll();
			Node parent = new Node('\0', left.getFrequency() + right.getFrequency(), left, right);
			priorityQueue.add(parent);
		}
		return priorityQueue.poll();
	}

	// =====================================================
	// inicializiram map i go vrushtam populnen
	private static Map<Character, String> buildLookupTable(/* koreut_na_durvoto */ Node root) {

		Map<Character, String> lookupTable = new HashMap<>();
		buildLookupTableImpl(root, "", lookupTable);
		return lookupTable;
	}

	// =====================================================
	// zadavam cod na vseki znak
	private static void buildLookupTableImpl(Node node, String s, Map<Character, String> lookupTable) {
		// rekursivno zapisvam kodovete na vsqko dete sus concatenaciq
		if (!node.isLeaf()) {
			buildLookupTableImpl(node.getLeftChild(), s + '0', lookupTable);
			buildLookupTableImpl(node.getRightChild(), s + '1', lookupTable);
		} else {
			// zapisva listoto vuv Map-a
			lookupTable.put(node.getCharacter(), s);
		}

	}

	// =====================================================
	// subiram vsichkite codove na elementi
	private static String generateEncodetData(String data, Map<Character, String> lookupTable) {
		StringBuilder builder = new StringBuilder();
		for (char character : data.toCharArray()) {
			builder.append(lookupTable.get(character));
		}
		return builder.toString();
	}

	// =====================================================
	public String decompress(HuffmanEncodedResult result) {
		StringBuilder resultBuilder = new StringBuilder();
		Node current = result.getRoot();
		int i = 0;
		while (i < result.getEncodedData().length()) {
			while (!current.isLeaf()) {
				char bit = result.getEncodedData().charAt(i);
				if (bit == '1') {
					current = current.getRightChild();
				} else if (bit == '0') {
					current = current.getLeftChild();
				} else {
					throw new IllegalArgumentException("Invalid bit " + bit);
				}
				i++;
			}
			resultBuilder.append(current.getCharacter());
			current = result.getRoot();
		}
		return resultBuilder.toString();
	}

//======================================================================

	public static void main(String[] args) {

		String test = "Hello World!";
		System.out.println("String: " + test + "\n");

		// printfreq(buildFrequencyTable(test));
		Huffman encoder = new Huffman();
		HuffmanEncodedResult result = encoder.compress(test);

		System.out.println("Encoded message: " + result.encodedData);
		System.out.println("Decoded message: " + encoder.decompress(result));
	}

}
