package Main;

import java.awt.*;
import java.util.*;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
public class Menu extends Background {
    
    ArrayList<Menu> items = new ArrayList<Menu>();
    
    public Menu(){}
    
    public Menu(int x, int y, String s){
        this.x = x;
        this.y = y;
    }
    
    public void drawText(String s1, String s2){
        Graphics2D g2d;
        
    }
    
    public void coverScreen(Color c, Graphics2D g2d){
        g2d.setColor(c);
        g2d.drawRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
        g2d.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
    }
    
    public void gameOver(Graphics2D g2d){
        coverScreen(Color.RED, g2d);
        g2d.setColor(Color.ORANGE);
        g2d.drawString("GAME OVER", Frame.WIDTH/2, Frame.HEIGHT/2);
        g2d.drawString("Continue?(Y/N)", (Frame.WIDTH/2), 
                (Frame.HEIGHT/2) + 100);
    }
    
    public void startMenu(Graphics2D g2d){
        coverScreen(Color.DARK_GRAY, g2d);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Alien Genocide in Space!", 300, (Frame.HEIGHT/2) - 200);
        g2d.drawString("Press Enter to Start", 350, (Frame.HEIGHT/2) + 100);
        g2d.drawString("Arrow Keys Move Character", 275, (Frame.HEIGHT/2) + 200);
        g2d.drawString("Shift/Space Shoot", 350, (Frame.HEIGHT/2) + 300);
        g2d.drawString("ENTER Pause", 400, (Frame.HEIGHT/2) + 400);
        
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
    
    }
    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
    }
    
    public void upgradeMenu(Graphics2D g2d){
    }
    
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}
