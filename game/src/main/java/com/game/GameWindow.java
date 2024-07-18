package com.game;

import javax.swing.JPanel;

import com.Entity.Food;
import com.Entity.Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameWindow extends JPanel implements Runnable{
    final int tileSize = 20;
    final int scale = 3;
    
    final int stepSize = tileSize * scale; //screen size/player size
    final int columns = 15;
    final int rows = 11;
    final int width = columns * stepSize;
    final int height = rows * stepSize;
    final int fps = 200000000;

    int[] playerPosition, applePosition;
    
    Snake player;
    Food apple;
    boolean collision, movement;
    int score;
    KeyHandler input = new KeyHandler();
    Thread gameThread;
    Font pixelFont;

    public GameWindow(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);
        pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/fonts/Arcade.ttf"));
        player = new Snake(width/2-stepSize/2, height/2-stepSize/2, stepSize);
        playerPosition = player.getPosition();
        apple = new Food(stepSize);
        applePosition = apple.moveToRandPos(rows, columns);
        movement = true;
        score = 0;
    }

    public void StartGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        int dir = input.direction;
        if(movement){
            if(dir == 0){
                playerPosition[1] -= stepSize;
            }
            else if(dir == 1){
                playerPosition[1] += stepSize;
            }
            else if(dir == 2){
                playerPosition[0] -= stepSize;
            }
            else if(dir == 3){
                playerPosition[0] += stepSize;
            }
        }

        if(playerPosition[0] == applePosition[0] && playerPosition[1] == applePosition[1]){
            //Food has been eaten
            score++;
            applePosition = apple.moveToRandPos(rows, columns); 
            player.addTail();
        }
        //Update player position and check if collided with self or with boundary
        collision = player.setPosition(playerPosition);
        if(playerPosition[0] < 0 || playerPosition[0] > width || playerPosition[1] < 0 || playerPosition[1] > height){
            collision = true;
        }

        //If collision occurred, delete snake, show game over and try again
        if(collision){
            movement = false;
            this.setBackground(Color.WHITE);
            boolean destroyed = player.destroySnake();
            if(destroyed){

            }
        }
    }    

    @Override
    public void run() {
        double nextDrawtime = System.nanoTime() + fps;
        while(gameThread != null){
            update();

            repaint();

            try{
                double remainingTime = nextDrawtime - System.nanoTime();
                remainingTime /= 1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawtime += fps;
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //Draw snake head
        g2.setColor(Color.blue);
        g2.fillRect(playerPosition[0], playerPosition[1], stepSize, stepSize); 
        //Draw tail
        g2.setColor(Color.CYAN);
        for(int i = 0; i<player.tailPositions.size();i++){
            int[] p = player.tailPositions.get(i);
            g2.fillRect(p[0], p[1], stepSize, stepSize);
        }
        //Draw food
        g2.setColor(Color.red);
        g2.fillRect(applePosition[0], applePosition[1], stepSize, stepSize);
        //Draw score
        g2.setColor(Color.white);
        g2.setFont(getFont());
        g2.drawString("Score: " + score, 15, 25);

        g2.dispose();
    }
}
   
