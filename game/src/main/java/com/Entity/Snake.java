package com.Entity;

import java.util.ArrayList;

public class Snake {

    public ArrayList<Integer> directions;
    public ArrayList<int[]> tailPositions;
    public int[] position, prevPosition; //headPos
    int tail, size;


    public Snake(int x, int y, int s){
        directions = new ArrayList<Integer>();
        tailPositions = new ArrayList<int[]>();
        position = new int[]{x,y};
        prevPosition  = new int[2];
        size = s;
        tail = 0;
    }

    public void addTail(){
        tailPositions.add(prevPosition);
    }

    public boolean setPosition(int[] p){

        prevPosition = position.clone();
        position = p.clone();

        for(int i = tailPositions.size()-1; i > 0; i--){
            int[] pos = tailPositions.get(i-1);
            tailPositions.set(i, pos);
            if(pos[0] == position[0] && pos[1] == position[1]){
                return true;
            }
        }
        
        if(tailPositions.size()>0){
            tailPositions.set(0, prevPosition);
            if(prevPosition[0] == position[0] && prevPosition[1] == position[1]){
                return true;
            }
        }

        return false;
    }

    public boolean destroySnake(){
        if(tailPositions.size() > 0){
            tailPositions.remove(tailPositions.size()-1);
            return true;
        }
        return false;
    }

    public int[] getPosition(){return position;}
}
