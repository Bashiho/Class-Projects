public class Teacher extends Person implements Displayable{
   private String subject;
   public Teacher(){
   }
   public Teacher(String subject, String firstName, String lastName){
      this.subject = subject;
      setFirstName(firstName);
      setLastName(lastName);
   }
   public void setSubject(String subject){
      this.subject = subject;
   }
   public String getSubject(){
      return this.subject;
   }

   public String display(){
      return (getFullName() + " teaches " + subject);
   }
}