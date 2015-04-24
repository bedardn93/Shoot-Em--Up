package Main;

import javax.swing.*;

public class Frame {
    
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    
    public Frame(){
        JFrame frame = new JFrame();
        frame.add(new Board());
        frame.setTitle("2D Test Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
    
    
    
    public static void main(String[] args) {
        Frame f = new Frame();
    }
}