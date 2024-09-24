package com.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    int direction;

    @Override
    public void keyPressed(KeyEvent e) {
       int kc = e.getKeyCode();

       if(kc == KeyEvent.VK_W || kc == KeyEvent.VK_UP){
        //up
        direction = 0;
       }
       else if(kc == KeyEvent.VK_S || kc == KeyEvent.VK_DOWN){
        //down
        direction = 1;
       }

       if(kc == KeyEvent.VK_A || kc == KeyEvent.VK_LEFT){
        //left
        direction = 2;
       }
       else if(kc == KeyEvent.VK_D || kc == KeyEvent.VK_RIGHT){
        //right
        direction = 3;
       }

       if(kc == KeyEvent.VK_ENTER){
        direction = 5; 
       }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
}
