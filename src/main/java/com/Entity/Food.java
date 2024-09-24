package com.Entity;

import java.util.Random;

public class Food {
    Random rand;
    int size;

    public Food(int s){
        size = s;
        rand = new Random();
    }

    public int[] moveToRandPos(int rows, int cols){
        int[] pos = new int[2];

        pos[0] = rand.nextInt(cols) * size;
        pos[1] = rand.nextInt(rows) * size;

        return pos;
    }
}
