abstract class Person{
   private String firstName;
   private String lastName;
   public Person(){
   }
   public Person(String firstName, String lastName){
      this.firstName = firstName;
      this.lastName = lastName;
   }
   public void setFirstName(String firstName){
      this.firstName = firstName;
   }
   public void setLastName(String lastName){
      this.lastName = lastName;
   }
   public String getFirstName(){
      return this.firstName;
   }
   public String getLastName(){
      return this.lastName;
   }
   public String getFullName(){
      return this.firstName + " " + this.lastName;
   }
}