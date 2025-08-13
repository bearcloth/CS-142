package caesarCypher;

import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;


/**
 * A utility class which runs and operates a Caeser Cipher style encryption and decryption machine.
 * 
 * @author Anthony Rivera, Erick Ruiz, Cooper Fultz, Brian Vargas
 */
public class CaesarCipherV2 {
	
	private static final int FIRST_INDEX_ALPHABET = 0;  // Letter 'a' beginning of alphabet
	private static final int NUM_LETTERS_ALPHABET = 26; // Letter 'z' end of alphabet
	private static final int ZERO_ATTEMPTS = 0;  // Minimum attempts before fail
	private static final int MAX_NUM_ATTEMPTS = 3; // Max attempts before fail
	private static final int MAX_SHIFT = 25; // max allowed number of shifts
	private static final int MIN_SHIFT = 1; // min allowed number of shifts
	
	
	public static final char[] ALPHABET =
		{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	public static void main(String[] args) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		File messageFile = new File("encryptedMessage.txt");
		File keyFile = new File("key.txt");
		
		//Program Loop, begins with E or D prompt then runs differnt logic based on input
		while(true) {
			
			System.out.print("Choose encrypt or decrypt (E/D): ");
			String choice = scanner.nextLine();
			
			if (choice.toLowerCase().equals("e") || 
					choice.toLowerCase().equals("encrypt")) {
				
				if (parseTextFile(scanner)) {
					String inputMessageString = jFilePullString();
					
					FileWriter writer = new FileWriter(messageFile);
					encyrptSystem(scanner, messageFile, writer, inputMessageString);
					System.exit(0);
					//inputMessageString = "Test";
				} else {
					encryptMessage(scanner, messageFile);
					System.exit(0);
				}
				
				
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
	 * Prompts the user to choose whether to read the message from a text file.
	 * Repeats until the user enters 'Y' or 'N' (not case insensitive ).
	 *
	 * @param scanner the Scanner to read user input from
	 * @return true if the user chooses 'Y', false if the user chooses 'N'
	 */
	public static boolean parseTextFile(Scanner scanner) {
		while(true) {
			
			System.out.print("Do you want to use a text file. Y/N ");
			
			String inputMessageString = scanner.nextLine();
			if (inputMessageString.equalsIgnoreCase("Y")) {
				return true;
			} else if (inputMessageString.equalsIgnoreCase("N")) {
				return false;
			} else {
				System.out.println("Not valid Repsonse ");
			}
		}
	}
	
	//Window will appear behind screen
	public static String jFilePullString() throws FileNotFoundException {
		//JFileChooser Allows file to be chosen and returns a file path once chosen
		JFileChooser chooser = new JFileChooser(); 
		chooser.setDialogTitle("Select a file");
		
		setFrameOnTop(chooser);  
		
		 // Show dialog with the always-on-top frame as parent
		Scanner scanner = new Scanner(chooser.getSelectedFile()); 
		
		//Delimiter is what is chosen to separate sequences of strings. 
		scanner.useDelimiter("\\Z"); 
		//changes the delimiter to end of input (\\z) and will read entire scanner text as string. 
		
		return scanner.next();
		
	}

	/**
	 * Displays the given JFileChooser dialog above all other windows by
	 * creating a temporary always-on-top parent frame.
	 *
	 * @param chooser the JFileChooser instance to display
	 */
	private static void setFrameOnTop(JFileChooser chooser) {
		JFrame frame = new JFrame();  // Create an invisible parent frame
		frame.setAlwaysOnTop(true);  // force top
		chooser.showOpenDialog(frame);
	}
	
	/**
	 * Prompts the user for a message, writes it to the specified file,
	 * and hands off to the encryption routine.
	 *
	 * @param scanner     Scanner to read user input from
	 * @param messageFile File where the plaintext message will be written
	 * @return the encrypted message returned by encyrptSystem
	 * @throws IOException if writing to the file fails
	 */
	public static String encryptMessage(Scanner scanner, File messageFile) throws IOException {
		
		String message;
		String inputDirection = null;
		int inputShift = 0;
		
		while (true) {
			System.out.print("Enter message: ");
			message = scanner.nextLine();
				
			//Confirm user has entered input 
			//(Info: checking !scanner.hasNext() will result errors since 
			//if the use types blank lines, the system can return true
			if (message.isEmpty()) { 
				System.out.println("Must enter a message. Try again.");
				continue;
			}
			
			break;
		}
		
		FileWriter writer = new FileWriter(messageFile);
		return encyrptSystem(scanner, messageFile, writer, message);
	}
	
	//Encrypts the given message and saves both the encrypted
	// message and encryption key to files.
	private static String encyrptSystem(
			Scanner scanner, File messageFile, FileWriter writer, String message)
			throws IOException {
		// Ask the user for the encryption direction (e.g., left or right shift)
		String direction = checkDirection(scanner);
		
		// Ask the user for the shift amount
		int shift = checkShift(scanner);
				
		char[] letters = message.toCharArray();
			
		// Apply the letter shift based on the chosen direction and shift amount
		performLetterShift(direction, shift, letters);
		
		//Output:
		String encryptedMessage = new String(letters);	
		System.out.println("\nEncrypted Message: " + encryptedMessage);
		writer.write(encryptedMessage);
		System.out.println("Message saved in " + messageFile.getName() +
				" at " + messageFile.getAbsolutePath());
		writer.close();
			
		File keyFile = new File("key.txt");
		writer = new FileWriter(keyFile);
			
		writer.write(direction + "\n" + shift);
		writer.close();
				
		return encryptedMessage;
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
	 * Helper method that checks if the user's input shift length is valid
	 */
	private static int checkShift(Scanner scanner) {
		
		int shift;
		
		while (true) {
			System.out.print("Enter number of shifts (1 - 25): ");
			//Check if input is int, save input if it is int
			if (scanner.hasNextInt()) {
				shift = scanner.nextInt();
				scanner.nextLine();  //consume leftover /n from nextInt
				
				if (shift < MIN_SHIFT || shift > MAX_SHIFT) {
					System.out.println("Must enter 1 - 25. Try again.");
					continue;
				} 
				
				break;
			//Check if next input is a string
			//Restart input prompt if String to prevent endless Loop
			} else if (scanner.hasNextLine()){
				System.out.println("This is not a number, please enter a number");
				scanner.nextLine();
				continue;
			}
			
			
		}
		return shift;
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
					letters[i] = 
							ALPHABET[(shift + indexOfAlphabet(letters[i]) - NUM_LETTERS_ALPHABET)];
				} else {
					letters[i] = 
							ALPHABET[(shift + indexOfAlphabet(letters[i]))];
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
					letters[i] = 
							ALPHABET[((indexOfAlphabet(letters[i]) - shift) 
									+ NUM_LETTERS_ALPHABET)];
				} else {
					letters[i] = 
							ALPHABET[(indexOfAlphabet(letters[i]) - shift)];
				}
				
				if (isUpper) {
					letters[i] = Character.toUpperCase(letters[i]);
				}
			}
			
		}
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
		
		//Setup attempt counter and variables for storing key information 
		int numAttempts = MAX_NUM_ATTEMPTS;
		String keyDirection = null;
		int keyShift = 0;
		
		String decryptedMessage = null;
		
		File encryptedMessageFile = null;
		
		 // Reference to the encrypted message file
		encryptedMessageFile = new File("encryptedMessage.txt");
		
		// Main loop: user gets multiple attempts to enter the correct decryption key
		while (numAttempts > ZERO_ATTEMPTS) {
			
			//Loop closes if encrypted message or key file does not exist in path
			if (!encryptedMessageFile.exists() || !key.exists()) {
				System.out.println("\nFile does not exist.");
				System.out.println("Please create a new file using encrypt.\n");
	            break;
	        } 
			
			System.out.println("You have " + numAttempts + " attempt(s) left!");
			
			// Get user-provided decryption key details
			String inputDirection = checkDirection(scanner);
			int inputShift = checkShift(scanner);
			
			 //Read the actual stored key from the key file
			Scanner keyScanner = new Scanner(key);
			
			if (keyScanner.hasNextLine()) {
				keyDirection = keyScanner.nextLine();
			} 
			
			if (keyScanner.hasNextLine()) {
				keyShift = keyScanner.nextInt();
			}
			
			// Check if user input matches the stored key and perform decrypt
			if (inputDirection.equalsIgnoreCase(keyDirection) &&
					inputShift == keyShift) {
				//Read File
				encryptedMessageFile = new File("encryptedMessage.txt");
				Scanner encryptedScanner = new Scanner(encryptedMessageFile);
				//Change token to read until end of file
				encryptedScanner.useDelimiter("\\Z"); 
				String encryptedMessage = encryptedScanner.next();
				encryptedScanner.close();
				
				//Convert to char array for shifting 
				char[] letters = encryptedMessage.toCharArray();
				
				// Altering the direction to properly decrypt the encrypted message
				if (inputDirection.equals("right")) {
					//Reverse Shift to decrypt 
					inputDirection = "left";
					performLetterShift(inputDirection, inputShift, letters);
					decryptedMessage = new String(letters);
				} else {
					//Reverse Shift to decrypt 
					inputDirection = "right";
					performLetterShift(inputDirection, inputShift, letters);
					decryptedMessage = new String(letters);
				}
				
				//Save Reverse Shift to file 
				File decryptedMessageFile = new File("decryptedMessage.txt");
				FileWriter writer = new FileWriter(decryptedMessageFile);
				writer.write(decryptedMessage);
				writer.close();
				
				//Output file location
				System.out.println(decryptedMessageFile.getName() + " saved at " +
				decryptedMessageFile.getAbsolutePath());
				
				//Close Program 
				System.exit(0);
				
			} else {
				numAttempts--;
				
				// If attempts run out, key.txt and encryptedMessage.txt is removed
				if (numAttempts == 0) {
					System.out.println("No attempts left. Decryption failed!");
					
					keyScanner.close();
					
					System.out.println("Files has been deleted!");
					encryptedMessageFile.deleteOnExit(); //delete files on exit
					System.exit(0);	
				}
			}
		}
		return decryptedMessage;
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
	
	




