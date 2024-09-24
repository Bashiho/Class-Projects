import java.util.*;
public class LabProgramMoney
{
   public static void main(String[] args)
   {
      Random rnd = new Random(10);
      Money[] list = new Money[15];
   // Generate 15 random Money objects
      for(int i=0;i<list.length;++i)
      {
         list[i] = new Money(1+rnd.nextInt(30),rnd.nextInt(100));
      }
      
      // Tetsing printList
      Money.printList(list,10);
      // Testing subttract method
      System.out.println(list[0].subtract(list[1]));
      System.out.println(list[2].subtract(list[1]));
      // Testing max
      System.out.println(Money.max(list));
      // Testing sum     
      Money sum = new Money();
      for(int i=0;i<list.length;++i) {
         sum = sum.add(list[i]);
      }
      System.out.println(sum);
      // Testing sort sort and print
      Money.selectionSort(list);
      Money.printList(list,5);      
       
   }
  
  
}