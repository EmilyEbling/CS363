/*
 * Emily Ebling
 * Project 1
 * CS 363
 * 
 * */

import java.util.*;

public class VigenereCipher {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		String command = "";

		while (!command.equals("q")) { //menu
			System.out.print("Enter a command (e, d, q): ");
			command = in.next();

			if (command.equalsIgnoreCase("e")) {
				System.out.print("Enter a keyword: ");
				String keyword = in.next().toUpperCase();
				System.out.print("Enter a plaintext: ");
				String plain = in.next().toUpperCase();
				String encryption = encrypt(keyword, plain);
				System.out.println("Encryption: " + encryption);
			}
			else if (command.equals("d")) {
				System.out.print("Enter a keyword: ");
				String keyword = in.next().toUpperCase();
				System.out.print("Enter a ciphertext: ");
				String cipher = in.next().toUpperCase();
				String decryption = decrypt(keyword, cipher);
				System.out.println("Decryption: " + decryption);
			}

		}

	}
	
	public static String encrypt(String keyword, String text) {
		String temp = "";
		int count = 0;

		for (int i = 0; i < text.length(); i++) { //for the length of the text to be encrypted
			char c = text.charAt(i);
			if (Character.isLetter(c)) { //only encrypts letters
				temp += (char)(((c + keyword.charAt(count)) % 26) + 'A'); //shifts character based off rules
				count = ++count % keyword.length(); //enables repetition of key
			}
		}

		return temp;

	}

	public static String decrypt(String keyword, String text) {
		String temp = "";
		int count = 0;

		for (int i = 0; i < text.length(); i++) { //for the length of text to be decrypted
			char c = text.charAt(i);
			if (Character.isLetter(c)) { //only decrypts letters
				temp += (char)((c - keyword.charAt(count) + 26) % 26 + 'A'); //shifts character based off rules
				count = ++count % keyword.length(); //enables repetition of key
			}
		}

		return temp;
	}

}