public class Length { 
   private int feet;
   private int inches;
   public Length(){
      this.feet = 1;
      this.inches = 1;
   }
   public Length(int newFeet, int newInches){
      this.feet = newFeet;
      this.inches = newInches;
   }
   public int getFeet(){
      return feet;
   }
   public void setFeet(int newFeet){
      this.feet = newFeet;
   }
   public int getInches(){
      return inches;
   }
   public void setInches(int newInches){
      this.inches = newInches;
   }
   public Length add(Length other){
      this.inches = inches + feet*12;
      other.inches = other.inches + other.feet*12;
      this.inches += other.inches; 
      while(this.inches >=12){
         ++this.feet;
         this.inches = this.inches - 12;
      }
      return new Length(this.feet, this.inches);
   }
   public Length subtract(Length o){
      this.inches = inches + feet*12;
      o.inches = o.inches + o.feet*12;
      this.inches = this.inches - o.inches; 
      while(this.inches >=12){
         ++this.feet;
         this.inches = this.inches - 12;
      }
      return new Length(this.feet, this.inches);
   }
   public boolean equals(Length other){
      return (this.feet * 12)+this.inches == other.feet*12 + other.inches;
   }
   public int compareTo(Length other){
      return (this.feet*12 - other.feet*12)+(this.inches - other.inches);
   }
   public String toString(){
      return "[" + feet + "' " + inches + "\"]";
   }
   public int binarySearch(int arr[], int l, int r, int x)
   {
      while (l <= r) {
         int m = l + (r - l) / 2;
      
        // Check if x is present at mid
         if (arr[m] == x)
            return m;
      
        // If x greater, ignore left half
         if (arr[m] < x)
            l = m + 1;
         
         // If x is smaller, ignore right half
         else
            r = m - 1;
      }
   
    // If we reach here, then element was not present
      return -1;
   }
}