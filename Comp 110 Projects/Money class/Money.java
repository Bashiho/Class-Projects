public class Money
{
   private int dollar;
   private int cent;
   final static int MAX_CENT_VALUE = 99;
   final static int MIN_CENT_VALUE = 0;
   // Your code goes here
   public Money(){
   }

   public Money(int dollar, int cent){
      this.dollar = dollar;
      this.cent = cent;
      while(cent > MAX_CENT_VALUE){
         this.dollar++;
         this.cent -= 100;
      }
      while(cent <MIN_CENT_VALUE){
         this.dollar--;
         this.cent+= 100;
      }
      if(this.dollar<0 && this.cent<100){
         this.dollar = -1;
         this.cent = -1;
      }
   }
   
   public int getDollar(){
      return dollar;
   }

   public void setDollar(int dollar){
      this.dollar = dollar;
   }

   public int getCent(){
      return cent;
   }

   public void setCent(int cent){
      if(cent>MAX_CENT_VALUE ||cent<MIN_CENT_VALUE){
         this.cent = this.cent;
      }
      else 
         this.cent = cent;
   }

   public Money add(Money otherMoney){
      Money sum = new Money(0,0);
      sum.dollar = this.dollar + otherMoney.dollar;
      sum.cent = this.cent + otherMoney.cent;
      while(sum.cent > 100){
         sum.cent -= 100;
         sum.dollar += 1;
      }
      return sum;
   }
   public Money subtract(Money otherMoney){
      Money difference = new Money(0,0);
      if(this.cent < otherMoney.cent){
         this.dollar -= 1;
         this.cent += 100;
      }
      difference.dollar = this.dollar - otherMoney.dollar;
      difference.cent = this.cent - otherMoney.cent;
      this.dollar += 1;
      this.cent -= 100;
      if(difference.dollar < 0){
         return null; // might breaky
      }
      else 
         return difference;
   }
   public boolean equals(Money otherMoney){
      if(otherMoney.dollar == this.dollar && otherMoney.cent == this.cent)
         return true;
      else 
         return false;
   }
   public int compareTo(Money otherMoney){
      Money newMoney = new Money(this.dollar - otherMoney.dollar, this.cent - otherMoney.cent);
      if(newMoney.dollar >= 0 && newMoney.cent >=0){
         if(newMoney.dollar>0 || newMoney.cent >0)
            return 1;
         else 
            return 0;
      }
      else 
         return -1;
   }
   public String toString(){
      if(this.cent > 9){
         return "$" + this.dollar + "." + this.cent;}
      else{
         return "$" + this.dollar + ".0" + this.cent;}
   }
   
   public static void selectionSort(Money list[]){
      Money temp = new Money();
      for(int i = 0; i<list.length; ++i){
         for(int j = i+1; j<list.length;++j){
            if((list[j].dollar*100+list[j].cent)<(list[i].dollar*100 + list[i].cent)){
               temp = list[j];
               list [j] = list[i];
               list[i] = temp;
            }
         }
      }
   }
   public static Money max(Money list[]){
      Money max = new Money(0,0);
      for(int i=0; i<list.length; ++i){
         if ((list[i].dollar*100 + list[i].cent) > (max.dollar*100 + max.cent)){
            max = list[i];}
      }
      return max;
   }
   public static void printList(Money list[], int n){
      for(int i = 0; i<list.length; ++i){
         if((i+1)%n==0 && i != 0){
            System.out.println(list[i]);}
         else System.out.print(list[i] + " ");
      }
      System.out.println();
   }
      
}