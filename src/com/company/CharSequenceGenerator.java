package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class CharSequenceGenerator {

    static LinkedList<Character> prevMet = new LinkedList<Character>();

    public static void main(String[] args) {
        int intervalToHaveNoRepeatingCharactersIn = 5;
        for (int i = 0; i < intervalToHaveNoRepeatingCharactersIn; i++) {
            prevMet.add(' ');
        }
        for (int i =0; i < 750; i++ ) {
            System.out.print(rndChar());
        }
    }

    private static char rndChar () {
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ        \n".toLowerCase();
        char generated= ';';
        do {
            generated = abc.charAt(new Random().nextInt(abc.length()));
        }
        while (new HashSet<Character>(prevMet).contains(generated));
        prevMet.removeFirst();
        prevMet.add(generated);
        return generated;
    }
}
