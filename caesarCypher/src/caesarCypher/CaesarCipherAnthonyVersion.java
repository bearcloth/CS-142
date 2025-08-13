package caesarCypher;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A utility class which runs and operates a Caeser Cipher style encryption and decryption machine.
 * 
 * @author Anthony Rivera, Erick Ruiz, Cooper Fultz, Brian Vargas
 */
public class CaesarCipherAnthonyVersion {
	
	private static final int MAX_SHIFT = 25; // max allowed number of shifts
	private static final int MIN_SHIFT = 1; // min allowed number of shifts
	private static final int FIRST_INDEX_ALPHABET = 0; // Letter 'a' beginning of alphabet
	private static final int NUM_LETTERS_ALPHABET = 26; // Letter 'z' end of alphabet
	private static final int ZERO_ATTEMPTS = 0; // Minimum attempts before fail
	private static final int MAX_NUM_ATTEMPTS = 3; // Max attempts before fail
	private static final int EMPTY_MESSAGE = 0; // Message length of 0
	
	/**
	 * Character array of the english alphabet
	 */
	public static final char[] ALPHABET =
		{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
		 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	/**
	 * Runs encryption and decryption methods for Caeser cipher machine
	 * 
	 * @param args ignored
	 * @throws IOException Throws exception for input/output stream failure.
	 */
	public static void main(String[] args) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		File messageFile = new File("encryptedMessage.txt");
		File keyFile = new File("key.txt");
		
		while(true) {
			
			System.out.print("Choose encrypt or decrypt (E/D): ");
			String choice = scanner.nextLine();
			
			// Decide whether to start encrypting or decrypting based on input
			if (choice.toLowerCase().equals("e") || 
				choice.toLowerCase().equals("encrypt")) {
				encrypt(scanner, messageFile);
				System.exit(0);
			} else if(choice.toLowerCase().equals("d") ||
					  choice.toLowerCase().equals("decrypt")) {
					  decrypt(scanner, keyFile);
			} else {
				System.out.println("Not a valid choice. Try again.");
				continue; 
			}	
		}
	}
		
	/**
	 * Runs the encrypt portion of the Caeser Cipher
	 * 
	 * @param scanner Java scanner
	 * @param messageFile File containing encrypted message
	 * @return Encrypted message
	 * @throws IOException Throws exception for input/output stream failure.
	 */
	public static String encrypt(Scanner scanner, File messageFile) throws IOException {
		
		FileWriter writer = new FileWriter(messageFile);
		
		String message;

		while (true) {
			System.out.print("Enter message: ");
			message = scanner.nextLine();
				
			if (message.length() == EMPTY_MESSAGE) {
				System.out.println("Must enter a message. Try again.");
				continue;
			}
			break;
		}
			
		String direction = checkDirection(scanner);
			
		int shift = checkShift(scanner);
			
		char[] letters = message.toCharArray();
		
		performLetterShift(direction, shift, letters);
				
		String encryptedMessage = new String(letters);
		
		System.out.println(encryptedMessage);
		// Pastes message into encrypted message file
		writer.write(encryptedMessage);
		System.out.println("Message saved in " + messageFile.getName() +
				" at " + messageFile.getAbsolutePath());
		writer.close();
		
		// Key file contains the direciton and shift to decrypt encrypted message
		File keyFile = new File("key.txt");
		writer = new FileWriter(keyFile);
		
		writer.write(direction + "\n" + shift);
		writer.close();
			
		return encryptedMessage;
	}
	
	/**
	 * Runs the decrypt portion of the Caeser Cipher
	 * 
	 * @param scanner Java scanner
	 * @param key The saved information regarding the direction and length of character shift
	 * @return Decrypted message
	 * @throws IOException Throws exception for input/output stream failure.
	 */
	public static String decrypt(Scanner scanner, File key)
			throws IOException {
		
		int numAttempts = MAX_NUM_ATTEMPTS;
		String keyDirection = null;
		int keyShift = 0;
		
		String decryptedMessage = null;
		
		File encryptedMessageFile = new File("encryptedMessage.txt");
		
		while (numAttempts > ZERO_ATTEMPTS) {
			
			// Program closes if encrypted message file does not exist in path
			if (!encryptedMessageFile.exists()) {
				System.out.println("\nFile Does not exist");
				System.out.println("Please create a new file using encrypt.\n");
				System.exit(0);
			}
			
			System.out.println("You have " + numAttempts + " attempt(s) left!");
			
			String inputDirection = checkDirection(scanner);
			int inputShift = checkShift(scanner);
			
			// Getting the key direciton and shift from key.txt
			try (Scanner keyScanner = new Scanner(key)) {
				if (keyScanner.hasNextLine()) {
					keyDirection = keyScanner.nextLine();
				} 
				
				if (keyScanner.hasNextLine()) {
					keyShift = keyScanner.nextInt();
				}
			}
			
			if (inputDirection.equalsIgnoreCase(keyDirection) &&
				inputShift == keyShift) {
				
				encryptedMessageFile = new File("encryptedMessage.txt");
				Scanner encryptedScanner = new Scanner(encryptedMessageFile);
				String encryptedMessage = encryptedScanner.nextLine();
				encryptedScanner.close();
				char[] letters = encryptedMessage.toCharArray();
				
				// Altering the direction to properly decrypt the encrypted message
				if (inputDirection.equals("right")) {
					inputDirection = "left";
					performLetterShift(inputDirection, inputShift, letters);
					decryptedMessage = new String(letters);
				} else {
					inputDirection = "right";
					performLetterShift(inputDirection, inputShift, letters);
					decryptedMessage = new String(letters);
				}
				
				File decryptedMessageFile = new File("decryptedMessage.txt");
				FileWriter writer = new FileWriter(decryptedMessageFile);
				writer.write(decryptedMessage);
				writer.close();
				System.out.println(decryptedMessageFile.getName() + " saved at " +
				decryptedMessageFile.getAbsolutePath());
				System.exit(0);
				break;
				
			} else {
				numAttempts--;
				
				// If attempts run out, key.txt and encryptedMessage.txt is removed
				if (numAttempts == ZERO_ATTEMPTS) {
					System.out.println("No attempts left. Decryption failed!");	
					if (encryptedMessageFile.delete() && key.delete()) {
						System.out.println("Files has been deleted!");
						System.exit(0);
					}
				}
			}	
		}
		return decryptedMessage;
	}

