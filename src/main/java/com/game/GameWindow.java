package com.game;

import javax.swing.JPanel;

import com.Entity.Food;
import com.Entity.Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;

public class GameWindow extends JPanel implements Runnable{
    final int tileSize = 20;
    final int scale = 3;
    
    final int stepSize = tileSize * scale; //screen size/player size
    final int columns = 15;
    final int rows = 11;
    final int width = columns * stepSize;
    final int height = rows * stepSize;
    final int fps = 200000000;

    final int mainMenu = 0;
    final int game = 1;
    final int gameOver = 2;

    int[] playerPosition, applePosition;
    
    Snake player;
    Food apple;
    boolean collision;
    int score, gameState;
    KeyHandler input = new KeyHandler();
    Thread gameThread;
    Font pixelFont;

    public GameWindow(){
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(input);
        this.setFocusable(true);

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Arcade.ttf");
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameState = 0;
    }

    public void StartGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        int dir = input.direction;
        if(gameState == mainMenu || gameState == gameOver){
            if(dir == 5){
                resetGame();
                gameState = 1;
            }
        }

        else if(gameState == game){
            if(dir == 0){
                collision = player.setPosition(0, -stepSize);
            }
            else if(dir == 1){
                collision = player.setPosition(0, stepSize);
            }
            else if(dir == 2){
                collision = player.setPosition(-stepSize, 0);
            }
            else if(dir == 3){
                collision = player.setPosition(stepSize, 0);
            }
            

            if(player.getPosition()[0] == applePosition[0] && player.getPosition()[1] == applePosition[1]){
                //Food has been eaten
                score++;
                applePosition = apple.moveToRandPos(rows, columns); 
                player.addTail();
            }
            //Update player position and check if collided with self or with boundary
            if(player.getPosition()[0] < 0 || player.getPosition()[0] >= width || player.getPosition()[1] < 0 || player.getPosition()[1] >= height){
                collision = true;
            }
            //If collision occurred, delete snake, show game over and try again
            if(collision){
                gameState = gameOver;
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

        if(gameState == game){
            drawMainGame(g2);
        }
        else if(gameState == mainMenu){
            drawMainMenu(g2);
        }
        else if(gameState == gameOver){
            drawGameOver(g2);
        }
       
    }

    public void drawMainGame(Graphics2D g2){
        //Draw snake head
        g2.setColor(Color.blue);
        int[] p = player.tailPositions.get(0);
        g2.fillRect(p[0], p[1], stepSize, stepSize);
        //Draw tail
        g2.setColor(Color.CYAN);
        for(int i = 1; i<player.tailPositions.size();i++){
            p = player.tailPositions.get(i);
            g2.fillRect(p[0], p[1], stepSize, stepSize);
        }
        //Draw food
        g2.setColor(Color.red);
        g2.fillRect(applePosition[0], applePosition[1], stepSize, stepSize);
        //Draw score
        g2.setColor(Color.white);
        g2.setFont(pixelFont.deriveFont(60f));
        g2.drawString("Score : " + score, 15, 65);

        g2.dispose();
    }

    public void drawMainMenu(Graphics2D g2){
        //Draw title
        g2.setColor(Color.white);
        g2.setFont(pixelFont.deriveFont(100f));
        g2.drawString("SNAKE", width/2-110, height/2-50);

        //Draw start text
        g2.setColor(Color.white);
        g2.setFont(pixelFont.deriveFont(50f));
        g2.drawString("PRESS ENTER TO START", 200, height/2+100);
    }

    public void drawGameOver(Graphics2D g2){
        this.setBackground(Color.black);
        g2.setColor(Color.white);
        g2.setFont(pixelFont.deriveFont(50f));
        g2.drawString("GAME OVER. FINAL SCORE: " + score, 150, height/2-50);
        g2.drawString("PRESS ENTER TO TRY AGAIN", 150, height/2+100);
    }

    public void resetGame(){
        player = new Snake(width/2-stepSize/2, height/2-stepSize/2, stepSize);
        apple = new Food(stepSize);
        applePosition = apple.moveToRandPos(rows, columns);
        score = 0;
        collision = false;
    }
}
   
