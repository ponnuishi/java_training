/**
 * MultiDimensionalArrays.java
 * This program demonstrates the concepts of multi-dimensional arrays in Java,
 * including 2D arrays, matrix operations, and practical applications.
 */
public class MultiDimensionalArrays {
    public static void main(String[] args) {
        // 1. 2D Array Declaration and Initialization
        System.out.println("1. 2D Array Declaration and Initialization");
        System.out.println("----------------------------------------");

        // Different ways to declare and initialize 2D arrays
        int[][] matrix1 = new int[3][3];  // 3x3 matrix with default values
        int[][] matrix2 = {               // 3x3 matrix with initial values
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // Jagged array (array of arrays with different lengths)
        int[][] jaggedArray = {
                {1, 2},
                {3, 4, 5},
                {6, 7, 8, 9}
        };

        // 2. Accessing Elements in 2D Arrays
        System.out.println("\n2. Accessing Elements in 2D Arrays");
        System.out.println("--------------------------------");

        System.out.println("Accessing element at matrix2[1][1]: " + matrix2[1][1]);
        System.out.println("Accessing element at jaggedArray[2][3]: " + jaggedArray[2][3]);

        // 3. Printing 2D Arrays
        System.out.println("\n3. Printing 2D Arrays");
        System.out.println("-------------------");

        System.out.println("Matrix 2:");
        print2DArray(matrix2);

        System.out.println("\nJagged Array:");
        printJaggedArray(jaggedArray);

        // 4. Matrix Operations
        System.out.println("\n4. Matrix Operations");
        System.out.println("------------------");

        // Matrix Addition
        int[][] matrixA = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrixB = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };

        System.out.println("Matrix A:");
        print2DArray(matrixA);

        System.out.println("\nMatrix B:");
        print2DArray(matrixB);

        System.out.println("\nMatrix A + B:");
        int[][] sumMatrix = addMatrices(matrixA, matrixB);
        print2DArray(sumMatrix);

        // 5. Transpose of a Matrix
        System.out.println("\n5. Matrix Transpose");
        System.out.println("-----------------");

        System.out.println("Original Matrix:");
        print2DArray(matrixA);

        System.out.println("\nTransposed Matrix:");
        int[][] transposedMatrix = transposeMatrix(matrixA);
        print2DArray(transposedMatrix);

        // 6. Practical Application: Student Grades
        System.out.println("\n6. Practical Application: Student Grades");
        System.out.println("--------------------------------------");

        // Each row represents a student, columns represent grades in different subjects
        int[][] studentGrades = {
                {85, 90, 88},  // Student 1
                {92, 87, 95},  // Student 2
                {78, 85, 80}   // Student 3
        };

        String[] subjects = {"Math", "Science", "English"};

        // Calculate and display average grades for each student
        for (int i = 0; i < studentGrades.length; i++) {
            double average = calculateAverage(studentGrades[i]);
            System.out.printf("Student %d average: %.2f%n", (i + 1), average);
        }

        // Calculate and display average grades for each subject
        for (int j = 0; j < subjects.length; j++) {
            double subjectAverage = calculateSubjectAverage(studentGrades, j);
            System.out.printf("%s average: %.2f%n", subjects[j], subjectAverage);
        }
    }

    // Helper method to print a 2D array
    private static void print2DArray(int[][] arr) {
        for (int[] row : arr) {
            for (int element : row) {
                System.out.print(element + "\t");
            }
            System.out.println();
        }
    }

    // Helper method to print a jagged array
    private static void printJaggedArray(int[][] arr) {
        for (int[] row : arr) {
            for (int element : row) {
                System.out.print(element + "\t");
            }
            System.out.println();
        }
    }

    // Method to add two matrices
    private static int[][] addMatrices(int[][] a, int[][] b) {
        int rows = a.length;
        int cols = a[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }

        return result;
    }

    // Method to transpose a matrix
    private static int[][] transposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return transposed;
    }

    // Method to calculate average of an array
    private static double calculateAverage(int[] grades) {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.length;
    }

    // Method to calculate average for a specific subject
    private static double calculateSubjectAverage(int[][] grades, int subjectIndex) {
        int sum = 0;
        for (int[] studentGrade : grades) {
            sum += studentGrade[subjectIndex];
        }
        return (double) sum / grades.length;
    }
} 