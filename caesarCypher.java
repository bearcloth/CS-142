package caesarCypher;

import java.util.Arrays;
import java.util.Scanner;

import java.awt.Choice;
import java.io.File;
import java.lang.reflect.Array;
import java.net.PasswordAuthentication;
import java.security.PublicKey;

public class caesarCypher {

	/**
	 * Utility Class for ciphering / deciphering using Caesar's Algorithm
	 * 
	 * @author Anthony Rivera, Brian Vargas, Cooper Fultz, Erick Ruiz
	 */

	/**
	* Main method for use of testing / finalized implementation
	* 
	* @param args Ignored
	*/
	public static void main(String[] args) {
		Scanner ScannerSystem = new Scanner(System.in);
		
		//System.out.println(inputShift);
		//String testString = "zebra";
		//System.out.println(Arrays.toString(testChar));
		
		
		String systemChoiceString = promptUser(ScannerSystem);
		
		char[] testChar = convertStringToText(systemChoiceString);
		int inputShift = inputShift(ScannerSystem);
		System.out.println(shiftChracters(testChar, inputShift));
	
	}
		
	/**
	* 
	* @param input
	*/
	public static String promptUser(Scanner input) {
		
		String testChoice;
				
		while (true) {
			System.out.print("Do you want to encrypt or decrypt? ");
			String choice = input.next();
				
			switch (choice.toLowerCase()) {
				case "encrypt": {
					testChoice = "encrypt";
					break;
				}
				case "decrypt": {
					testChoice = "encrypt";
					break;
				}
				default:
					System.out.println("That is not a valid command!");
					continue;
			}
			System.out.println(testChoice);
			return testChoice;
		}				
	}
	
	public static String inputString(Scanner scanner, String inputChoice) {
		System.out.println("What is the message you want to " + inputChoice);
		String 
	}
	
	public static int inputShift(Scanner scanner) {
		while (true) {
			System.out.print("Shift by: ");
			
			//Checks if next line is Int
			if(!scanner.hasNextInt()) {
				System.out.println("That was not a valid number! Please try again.");
				scanner.nextLine(); //Clears Line to prevent Loop
				continue;
			}
			
			int inputNumberShift = scanner.nextInt();
			scanner.nextLine(); // consume leftover newline
			
			return inputNumberShift;
		}		
	}
		
	public static char[] convertStringToText(String inputString) {
		char[] inputWordArray = new char[inputString.length()];
			
		for (int i = 0; i < inputString.length(); i++) {
			inputWordArray[i] = inputString.charAt(i);
		}
			
		return inputWordArray;
	}
		
		

}
