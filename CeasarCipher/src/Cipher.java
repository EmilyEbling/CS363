import java.util.*;

public class Cipher {
	
	public static String alphabet = "abcdefghijklmnopqrstuvwxyz";

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		String input = in.nextLine().toUpperCase();
		char start = 'a';
		char end = 'z';
		int shift = 10;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isLetter(c)) {
				int position = alphabet.indexOf(input.charAt(i));
				int value = (position - shift) % 26;
				if (value < 0)
					value = alphabet.length() + value;
				 c = alphabet.charAt(value);		
			}
			else
				c = input.charAt(i);

			System.out.print(c);

		}

	}

}
