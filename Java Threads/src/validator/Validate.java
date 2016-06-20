/*
 * CSI3131 - Assignment 2
 * Ryan Fitzgerald (7233237)
 * 
 */

package validator;

public class Validate {
	
	static int[][] sudoku; // Store sudoku solution
	static int[] results; // Store results of each child thread
	
	public static void main(String[] args) {
		
		// Store whether sudoku is valid or not (default: true)
		boolean valid = true;
		
		// Default sudoku solution provided (can be changed for testing)
		// This solution is valid
		sudoku = new int[][]{
			{6, 2, 4, 5, 3, 9, 1, 8, 7},
			{5, 1, 9, 7, 2, 8, 6, 3, 4},
			{8, 3, 7, 6, 1, 4, 2, 9, 5},
			{1, 4, 3, 8, 6, 5, 7, 2, 9},
			{9, 5, 8, 2, 4, 7, 3, 6, 1},
			{7, 6, 2, 3, 9, 1, 4, 5, 8},
			{3, 7, 1, 9, 5, 6, 8, 4, 2},
			{4, 9, 6, 1, 8, 2, 5, 7, 3},
			{2, 8, 5, 4, 7, 3, 9, 1, 6}
		};
		
		// Create the results array (1 spot for each thread)
		results = new int[11];
		
		// Create thread for rows
		Thread rows = new Thread(new ValidateRows(sudoku));
		
		// Create thread for columns
		Thread columns = new Thread(new ValidateColumns(sudoku));
		
		// Create threads for subgrids
		Thread subgrids[] = new Thread[9];
		for (int i = 0; i < 9; i++) {
			subgrids[i] = new Thread(new ValidateSubgrids(sudoku, i));
		}
		
		// Start the threads
		rows.start();
		columns.start();
		for (int i = 0; i < 9; i++) {
			subgrids[i].start();
		}
		
		// Wait for all child threads to complete before continuing
		try {
			
			// Join row thread
			rows.join();
			
			// Join column thread
			columns.join();
			
			// Loop through subgrid threads to join
			for (int i = 0; i < 9; i++) {
				subgrids[i].join();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Loop Through the results array
		for (int i = 0; i < results.length; i++) {
			// If a false is found, entire puzzle is invalid
			if (results[i] == 0) {
				valid = false;
			}
		}
		
		// Print final result based on valid flag
		if (valid) {
			System.out.println("The provided sudoku solution is valid!");			
		} else {
			System.out.println("The provided sudoku solution is invalid!");	
		}
		
	}

}
