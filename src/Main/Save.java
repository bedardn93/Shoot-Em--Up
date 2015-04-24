package Main;

import java.io.*;
import java.util.*;
public class Save {
    
        Scanner reader = new Scanner(System.in);
        Scanner inputFile = null;
        PrintStream outStream = null;
        boolean fileOpened = true;
        int num;
        private Character c;
        
        public void openFile(){
            try{
                inputFile = new Scanner(new File("input.txt"));
            }catch(FileNotFoundException e){
                System.out.println("--- File Not Found! ---");
                fileOpened = false;
            }
            if(fileOpened){
                while(inputFile.hasNext()){
                    if(inputFile.hasNextInt()){
                        num = inputFile.nextInt();
                        c.setHighScore(num);
                    }else
                        inputFile.next();
                }
            inputFile.close();
            }
        }
        
        public void writeFile(){
            try{
                inputFile = new Scanner(new File("input.txt"));
                outStream = new PrintStream(new File("output.txt"));
            }catch(FileNotFoundException e){
                System.out.println("---File Not Found!---");
                fileOpened = false;
            }
            if(fileOpened){
                while(inputFile.hasNext()){
                    if(inputFile.hasNextInt()){
                        num = inputFile.nextInt();
                        outStream.print(num);
                    }else
                        inputFile.next();
                }
                inputFile.close();
                outStream.close();
            }
        }
}