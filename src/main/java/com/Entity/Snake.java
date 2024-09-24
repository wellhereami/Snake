package com.Entity;

import java.util.ArrayList;

public class Snake {

    public ArrayList<int[]> tailPositions;
    public int[] position, prevPosition; //headPos
    int tail, size;


    public Snake(int x, int y, int s){
        tailPositions = new ArrayList<int[]>();
        position = new int[]{x,y};
        tailPositions.add(new int[]{x,y});
        prevPosition  = new int[2];
        size = s;
        tail = 0;
    }


    public void addTail(){
        tailPositions.add(tailPositions.get(tailPositions.size()-1).clone());
    }

    public boolean setPosition(int x, int y){

        //Create position to check collisions for
        int[] checkPos = tailPositions.get(0).clone();
        checkPos[0] += x;
        checkPos[1] += y;

        //Update tail starting from the end        
        for(int i = tailPositions.size()-1; i > 0; i--){
            int[] pos = tailPositions.get(i-1);
            tailPositions.set(i, pos.clone());
            if(pos[0] == checkPos[0] && pos[1] == checkPos[1]){
                return true;
            }
        }
        
        //Then update head
        tailPositions.get(0)[0] += x;
        tailPositions.get(0)[1] += y;

        return false;
    }

    public boolean destroySnake(){
        if(tailPositions.size() > 0){
            tailPositions.remove(tailPositions.size()-1);
            return true;
        }
        return false;
    }

    public int[] getPosition(){return tailPositions.get(0);}
}
