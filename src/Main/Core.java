package Main;

import java.awt.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
//import sun.audio.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


//Abstract class with a compilation of methods and variables used throughout program
//includes methods for drawing, sounds, and actors
public abstract class Core {
    
    static final int LEFT = -5;
    static final int RIGHT = 5;
    static final int UP = -5;
    static final int DOWN = 5;
    
    protected int x, vx;
    protected int y, vy;
    protected int width, height, centerX, centerY;

    protected int health;

    protected Image still;
    protected boolean visible;
    
    //AudioStream as = null;
    //InputStream in = null;
    
    ArrayList<AudioInputStream> sounds = new ArrayList<AudioInputStream>();
    //ArrayList<AudioStream> music = new ArrayList<AudioStream>();
    

    public Core(){}

    public Core(int hlth,int x, int y, String img){
        health = hlth;
        this.x = x;
        this.y=y;
	try {
	    still = ImageIO.read(ClassLoader.getSystemResource("Programming Dir/"+img));
	} catch (IOException ex) {
	    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    //getter methods below
    public Rectangle getBounds(){
        return new Rectangle(x,y,getWidth(),getHeight());
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

    public int getVelocityX(){
        return vx;
    }

    public int getVelocityY(){
        return vy;
    }
    
    public int getHeight(){
        return still.getHeight(null);
    }

    public int getWidth(){
        return still.getWidth(null);
    }

    public int getHealth(){
        return health;
    }
    
    public boolean getVisible(){
        return visible;
    }
    
    public Image getImage(){
        return still;
    }
    
    public ArrayList getSounds(){
        return sounds;
    }
    

    //setter methods
    //first two are for x and y velocity respectively
    public void setVX(int num){
        vx = num;
    }

    public void setVY(int num){
        vy = num;
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setHealth(int num){
        health = num;
    }
    
    public void setVisible(boolean vis){
        visible = vis;
    }
    
    public void setImage(String img){
        still = new ImageIcon(img).getImage();
    }
    
    public boolean isAlive(){
        if(health <= 0){
            return false;
        }
        return true;
    }
    
    public void moveLeft(){
        setVX(LEFT);
    }
    
    public void moveRight(){
        setVX(RIGHT);
    }
    
    public void moveUp(){
        setVY(UP);
    }
    
    public void moveDown(){
        setVY(DOWN);
    }

    public int damage(int dam){
        return health -= dam;
    }
    
    public void addSound(String url){
        try{
            sounds.add(AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("Programming Dir/"+url)));
        }catch(Exception e){
            System.err.print(e);
        }
    }
    
    //plays sounds heard in the game
    //can automatically add sounds to sound list without having to invoke
    //the addSound method
    public void playSound(String url){
        /*
        try{
            Clip clip = AudioSystem.getClip();
            
            clip.open(inputStream);
            clip.start();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
         */
        try{
            Clip clip = AudioSystem.getClip();
            if(sounds.isEmpty())
                addSound(url);
	    for (AudioInputStream sound : sounds) {
		if (sound.equals(AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("Programming Dir/"+url)))) {
		    clip.open(sound);
		} else {
		    addSound(url);
		    clip.open(AudioSystem.getAudioInputStream(ClassLoader.getSystemResource("Programming Dir/"+url)));
		}
		clip.start();
		break;
	    }
	}catch(Exception e){
            System.err.println(e);
        }
    }
    //can't figure out how to start and stop music properly
    //come back to later with better method
    /*
    public ArrayList getMusic(){
        return music;
    }
    public void addMusic(String url){
        try{
            in = new FileInputStream(url);
            as = new AudioStream(in);  
            music.add(as);
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    public void playSong(String url){
        /*
        boolean fileOpened = false;
        //** add this into your application code as appropriate
        // Open an input stream  to the audio file.
        try{
            in = new FileInputStream(url);
            // Create an AudioStream object from the input stream.
            as = new AudioStream(in);  
            fileOpened = true;
        }catch(Exception e){
            System.out.println(e);
        }

        if(fileOpened){
            // Use the static class member "player" from class AudioPlayer to play
            // clip.
            AudioPlayer.player.start(as);
        }
        try{
            AudioStream stream = new AudioStream(new FileInputStream(url));
            if(music.isEmpty())
                addMusic(url);
            for(int i = 0; i < music.size(); i++){
                if(music.get(i).equals(stream))
                    AudioPlayer.player.start(music.get(i));
            }
        }catch(Exception e){
            System.err.println(e);
        }
        
    }
    
    public void stopSong(String url){
        /*
        boolean fileOpened = false;
        //** add this into your application code as appropriate
        // Open an input stream  to the audio file.
        try{
            in = new FileInputStream(url);

            // Create an AudioStream object from the input stream.
            as = new AudioStream(in);  
            fileOpened = true;
        }catch(Exception e){
            System.out.println(e);
        }

        if(fileOpened){
            AudioPlayer.player.stop(as);
        }
        
        try{
            AudioStream stream = new AudioStream(new FileInputStream(url));
            for(int i = 0; i < music.size(); i++){
                if(music.get(i).equals(stream)){
                    AudioPlayer.player.stop(music.get(i));
                    System.out.println("stopped music");
                }
            }
        }catch(Exception e){
            System.err.println(e);
        }

        
    }
*/
    
    //used to draw single object onscreen(players, special objects, etc.)
    public void drawObject(Graphics2D g2d, Core actor){
        g2d.drawImage(still, x, y,null);
    }
    
    //used to draw lists of objects onscreen(enemies, bullets, backgrounds, etc.)
    public void drawList(Graphics2D g2d, ArrayList<Core> list){
        for(int i = 0; i < list.size(); i++){
            g2d.drawImage(list.get(i).still, list.get(i).x, list.get(i).y,null);
        }
    }

}
