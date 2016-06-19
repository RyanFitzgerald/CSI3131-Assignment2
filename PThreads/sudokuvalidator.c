#include "sudokuvalidator.h"

// Default solution (can be changed)
int sudoku[9][9] = {
	{ 6, 2, 4, 5, 3, 9, 1, 8, 7 },
	{ 5, 1, 9, 7, 2, 8, 6, 3, 4 },
	{ 8, 3, 7, 6, 1, 4, 2, 9, 5 },
	{ 1, 4, 3, 8, 6, 5, 7, 2, 9 },
	{ 9, 5, 8, 2, 4, 7, 3, 6, 1 },
	{ 7, 6, 2, 3, 9, 1, 4, 5, 8 },
	{ 3, 7, 1, 9, 5, 6, 8, 4, 2 },
	{ 4, 9, 6, 1, 8, 2, 5, 7, 3 },
	{ 2, 8, 5, 4, 7, 3, 9, 1, 6 }
};

// Create empty array to hold results of each thread
int results[NUM_THREADS]; 

// Main function
int main(void)
{

	pthread_t threads[NUM_THREADS];

	parameters **data = malloc(sizeof(parameters)*NUM_THREADS); // Store params in data pointer
	int i; // Index for subgrids
	int valid = 1; // Store validity of solution (default to true)

	// Created to store data for each thread
	for (i = 0; i < NUM_THREADS; i++) {
		data[i] = malloc(sizeof(parameters));
	}

	// Create thread for row validations (thread 9)
	data[9]->row = 0;
	data[9]->column = 0;
	data[9]->thread_id = 9;
	pthread_create(&threads[9], NULL, validateRows, (void *)data[9]);

	// Create thread for column validations (thread 10)
	data[10]->row = 0;
	data[10]->column = 0;
	data[10]->thread_id = 10;
	pthread_create(&threads[10], NULL, validateColumns, (void *)data[10]);

	// Create threads for subgrid validations (thread 0-8)
	for (i = 0; i < 9; i++) {
		data[i]->row = i % 3 * 3; // Calculate start row
		data[i]->column = i / 3 * 3; // Calculator start column
		data[i]->thread_id = i;
		pthread_create(&threads[i], NULL, validateSubgrids, (void *)data[i]);
	}

	// Join all the threads
	for (i = 0; i < NUM_THREADS; i++) {
		pthread_join(threads[i], NULL);
	}

	// Check if any errors were found
	for (i = 0; i < NUM_THREADS; i++) {
		// Check if currenty entry found an error and set valid to 0 if so
		if (results[i] == 0) {
			valid = 0;
		}

		// Free up the memory
		free(data[i]);
	}

	// Free remaining allocated memory
	free(data);

	// Print final result
	if (valid == 1) {
		printf("The provided sudoku solution is valid!\n");
	} else {
		printf("The provided sudoku solution is invalid!\n");
	}

	return 0;
}

// Validate the rows of the sudoku
void *validateRows(void *arg)
{

	// Get params and create indices
	parameters *params = (parameters *) arg;
	int i, j;
	int result = 1; // Default to true
	int thread_id = params->thread_id;

	// Loop through rows
	for (j = 0; j < 9; j++) {

		int nums[9] = { 0 }; // Create empty array of length 9 to store numbers

		// Loop through columns
		for (i = 0; i < 9; i++) {

			// If number is 0 or greater than 9 or if a number already exists (duplicate)
			// set result to false, otherwise, insert the number
			if (sudoku[i][j] == 0 || sudoku[i][j] > 9 || nums[sudoku[i][j] - 1] != 0) {
				result = 0;
			} else {
				nums[sudoku[i][j] - 1] = sudoku[i][j];
			}

		}

	}

	// Set the result of this thread
	results[thread_id] = result;

}

// Validate the columns of the sudoku
void *validateColumns(void *arg)
{

	// Get params and create indices
	parameters *params = (parameters *)arg;
	int i, j;
	int result = 1; // Default to true
	int thread_id = params->thread_id;

	// Loop through columns
	for (i = 0; i < 9; i++) {

		int nums[9] = { 0 }; // Create empty array of length 9 to store numbers

		// Loop through rows
		for (j = 0; j < 9; j++) {

			// If number is 0 or greater than 9 or if a number already exists (duplicate)
			// set result to false, otherwise, insert the number
			if (sudoku[i][j] == 0 || sudoku[i][j] > 9 || nums[sudoku[i][j] - 1] != 0) {
				result = 0;
			} else {
				nums[sudoku[i][j] - 1] = sudoku[i][j];
			}

		}

	}

	// Set the result of this thread
	results[thread_id] = result;

}

// Validate the subgrids of the sudoku
void *validateSubgrids(void *arg)
{

	// Get params and create indices
	parameters *params = (parameters *)arg;
	int i, j;
	int result = 1; // Default to true
	int column = params->column;
	int row = params->row;
	int thread_id = params->thread_id;

	int nums[9] = { 0 }; // Create empty array of length 9 to store numbers

	// Loop through subgrid provided to thread
	for (i = column; i < column + 3; i++) {

		for (j = row; j < row + 3; j++) {

			// If number is 0 or greater than 9 or if a number already exists (duplicate)
			// set result to false, otherwise, insert the number
			if (sudoku[i][j] == 0 || sudoku[i][j] > 9 || nums[sudoku[i][j] - 1] != 0) {
				result = 0;
			} else {
				nums[sudoku[i][j] - 1] = sudoku[i][j];
			}

		}

	}

	// Set the result of this thread
	results[thread_id] = result;

}