import javax.swing.*;
import java.util.*;

public class Ant extends Organism
{
  private ImageIcon antIcon=new ImageIcon("antImg.png");
    
  public Ant(int x, int y, int start){
      super(x,y, start);
  }
  
  public Organism breed(int s){
    Organism offspring = null;
    int posX = getPosX();
    int posY = getPosY();
    Random rand = new Random();
    int r = rand.nextInt(4);
    
    switch(r){
        case 0:{
            posY--;
        }
        break;
        case 1:{
            posX++;
        }
        break;
        case 2:{
            posY++;
        }
        break;
        case 3:{
            posX--;
        }
    }
    
    if(posX>=0&&posX<20&&posY>=0&&posY<20){
        offspring=new Ant(posX, posY,s);
    }
    
    return offspring;
  }
  public ImageIcon getIcon(){
      return antIcon;
  }
  public String toString(){
      return "Type: Ant\nBirth at: Turn "+getBirth()+"\nAge: "+getAge();
  }
}
