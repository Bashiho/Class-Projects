import java.util.*;
public class Last10{
   public static void main(String[] args){
      Scanner in = new Scanner(System.in);
      int[] list = new int[10];
      int input = 0;
      int i = 0;
      int count = 0;
      while(input!=-1){ //exits when -1 is input
         input=in.nextInt();
         if(input!=-1) //prevents -1 from being input into array
            list[i] = input; //loads inputs into the array
         ++i;
         count++; //counts the amount of inputs to avoid printing 0s if <10 inputs
         if(i==10) //resets i to 0 when it reaches 10
            i-=10;
      }
      i-=1;
      if(count>10){
         for(int j = 0; j < 10; j++){
            System.out.print(list[i] + " "); //prints from array
            i++;
            if(i>9){
               i=0;
            }
         }
      }
      else{ //if <10 inputs, prints from beginning of list to last input
         for(int k = 0; k<count-1;k++){
            System.out.print(list[k] + " ");
         }
      }
   }
}