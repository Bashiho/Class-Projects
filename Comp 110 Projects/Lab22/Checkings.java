public class Checkings extends Account{
   protected double overdraftlimit = 500;

   public Checkings(){
   }
   public Checkings(int id, String name, double balance){
      this.balance = balance;
      this.id = id;
   }
   @Override
   public void withdraw(double amount){
   if(amount>overdraftlimit){
   this.balance = balance;
   System.out.println("Error: Withdraw cancelled. Account over draft");
   }
   else{
      this.balance-=amount;
      }
   }
   @Override
   public String toString(){
      return "Checkings\n"+ "Account id: " + id + "\nAccount opened: " + dateOpened + "\nBalance: $" + String.format("%.2f",balance);
   }

}