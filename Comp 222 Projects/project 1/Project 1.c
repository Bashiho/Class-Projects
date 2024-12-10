#include <stdio.h>
#include <stdlib.h>

int *arrCPI;
int *arrCnt;
int x = 0, frequency = 0, temp = 0, arrCheck = 0;
double averageCPI = 0, CPUTime = 0, MIPS = 0;
//method to print beginning statements
void printIntro() {
    printf("Performance assessment:\n");
    printf("-------------------\n");
    printf("1) Enter parameters\n");
    printf("2) Print table of input parameters\n");
    printf("3) Print table of performance\n");
    printf("4) Quit\n");
}

//calculates values to be used later when printing table
void calcVals(int arrCPI[], int arrCnt[], const int freq, const int size){
    //average CPI
    int totCnt = 0;
    for(int i = 0; i<size; i++) {
        averageCPI += (arrCPI[i]*arrCnt[i]);
        totCnt += arrCnt[i];
    }
    averageCPI = averageCPI/totCnt;

    //calculates CPU time
    for(int i = 0; i<size; i++){
        CPUTime += (arrCnt[i] * arrCPI[i]);
    }
    CPUTime = CPUTime/freq;

    //calculates MIPS
    int totInst = 0;
    for(int i = 0; i<size; i++) {
        totInst += arrCnt[i];
    }
    MIPS = totInst/CPUTime;

    //converts CPUTime to ms
    CPUTime = CPUTime * 1000;
}

//prints parameters when 2 is input by user
void printParam(int arrCPI[], int arrCnt[], const int size) {
    if(!arrCPI[0]){
        printf("Error, no parameters were input");
    }
    else{
    printf("-------------------------\n");
    printf("|Class |CPI |Count |\n");
    //loops printing parameters and table formatting
    for(int i = 1; i<=size; i++) {
        printf("-------------------------\n");
        printf("|%d |%d |%d |\n", i, arrCPI[i-1], arrCnt[i-1]);
    }
    printf("-------------------------\n");
    }
    printf("\n");
}

//prints table of calculated values when 3 is input by use
void printTable(const double CPI, const double time, const double MIPS){
    printf("-------------------------\n");
    printf("|Performance   |Value  |\n");
    printf("-------------------------\n");
    printf("|Average CPI   |%.2f  |\n", CPI );
    printf("-------------------------\n");
    printf("|CPU Time      |%.2f  |\n", time);
    printf("-------------------------\n");
    printf("|MIPs          |%.2f  |\n", MIPS);
    printf("-------------------------\n\n");
}

//allocates memory based on the given number of classes
void alloc(const int size) {
    arrCPI = malloc(size*sizeof(int));
    arrCnt = malloc(size*sizeof(int));
}

void assign(int *arrCPI, int *arrCnt, const int size) {
    for(int i = 0; i<size; i++){
        printf("\nEnter CPI of class %d: ", i + 1);
        scanf("%d", &arrCPI[i]);
        printf("\nEnter instruction count of class %d (millions): ", i + 1);
        scanf("%d", &arrCnt[i]);
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
    if(x == 1) {
        //allows for entering of parameters and stores information in arrays or vars
        printf("Enter the number of instruction classes:");
        scanf("%d", &temp);
        //calls alloc method to create arrays w/ size = number of classes given
        alloc(temp);
        printf("\nEnter the frequency of the machine: ");
        scanf("%d", &frequency);
        //fills arrays w/ parameters from inputs
        assign(arrCPI, arrCnt, temp);
        arrCheck = 1;
        calcVals(arrCPI, arrCnt, frequency, temp);
    }
    
    else if(x == 2){
        //if input is 2, checks for parameters
        //if no params prints error, else prints params
        if(arrCheck != 0)
            printParam(arrCPI, arrCnt, temp);
        else
            printf("No values\n");
    }
    else if(x == 3) {
        //if input is 3, checks for parameters
        //if no params prints error, else prints calculations
        if(arrCheck != 0) {
            //calculates values based on parameters
            printTable(averageCPI, CPUTime, MIPS);
        }
        else
            printf("No values\n");
    }
    else if(x == 4) {
        free(arrCPI);
        free(arrCnt);
        return 0;
    }

    else
        //prints invalid input if input is out of range
        printf("Invalid Input\n");
    }
}
