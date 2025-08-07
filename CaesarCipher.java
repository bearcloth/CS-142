import java.util.Scanner;
import java.util.Arrays;

public class CaesarCipher {
	
	public static final char[] ALPHABET =
		{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			System.out.println("Choose encrypt or decrypt (E/D): ");
			String choice = scanner.nextLine();
			
			if (choice.toLowerCase().equals("e") || 
					choice.toLowerCase().equals("encrypt") ||
					choice.toLowerCase().equals("d") ||
					choice.toLowerCase().equals("decrypt")) {
				
				encrypt(scanner);
				break;
				
			} else {
				
				System.out.println("Not a valid choice. Try again.");
				continue; 
			}	
		}
	}
		

	
	public static String encrypt(Scanner scanner) {
		
		String message;
		String direction;
		int shift;
		
		while (true) {
			System.out.println("Enter message: ");
			message = scanner.nextLine();
				
			if (message.length() == 0) {
				System.out.println("Must enter a message. Try again.");
				continue;
			}
			break;
		}
			
		while (true) {
			System.out.println("Enter direction of shift (left/right): ");
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
			
		while (true) {
			System.out.println("Enter number of shifts (1 - 25): ");
			if (scanner.hasNextInt()) {
				shift = scanner.nextInt();
				
				if (shift < 1 || shift > 25) {
					System.out.println("Must enter 1 - 25. Try again.");
					continue;
				}
				break;
			}
			
		}
			
		char[] letters = message.toCharArray();
		
		if (direction.toLowerCase().equals("r") ||
				direction.toLowerCase().equals("right")) {
			
			for (int i = 0; i < letters.length; i++) {
				
				if ((!(letters[i] >= 0x41) || !(letters[i] <= 0x5A)) &&
						(!(letters[i] >= 0x61)) || !(letters[i] <= 0x7A)) {
					continue;
				}
				if ((shift + indexOfAlphabet(letters[i])) > 25) {
					letters[i] = ALPHABET[(shift + indexOfAlphabet(letters[i]) - 26)];
				} else {
					letters[i] = ALPHABET[(shift + indexOfAlphabet(letters[i]))];
				}
			}
		}
		
		String encryptedMessage = new String(letters);
		
		System.out.println(encryptedMessage);
			
		return encryptedMessage;
	}
	
	public static int indexOfAlphabet(char letter) {
		for (int i = 0; i < ALPHABET.length; i++) {
			if (ALPHABET[i] == letter) {
				return i;
			}
		}
		return -1;
	}
}
	
	


