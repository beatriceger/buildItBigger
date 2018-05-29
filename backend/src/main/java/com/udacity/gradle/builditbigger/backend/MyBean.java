package com.udacity.gradle.builditbigger.backend;

import com.example.beatrice.jokelibrary.JokeMaker;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private JokeMaker myData;

    public MyBean(){
        myData=new JokeMaker();
    }

    public String getData() {
        return myData.getRandomJoke();
    }
}