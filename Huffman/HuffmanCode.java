// class HuffmanCode and inner class HuffmanNode to compress 
//and decompress data. This HuffmanCode object represents 
//the overall binary tree of character frequencies in 
// the given frequency array. 
//A client can construct a new HuffmanTree, query the object
// for a map of character encodings based on the tree's structure, 
//and compress/decompress an input stream into a respective output stream.

import java.io.*;
import java.util.*;

public class HuffmanCode {
	//Define the priority queue
	private Queue<HuffmanNode> minPriorityQueue ;
	//The root of the Huffman tree
	private HuffmanNode rootNode;
	
	//An inner class , Defines node of the Huffman tree.
	//implements the Comparable interface. 
    private static class HuffmanNode implements Comparable<HuffmanNode> {    	
    	public Character ch; // Node character
		public int freq;// Character frequency
		
    	public HuffmanNode left; // Left child node reference
    	public HuffmanNode right; // Reference the right child node
    	
    	//pre: take character ch and integar freq as parameters
    	//initializes a new HuffmanNode object based on 
    	//the given character and frequency
    	public HuffmanNode(Character ch,int freq) {
    		this.ch = ch;
        	this.freq = freq;
        	this.left = null;
        	this.right = null;
    	}

		//pre: take the HuffmanNode o as a parameter
    	//the comparison function
		public int compareTo(HuffmanNode o) {			
			return this.freq - o.freq;
		}  
    }
	
	//pre: take array frequencies as a parameter
	// initializes a new Huffman object based on 
	//the given array of character frequencies.
	//The frequency has to be greater than 0
	public HuffmanCode(int[] frequencies) {
		minPriorityQueue = new PriorityQueue<>();//�������ȼ�����
		//Sets the priority queue based on the given frequency array
		for(int i=0;i<frequencies.length;i++) {
			//Generate a new node
			HuffmanNode newNode=new HuffmanNode((char)i,frequencies[i]);
			//If the frequency is greater than 0, 
			//the node is added to the Huffman tree
			if(frequencies[i]>0) {
				minPriorityQueue.offer(newNode);	
			}			
		}
		
		//According to the priority queue, Huffman tree is established
		for(;!minPriorityQueue.isEmpty();) {			
			
			HuffmanNode first=minPriorityQueue.remove();			
			HuffmanNode second=minPriorityQueue.remove();
			HuffmanNode root=new HuffmanNode(null,first.freq+second.freq);		

            //The node with low frequency is the left child node, 
			//and the node with high frequency is the right child node
			if(first.freq<=second.freq) {
				root.left=first;
				root.right=second;
			}else {
				root.left=second;
				root.right=first;
			}
			
			//If the priority queue is not empty,
			//continue to build the Huffman tree			
			if (!minPriorityQueue.isEmpty()) {
				minPriorityQueue.add(root);
			}else{
			rootNode = root;//Update the root node of the tree
			}
		}
		
		
	}

	// pre: take scanner input as a parameter
	// The Huffman constructor initializes the new Huffman object based on 
	//the input encoding and restores the Huffman tree.
	public HuffmanCode(Scanner input) {
		//Setting the root	
		if(rootNode==null) {
			rootNode=new HuffmanNode(null,0);
		}

		HuffmanNode curNode=rootNode;
		//Read all characters and their Huffman encoding
		while(input.hasNext()) {
			//Read character (ASCII code)
			Character ch=(char)Integer.parseInt(input.next());			
			if(input.hasNext()) {
				//Reads the Huffman encoding of the character
				String code=input.next();
				//A Huffman tree is built based on characters and their Huffman codes
				for (int i=0;i<code.length();i++){
					//1,Traverse the right subtree
					if (code.charAt(i)=='1' ) {						
						if(i==code.length()-1)curNode.right=new HuffmanNode(ch,0);
						else if(curNode.right==null)curNode.right=new HuffmanNode(null,0);
						curNode=curNode.right;
					}else {
						//0,traverses the left subtree
						if(i==code.length()-1)curNode.left=new HuffmanNode(ch,0);
						else if(curNode.left==null)curNode.left=new HuffmanNode(null,0);
						curNode=curNode.left;
					}
				}
				curNode=rootNode;				
			}
		}		
	}
	
	//pre: take printStream output as a parameter
	//Store the current Huffman codes to the given output stream
	//in the standard format 
	public void save(PrintStream output) {			
		// The Huffman code of all characters is saved in the Huffman tree
		save(output,rootNode,"");		
	}
	
	//pre: take printStream output,HuffmanNode node and string encode as parameters
	// This method is a private help method. Used to achieve recursive traversal Huffman tree,
	//obtain and save the corresponding character Huffman code
	private void save(PrintStream output,HuffmanNode node,String encode) {
		if (node == null) {
			return;
		}
		if (node.left == null && node.right == null) {
			output.println((int) node.ch);
			output.println(encode);
		}
		if (node.left != null) {
			save(output, node.left, encode + "0");
		}
		if (node.right != null) {
			save(output, node.right, encode + "1");
		}
		
	}

	//pre: take scanner input and printStream output as parameters
	//Read the input encoding bit,
	//convert to the pre-compression content, and output.
	public void translate(Scanner input, PrintStream output) {
		HuffmanNode node=rootNode;
		//Read all compressed content and restore 
		//the original content based on the Huffman tree
		while (input.hasNext()) {
			String encode = input.next();
			for (int i = 0; i < encode.length(); i++) {
				char bitChar = encode.charAt(i);
				if (bitChar == '0' && node.left != null) {
					node = node.left;
				}else if(bitChar == '1' && node.right != null) {
					node = node.right;
				}
				if (node.left == null && node.right == null) {
					output.write(node.ch);
					node = rootNode;
				}
			}
		}
			
	}

}


