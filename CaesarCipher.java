import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class CaesarCipher {
	
	public static final char[] ALPHABET =
		{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	public static void main(String[] args) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		File messageFile = new File("encryptedMessage.txt");
		File keyFile = new File("key.txt");
		
		while(true) {
			
			System.out.print("Choose encrypt or decrypt (E/D): ");
			String choice = scanner.nextLine();
			
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
		

	
	public static String encrypt(Scanner scanner, File messageFile) throws IOException {
		
		FileWriter writer = new FileWriter(messageFile);
		
		String message;
		String inputDirection = null;
		int inputShift = 0;
		
		while (true) {
			System.out.print("Enter message: ");
			message = scanner.nextLine();
				
			if (message.length() == 0) {
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



	private static void performLetterShift(String direction, int shift, char[] letters) {
		if (direction.toLowerCase().equals("r") ||
				direction.toLowerCase().equals("right")) {
			
			for (int i = 0; i < letters.length; i++) {
				
				boolean isUpper = Character.isUpperCase(letters[i]);
				
				if (!((letters[i] >= 'A' && letters[i] <= 'Z') ||
					      (letters[i] >= 'a' && letters[i] <= 'z'))) {
					continue;
				}
				if ((shift + indexOfAlphabet(letters[i])) >= 26) {
					letters[i] = ALPHABET[(shift + indexOfAlphabet(letters[i]) - 26)];
				} else {
					letters[i] = ALPHABET[(shift + indexOfAlphabet(letters[i]))];
				}
				
				if (isUpper) {
					letters[i] = Character.toUpperCase(letters[i]);
				}
			} 
		} else if(direction.toLowerCase().equals("l") ||
				direction.toLowerCase().equals("left")) {
			
			for (int i = 0; i < letters.length; i++) {
				
				boolean isUpper = Character.isUpperCase(letters[i]);
				
				if (!((letters[i] >= 'A' && letters[i] <= 'Z') ||
					      (letters[i] >= 'a' && letters[i] <= 'z'))) {
					continue;
				}
				if ((indexOfAlphabet(letters[i]) - shift) < 0) {
					letters[i] = ALPHABET[((indexOfAlphabet(letters[i]) - shift) + 26)];
				} else {
					letters[i] = ALPHABET[(indexOfAlphabet(letters[i]) - shift)];
				}
				
				if (isUpper) {
					letters[i] = Character.toUpperCase(letters[i]);
				}
			}
			
		}
	}



	private static int checkShift(Scanner scanner) {
		
		int shift;
		
		while (true) {
			System.out.print("Enter number of shifts (1 - 25): ");
			if (scanner.hasNextInt()) {
				shift = scanner.nextInt();
				scanner.nextLine();
				
				if (shift < 1 || shift > 25) {
					System.out.println("Must enter 1 - 25. Try again.");
					continue;
				}
				break;
			}
			
		}
		return shift;
	}



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
	
	public static String decrypt(Scanner scanner, File key)
			throws IOException {
		
		int numAttempts = 3;
		String keyDirection = null;
		int keyShift = 0;
		
		String decryptedMessage = null;
		
		File encryptedMessageFile = null;
		
		while (numAttempts > 0) {
			
			System.out.println("You have " + numAttempts + " attempt(s) left!");
			
			String inputDirection = checkDirection(scanner);
			int inputShift = checkShift(scanner);
			
			Scanner keyScanner = new Scanner(key);
			
			if (keyScanner.hasNextLine()) {
				
				keyDirection = keyScanner.nextLine();
			} 
			
			if (keyScanner.hasNextLine()) {
				keyShift = keyScanner.nextInt();
			}
			
			if (inputDirection.equalsIgnoreCase(keyDirection) &&
					inputShift == keyShift) {
				
				encryptedMessageFile = new File("encryptedMessage.txt");
				Scanner encryptedScanner = new Scanner(encryptedMessageFile);
				String encryptedMessage = encryptedScanner.nextLine();
				encryptedScanner.close();
				char[] letters = encryptedMessage.toCharArray();
				
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
				break;
				
			} else {
				
				numAttempts--;
				
				if (numAttempts == 0) {
					System.out.println("No attempts left. Decryption failed!");
					Files.delete(encryptedMessageFile.toPath());
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
		return -1;
	}

}
	
	


