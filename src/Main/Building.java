package Main;

import java.util.*;
import java.awt.*;
import javax.swing.ImageIcon;

/* blocks or obstacles that can be stepped on and collide with character/enemies*/
public class Building extends Core{

    public ArrayList<Building> build = new ArrayList<Building>();

    public Building(){}

    public Building(int x, int y, String img){
        this.x=x;
        this.y=y;
        still = new ImageIcon("C:\\Users\\Nick\\Pictures\\" + img).getImage();
    }
    
    public Building(int x, int y, int wid, int ht, String img){
        this.x=x;
        this.y=y;
        width = wid;
        height = ht;
        still = new ImageIcon("C:\\Users\\Nick\\Pictures\\" + img).getImage();
    }

    public void addBuilding(int x, int y, String img){
        build.add(new Building(x,y,img));
    }
    
    public void removeBuilding(int ind){
        build.remove(ind);
    }
    
    public ArrayList getList(){
        return build;
    }
    
    public void clearBuildings(){
        for(int i = 0; i < build.size(); i++){
            build.remove(i);
        }
    }

    public void drawBuildings(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < build.size(); i++)
            g2d.drawImage(build.get(i).getImage(), build.get(i).getX(), build.get(i).getY(),null);
    }

}
