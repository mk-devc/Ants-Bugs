import javax.swing.*;
import java.util.*;

public class Bug extends Organism
{
  private ImageIcon bugIcon=new ImageIcon("bugImg.png");
  private int stepsAfterEat =0;
  
  public Bug(int x, int y, int start){
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
        offspring=new Bug(posX, posY,s );
    }
    
    return offspring;
  }
  public ImageIcon getIcon(){
      return bugIcon;
  }
  public String toString(){
      return "Type: Bug\nBirth at: Turn "+getBirth()+"\nAge: "+getAge();
  }
  
  public boolean die(){
      return stepsAfterEat>=3;
  }
  public void starve(){
      stepsAfterEat++;
  }
  public void eat(){
      stepsAfterEat=0;
  }
  
}