	/*
	 * Helper method that shifts characters based upon the user's input direction of shift
	 * and length of shift
	 */
	private static void performLetterShift(String direction, int shift, char[] letters) {
		
		// Shift going towards the right
		if (direction.toLowerCase().equals("r") ||
				direction.toLowerCase().equals("right")) {
			
			for (int i = 0; i < letters.length; i++) {
				
				boolean isUpper = Character.isUpperCase(letters[i]);
				
				// Checks to see if the character is not uppercase or lowercase letter.
				// If it is, we skip that character
				if (!((letters[i] >= 'A' && letters[i] <= 'Z') ||
					      (letters[i] >= 'a' && letters[i] <= 'z'))) {
					continue;
				}
				// If adding the shift is more than the letters in the alphabet,
				// we will subtract the sum of the index and shift by 26 to ensure it loops
				// to the beginning of the alphabet.
				if ((shift + indexOfAlphabet(letters[i])) >= NUM_LETTERS_ALPHABET) {
					letters[i] = ALPHABET[(shift + indexOfAlphabet(letters[i]) - NUM_LETTERS_ALPHABET)];
				} else {
					letters[i] = ALPHABET[(shift + indexOfAlphabet(letters[i]))];
				}
				
				// Checks to see if the letter we iterate over is uppercase
				// to ensure it stays upper case when printed
				if (isUpper) {
					letters[i] = Character.toUpperCase(letters[i]);
				}
			} 
			// Shift going towards the left 
		} else if(direction.toLowerCase().equals("l") ||
				direction.toLowerCase().equals("left")) {
			for (int i = 0; i < letters.length; i++) {
				
				boolean isUpper = Character.isUpperCase(letters[i]);
				
				if (!((letters[i] >= 'A' && letters[i] <= 'Z') ||
					(letters[i] >= 'a' && letters[i] <= 'z'))) {
					continue;
				}
				// If when subtracting the shift and it becomes less than 0,
				// we add 26 to ensure it loops properly
				if ((indexOfAlphabet(letters[i]) - shift) < FIRST_INDEX_ALPHABET) {
					letters[i] = ALPHABET[((indexOfAlphabet(letters[i]) - shift) + NUM_LETTERS_ALPHABET)];
				} else {
					letters[i] = ALPHABET[(indexOfAlphabet(letters[i]) - shift)];
				}
				
				if (isUpper) {
					letters[i] = Character.toUpperCase(letters[i]);
				}
			}
		}
	}

	/*
	 * Helper method that checks if the user's input shift length is valid
	 */
	private static int checkShift(Scanner scanner) {
		
		int shift;
		
		while (true) {
			System.out.print("Enter number of shifts (1 - 25): ");
			if (scanner.hasNextInt()) {
				shift = scanner.nextInt();
				scanner.nextLine();
				
				// checks to make sure input is within 1-25
				if (shift < MIN_SHIFT || shift > MAX_SHIFT) {
					System.out.println("Must enter 1 - 25. Try again.");
					continue;
				}
				break;
			}	else if (scanner.hasNextLine()) {
				System.out.println("This is not a number, please enter a number");
				scanner.nextLine();
				continue;
			}
		}
		return shift;
	}

	/*
	 * Helper method that checks if the user's input shift direction is valid
	 */
	private static String checkDirection(Scanner scanner) {
		
		String direction;
		
		while (true) {
			System.out.print("Enter direction of shift (left/right): ");
			direction = scanner.nextLine();
			
			if (direction.toLowerCase().equals("left") ||
				direction.toLowerCase().equals("l") ||
				direction.toLowerCase().equals("right") ||
				direction.toLowerCase().equals("r")) {
				
				break;					
				
			} else {
				System.out.println("Must enter valid direction. Try again.");
				continue;
			}
		}
		return direction;
	}
	
	/*
	 * Helper method that finds where a certain letter
	 * is in the message
	 */
	private static int indexOfAlphabet(char letter) {
		for (int i = 0; i < ALPHABET.length; i++) {
			if (ALPHABET[i] == Character.toLowerCase(letter)) {
				return i;
			}
		}
		return -1; // Indicates failure
	}
}