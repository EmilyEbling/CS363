//Emily Ebling 
//CS 363

import java.util.Random;

public class RainbowAttack {

	public static void main(String[] args) {
		
		int RainbowTable [][] = new int [50][2];
		createTable(RainbowTable);
		
		int successCount = attack(RainbowTable);
		
		double successRate = successCount/10000.0;
		System.out.println("Successful Attemps: " + successCount);
		System.out.println("Success Rate: " + successRate + " (" + successRate*100 + "%)");
		
	}	
	
	private static int attack (int[][] RainbowTable) { //performs attack on previously generated rainbow table
		int successCount = 0; //keeps track of how many successes there are
		int hash;
		int count = 0;
		int temp = 0;
		boolean flag  = false; 

		for (int i = 0; i < 10000; i++) { //for all pin options
			hash = hash(i); 
			while (count < 100 && !flag ) { //chain 100 times
				for (int j = 0; j < 50; j++) {
					if (hash == RainbowTable[j][1]) { //if the hash is in rainbow table
						int index = RainbowTable[j][0];

						while (hash != index && temp < 100 && !flag ) { //finds original pin
							if (index == i) {
								successCount++;
								flag = true;
							}
							index = hash(index);
							temp++;
						}
						temp = 0;		
					}	
					if(flag)
						break;
				}

				if (!flag) { //if the hash was not in the rainbow table 
					hash = hash(hash);
					count++;
				}
				else 
					break;
			}
			count = 0;
			flag = false;
		}
		
		return successCount;
		
	}

	private static void createTable(int[][] RainbowTable) { //creates the rainbow table 
		int[] pins = new int[50];
		int hash;

		for (int i = 0; i < pins.length; i++)
			pins[i] = (int) (Math.random() * 9999); //generates 50 random numbers 

		for (int i = 0; i < 50; i++) { //only stores first and last values
			hash = pins[i];
			for (int j = 0; j < 100; j++) {
				if (j == 0)
					RainbowTable[i][0] = hash;
				else
					hash = hash(hash);
			}		
			RainbowTable[i][1] = hash;
		}
	}
	
	private static int hash(int pin) { //hash function

		return (int) Math.floor((((pin + 25 % 10000) * (pin + 25 % 10000))/100) % 10000);

	}

	private static int reduce(int hash) { //reduce function

		return hash;

	}

}
