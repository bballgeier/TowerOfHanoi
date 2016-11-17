// Node.java
// implements a standard Node class
// to be used by the Stack class

public class Node {
	private String data;
	private Node next;
	
	public static void main(String[] args) {
		Node node = new Node("some data", null);
		Node newNode = new Node(node);
	}
	
	public Node(String data, Node next){
		this.data = data;
		this.next = next;
	} // end constructor
	
	// copy constructor
	public Node(Node aNode){
		this(aNode.getData(), aNode.getNext());
	} // end copy constructor
	
	public void setData(String data){
		this.data = data;
	}
	
	public String getData(){
		return this.data;
	}
	
	public void setNext(Node next){
		this.next = next;
	}
	
	// defensive copying since Node is mutable -- note that String is immutable
	public Node getNext(){
		if (this.next != null){ // don't want to call null.getNext
			return new Node(this.next.getData(), this.next.getNext()); // recursive
		} // end if
		else {
			return null;
		}
		//return this.next;
	}
	
	public int getSize(){
		return this.data.length();
	}
} // end Node