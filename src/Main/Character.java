package Main;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Character extends Core{
    
    //private int jumpRange = y - 300;
    public final int maxHealth = 5;
    public int score = 0;
    public int highScore = 0;
    public static boolean facingLeft;
    public static boolean facingDown;
    //public static boolean onGround = false;
    
    //public static boolean jumping = false;
    boolean movingLeft = false;
    boolean movingRight = false;
    boolean movingUp = false;
    boolean movingDown = false;
    
    
    private int spawnX = Frame.WIDTH/2;
    private int spawnY = Frame.HEIGHT/2;
    
    private Bullet bull = new Bullet();

    public Character(){}

    public Character(int hlth,int x, int y, String img){
        health = hlth;
        this.x = x;
        this.y=y;
        still = new ImageIcon(img).getImage();
        visible = true;
    }
    
    public void reset(){
        x = spawnX;
        y = spawnY;
        health = maxHealth;
        score = 0;
    }
    
    public void move(){
        x += vx;
        y += vy;
        width = x + getWidth();
        height = y + getHeight();
        centerX = x + getWidth()/2;
        centerY = y + getHeight()/2;
    }

    public void fire(){
        bull.addBullet(centerX, 0, y - 100, -10, "bulletUp.png");
        playSound("laser.wav");
    }
    
    public void boundaries(){
        if(width >= Frame.WIDTH && facingLeft == false)
            setVX(0);
        if(x <= 0 && facingLeft == true)
            setVX(0);
        if(height >= Frame.HEIGHT && facingDown == true)
            setVY(0);
        if(y <= 0 && facingDown == false)
            setVY(0);
    }
    
    public int getScore(){
        return score;
    }
    
    public void setHighScore(int num){
        if(num > highScore)
            highScore = num;
    }
    
    public void addToScore(int num){
        score += num;
    }
    
    public void takeFromScore(int num){
        score -= num;
    }
    
    //CODE BELOW ORIGINALLY PART OF PLATFORMER GAME
    
    /*
    public void fire(){
        int side;
        if(facingLeft){
            side = x;      //x,vx,y,vy,img
            bull.addBullet(side, -10, center,0,"bulletLeft.png");
        }else{
            side = width;
            bull.addBullet(side, 10, center, 0,"bullet.png");
        }
        
    }
    
    public boolean hitTop(){
        if(onGround){
            return false;
        }else{
            if(getY() <= jumpRange)
                return true;
            return false;
        }
        
    }
    
    public void jumping(){
        if(hitTop()==false && onGround ==true){
            jumpRange = y - 300;
            jumping = true;
            setVelocityY(-5);
        }else if(hitTop()==true){
            jumping = false;
            falling();
        }
    }
    
    public void falling(){
        if(jumping == false){
            if(onGround == false)
                setVelocityY(5);
            else
                setVelocityY(0);
        }
    }
     *
     */
    
    public void characterMovement(){
        if(movingLeft && movingRight)
            setVX(0);
        else if(movingLeft)
            setVX(-5);
        else if(movingRight)
            setVX(5);
        else
            setVX(0);
        
        if(movingUp && movingDown)
            setVY(0);
        else if(movingUp)
            setVY(-5);
        else if(movingDown)
            setVY(5);
        else
            setVY(0);
    }
    
    public void debugCharacter(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g2d.setColor(Color.RED);
        g2d.drawString("Score: " + score, 0, 150);
        g2d.drawString("High Score: " + highScore, 0, 100);
        g2d.drawString("Health: " + health, 0, 200);
        
        //g2d.drawString("X: " + x + " Y: " + y, x, y);
        //g2d.drawString("Width: " + width + " Height: " + height, x, y + 100);
    }

    boolean shooting = false;

    //controls and stuff
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        
        switch(key){
            case KeyEvent.VK_LEFT:
                //vx = (x >= 0) ? Actor.LEFT : 0;
                facingLeft = true;
                movingLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                //vx = (width <= Frame.WIDTH) ? Actor.RIGHT : 0;
                facingLeft = false;
                movingRight = true;
                break;
            case KeyEvent.VK_UP:
                //vy = (y >= 0) ? Actor.UP : 0;
                facingDown = false;
                movingUp = true;
                break;
            case KeyEvent.VK_DOWN:
                //vy = (height <= Frame.HEIGHT) ? Actor.DOWN : 0;
                facingDown = true;
                movingDown = true;
                break;
            case KeyEvent.VK_SHIFT:
                if(shooting == false){
                    fire();
                    shooting = true;
                    
                }
                break;
            case KeyEvent.VK_SPACE:
                if(shooting == false){
                    fire();
                    shooting = true;
                }
                //jumping();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
        }
    }
    
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        
        switch(key){
            case KeyEvent.VK_LEFT:
                movingLeft = false;
            case KeyEvent.VK_RIGHT:
                movingRight = false;
                //setVelocityX(0);
                break;
            case KeyEvent.VK_UP:
                movingUp = false;
            case KeyEvent.VK_DOWN:
                movingDown = false;
                //setVelocityY(0);
                break;
            case KeyEvent.VK_SHIFT:
                shooting = false;
                break;
            case KeyEvent.VK_SPACE:
                shooting = false;
                //jumping = false;
                break;
        }
    }
    public void keyTyped(KeyEvent e){}

}

