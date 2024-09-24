
import java.util.Arrays;
import java.util.Scanner;


public class ReverseTextOriginal {
	
	 
	    

    // DO NOT MODIFY main!
   public static void main(String[] args) {
      String S = "";
      for (int i=0; i < args.length; i++){
         S = S + args[i] + " ";
         
      }
      
      System.out.print(reverseText(S));
   
   }
   public static String reverseText(String S){
      String reverse = "";
      int firstSpace = 0;
      int secondSpace = 0;
      for(int i = 0; i<S.length();++i){
         while(S.charAt(i)!=' '){ //loop to find next blank space
            i++;
         }
         firstSpace = secondSpace; //set to beginning of each word
         secondSpace = i; //set to end of each word
         for(int k = secondSpace-1;k>=firstSpace;k--){
            if(S.charAt(k) != ' ') //checks to prevent adding blank space to String
               reverse += S.charAt(k); //adds characters to new reverse String
            if(k==firstSpace)
               reverse+= ' '; //adds black space to String after each word
         }
      }
      return reverse;
   }
      //use 2 variables, one for the first and one for the last char of each word
      //set so when variable for lastChar<firstChar it goes to next word
}
