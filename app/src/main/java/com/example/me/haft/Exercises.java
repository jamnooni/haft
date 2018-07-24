package com.example.me.haft;

import java.util.ArrayList;

public class Exercises extends ArrayList<Workout>{
    public Exercises() {
/*        add(
                new Workout(0,"full body",0,0,"blah")
        );
        add(
                new Workout(1,"cardio",0,0,"blah blah")
        );
        add(
                new Workout(2,"upper body",0,0,"sdsf")
        );
        add(
                new Workout(3,"csdfs",0,0,"sdfs")
        );*/
    }

    public String getNames(){
        String names="";
        for (int i=0; i<=this.size();i++){
            names+="     "+this.get(i).getName();
        }
        return names;
    }

}
