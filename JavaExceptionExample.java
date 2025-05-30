import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

//FileNotFoundException extends IOException
//IOException extends Exception

//ArithmeticException extends RuntimeException

//InputMismatchException extends NoSuchElementException
//NoSuchElementException extends RuntimeException {

public class JavaExceptionExample {

    public static void main(String[] args) {
        readAndCompute();
    }

    public static void readAndCompute() {
        try {
            Scanner scanner = new Scanner(new File("numbers.txt"));
            int number = scanner.nextInt();
            if(number < 0) {
                throw new InputMismatchException("invalid input");
            }
            int result = 100 / number;
            System.out.println("Result: " + result);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
            e.printStackTrace();
        }
        catch (ArithmeticException e) {
            System.out.println("Error: Division by zero");
        }
//        catch (NoSuchElementException e){
//            System.out.println("NoSuchElementException" + e.getMessage());
//        }
        catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }

        catch (RuntimeException e){
            e.printStackTrace();
        }
    }
}
