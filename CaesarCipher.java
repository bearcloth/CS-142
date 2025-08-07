import java.util.Scanner;
import java.io.File;

/**
 * Utility Class for ciphering / deciphering using Caesar's Algorithm
 * 
 * @author Anthony Rivera, Brian Vargas, Cooper Fultz, Erick Ruiz
 */
public class CaesarCipher {

	/**
	 * Main method for use of testing / finalized implementation
	 * 
	 * @param args Ignored
	 */
	public static void main(String[] args) {
		

	}
	
	/**
	 * 
	 * @param input
	 */
	public static void promptUser(Scanner input) {
		
		System.out.print("Do you want to encrypt or decrypt? ");
		
		String choice = input.next();
		
		while (true) {
			
			if (choice.toLowerCase().equals("encrypt")) {
				// do this method
				break;
			} else if (choice.toLowerCase().equals("decrypt")) {
				// do that method
				break;
			} else {
				System.out.println("That is not a valid command!");
			}
			
		}
		
	}

}
