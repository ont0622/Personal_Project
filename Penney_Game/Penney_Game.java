package Personal_Project.Penney_Game; /**
 * Created by Daniel Oh on 2/21/2017.
 * This is a simulation for Penny's Game
 */

import java.util.*;

public class Penney_Game {
    public static void main(String[] args) {
        int HeadCount = 0;
        int TailCount = 0;
        int p1Wins = 0;
        int p2Wins = 0;
        String sequences = "";

        while (p1Wins < 1000000 && p2Wins < 1000000) {
            int randomNum = 1 + (int)(Math.random() * 2);
            if (randomNum == 1) {
                sequences += "H";
                HeadCount++;
            } else{
                sequences += "T";
                TailCount++;
            }
            if (sequences.contains("HTT")){
                p1Wins++;
                sequences = new String("");
            }
            if (sequences.contains("TTT")){
                p2Wins++;
                sequences = new String("");
            }
        }
        System.out.println("Head: " +HeadCount + " Tail: " + TailCount);
        System.out.printf("Head:Tail ratio: %.5f:1\n",(float) HeadCount/TailCount);
        System.out.println("P1 wins: " + p1Wins + " P2 wins: " + p2Wins);
        System.out.println("P1 winning chance: " + (double) p1Wins /(p1Wins+p2Wins)* 100 + "%") ;
        System.out.printf("P1:P2 Ratio: %.2f:1", (float)p1Wins/p2Wins);
    }
}
