package Main;

import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Background extends Core{
    
    public ArrayList<Background> b = new ArrayList<Background>();
    
    private int bgLength = 0;
    
    public Background(){}
    
    public Background(int x, int y, String img) {
        this.x =x;
        this.y=y;
	try {
	    still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+img));
	} catch (IOException ex) {
	    Logger.getLogger(Background.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    //scrolls the background
    //starts by constantly moving the array of image down
    //if the y coord of the image gets below the height of the frame
    //then set the 
    public void scroll(){
        vy = 3;
        for(int i = 0; i < b.size(); i++){
            b.get(i).y += vy;
            if(b.get(i).y >= Frame.HEIGHT){
                System.out.println("bg:" + bgLength);
                b.get(i).setPosition(b.get(i).x, bgLength);
            }
        }
    }
    
    public void addBackground(int x, int y, String img){
	b.add(new Background(x,y,img));
    }
    
    public ArrayList getList(){
        return b;
    }
    
    public int getBgLength(){
        return bgLength;
    }
    
    //generates background
    //enter in amount of times you want background copied
    public void generateBackgroundX(int amount, int x, int y, String img){
        int tempWidth = x;
        for(int i = 0; i < amount; i++){
            addBackground(tempWidth,Frame.HEIGHT - getHeight(),img);
            tempWidth += b.get(i).getWidth();
            bgLength += tempWidth;
        }
    }
    /*
    public void generateBackgroundY(int amount, int x, int y, String img){
        int tempHeight = y;
        for(int i = 0; i < amount; i++){
            addBackground(x, tempHeight, img);
            tempHeight += b.get(i).getHeight();
            bgLength += tempHeight;
        }
    }*/
    
    public void generateBackgroundY(int amount, int x, String img){
        int tempHeight = y;
        for(int i = 0; i < amount; i++){
            addBackground(x, tempHeight, img);
            tempHeight -= b.get(i).getHeight();
            bgLength = -(b.get(i).getHeight());
        }
    }
    
    public void generateBackgroundY(int x, String img){
        int tempHeight = y;
        for(int i = 0; i < 2; i++){
            addBackground(x, tempHeight, img);
            tempHeight -= b.get(i).getHeight();
            bgLength = -(b.get(i).getHeight());
        }
    }
}