package Main;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Power extends Core {
    
    ArrayList<Power> p = new ArrayList<Power>();
    Character c = new Character();
    
    public Power(){}
    
    public Power(int x, int y){
        this.x = x;
        this.y = y;
        still = new ImageIcon("powerup.png").getImage();
    }
    
    public Power(int x, int y, String img){
        this.x = x;
        this.y = y;
        still = new ImageIcon(img).getImage();
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
    
}
