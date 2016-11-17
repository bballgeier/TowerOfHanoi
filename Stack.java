// Stack.java
// implements a standard stack class

public class Stack {
    private Node top;
    private String id;
    private int size;
    
    // test harness
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push("First");
        System.out.println("size of first: " + stack.getTopSize());
        Node second = new Node("Second", stack.getTop());
        stack.push(second.getData());
        
        stack.push("Third");
        stack.push("Fourth");
        System.out.println("Size of stack is 4? " + stack.getSize());
        
        System.out.println("size of second: " + stack.getTopSize());
        System.out.println("Size of stack: " + stack.getSize());
        //System.out.println(stack.pop());
        //System.out.println(stack.pop
        
        Stack stack2 = getInstance(stack);
        System.out.println("Testing equality for tops");
        if (stack.getTop() == stack2.getTop()) {
            System.out.println("tops are equal");
        }
        
        //System.out.println("Testing equality for tops after changing stack's top");
        //System.out.println("Here is stack's top: " + stack.pop());
        //if (stack.getTop() == stack2.getTop()) {
        //    System.out.println("tops are equal");
        //}
        // pop changes what stack.top references, so the tops are no longer equal

        System.out.println("Testing equality for tops after mutating stack's top");
        stack.getTop().setData("somethingNew");
        //System.out.println("Here is stack's top: " + stack.pop());
        if (stack.getTop() == stack2.getTop()) {
            System.out.println("tops are equal");
        }
        
        // ah, be careful -- calling getTop creates a new node
        //stack.getTop().setNext(second);
        // so lets push something on to stack, that will change its top
        stack.push("Fifth");
        
        System.out.println("Using printStack on stack");
        stack.printStack();
        
        System.out.println("stack:");
        stack.print();
        System.out.println("stack2");
        stack2.print();
        System.out.println(stack.pop());
        System.out.println(stack2.pop());
    } // end main
    
    public Stack(){
        top = null;
        id = "";
        size = 0;
    } // end constructor
    
    public Stack(String id){
        top = null;
        this.setId(id);
        size = 0;
    }
    
    public void push(String data){
        Node newNode = new Node(data, top);
        top = newNode;
        size++;
    } // end push
    
    public String pop(){
        String value;
        if (top == null){
            value = "stack empty";
        } else {
            value = top.getData();
            top = top.getNext();
            size--;
        } // end if
        return value;
    } // end pop
    
    // defensive copying
    public Node getTop(){  
        if (this.top != null){      // don't want to call Node(null)
            return new Node(this.top);
        } // end if
        else { // top is indeed null
            return null;
        }
        //return this.top;
    } // end getTop
    
    public void setTop(Node top){
        this.top = top;
    } // end setTop
    
    public int getTopSize(){
        // returns -1 if top is null
        // 0 doesn't seem right because top.data could be a string of length 0
        if (this.getTop() != null){
            return this.getTop().getSize();
        } else {
            return -1;
        }
    } // end getTopSize
    
    public String getTopData(){
        String data = this.getTop().getData();
        return data;
    } // end getTopData
    
    public String getId(){
        return id;
    } // end getId
    
    public void setId(String id){
        this.id = id;
    } // end setId
    
    // override when printing object
    public String toString(){
        return id;
    } // end toString
    
    public void print(){                 // this pops data, might want to avoid that
        System.out.println(this + " has size " + this.getSize());
        while (this.getTop() != null) {
            int size = this.getTopSize();
            System.out.println(this + ": " + this.pop() + " has size " + size);
        } // end while
    } // end print
    
    // here is an alternative print
    // it probably makes more sense
    // but I learned a lot by using the one above -- because it calls pop which pops the top off the
    // some people on the internet that you shouldn't use a stack if you are going to do this
    public void printStack(){
        System.out.println(this + " has size " + this.getSize());
        Stack temp = getInstance(this);
        while (temp.getTop() != null) {
            int size = temp.getTopSize();
            System.out.println(temp + ": " + temp.pop() + " has size " + size);
        } // end while
    } // end printStack
    
    // the implementation of getSize below works but...
    // it seems that it would be more efficient to add a field for size
    // instead of essentially copying a stack twice
    
    //// pop from stack to temp stack
    //// count while doing so
    //// pop from temp back to stack
    //public int getSize(){          
    //    if (this.getTop() == null){
    //        return 0;
    //    } else {
    //        Stack temp = new Stack();
    //        int size = 0;
    //        while (this.getTop() != null){
    //            String data = this.pop();
    //            temp.push(data);
    //            size++;
    //        } // end while
    //        while (temp.getTop() != null){
    //            this.push(temp.pop());
    //        } // end while
    //        return size;
    //    } // end else
    //} // end getSize
    
    public int getSize(){
        return size;
    }
    
    private void setSize(int size){
        this.size = size;
    } // end setSize
    
    // factory method (alternative to a copy constructor)
    public static Stack getInstance(Stack original){
        Stack stack = new Stack();
        stack.setTop(original.getTop());  // need getTop to return a new Node instead of
                                          // a reference to original's top
        stack.setId(original.getId());
        stack.setSize(original.getSize());
        return stack;
    } // end 
    
} // end Stack