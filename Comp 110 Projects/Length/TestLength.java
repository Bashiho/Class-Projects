import java.util.Random;

public class TestLength {
   public static void main(String[] args) {
      Random rand = new Random(10);
      Length length[] = new Length[10];
         
      // fill length array and print 
      for (int i = 0; i < length.length; i++) {
        
         length[i] = new Length(rand.nextInt(11) + 10, rand.nextInt(12));
      
         
         System.out.print(length[i].toString() + "  ");
      }
      
      System.out.println();
      System.out.println();
      
            
      // use sort method and print
      System.out.println("Sorted List");
      selectionSort(length);
      
      for (int i = 0; i < length.length; i++) {
         System.out.print(length[i].toString() + "  ");
      }
      System.out.println();
      
      // use BinarySearch
      System.out.println("Testing binary search: "+binarySearch(length, new Length(2,5)));
      System.out.println("Testing binary search: "+binarySearch(length, new Length(13,1)));
       
      System.out.println();
   }
   
    
   public static void selectionSort(Length[] length){  
        // Your code goes here
      for (int i = 0; i < length.length - 1; i++) {
         for (int j = i + 1; j < length.length; j++) {
            if (length[i].compareTo(length[j]) > 0 ){
               Length tempL = length[i];
               length[i] = length[j];
               length[j] = tempL;
            }
         }
      }
   }
    
   public static int  binarySearch(Length[] list, Length l) {
     // Your code goes here
      for(int i = 0; i<list.length; ++i){
         if(list[i].compareTo(l)==0){
            return i;
         }
         else if (i == list.length){
            return -1;}
      }
      return -1;
   }
}