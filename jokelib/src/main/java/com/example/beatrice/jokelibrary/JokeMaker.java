package com.example.beatrice.jokelibrary;

import java.util.Random;

public class JokeMaker {

    private String[] jokes;
    private Random random;

    public JokeMaker() {
        jokes = new String[3];
        jokes[0] = "What is the longest word in the English language? \n" +
                "SMILES: there is a mile between the first and last letters!\" ";
        jokes[1] = "A: Aren't you wearing your wedding ring on the wrong finger?\n" +
                "B: Yes I am, I married the wrong woman. ";
        jokes[2] = "Q: What travels around the world and stays in a corner? \n" +
                "A: A stamp";
        random = new Random();
    }

    public String[] getJokes() {
        return jokes;
    }

    public String getRandomJoke() {
        return jokes[random.nextInt(jokes.length)];
    }
}
