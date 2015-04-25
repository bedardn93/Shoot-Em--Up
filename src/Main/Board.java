package Main;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener{
    
    private Character c = new Character();
    private Enemy en = new Enemy();
    private Building build = new Building();
    private Bullet bullet = new Bullet();
    private Background bg = new Background();
    private Save s = new Save();
    private Menu m = new Menu();
    private Power pow = new Power();
    
    private Timer time;
    public static boolean pause = true;
    public static boolean gameOver = false;
    
    //place all your crap on the board here
    public Board(){
        c = new Character(c.maxHealth, Frame.HEIGHT/2, Frame.WIDTH/2,"spaceship.png");
        c.addSound("laser.wav");
        en.addSound("enemylaser.wav");
        //c.playSong("background.wav");
        bg.generateBackgroundY(0, "Space_Deep.jpg");
        addKeyListener(new AL());
        setFocusable(true);
        //adjust number in timer if speeds are too fast or slow
        time = new Timer(10, this);
        time.start();
    }

    //bascially updates the stuff happening on the screen(ie collisions, movements, etc.)
    //update speed may depend on speed of computer
    public void actionPerformed(ActionEvent e){
        if(pause == false){
            checkCollision();
            bullet.shoot();
            c.move();
            c.characterMovement();
            bg.scroll();
            en.spawnLanedEnemies(50, 6);
            en.move(2);
            pow.spawnLanedPowerups(800, 6);
            pow.move(1);
            repaint();
            gameOver();
        }
    }
    
    public void gameOver(){
        if(c.isAlive() == false){
            pause = true;
            gameOver = true;
            c.playSound("gameover.wav");
        }
    }

    //checks for collision using rectangles
    //pros: relatively easy to calculate collision without too much hassle in implementing
    //cons: not accurate; can detect collision when objects are clearly not touching 
    //especially rounded objects: isn't pixel accurate
    //IMPORTANT DONT FORGET THIS!!!
    public void checkEnemyCollision(){
        for(int i = 0; i < en.e.size(); i++){
             Rectangle enemy = en.e.get(i).getBounds();
             Rectangle character = c.getBounds();
             if(enemy.intersects(character)){
                 System.out.println("Player crashing!");
                 c.damage(1);
                 en.removeEnemy(i);
             }
        }
    }
    
    public void checkPowerupCollision(){
        for(int i = 0; i < pow.getList().size(); i++){
            Rectangle powerup = pow.p.get(i).getBounds();
            Rectangle character = c.getBounds();
            if(powerup.intersects(character)){
                System.out.println("Got Powerup!");
                c.damage(-1);
                pow.removePowerup(i);
            }
        }
    }
    
    /*
    //gravity and colliding against walls
    public void checkBuildingCollision(){
        b: for(int i = 0; i < build.build.size(); i++){
            Rectangle building = build.build.get(i).getBounds();
            Rectangle character = c.getBounds();
            if(!building.intersects(character)){
                c.onGround = false;
                c.falling();
            }else{
                c.setVelocityY(0);
                c.onGround = true;
                break b;
            }
        }
    }
     * 
     */
    
    //calls all collision code cause i'm too lazy to put it in one at a time and
    //think this is somehow better even though it's probably more lines
    public void checkCollision(){
        checkBulletCollision();
        //checkCharBulletCollision();
        c.boundaries();
        //checkBulletMiss();
        checkEnemyCollision();
        //checkBuildingCollision();
        checkPowerupCollision();
    }

    //check if bullets hit an enemy
    //need to make adjustments to this since the bullets array is used in this
    //collision detection as well as the player collision so make the bullet arrays
    //seperate or you'll likely to get an out of bounds exception during play
    public void checkBulletCollision(){
        for(int i = 0; i < en.e.size(); i++){
            for(int j = 0; j < Bullet.bullets.size(); j++){
                Rectangle enemy = en.e.get(i).getBounds();
                Rectangle bull = Bullet.bullets.get(j).getBounds();
                Rectangle character = c.getBounds();
                if(bull.intersects(enemy)){
                    en.removeEnemy(i);
                    bullet.removeBullet(j);
                    System.out.println("bull: " + j);
                    c.addToScore(50);
                    System.out.println("Enemy Hit!");
                }else if(bull.intersects(character)){
                    c.damage(1);
                    bullet.removeBullet(j);
                    System.out.println("Player Hit!");
                }else if(enemy.intersects(character)){
                    c.damage(1);
                    en.removeEnemy(i);
                }
            }
        }
    }
    
    /*
    //check if bullets hit an enemy
    public void checkCharBulletCollision(){
        for(int i = 0; i < Bullet.bullets.size(); i++){
            Rectangle character = c.getBounds();
            Rectangle bull = Bullet.bullets.get(i).getBounds();
            if(bull.intersects(character)){
                c.damage(1);
                bullet.removeBullet(i);
                System.out.println("Player Hit!");
            }
        }
    }
    * 
    */
    
    
    /*
    //check if bullet hits a wall or something that's not an enemey
    public void checkBulletMiss(){
        for(int i = 0; i < build.build.size(); i++){
            for(int j = 0; j < bullet.bullets.size(); j++){
                Rectangle block = build.build.get(i).getBounds();
                Rectangle bull = bullet.bullets.get(j).getBounds();
                if(bull.intersects(block)){
                    bullet.removeBullet(j);
                    System.out.println("miss");
                }
            }
        }
    }
*/
    public void paint(Graphics g){
        //super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        //draw all the crap
        bg.drawList(g2d, bg.getList());
        en.drawList(g2d, en.getList());
        c.drawObject(g2d, c);
        c.debugCharacter(g2d);
        bullet.drawList(g2d, bullet.getList());
        pow.drawList(g2d, pow.getList());
        
        
        if(pause == true && gameOver == false)
            m.startMenu(g2d);
        if(gameOver)
            m.gameOver(g2d);
    }
    
    public void wipeBoard(){
        c.setHighScore(c.getScore());
        s.writeFile();
        c.reset();
        bullet.clearBullets();
        en.clearEnemies();
        pause = false;
        gameOver = false;
    }

    private class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(pause && key == KeyEvent.VK_ENTER)
                pause = false;
            else if(pause == false && key == KeyEvent.VK_ENTER)
                pause = true;
            if(gameOver && key == KeyEvent.VK_Y){
                wipeBoard();
            }else if(gameOver && key == KeyEvent.VK_N)
                System.exit(0);
            if(pause == false)
                c.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            c.keyReleased(e);
        }
        public void keyTyped(KeyEvent e){}
    }
    
}
