/*
 * Emily Ebling
 * Project 1
 * CS 363
 * 
 * */

import java.util.*;

public class NoKey {

	final private static double[] frequencies = {.082, .014, .025, .046, .124, .022, .020, .065, .069, .001, .008,
			.039, .024, .073, .076, .018, .001, .059, .065, .089, .026, .011, .023, .002, .018, .001};

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("What message would you like to decrypt?: ");
		String cipherText = in.next().toUpperCase();

		int keyLength = findKeyLength(cipherText);
		String message = decrypt(cipherText, keyLength);
		System.out.println(message);

	}

	private static String decrypt(String cipherText, int keyLength) {
		String[] subString = new String[keyLength]; //holds keyLength subsequences 
		String[] caesarShifts = new String[26]; //will hold each caesar shift for a given subsequence
		String[] decrypted = new String[keyLength]; //will hold the best caesar shift per subsequence

		String message = ""; //used to hold final message

		for (int i = 0; i < subString.length; i++) //initializes array to empty string
			subString[i] = "";

		for (int i = 0; i < cipherText.length(); i++)  //want keyLength different sets of subsequences
			subString[i % keyLength] += cipherText.charAt(i); //makes sure extracted subsequences map to correct location
		
		for (int i = 0; i < subString.length; i++) { //iterates through every subsequence
			String temp = subString[i]; //store current subsequence in a string for easier manipulation
			for (int j = 0; j < 26; j++)  //performs caesar shift all possible times on subsequence
				caesarShifts[j] = caesar(temp, j);		
			decrypted[i] = chiSquare(caesarShifts, temp); //stores best caesar shift in decrypted array
		}

		for (int i = 0; i < decrypted[0].length(); i++) 
			for (int j = 0; j < keyLength; j++)
				if (decrypted[j].length() > i)
					message += decrypted[j].charAt(i); //puts together final, decrypted message

		return message;
	}

	private static String chiSquare(String[] caesar, String subsequence) { 
		double min = Double.MAX_VALUE;
		String shift = "";

		for (int i = 0; i < caesar.length; i++) { //iterate through the length of the current subsequence
			String temp = caesar[i]; //stores in string for easier manipulation
			int[] observed = letterFreq(temp); //calculates the letter frequency for current subsequence 
			double chi = 0;
			for (int j = 0; j < observed.length; j++) { //calculates the chi square value using observed frequencies and expected frequencies 
				double expected = temp.length() * frequencies[j];
				chi += ((observed[j] - expected) * (observed[j] - expected))/expected;
			}

			if (min > chi) { //chooses lowest chi square score
				min = chi;
				shift = temp;
			}
		}
		return shift; 
	}

	private static String caesar(String text, int shift) {
		String shifted = "";

		for (int i = 0; i < text.length(); i++) { //calculates caesar shift
			char c = (char) (text.charAt(i) + shift);
			if (c > 'Z')
				shifted += (char) (c - ('Z' - 'A' + 1));
			else
				shifted += c;
		}

		return shifted;
	}

	private static int findKeyLength(String cipherText) {
		int keyLength = 0;
		double iOC = 0; //keeps track of index of confidence of the ciphertext

		for (int i = 1; i <= 20; i++) { //word unlikely more then 20 characters 
			String tempStr = "";
			double stringIOC = 0; //keeps track of the index of confidence of the temp string 
			int temp = 1;

			for (int j = 0; j < cipherText.length(); j+=i) { //compares original ciphertext to ciphertext shifted over j times for all possible key lengths
				tempStr += cipherText.charAt(j); //stores ciphertext into temp string
				if (j + i >= cipherText.length() && temp != i) { 
					stringIOC += indexOfCoincidence(tempStr); //finds IOC for temp string 
					j = temp++ - i;
				} 
				else if (j + i >= cipherText.length()) {
					stringIOC += indexOfCoincidence(tempStr);
				}
			}

			stringIOC = stringIOC / i;

			if (stringIOC > iOC) { //want to find the best IOC 
				iOC = stringIOC;
				keyLength = i;
			}

		}

		return keyLength;
	}

	private static double indexOfCoincidence(String text) {
		int[] freq = new int[26]; //hold number of times each letter in english alphabet appears in text

		double freqSum = 0;

		for (int i = 0; i < text.length(); i++) 
			freq[text.charAt(i) - 'A']++; //freq[0] will correspond to the letter A, freq[25] corresponds to Z, etc. increases count of each letter found

		for (int i = 0; i < 26; i++)  //calculates sum of frequencies for text
			freqSum += freq[i] * (freq[i] - 1);

		return freqSum / (text.length()*(text.length()-1)); //calculates IOC for text;
	}

	private static int[] letterFreq(String text) { 
		int[] freq = new int[26];

		for (int i = 0; i < text.length(); i++) //finds the letter frequences for a given text
			if (Character.isLetter(text.charAt(i)))
				freq[text.charAt(i) - 'A']++; //increments index corresponding to the letter found 

		return freq;
	} 

}
