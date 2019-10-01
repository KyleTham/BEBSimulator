package com.BEB;

import java.util.Random;

public class Node {
    private int timeSlotsToSkip;
    protected int id;
    public Node(int id){
        this.id = id;
        this.timeSlotsToSkip = 0;
    }

    boolean sendFrame(double probability){
        if(this.timeSlotsToSkip == 0){
            double rand = Math.random();
            if(probability > rand){
                return true;
            }
            else{
                return false;
            }
        }
        this.timeSlotsToSkip--;
        if(this.timeSlotsToSkip == 0){
            return true;
        }
        else{
            return false;
        }
    }

    void collision(int frameTimes){
        int rand = new Random().nextInt(frameTimes);
        this.timeSlotsToSkip = rand;
    }
}
