/*
 * CSI3131 - Assignment 2
 * Ryan Fitzgerald (7233237)
 * 
 */

package validator;

public class ValidateColumns implements Runnable {

	private int[][] sudoku; // Store sudoku grid
	private boolean result = true; // Store result (default to true)
	
	public ValidateColumns(int[][] sudoku) {
		this.sudoku = sudoku;
	}
	
	public void run() {
		
		// Loop through columns
		for (int i = 0; i < 9; i++) {
			
			// Create empty array of length 9 to store numbers
			int[] nums = new int[9];
			
			// Loop throw all rows in a column
			for (int j = 0; j < 9; j++) {

				// If number is 0 or greater than 9 or if a number already exists (duplicate)
				// set result to false, otherwise, insert the number
				if (sudoku[i][j] == 0 || sudoku[i][j] > 9 || nums[sudoku[i][j] - 1] != 0) {
					result = false;
				} else {
					nums[sudoku[i][j] - 1] = sudoku[i][j];
				}

			}

		}
		
		// Pass result back to parent thread results array
		if (result) {
			Validate.results[10] = 1;
		} else {
			Validate.results[10] = 0;
		}
		
	}
	
}
