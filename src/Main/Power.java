package Main;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Power extends Core {
    
    ArrayList<Power> p = new ArrayList<Power>();
    Character c = new Character();
    int start = 0;
    
    public Power(){}
    
    public Power(int x, int y){
        this.x = x;
        this.y = y;
        try {
	    still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+"powerup.png"));
	} catch (IOException ex) {
	    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public Power(int x, int y, String img){
        this.x = x;
        this.y = y;
        try {
	    still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+img));
	} catch (IOException ex) {
	    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public ArrayList getList(){
        return p;
    }
    public void move(int vy){
        for(int i = 0; i < p.size(); i++){
            p.get(i).y += vy;
            p.get(i).height = p.get(i).y + p.get(i).getHeight();
            p.get(i).centerX = p.get(i).x + p.get(i).getWidth()/2;
            p.get(i).centerY = p.get(i).y + p.get(i).getHeight()/2;
            if(p.get(i).y > Frame.HEIGHT){
                c.takeFromScore(-15);
                System.out.println("Removed!");
            }
        }
    }
    
    public void addPower(int x, int y, String img){
        p.add(new Power(x,y,img));
    }
    
    public void removePowerup(int index){
        try{
            p.remove(index);
            p.trimToSize();
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    public void spawnLanedPowerups(int update, int size){
        int[] lanes = generateLaneX(size);
        Random rand = new Random();
        int x = rand.nextInt(lanes.length) + 0;
        int y = rand.nextInt(Frame.WIDTH) + 300;
        int temp = lanes[x];
        if(p.isEmpty())
            addPower(temp, -y, "powerup.png");
        for(int i = 0; i < p.size(); i++){
            for(int j = 0; j < p.size(); j++){
                Rectangle en = p.get(i).getBounds();
                Rectangle en1 = p.get(j).getBounds();
                if(en.contains(x, y) || en1.contains(en)){
                    x = rand.nextInt(lanes.length) + 0;
                    y = rand.nextInt(Frame.WIDTH) + 300;
                    temp = lanes[x];
                }if(!en.contains(x, y) && start > update){
                    System.out.println("Spawn!");
                    addPower(temp, -y, "powerup.png");
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
    
}
