public class Savings extends Account{
   protected double annualInterestRate;
   public Savings(){
   }
   public Savings(int id, String name, double balance, double rate){
      this.id = id;
      this.balance = balance;
      this.annualInterestRate = rate;
   }
   public double getAnnualInterestRate(){
      return annualInterestRate;
   }
   public void setAnnualInterestRate(double rate){
      this.annualInterestRate = rate;
   }
   public double getMonthlyInterestRate(){
      return (super.getBalance()*annualInterestRate)/1200;
   }
   @Override
   public void withdraw(double amount){
      if(balance-amount<0){
         System.out.println("Error: Account will be overdrawn. Withdraw cancelled.");
      }
      else{
         balance-=amount;
      }
   }
   public String toString(){
      return "Savings \n" + "Account id: " + id + "\nAccount opened: " + dateOpened + "\nBalance: " + String.format("%.2f",balance) + "\nInterest Rate: " + annualInterestRate;
   }
}