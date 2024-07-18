package com.game;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
         JFrame mainFrame = new JFrame("SNAKE");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        GameWindow g = new GameWindow();
        mainFrame.add(g);
        mainFrame.pack();
        mainFrame.getContentPane().setBackground(Color.black);
 
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        g.StartGameThread();


    }
}