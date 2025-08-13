package caesarCypher;

import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFileChooser;

import java.awt.Choice;
import java.io.File;
import java.io.FileNotFoundException;
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
	 * @throws FileNotFoundException 
	*/
	public static void main(String[] args) throws FileNotFoundException {
		Scanner ScannerSystem = new Scanner(System.in);
		
		//System.out.println(inputShift);
		//String testString = "zebra";
		//System.out.println(Arrays.toString(testChar));
		
		
		String systemChoiceString = promptUser(ScannerSystem);
		
		String inputMessageString;
		
		if (parseTextFile(ScannerSystem)) {
			inputMessageString = jFilePullString();
			//inputMessageString = "Test";
		} else {
			inputMessageString = inputString(ScannerSystem, systemChoiceString);
		}
		//System.out.println(inputMessageString);
		char[] testChar = convertStringToArray(inputMessageString);
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
					testChoice = "decrypt";
					break;
				}
				default:
					System.out.println("That is not a valid command!");
					continue;
			}
			//System.out.println(testChoice);
			return testChoice;
		}				
	}
	
	public static boolean parseTextFile(Scanner scanner) {
		while(true) {
			System.out.print("Do you want to use a text file. Y/N ");
			//scanner.nextLine();
			String inputMessageString = scanner.next();
			if (inputMessageString.equalsIgnoreCase("Y")) {
				return true;
			} else if (inputMessageString.equalsIgnoreCase("N")) {
				return false;
			} else {
				System.out.println("Not valid Repsonse ");
			}
		}
		
		
		
	}
	
	public static String inputString(Scanner scanner, String inputChoice) { 
		System.out.print("What is the message you want to " + inputChoice + " ");
		scanner.nextLine();
		String inputMessageString = scanner.nextLine();
		return inputMessageString;
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
		
	public static char[] convertStringToArray(String inputString) {
		char[] inputWordArray = new char[inputString.length()];
			
		for (int i = 0; i < inputString.length(); i++) {
			inputWordArray[i] = inputString.charAt(i);
		}
			
		return inputWordArray;
	}
		
	public static char[] shiftChracters(char[] wordArray, int numberShift) {
		int shiftReset = 0; 
		for (int i = 0; i < wordArray.length; i++) {
			 char currentCharacterUnicode = wordArray[i];
			
			 if (Character.isLowerCase(currentCharacterUnicode)) {
		            wordArray[i] = (char) ('a' + (currentCharacterUnicode - 'a' + numberShift + 26) % 26);
		        } else if (Character.isUpperCase(currentCharacterUnicode)) {
		            wordArray[i] = (char) ('A' + (currentCharacterUnicode) % 26);
		        }
		        // else: Non-alphabetic character, leave unchanged
		    }
			
		return wordArray;	
	}		
	
	public static String jFilePullString() throws FileNotFoundException {
		JFileChooser chooser = new JFileChooser(); //JFileChooser Allows file to be chosen and returns a file path once chosen
		chooser.showOpenDialog(null); //Pops up an "Open File" file chooser dialog. Note that the text that appears in the approve button is determined by the L&F.
		Scanner scanner = new Scanner(chooser.getSelectedFile()); 
		
		//Delimiter is what is chosen to separate sequences of strings. 
		scanner.useDelimiter("\\Z"); //changes the delimiter to end of input (\\z) and will read entire scanner text as string. 
		
		return scanner.next();
		
	}
}
