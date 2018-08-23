package com.example.jokelibrary;

import java.util.Random;

public class MainClass {

    public static String[] jokes = {
            "What does a cat have that no other animal has? Kittens.",
            "Can a kangaroo jump higher than a house? Of course, a house doesn’t jump at all.",
            "Anton, do you think I’m a bad mother? My name is Paul.",
            "My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.",
            "What is the difference between a snowman and a snowwoman? Snowballs."
    };

    public static String getJoke(){
        int index = new Random().nextInt(jokes.length);
        return jokes[index];
    }

}
