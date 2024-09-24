import java.util.*;
public class PrintReport {
   Scanner in = new Scanner(System.in);
   char answer;
   ArrayList<Displayable> list = new ArrayList<Displayable>();
   public PrintReport()
   {
      System.out.println("First You Need To Create A Classroom");
      do {
         Displayable c=enterClassroom();
      	
         list.add(c);
         System.out.print("Enter another class room (Y or N)? ");
         answer=in.next().toUpperCase().charAt(0); in.nextLine();
      } while( answer != 'N');
   	
      report(list);
   	
   }
   public Displayable enterClassroom()
   {
      ArrayList<Displayable> studentList=new ArrayList<Displayable>();
      int room = 0;
      while(room < 100){
         System.out.print("Enter Room Number: ");  
         room=in.nextInt(); in.nextLine();
      }
      System.out.println("Now you need to enter a teacher for the classroom.");
      Displayable teacher = enterTeacher();
      System.out.println("Now you need to Add Students for the classroom.");
      do {
         Displayable student = enterStudent();
         studentList.add(student);
         System.out.println("Enter another student? (Y/N)");
         answer = in.next().toUpperCase().charAt(0); in.nextLine();
      } while ( answer != 'N');
   
      return new Classroom(room,teacher,studentList);
   }
	
   public Displayable enterTeacher()
   {
      System.out.println("What is the teacher's first name?");
      String firstName = in.next();
      System.out.println("What is the teacher's last name?");
      String lastName = in.next();
      System.out.println("What subject does the teacher teach?");
      String subject = in.next();
      Teacher teacher = new Teacher(subject, firstName, lastName);
      return teacher;
   }
	
   public Displayable enterStudent()
   {
      int studentIdInput = 0;
      while(studentIdInput<=0){
         System.out.println("what is the student's Student Id?");
         studentIdInput = in.nextInt();
      }
      System.out.println("What is the student's first name?");
      String firstInput = in.next();
      System.out.println("What is the student's last name?");
      String lastInput = in.next();
      System.out.println("What is the student's final grade?");
      int grade=in.nextInt();
      while(grade<0 || grade>100){
         System.out.println("Error: Invalid Grade");
         grade = in.nextInt();
      }
      Student student = new Student(firstInput, lastInput, studentIdInput, grade); 
      return student;
   }
	
   void report(ArrayList<Displayable> list)
   {
      for(int i = 0; i<list.size(); i++){
         System.out.print(list.get(i).display());
      }
     
   }

}