import java.util.*;
public class Classroom extends Person implements Displayable{

   int roomNumber;
   Displayable teacher;
   ArrayList<Displayable> students = new ArrayList<Displayable>();

   public Classroom(){
   }

   public Classroom(int room, Displayable teacher, ArrayList<Displayable> students){
      roomNumber = room;
      this.teacher = teacher;
      this.students = students;
   }

   public String display(){
      String classroomDisplay = "Room Number: " + this.roomNumber + "\n";
      classroomDisplay += teacher.display() + "\n";
      for(int i = 0; i<students.size();i++){
         classroomDisplay += students.get(i).display() + "\n";
      }
      return classroomDisplay;
   }
}