import javax.swing.*;
import java.util.*;

public abstract class Organism
{
    private int posX;
    private int posY;
    private int age=0;
    private int startLife;
    
    //projected moving coordinates of the Organism
    public void move(){
        int x=posX;
        int y=posY;
        Random rand = new Random();
        int r = rand.nextInt(4);
        
        switch(r){
            case 0:{
                y--;
            }
            break;
            case 1:{
                x++;
            }
            break;
            case 2:{
                y++;
            }
            break;
            case 3:{
                x--;
            }
        }
        
        //if the coordinates still in the game board
        if(x>=0&&x<20&&y>=0&&y<20){
            posX=x;
            posY=y;
        }
    } 
    
    public abstract Organism breed(int s);
    public abstract ImageIcon getIcon();
    
    //initialize the Organism
    public Organism(int x, int y, int start){
        startLife=start;
        posX=x;
        posY=y;
    }
    //get x-coordinates of Organism
    public int getPosX(){
        return posX;
    }
    
    //get y-coordinates of Organism
    public int getPosY(){
        return posY;
    }
    
    //set x-coordinates of Organism
    public void setPosX(int x){
        posX=x;
    }
    
    //set y-coordinates of Organism
    public void setPosY(int y){
        posY=y;
    }
    
    //add one age of the Organism
    public void addAge(){
        age++;
    }
    
    //get the age of the Organism (for info display)
    public int getAge(){
        return age;
    }
    
    //get the birth turn of the Organism (for info display)
    public int getBirth(){
        return startLife;
    }
    
    //check whether the Organism is already moved or not
    public boolean isMoved(int step){
        return (age+startLife)<step;
    }
}
