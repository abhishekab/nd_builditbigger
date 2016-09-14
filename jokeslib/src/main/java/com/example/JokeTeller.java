package com.example;

import java.util.Random;

public class JokeTeller {
    private final String[] JOKES= {"This is the funniest joke",
            "This is the best joke" ,
            "This is a great joke",
            "This is a really funny joke",
            "This is a poor joke"};
    private final int NO_OF_JOKES=JOKES.length;

    public String getJoke(){
        int randomInt= new Random().nextInt(NO_OF_JOKES);
        return JOKES[randomInt];

    }


}
