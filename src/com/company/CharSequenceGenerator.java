package com.company;

import java.util.*;

public class CharSequenceGenerator {

    private LinkedList<Character> allowed = new LinkedList<>();
    private LinkedList<Character> met = new LinkedList<>();
    private final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
    private final Random rnd = new Random();
    private final int nonRepeatDist = 5;


    public void generateList() {
        for(int i = 0; i < abc.length(); i++) {
            char c = abc.charAt(i);
            allowed.add(c);
        }
        boolean justPrintedSpace = true;
        for (int i =0; i < 750; i++ ) {

            if(rnd.nextInt(8) == 0 && !justPrintedSpace) {
                if(rnd.nextInt(8) == 0) {
                    System.out.print('\n');
                }
                else {
                    System.out.print(' ');
                }
                justPrintedSpace = true;
            }
            else {
                justPrintedSpace = false;
                System.out.print(rndChar());
            }
        }
    }

    private char rndChar () {
        int idxOfRandChar = rnd.nextInt(allowed.size());
        char generatedChar = allowed.remove(idxOfRandChar);
        met.add(generatedChar);
        if(met.size() >= nonRepeatDist) {
            char newlyAvailableChar = met.removeFirst();
            allowed.add(newlyAvailableChar);
        }
        return generatedChar;
    }

    public static void main(String[] args) {
        new CharSequenceGenerator().generateList();
    }
}
