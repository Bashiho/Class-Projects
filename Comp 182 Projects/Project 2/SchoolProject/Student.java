public class Student extends Person implements Displayable{
   private int studentId;
   private int finalGrade;
   public Student(){
   }
   public Student(String firstName, String lastName, int studentId, int finalGrade){
      setFirstName(firstName);
      setLastName(lastName);
      this.studentId = studentId;
      this.finalGrade = finalGrade;
   }
   public int getStudentId(){
      return studentId;
   }
   public void setStudentId(int id){
      this.studentId = id;
   }
   public int getFinalGrade(){
      return finalGrade;
   }
   public void setFinalGrade(int finalGrade){
      this.finalGrade = finalGrade;
   }
   public String display(){
      return ("Student ID: " + studentId + "    " + getFullName() + " Final Grade: " + finalGrade);
   }
}