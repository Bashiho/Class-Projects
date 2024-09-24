import java.util.*;

public class MyDoubleLinkedList<E extends Comparable<E>>  {
	
   private Node<E> head, tail;
   private int size;

	/** Create a default list */
   public MyDoubleLinkedList() {
      head=tail=null;
   }

	/** Create a list from an array of objects */
   // Left as Exercise
   public MyDoubleLinkedList(E[] objects) { 
      for(int i = 0; i<objects.length;i++)
         addLast(objects[i]); 
   }

	    /** Return the head element in the list */
   public E getFirst() {
      if (size == 0) {
         return null;
      } else {
         return head.element;
      }
   }

	/** Return the last element in the list */
   public E getLast() {
      if (size == 0) {
         return null;
      } else {
         return tail.element;
      }
   }

	/** Add an element to the beginning of the list */
   public void addFirst(E e) {
      Node<E> newNode = new Node<E>(e); // Create a new node
      if(head == null){
         head = tail = newNode;
      }
      else{
         newNode.next = head; // link the new node with the head
         if(head != null)
            head.previous = newNode;
         head = newNode;
      }
      size++; // Increase list size
   }

	/** Add an element to the end of the list */
   public void addLast(E e) {
      Node<E> newNode = new Node<E>(e); // Create a new for element e
      if (head == null){
         head = tail = newNode; // The new node is the only node in list
         head.previous = null;
         tail.next = null;
      } 
      else {
         tail.next = newNode; // Link the new with the last node
         newNode.previous = tail;
         tail = newNode; // tail now points to the last node
         tail.next = null;
      }
   
      size++; // Increase size
   }
   
   /** Return true if this list contains no elements */
   public  boolean isEmpty() {
      return size() == 0;
   }
   
   /**
	 * Remove the head node and return the object that is contained in the
	 * removed node.
	*/
   public E removeFirst() {
      if (size == 0) {
         return null;
      } else {
         Node<E> temp = head;
         head = head.next;
         size--;
         if (head == null) {
            tail = null;
         }
         return temp.element;
      }
   }

   /**
	 * Remove the last node and return the object that is contained in the
	 * removed node.
	 */
   public E removeLast() {
      if (size == 0) {
         return null;
      } else if (size == 1) {
         Node<E> temp = head;
         head = tail = null;
         size = 0;
         return temp.element;
      } else {
         Node<E> current = head;
      
         for (int i = 0; i < size - 2; i++) {
            current = current.next;
         }
      
         Node<E> temp = tail;
         tail = current;
         tail.next = null;
         size--;
         return temp.element;
      }
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder("[");
   
      Node<E> current = head;
      for (int i = 0; i < size; i++) {
         result.append(current.element);
         current = current.next;
         if (current != null) {
            result.append(", "); // Separate two elements with a comma
         } else {
            result.append("]"); // Insert the closing ] in the string
         }
      }
      return result.toString();
   }

   /** Return number of elements in the list */
   public int size() {
      return size;
   }
   
   
	/** Clear the list */
   public void clear() {
      head = tail = null;
   }



	/**
	 * Add a new element at the specified index in this list The index of the
	 * head element is 0
	 */
   public void add(int index, E e) {
     // "Implementation left as an exercise";
      Node<E> newNode = new Node<E>(e);
      if(size == index)
         addLast(e);
      else if(index == 0)
         addFirst(e);
      else{
         Node<E> temp = head;
         for(int i = 0; i<index;++i)
            temp=temp.next;
         newNode.previous = temp.previous;
         temp.previous.next = newNode;
         temp.previous = newNode;
         newNode.next = temp;
         size++;
      }
   }
	    
	/** Add a new element at the specified index in this list in ascending order */
   public void addInOrder(E e) {
      // "Implementation left as an exercise";
      if(head == null){
         addFirst(e);
      }
      else if(e.compareTo(head.element)<0){
         addFirst(e);
      }
      else if(e.compareTo(tail.element)>0){
         addLast(e);
      }
      else{
         Node<E> curr = head;
         int added = 0;
         for(int i = 0; i < size; i++){
            if(e.compareTo(curr.element)<0 && added == 0){
               add(i, e);
               added = 1;
            }
            else if(curr.next != null && added == 0){
               curr = curr.next;
            }
            else if(curr.next == null && added ==0){
               addLast(e);
            }
         }
      }
   }
	    
	    
	/** Check to see if this list contains element e */
   public boolean contains(E e) {
     	  // "Implementation left as an exercise";
      Node<E> curr = head;
      for(int i = 0;i<size;i++){
         if(curr.element.equals(e))
            return true;
         else
            curr = curr.next;
      }
      return false;
   }

	
	/**
	 * Remove the element at the specified position in this list. Return the
	 * element that was removed from the list.
	 */
   public E remove(int index) {
      // "Implementation left as an exercise";
      Node<E> curr = head;
      for(int i = 0;i<index;i++)
         curr=curr.next;
      curr.previous.next = curr.next;
      if(curr.next != null)
         curr.next.previous = curr.previous;
      size--;
      return curr.element;
   }
	    
