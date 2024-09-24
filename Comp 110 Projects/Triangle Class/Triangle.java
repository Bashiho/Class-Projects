public class Triangle  {
   // Your code goes here
   private double Side1;
   private double Side2;
   private double Side3;
   public boolean isValid(double s1, double s2, double s3){
      if((s1+s2>s3)&&(s2+s3>s1)&&(s3+s1>s2))
         return true;
      else 
         return false;
   }
   public Triangle() {
      this.Side1 = 1.0;
      this.Side2 = 1.0;
      this.Side3 = 1.0;
   }
   public Triangle(double s1, double s2, double s3){
      if((s1 + s2 > s3 )&& (s1 + s3 > s2) && (s2 + s3 > s1)){
         this.Side1 = s1;
         this.Side2 = s2;
         this.Side3 = s3;}
      else{
         this.Side1 = 0;
         this.Side2 = 0;
         this.Side3 = 0;
      }
   }
   public void setSide1(double newS1){
      
      if(isValid(newS1, Side2, Side3)){
         this.Side1 = newS1;}
   }
   public void setSide2(double newS2){
      
      if(isValid(Side1, newS2, Side3)){
         this.Side2 = newS2;}
   }
   public void setSide3(double newS3){
      
      if(isValid(Side1, Side2, newS3)){
         this.Side3 = newS3;}
   }
   public double getSide1(){
      return Side1;
   }
   public double getSide2(){
      return Side2;
   }
   public double getSide3(){
      return Side3;
   }
   public double getArea(){
      double a = (Side1 + Side2 + Side3)/2;
      return Math.sqrt(a*(a-Side1)*(a-Side2)*(a-Side3));
   }
   public double getPerimeter(){ 
      return Side1+Side2+Side3;
   }
}
