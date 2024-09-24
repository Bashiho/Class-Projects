import java.util.Scanner; 
public class LabProblem { 
   public static void main(String[] args) { 
      Scanner input = new Scanner(System.in); 
     // Your code goeas here
      double s1 = input.nextDouble();
      double s2 = input.nextDouble();
      double s3 = input.nextDouble();
      Triangle t1 = new Triangle (s1, s2, s3);
      System.out.printf("%.2f\n", t1.getArea());
      System.out.printf("%.2f\n", t1.getPerimeter());
   } 
}
