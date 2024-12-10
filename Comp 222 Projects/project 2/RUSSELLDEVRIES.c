#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int x = 0, clockCycles = 0, numInstructions = 0;
//method to print beginning statements
void printIntro() {
    printf("Performance assessment:\n");
    printf("-------------------\n");
    printf("1) Enter instructions\n");
    printf("2) Print chart of pipelined instructions\n");
    printf("3) Print total clock cycles\n");
    printf("4) Quit\n");
    printf("Enter Selection: \n");
}

//method for inputting instructions
void inputInstructions(){
    //create loop to add instructions to arrInstructions until told to stop
    //Increment numInstructions in each iteration of loop where instructin is added
    printf("Enter the number of instructions: ");
    scanf("%d", &numInstructions);
    printf("Input instructions \n");
    int temp = 1;
    char tempString[10];
    for(int i = 0; i < numInstructions; i++){
        //scans input and temporarily stores
        //Strings not used elsewhere so no permanent storage
        printf("%d)" , temp);
        scanf("%s", tempString);
        temp++;
    }
}

//method for printing chart of pipelined instructions
void printPipeline(){
    //prints instructions formatted to resemble pipeline instructions
    if(numInstructions == 0 || numInstructions == 0){
        printf("No instructions \n");
        return;
    }
    for(int j = 0; j<numInstructions; j++){
        printf("%d: ", j+1);
        //uses spaces to space out the starting points of each instruction
        for(int i = 0; i < j; i++){
            printf("     ");
        }
        //prints 5 parts of pipeline for each instruction
        printf(" |IF  |ID  |EX  |MEM |WB\n");
    }
}

int main() {
    //loops until return statement
    while(1){
    //prints introductory message asking for input
    printIntro();
    //scans input
    scanf("%d", &x);
    //calls appropriate method based on given input, errors if invalid input
    //if input is 1, takes instructions and stores them
    if(x == 1) {
        //allows for instructions to be input
        inputInstructions();
    }
    //if input is 2, prints pipelined instructions chart
    else if(x == 2){
        printPipeline();
    }
    //if input is 3, prints total clock cycles
    else if(x == 3) {
        //if no instructions, prints no instructions, else prints clock cycles
        if(numInstructions == 0)
            printf("No Instructions \n");
        else{
            //calculates total number of clock cycles and stores it in clockCycles
            clockCycles = 5 + (numInstructions-1);
            //prints clockCycles
            printf("Total Clock Cycles: %d \n", clockCycles);
        }  
    }
    //if input is 4, quits
    else if(x == 4) {
        //ends program
        return 0;
    }

    else
        //prints invalid input if input is out of range
        printf("Invalid Input\n");
    }
}
