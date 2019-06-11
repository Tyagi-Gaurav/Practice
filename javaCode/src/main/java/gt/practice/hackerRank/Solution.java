package gt.practice.hackerRank;

import java.io.*;
import java.util.*;
public class Solution {
    public static void main(String args[] ) throws Exception {

        //Write code here
        Scanner scanner = new Scanner(System.in);
        int numberOfTests = scanner.nextInt();

        for (int i = 0; i < numberOfTests; i++) {
            int numberOfPlayers = scanner.nextInt();
            int vill[] = new int[numberOfPlayers];
            int play[] = new int[numberOfPlayers];

            read(scanner, numberOfPlayers, vill);
            read(scanner, numberOfPlayers, play);

            Arrays.sort(vill);
            Arrays.sort(play);

            int j = 0;
            while (j < numberOfPlayers) {
                if (vill[j] > play[j]) {
                    System.out.println("LOSE");
                    break;
                }

                j++;
            }

            if (j == numberOfPlayers) {
                System.out.println("WIN");
            }
        }
    }

    private static void read(Scanner scanner, int numberOfPlayers, int[] vill) {
        for (int j = 0; j < numberOfPlayers; j++) {
            vill[j] = scanner.nextInt();
        }
    }
}





