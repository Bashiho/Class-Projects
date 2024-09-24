import java.util.*;
public class MyBank {
   static Scanner in = new Scanner(System.in);
   static ArrayList<Checkings> checking = new ArrayList<>();
   static ArrayList<Savings> saving = new ArrayList<>();
   public static void main(String[] args) {
   
      int id=0;
    
      boolean done = false;
      do {
         System.out.println("\n**********************************\n");
         System.out.println ( "Select an option:");
         System.out.println ( "1) Open a checking account" );
         System.out.println ( "2) Checking deposit" );
         System.out.println ( "3) Checking withdraw" );
         System.out.println ( "4) Print checking account info." );
         System.out.println ( "5) Open a saving account" );
         System.out.println ( "6) Saving deposit." );
         System.out.println ( "7) Saving withdraw." );
         System.out.println ( "8) Print monthly interest" );
         System.out.println ( "9) Print saving account info.");
         System.out.println ( "10) Exit");
         int option;
         option = in.nextInt();in.nextLine();
         System.out.println ( "\n**********************************\n" );
          
         switch (option) {
            case 1:
               openChecking();
               break;
           
            case 2:
               System.out.print ( "Enter account number: ");
               id= in.nextInt();
               
               if(id>=1000&&id<checking.size()+1000) {
                  System.out.print("Enter deposit amount: ");
                  double amount = in.nextDouble();
                  checking.get(id-1000).deposit(amount);
               }
               else
                  System.out.println("Account "+id+" does not exist");
               
               
               break;
            case 3:
               System.out.println ( "Enter account number: ");
               id = in.nextInt();
               if(id>=1000&&id<checking.size()+1000)
               {
                  System.out.print("Enter withdraw amount: ");
                  double amount = in.nextDouble();
                  checking.get(id-1000).withdraw(amount);
               }
               else
                  System.out.println("Account "+id+" does not exist");
               
               break;
            case 4:
               
               System.out.println ( "Enter account number: ");
               id = in.nextInt(); 
               if(id>=1000&&id<checking.size()+1000)
                  printAccount(checking.get(id-1000));
               else
                  System.out.println("Account "+id+" does not exist");
               
            
               break;
            case 5:
               openSaving();
               break;
            case 6:
               System.out.print ( "Enter account number: ");
               id= in.nextInt();
               
               if(id>=1000&&id<saving.size()+1000) {
                  System.out.print("Enter deposit amount: ");
                  double amount = in.nextDouble();
                  saving.get(id-1000).deposit(amount);
               }
               else
                  System.out.println("Account "+id+" does not exist");
               break;
            case 7:
               System.out.println ( "Enter account number: ");
               id = in.nextInt(); 
               if(id>=1000&&id<saving.size()+1000)
               {
                  System.out.print("Enter withdraw amount: ");
                  double amount = in.nextDouble();
                  saving.get(id-1000).withdraw(amount);
               }
               else
                  System.out.println("Account "+id+" does not exist");
                  
               
               break;
            case 8:
               System.out.println ( "Enter account number: ");
               id = in.nextInt(); 
               if(id>=1000&&id<saving.size()+1000) {
                  System.out.print("Account " + (id) + " earned ");
                  System.out.println(String.format("$%1.02f", saving.get(id-1000).getMonthlyInterestRate())+ " interest.");
               }
               else
                  System.out.println("Account "+id+" does not exist");
               break;
            case 9:
               
               System.out.println ( "Enter account number: ");
               id = in.nextInt(); 
               if(id>=1000&&id<saving.size()+1000)
                  printAccount(saving.get(id-1000));
               else
                  System.out.println("Account "+id+" does not exist");
               break;
               
            case 10:
               done = true;
               break;
            default:
               System.out.println ( "Illegal option, try again");
         }
      } while (!done);
   
   }
 
   public static void openChecking() {
      int id = checking.size();
      System.out.print("Enter customer name: ");
      String name = in.nextLine();
      System.out.print("Enter initial balance for account # " + (id+1000) +": ");
      double amount = in.nextDouble();
      checking.add(new Checkings(id+1000,name,amount));
   }
 
 
   public static void openSaving() {
      int id = saving.size();
      System.out.print("Enter customer name: ");
      String name = in.nextLine();
      System.out.print("Enter initial balance for account # " + (id+1000) +": ");
      double amount = in.nextDouble();
      System.out.print("Enter annual interest rate for account # " + (id+1000) + ": ");
      double rate = in.nextDouble();
      saving.add(new Savings(id+1000,name,amount,rate));
   }
 
   public static void printAccount(Account account) {
      System.out.println(account.toString());
   }
 
 
}
