package com.company;

import java.util.Random;

public class WordSequenceGenerator {
    private final Random rnd = new Random();
    private final String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();

    public void generateWordSequence() {
        int lineCount = rnd.nextInt(5)+10;
        final StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < lineCount; i++) {
            sequence.append(generateLine());
            sequence.append('\n');
        }
        sequence.deleteCharAt(sequence.length()-1);
        System.out.println(sequence);;
    }

    public String generateLine(){
        int wordCount = rnd.nextInt(4)+10;
        final StringBuilder line = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            line.append(generateWord());
            line.append(' ');
        }
        line.deleteCharAt(line.length()-1);
        return line.toString();
    }

    public String generateWord(){
        int wordLength = rnd.nextInt(7)+2;
        final StringBuilder word = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            word.append(rndChar());
        }
        return word.toString();
    }

    public char rndChar(){
        return abc.charAt(rnd.nextInt(abc.length()));
    }

    public static void main(String[] args) {
        new WordSequenceGenerator().generateWordSequence();
    }
}
