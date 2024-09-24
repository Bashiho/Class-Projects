import java.util.*;
public class Account{
   protected int id;
   protected double balance;
   protected java.util.Date dateOpened;
   public Account(){
      dateOpened = new java.util.Date();
   }
   public Account(int id, double balance){
      this.id = id;
      this.balance = balance;
      dateOpened = new java.util.Date();
   }
   public int getId(){
      return id;
   }
   public double getBalance(){
      return balance;
   }
   public void setId(int id){
      this.id = id;
   }
   public void setBalance(double bal){
      this.balance = bal;
   }
   public java.util.Date getDateOpened(){
      return dateOpened;
   }
   public void withdraw(double amount){
      balance-=amount;
   }
   public void deposit(double amount){
      balance+=amount;
   }
   public String toString(){
      return "Account id: " + id + "\nAccount opened: \n" + dateOpened + "Balance: %.2f$" + String.format("%.2f",balance); //might break everything :)
   }

}