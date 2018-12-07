///*
// * Emily Ebling
// * Project 1
// * CS 363
// * 
// * */

import java.util.*;
import java.io.*;

public class LFSR {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);

		File file = new File("LFSR.encrypted");
		FileInputStream input = new FileInputStream(file);

		long length = file.length();
		LFSR();

	}

	private static void LFSR() {
		boolean[] initial = {true, true, true, true, true, true, true, true};

		for (int i = 0; i < 50; i++) {
			boolean next = (initial[7] ^ initial[3] ^ initial[2] ^ initial[1] ^ true);

			for (int j = initial.length - 1; j > 0; j--) {
				initial[j] = initial[j - 1];
			}
			initial[0] = next;

			if (next)
				System.out.print(1);
			else
				System.out.print(0);
		}
		System.out.println();
	}		

}

