//Creates MelodyQueue to hold note objects.
import java.util.*;

import org.w3c.dom.Node;
public class MelodyQueue 
{
	//Creates two nodes, one for front and one for back of queue.
   LinkedList<Note> queue = new LinkedList<>();
	//Initializes front and back nodes.
   public MelodyQueue()
   {

   }
	//Adds item to the back of the queue.
   public void enqueue(Note item) 
   {
     // Left as exercise
    queue.addLast(item); //calls LinkedList method to add item to end of queue
   }

	//Removes and returns item from the front of queue.
   public Note dequeue()
   {
   	 // Left as exercise
       if(queue.isEmpty() == true) //if queue is empty, returns null
       return null;
       else
       return queue.removeFirst(); //removes first note and returns element
   }


	//Checks if queue is empty.
   public boolean isEmpty() {
   	 // Left as exercise
       return queue.isEmpty(); //returns true if queue is empty, false otherwise
   }

	//Returns time of queue.
   public double duration() {
   	 // Left as exercise
       if(queue == null) //if queue is empty, duration = 0
       return 0;
       else{
       double durTime = 0; //summation of duration
       for(int i = 0; i < queue.size(); i++){ //moves through nodes in queue
       if(queue.get(i).isRepeat() == true){ //checks if note repeats
       do{
         i++;
       } while(queue.get(i).isRepeat() != true); //moves through queue to end of repSec
       i++;
       }
       else //if not repeated
       durTime += queue.get(i).getDuration(); //if not repeated, add duration
       }
       durTime += timeRepeat(); //adds duration of repSecs
       return durTime; //returns duration of song
      }
   }
	//This method is called to find the duration of repSecs, only runs through once.
   public double timeRepeat()
   {  //zombie == 9.3
      //twinkle == 24.5
      //birthday == 13
      // Left as exercise
      double repTime = 0;
      for(int i = 0; i<queue.size(); i++){
         if(queue.get(i).isRepeat() == true){ //finds start of repSec
            do{
               repTime+= queue.get(i).getDuration(); //adds duration of repNotes to repTime
               i++; //moves to next note
            } while(queue.get(i).isRepeat() != true); //moves through to end of repSec
            i++; //moves to end note of repSec
            repTime+= queue.get(i).getDuration(); //adds end note of repSec
         }
      }

      return repTime*2; //returns duration of repSecs
   }

	
	
	//Returns String of queue.
   public String makeString() {
       // Left as exercise
       String queueString = "";
       for(int i = 0; i<queue.size(); i++){ //goes through list
         queueString += queue.get(i).toString() + "\n"; //appends temp to string + newline
       }
       return queueString;
   }

   public void tempoChange(double tempo) {
       // Left as exercise
       for(int i = 0; i<queue.size(); i++){ //goes through queue
         queue.get(i).setDuration(queue.get(i).getDuration()*tempo); //multiplies duration of each note by tempo
       }
   }
	
	//Plays the song.
   public void play()
   {
      // Left as exercise
      LinkedList<Note> q2 = queue; //saves loaded song to q2
      while(queue.peek() != null){ //checks if queue is empty
      if(queue.peek().isRepeat() == true) //if repeated, calls playRepeat
      playRepeat();
      else
      queue.pop().play(); //plays note
      }
      queue = q2; //reloads song into queue
   }

	//Plays repeated sections.
   public void playRepeat()
   {
       // Left as exercise
       LinkedList<Note> queue2 = new LinkedList<>(); //creates queue to hold repNotes
       queue2.addLast(queue.pop()); //adds repNotes to q2
       while(queue.peek().isRepeat() != true) //moves through loop to end of repSec
       queue2.addLast(queue.pop());
       queue2.addLast(queue.pop()); //adds end of repSec to q2
       for(int j = 0; j<2; j++){ //outer loop to play q2 twice
       for(int i = 0; i<queue2.size(); i++) //inner loop to play through q2
       queue2.get(i).play(); //plays notes from q2
       }
      }

	//Adds one melody onto the end of another.
   public void appendMelody(MelodyQueue other)
   {
      // Left as exercise
      while(other.isEmpty() == false){
         queue.addLast(other.dequeue()); //dequeues from other and adds to queue
      }
   }

	//Reverses the order of the notes of a song.
   public MelodyQueue reverseMelody()
   {
       // Left as exercise
       MelodyQueue revqueue = new MelodyQueue(); //new temp revqueue
       Stack<Note> stack = new Stack<>(); //temp stack for reversing order
       for(int i =0; i<queue.size(); i++){ //goes through loop
       stack.add(queue.get(i)); //adds queue to stack
       }
       while(stack.isEmpty() != true) 
       revqueue.enqueue(stack.pop()); //takes notes from stack and adds to revqueue
       return revqueue; //returns revqueue
   }


}