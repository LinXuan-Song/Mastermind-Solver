package codes;

import java.util.Scanner;

public class Solver {

	public static final int ROW = 4; // space that needs to be filled
	public static final int POSSIBLITY = 1296; // (6^4) possible solutions    1296

	public static int[][] solution = new int[ROW][POSSIBLITY];
	public static boolean[] possibleSolution = new boolean[POSSIBLITY];
	public static int [] enter = new int [4];
	
	public static void populate () {  // populates the arrays
		
		int y = 0;    // can not use a for loop for y axis
		
		for (int number1 = 1; number1 <= 6; number1++)                 // populates solution array
			for (int number2 = 1; number2 <= 6; number2++)
				for (int number3 = 1; number3 <= 6; number3++)
					for (int number4 = 1; number4 <= 6; number4++) {   // populates the array with all possible solution
							solution [0] [y] = number1;
							solution [1] [y] = number2;
							solution [2] [y] = number3;
							solution [3] [y] = number4;
							y++;      // makes y bigger every time so the solution array gets populated
						}
		
		for (int x = 0; x < POSSIBLITY; x++)   // populates the possibleSolution array with true
			possibleSolution [x] = true;       // the true will become false if it's not a valid solution in elimination
	}

	public static void enter (int input) { // transpher the int into the array(not in use / optional)
		
		for (int x = ROW-1; x >= 0; x--) {
			enter [x] = input%10;
			input = input / 10;
		}
	}
	
	public static void display () {  // display the array
		
		for (int y = 0; y < POSSIBLITY; y++)
			if (possibleSolution [y] == true) {
				for (int x = 0; x < ROW; x++)
					System.out.print(solution[x][y]);
				System.out.println();
			}
	}

	public static void eliminate (int correct, int wrongSpot) {

		if (correct	== 0 && wrongSpot == 0)
			eliminate0();

		else{
			eliminate1(wrongSpot);
			eliminate2(correct);
		}

	} //calls the other eliminate methods

	public static void eliminate0 () {  // eliminates when there are no correct or no wrong spots

		for (int y = 0; y < POSSIBLITY; y++)
			for (int x = 0; x < 4; x++)
				for (int counter = 0; counter < 4; counter++)
					if (enter[counter] == solution[x][y])
						possibleSolution[y] = false;

	} // end eliminates when correct = 0 and wrongSpot = 0

	public static void eliminate1 (int wrongSpot) { //eliminate all non-possible solutions from the array from wrong spots

		int enterCopy [] = new int [4];
		int solutionCopy [] = new int [4];

		for (int y = 0; y < POSSIBLITY; y++) {

			int appear = 0;

			for (int x = 0; x < 4; x++) {  // populates the copied arrays
				enterCopy [x] = enter [x];
				solutionCopy [x] = solution [x][y];
			}

			for (int x = 0; x < 4; x++)                           // makes the matching spots and colors undetectable since they are suppose to be in the wrong spots
				for (int counter = 0; counter < 4; counter++)
					if ((x == counter )&&( enterCopy [x] == solutionCopy [counter])) {
						solutionCopy [counter] = -1;
						enterCopy [x] = -2;
					}
			for (int x = 0; x < 4; x++)                            // finds the correct colors in wrong spots
				for (int counter = 0; counter < 4; counter++)
					if (enterCopy [x] == solutionCopy [counter])
						appear++;

			if (appear != wrongSpot)
				possibleSolution [y] = false;      // eliminates if the color appearance is not equal to wrongSpots
		}

	} // end eliminate white method

	public static void eliminate2 (int correct) { // eliminates all non-possible solutions from the array from correct

		int appear = 0;

		for (int y = 0; y < POSSIBLITY; y++) {

			appear = 0;

			for (int x = 0; x < ROW; x++) {

				if (enter [x] == solution [x][y])   // appear++ when the spots match up
					appear++;
			}

			if (appear != 0 && correct == 0)         // eliminate when there are no correct spots
				possibleSolution [y] = false;

			else if (appear != correct && correct != 0)  // eliminates when the spots don't match up to correct
				possibleSolution [y] = false;
		}

	}  // end eliminate Black method

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
	
		populate();

		while(true) {

			display();
			System.out.println("What did you enter from the above?");
			enter(input.nextInt());
			
			System.out.print("Correct     :    ");
			int correct = input.nextInt();
			
			System.out.print("Wrong stop  :    ");
			int wrongSpot = input.nextInt();

			eliminate(correct,wrongSpot);

		}

	}

}
