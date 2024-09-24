import java.util.Arrays;


public class TestMyDoubleLinkedList {
   public static void main(String[] args) {
      String[] name1 = {"Tom", "George", "Peter", "Jean", "Jane"};
      String[] name2 = {"George", "Michael", "Michelle", "Daniel"};
      String[] name3 = {"Tom", "Peter","Jane","Adam","Peter","Mary","David"};
       
      MyDoubleLinkedList<String> list1 = new MyDoubleLinkedList<String>(); 
      MyDoubleLinkedList<String> temp = new MyDoubleLinkedList<>();
      for(int i=0;i<name1.length;++i)
         temp.addLast(name1[i]);
       
      
       
      System.out.println("\nTesting addInOrder()");
      for(int i=0;i<name1.length;++i)
         list1.addInOrder(name1[i]);
      System.out.println("list1: "+list1);
      System.out.println("\nTesting overloaded constructor");
      System.out.println("name1; "+Arrays.asList(name1));
      MyDoubleLinkedList<String> list2 = new MyDoubleLinkedList<String>(name1);  
      System.out.println("list2: "+list2);
      System.out.println("\nTesting equlas()");
      System.out.println("list2 equals temp: "+(list2.equals(temp)?"PASSED":"FAILED"));
      System.out.println("list1 !equals temp: "+(!list1.equals(temp)?"PASSED":"FAILED"));
      MyDoubleLinkedList<String> list3 = new MyDoubleLinkedList<String>(name3); 
          
      System.out.println("\nTesting list1.set(1,\"John\")");
      System.out.println("list1: "+list1);
      list1.set(1, "John");
      System.out.println("list1: "+list1);
      System.out.println("\nTesting list1.set(10,\"John\")");
      System.out.println("list1: "+list1);
      System.out.println("Indedx out of bound: "+(list1.set(10, "John")==null?"PASSESD":"FAILED"));
       
      System.out.println("\nTesting printList() & printReverse()");
      list2.printList();
      list2.printReverse();
       
      System.out.println("\nTesting indexOf(\"Peter\")");
      System.out.println("First index of Peter is 1: "+(list3.indexOf("Peter")==1?"PASSED":"FAILED"));
      System.out.println("\nTesting lastIndexOf(\"Peter\")");
      System.out.println("Last index of Peter is 4 "+(list3.lastIndexOf("Peter")==4?"PASSED":"FAILED"));
      System.out.println("\nTesting contains()");
      System.out.println("list1 contains Bahram is "+list1.contains("Bahram"));
      System.out.println("list3 contains Peter is "+list3.contains("Peter"));
      System.out.println("\nTesting getLength()");
      System.out.println("Length of list1 is 5 "+(list1.getLength()==5?"PASSED":"FAILED"));
      System.out.println("Length of list3 is 7 "+(list3.getLength()==7?"PASSED":"FAILED"));
       
      System.out.println("\nTesting removeElement(\"Peter\")");
      System.out.println("list2: "+list2);
      list2.removeElement("Peter");
      System.out.println("list2: "+list2);
       
      System.out.println("\nTesting remove(1)");
      System.out.println("list3: "+list3);
      list3.remove(1);
      System.out.println("list3: "+list3);
      System.out.println("\nTesting get(2) and get(10)");
      System.out.println(list1);
      System.out.println(list1.get(2));
      System.out.println(list1.get(10));
      
      
      System.out.println("\nTesting split()");
      System.out.println(list1);
      MyDoubleLinkedList<String> list4 = list1.split();
      System.out.println(list1);
      System.out.println(list4);
       
   }

}
