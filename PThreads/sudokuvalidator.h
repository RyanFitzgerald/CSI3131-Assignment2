// Get includes
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// Define constants
#define NUM_THREADS 11

// Define parameter stucture
typedef struct
{
	int row;
	int column;
	int thread_id;
} parameters;

// Function prototypes
void *validateRows(void *arg);
void *validateColumns(void *arg);
void *validateSubgrids(void *arg);