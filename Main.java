import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first number: ");
        String input1  = scanner.nextLine();
        System.out.print("Enter the second number: ");
        String input2 = scanner.nextLine();
        System.out.println("To reach the first power, enter a number as exponent");
        int exponent = scanner.nextInt();
        BigNumber num1 = new BigNumber(input1);
        BigNumber num2 = new BigNumber(input2);
        System.out.println("Addition: " + num1.add(num2));
        System.out.println("Subtraction: " + num1.subtract(num2));
        System.out.println("Multiplication: " + num1.multiply(num2));
        System.out.println("Division: " + num1.divide(new BigNumber(input2)));
        System.out.println("Left Shift: " + BigNumber.shiftLeft(input1 , 1));
        System.out.println("Right Shift: " + BigNumber.shiftRight(input1 , 1));
        System.out.println("power: " + num1.power(exponent));
        System.out.println("Karatsuba: "+num1.karatsuba(num2));
        // 1 < number < 100
//        System.out.println("Factorial: " + new BigNumber(input1).factorial());
    }

}