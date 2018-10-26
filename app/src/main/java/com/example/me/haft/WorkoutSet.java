package com.example.me.haft;

import java.util.ArrayList;

public class WorkoutSet extends ArrayList<Workout>{
    public WorkoutSet() {
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

    public String[] getNames(){
        int s=this.size();
        String[] names=new String[s];
        for (int i=0; i<s;i++){
            names[i]=this.get(i).getName();
        }
        return names;
    }

    public int[] getIconIds(){
        int s=this.size();
        int[] iconIds=new int[s];
        for (int i=0; i<s;i++){
            iconIds[i]=this.get(i).getIconId();
        }
        return iconIds;
    }

}
