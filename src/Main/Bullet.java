package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Bullet extends Core{
    
    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    
    public Bullet(){}

    public Bullet(int x, int vx, int y, int vy){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        try {
	    still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+"bulletUp.png"));
	} catch (IOException ex) {
	    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
	}
        visible = true;
    }

    public Bullet(int x, int vx, int y, int vy, String img){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        try {
	    this.still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+img));
	} catch (IOException ex) {
	    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
	}
        visible = true;
    }
    
    public ArrayList getList(){
        return bullets;
    }

    //adds a bullet to array list and puts it on the board where the character is
    public void addBullet(int x, int y){
        bullets.add(new Bullet(x,10,y,10));
    }
    
    //same but you can add velocity to x and/or y
    public void addBullet(int x, int vx, int y, int vy){
        bullets.add(new Bullet(x,vx,y,vy));
    }
    
    //use this for diff looking bullets
    public void addBullet(int x, int vx, int y, int vy, String img){
        bullets.add(new Bullet(x,vx,y,vy,img));
    }
    
    public void addPowerBullets(int x, int vx, int y, int vy, int amount, String img){
        for(int i = 0; i < amount; i++){
            bullets.add(new Bullet(x,vx,y,vy,img));
        }
    }
    
    public void removeBullet(int ind){
        try{
            bullets.remove(ind);
            bullets.trimToSize();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void clearBullets(){
        bullets.clear();
    }
    
    public void shoot(){
        for(int w = 0; w < bullets.size(); w++){
            Bullet m = (Bullet) bullets.get(w);
            if(m.getVisible() == true){
                m.y += m.vy;
                if(m.y < 0 || m.y > Frame.HEIGHT)
                    m.visible = false;                
            }else
                bullets.remove(w);
        }
    }

    /*
    //code that moves the bullet and turns it invisible once it hits wall
    public void shoot(){
        for(int w = 0; w < bullets.size(); w++){
            Bullet m = (Bullet) bullets.get(w);
            if(m.getVisible() == true){
                if(c.facingLeft){
                    m.x += m.vx;
                    if(m.x < 0)
                        m.visible = false;
                } else {
                    m.x += m.vx;
                    if(m.x > Frame.WIDTH)
                        m.visible = false;
                }
            }else
                bullets.remove(w);
        }
    }
     * 
     */
}