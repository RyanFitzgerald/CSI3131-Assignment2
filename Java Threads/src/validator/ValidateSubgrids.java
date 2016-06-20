/*
 * CSI3131 - Assignment 2
 * Ryan Fitzgerald (7233237)
 * 
 */

package validator;

public class ValidateSubgrids implements Runnable {
	
	private int[][] sudoku; // Store sudoku grid
	private int subgrid; // Current subgrid
	private boolean result = true; // Store result (default to true)
	
	public ValidateSubgrids(int[][] sudoku, int subgrid) {
		this.sudoku = sudoku;
		this.subgrid = subgrid;
	}
	
	public void run() {
		
		// Create empty array of length 9 to store numbers
		int[] nums = new int[9]; 
		
		// Loop through subgrid provided to thread
		for (int i = subgrid/3 * 3; i < (subgrid/3 * 3) + 3; i++) {
			
			for (int j = subgrid % 3 * 3; j < (subgrid % 3 * 3) + 3; j++) {
				
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
			Validate.results[subgrid] = 1;
		} else {
			Validate.results[subgrid] = 0;
		}
		
		
	}

}
