package application;

public class CursorStack {
	
private final static int space = 500 ;
static Node [] cursorSpace = new Node [space] ; //array of nodes
	
	static {
for (int i = 0 ; i < space ; i++) {
	cursorSpace[i] = new Node (null , i+1) ; //fill array 
}
cursorSpace[space-1].next = 0 ; //end of array
	}
	//////////////////////////////////////////////////////////////
	//curser>>>>alloc,free,isEmpty
	
	public static int alloc () {
		//time complexity>>>constant
		int temp = cursorSpace[0].next ; // any node after index zero is available to use
		cursorSpace[0].next = cursorSpace[temp].next ; 
		
		if (temp==0) { //the array is full
			throw new OutOfMemoryError() ;
		}
		cursorSpace[temp].next = 0;
		return temp ; //return available node
	}
	////////////////////////////////////////////////////////////////
	private static void free (int p ) { // add deleted node to " empty list cursorSpace[0]" to make it available to use
		cursorSpace[p].value = null; 
		//add first 
		cursorSpace[p].next = cursorSpace[0].next ;
		cursorSpace[0].next = p ;
	}
	/////////////////////////////////////////////////////////////////
	public static boolean isEmpty (int header) {
		return cursorSpace[header].next == 0 ;
	}
	//////////////////////////////////////////////////////////////////////////////////////
	//stack>>>>push,gettop,pop
	
	public static void push (int header , Object x) { //insert in cursor
		int tempAlloc = alloc() ; //available node
		
		if (isEmpty(header)) { //insert one node
			cursorSpace[header].next = tempAlloc ;
			cursorSpace[tempAlloc].value = x ;
		}
		else {//add first after header
			cursorSpace[tempAlloc].next = cursorSpace[header].next ;
			cursorSpace[header].next = tempAlloc ;
			cursorSpace[tempAlloc].value = x ;
		}
	}
	/////////////////////////////////////////////////////////////////////
	public static Object getTop(int header) { //get first node
		if (isEmpty(header)) { 
			return null ;
		}
		int top = cursorSpace[header].next  ; //first node after header
		return cursorSpace[top].value ;
	}
	//////////////////////////////////////////////////////////////////////
	public static boolean pop (int header) { //remove first node after header
		if (isEmpty(header)) {
			return false ;
		}
		int temp = cursorSpace[header].next ; //temp=first node after header
		cursorSpace[header].next = cursorSpace[temp].next ; //to delete it
		free(temp); //to make this node available to use		
		return true ;
	}
	//////////////////////////////////////////////////////////////////////
	public static void print (int header) {
		int temp = cursorSpace[header].next ;
		while (temp != 0) {
			System.out.println(cursorSpace[temp].value);
			temp = cursorSpace[temp].next ;
		}
	}
	//////////////////////////////////////////////////////////////////////////

}
		

