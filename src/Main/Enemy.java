package Main;

import javax.swing.ImageIcon;
import java.util.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Enemy extends Core{
    
    private Bullet bull = new Bullet();
    private Character c = new Character();
        
    ArrayList<Bullet> b = new ArrayList<Bullet>();
    ArrayList<Enemy> e = new ArrayList<Enemy>();
    int start = 0;
    
    public Enemy(){}

    public Enemy(int x, int y){
        this.x=x;
        this.y=y;
	try {
	    still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+"ghost.png"));
	} catch (IOException ex) {
	    Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
	}
        visible = true;
    }

    public Enemy(int x, int y, String img){
        this.x=x;
        this.y=y;
        try {
	    still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+"ghost.png"));
	} catch (IOException ex) {
	    Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
	}
        visible = true;
    }
    
    public ArrayList getList(){
        return e;
    }

    //adds enemies to game thru arraylist
    public void addEnemy(int x, int y, String img){
        e.add(new Enemy(x,y,img));
    }
    
    //removes enemies @param index number of the enemy
    public void removeEnemy(int index){
        try{
            e.remove(index);
            e.trimToSize();
        }catch(Exception e){
            System.err.println(e);
        }
    }

    //useful for starting games over
    public void clearEnemies(){
        e.clear();
    }
    
    //basic movement that moves enemies forward and they fire sometimes
    public void move(int vy){
        for(int i = 0; i < e.size(); i++){
            e.get(i).y += vy;
            e.get(i).height = e.get(i).y + e.get(i).getHeight();
            e.get(i).centerX = e.get(i).x + e.get(i).getWidth()/2;
            e.get(i).centerY = e.get(i).y + e.get(i).getHeight()/2;
            fire(500);
            if(e.get(i).y > Frame.HEIGHT){
                c.takeFromScore(-15);
                removeEnemy(i);
                System.out.println("Removed!");
            }
        }
    }
    
    public void laneMovement(int vy, int size){
        int[] lanesX = generateLaneX(size);
        int[] lanesY = generateLaneY(size);
        for(int i = 0; i < e.size(); i++){
            for(int j = 0; j < lanesY.length; j++){
                if(e.get(i).y > lanesY[j]){
                    vx = 3;
                    vy = 0;
                }else if(e.get(i).x < lanesX[j]){
                    vx = 0;
                    vy = 3;
                }
            }
            e.get(i).x += vx;
            e.get(i).y += vy;
            e.get(i).height = e.get(i).y + e.get(i).getHeight();
            e.get(i).centerX = e.get(i).x + e.get(i).getWidth()/2;
            e.get(i).centerY = e.get(i).y + e.get(i).getHeight()/2;
            fire(500);
            if(e.get(i).y > Frame.HEIGHT){
                removeEnemy(i);
                c.takeFromScore(-15);
                System.out.println("Removed!");
            }
        }
    }
    
    int bullStart = 0;
    //allows enemies to shoot at player
    public void fire(int update){
        for(int i = 0; i < e.size(); i++){
            if(e.get(i).y > 0 && bullStart > update){
               bull.addBullet(e.get(i).centerX, 0, e.get(i).height, 5, "bulletDown.png");
               playSound("enemylaser.wav");
               bullStart = 0;
            }
        }
        bullStart++;
    }
    
    //randomly spawns enemies however enemies can spawn on top of each other
    //i may work on putting enemies into "lanes" to avoid them piling
    public void spawnRandomEnemies(int update){
        Random rand = new Random();
        int x = rand.nextInt(Frame.HEIGHT) + 0;
        int y = rand.nextInt(Frame.WIDTH) + 300;
        if(e.isEmpty())
            addEnemy(x, -y, "ghost.png");
        for(int i = 0; i < e.size(); i++){
            Rectangle en = e.get(i).getBounds();
            if(en.contains(x, y)){
                x = rand.nextInt(Frame.HEIGHT + e.get(i).getHeight()) + 0;
                y = rand.nextInt(Frame.WIDTH) + 300;
            }else if(!en.contains(x, y) && start > update){
                System.out.println("Spawn!");
                addEnemy(x,-y,"ghost.png");
                start = 0;
            }
        }
        start++;
    }
    
    public void spawnLanedEnemies(int update, int size){
        int[] lanes = generateLaneX(size);
        Random rand = new Random();
        int x = rand.nextInt(lanes.length) + 0;
        int y = rand.nextInt(Frame.WIDTH) + 300;
        int temp = lanes[x];
        if(e.isEmpty())
            addEnemy(temp, -y, "ghost.png");
        for(int i = 0; i < e.size(); i++){
            for(int j = 0; j < e.size(); j++){
                Rectangle en = e.get(i).getBounds();
                Rectangle en1 = e.get(j).getBounds();
                if(en.contains(x, y) || en1.contains(en)){
                    x = rand.nextInt(lanes.length) + 0;
                    y = rand.nextInt(Frame.WIDTH) + 300;
                    temp = lanes[x];
                }if(!en.contains(x, y) && start > update){
                    System.out.println("Spawn!");
                    addEnemy(temp, -y, "ghost.png");
                    start = 0;
                }
            }
        }
        start++;
    }
    
    //creates different lanes for enemies to spawn in
    public int[] generateLaneX(int size){
        int num = Frame.WIDTH / size;
        int temp = 0;
        int[] lanes = new int[size];
        for(int i = 0; i < size; i++){
            lanes[i] = temp;
            temp += num;
        }
        return lanes;
    }
    
    public int[] generateLaneY(int size){
        int num = Frame.WIDTH / size;
        int temp = 0;
        int[] lanes = new int[size];
        for(int i = 0; i < size; i++){
            lanes[i] = temp;
            temp += num;
        }
        return lanes;
    }

    /*
    public void move(){
        boolean moveLeft = false;
        vx = 3;
        for(int i = 0; i < e.size(); i++){
            if(e.get(i).x >=0 && moveLeft == true){
                e.get(i).x += vx;
                moveLeft = false;
            }else if(e.get(i).x <= Frame.WIDTH && moveLeft == false){
                e.get(i).x -= vx;
                moveLeft = true;
            }
        }

    }
     * 
     */

}
