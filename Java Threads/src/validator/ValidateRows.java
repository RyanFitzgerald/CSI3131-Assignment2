package validator;

public class ValidateRows implements Runnable {

	private int[][] sudoku; // Store sudoku grid
	private boolean result = true; // Store result (default to true)
	
	public ValidateRows(int[][] sudoku) {
		this.sudoku = sudoku;
	}
	
	public void run() {
		
		// Loop through all rows
		for (int j = 0; j < 9; j++) {
			
			int[] nums = new int[9]; // Create empty array of length 9 for row
			
			// Loop thorugh each column in the row
			for (int i = 0; i < 9; i++) {

				// If number is 0 or greater than 9 or if a number already exists (duplicate)
				// set result to false, otherwise, insert the number
				if (sudoku[i][j] == 0 || sudoku[i][j] > 9 || nums[sudoku[i][j] - 1] != 0) {
					result = false;
				} else {
					nums[sudoku[i][j] - 1] = sudoku[i][j];
				}

			}

		}
		
		// Pass result back to parent
		if (result) {
			Validate.results[9] = 1;
		} else {
			Validate.results[9] = 0;
		}
		
	}
	
}