	/** Remove the first occurrence of the element e 
	 *  from this list. Return true if the element is removed. 
    */
   public boolean removeElement(E e){
   // "Implementation left as an exercise";
      Node<E> curr = head;
      while(curr!=null){
         if(curr.element.equals(e)){
            if(curr.next == null){
               tail = curr.previous;
            }
            else{
               curr.previous.next = curr.next;
               curr.next.previous = curr.previous;
            }
            size--;
            return true;
         }
         curr=curr.next;
      }
      return false;
   }
	    
	/** Return the length of this list using recursion */
   public int getLength() {
       	// "Implementation left as an exercise";
      return getLength(head);
   }
   public int getLength(Node<E> curr){
      if(curr==tail)
         return 1;
      else 
         return 1+ getLength(curr.next);
   }
	     
    	    
	/** Print the list in reverse order */
   public void printReverse() {
       	// "Implementation left as an exercise";
      Node<E> curr = tail;
      for(int i = 0; i < size; i++){
         System.out.print(curr.element + " ");
         curr = curr.previous;
      }
      System.out.println();
   }
	    
	/** Print this list using recursion */
   public void printList() {
     	  // "Implementation left as an exercise";
      printList(head);
   }
   public void printList(Node<E> curr){
      if(curr != null){
         System.out.print(curr.element + " ");
         printList(curr.next);
      }
      else 
         System.out.println();
   }
	    
      
	  

	/** Return the element from this list at the specified index */
   public E get(int index) {
        // "Implementation left as an exercise"
      Node<E> curr = head;
      if(index > size)
         return null;
      for(int i = 0; i < index; i++){
         curr = curr.next;
      }
      return curr.element;
   }

	/**
	 * Return the index of the head matching element in this list. Return -1 if
	 * no match.
	 */
   public int indexOf(E e) {
        // "Implementation left as an exercise"
      Node<E> curr = head;
      for(int i = 0; i < size; i++){
         if(curr.element == e)
            return i;
         curr = curr.next;
      }
      return -1;
      
   }

	/**
	 * Return the index of the last matching element in this list Return -1 if
	 * no match.
	 */
   public int lastIndexOf(E e) {
        // "Implementation left as an exercise";
      Node<E> curr = tail;
      for(int i = size; i > 0; i--){
         if(curr.element == e)
            return i-1;
         curr = curr.previous;
      }
      return -1;
   }

	/** Replace the element at the specified position 
   *  in this list with the specified element.
   *  throw exception if index out of bound and     
   *  return null */
   public E set(int index, E e) {
      // "Implementation left as an exercise"/
      try{
         Node<E> curr = head;
         for(int i = 0; i < index; i++)
            curr=curr.next;
         curr.element = e;
         return e;
      }
      catch(NullPointerException E){
         System.out.println("Index " + index + " out of bounds");
      }
      return null;
   }
   
   
	/** Split the original list in half. The original     
	 * list will continue to reference the 
	 * front half of the original list and the method 
	 * returns a reference to a new list that stores the 
	 * back half of the original list. If the number of 
	 * elements is odd, the extra element should remain 
	 * with the front half of the list. 
    */
   public MyDoubleLinkedList<E> split(){
          // "Implementation left as an exercise";
      MyDoubleLinkedList<E> temp = new MyDoubleLinkedList();
      if(head == null){
         return temp;
      }
      else{
         for(int i = 0; i<size/2; i++){
            temp.addFirst(this.tail.element);
            this.removeLast();
         }
         return temp;
      }
   }
   
   /** Check to see of two given lists are eqaul */
   public boolean equals(MyDoubleLinkedList list2)
   {
   // "Implementation left as an exercise";
      Node<E> thisCurr = this.head;
      Node<E> l2Curr = list2.head;
      for(int i = 0;i<size;i++){
         if(thisCurr.element.compareTo(l2Curr.element)!=0)
            return false;
         thisCurr=thisCurr.next;
         l2Curr=l2Curr.next;
      }
      return true;
   }


	    
   public static class Node<E extends Comparable<E>> {
      E element;
      Node<E> next;
      Node<E> previous;
   
      public Node(E o) {
         element = o;
      }
   }

   
}
	
