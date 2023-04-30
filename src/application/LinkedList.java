package application;

public class LinkedList {


public class Node {
	Object element;
	 Node next;
	public Node (Object x)
	{
		element=x;
	}
}
	private int count=0;
	Node first,last;
	
	public void addFirst(Object x) {
		if (count == 0) {
			first = last = new Node(x);
		} else {
			Node temp = new Node(x);
			temp.next = first;
			first = temp;
		}
		count++;
	}
	////////////////////////////////
	public boolean removeFirst() {
		if (count == 0) {
			return false;
		} else if (count == 1) {
			first = last = null;
			count--;
			return true;
		} else {
			first = first.next;
			count--;
			return true;
		}
	}
	/////////////////////////////////
	public Object getFirst() {
		if (count == 0)
			return null;
		else
			return first.element;
	}
	//////////////////////////////
	public void printList() {
		Node current = first;
		while (current != null) {
			System.out.print(current.element + " ");
			current = current.next;
		}
		System.out.print("\n");
	}
	///////////////////////////////
	
	public int getCount() {
		return count;
	}


}
