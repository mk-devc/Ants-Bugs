import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.*;

public class Game extends JFrame implements KeyListener
{
    private boolean isStart=false;
    private Block[][] area=new Block[20][20];
    private int ant =0;
    private int bug =0;
    private int gameStep=0;
    private JLabel turn = new JLabel("Turn: "+gameStep);
    private JLabel score = new JLabel("ant: "+ant+"   "+"bug: " +bug);
    private JPanel header = new JPanel(new BorderLayout());

    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()=='\n'){
            nextStep();
        }
    }
    
    //Initialize the game layout
    public Game() {
        super("Ant-Bug Simulator");
        setSize(800, 900); 
        setLayout(new BorderLayout()); 
        addKeyListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        header.add(turn, BorderLayout.LINE_START);
        header.add(score, BorderLayout.LINE_END);
        add(header,BorderLayout.PAGE_START);
        
        JPanel GameArea = new JPanel(new GridLayout(20,20));
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                Block sq=new Block();
                area[j][i]=sq;
                GameArea.add(area[j][i]);       
            }   
        }
        add(GameArea);
        
        start();
    }
   
    //Design pattern used: Factory method
    //Fill the board with bugs and ants randomly
    public void start(){  
        int antInit = 100;
        int bugInit = 5;
        Random rand = new Random();
        
         while(bugInit>0){
            int randX = rand.nextInt(20);
            int randY = rand.nextInt(20);
            
            while(!area[randX][randY].isFilled()){
                Organism b = new Bug(randX, randY, gameStep);
                area[randX][randY].fill(b);
                bugInit--;
                bug++;
            }
        }   
        
        while(antInit>0){
            int randX = rand.nextInt(20);
            int randY = rand.nextInt(20);
            
            while(!area[randX][randY].isFilled()){
                Organism a = new Ant(randX, randY, gameStep);
                area[randX][randY].fill(a);
                antInit--;
                ant++;
            }
        }
        
        score.setText("ant: "+ant+"   "+"bug: " +bug);
        setVisible(true);
    }
    
    //proceeds to next turn
    public void nextStep(){
        gameStep++;
        //each organism will be checked if they already move in the turn
        //bug turn
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                Organism b=area[j][i].getOrganism();
               
                if(b instanceof Bug&&b.isMoved(gameStep)){
                    ArrayList<Organism> prey = getAdjacentPrey(j,i);
                    Bug bb = (Bug)b;
                    b.addAge();
                    
                    //bug moving process
                    switch(prey.size()){
                        //no adjacent ant
                        case 0:{
                            b.move();
                            if(area[b.getPosX()][b.getPosY()].isFilled()){
                                b.setPosX(j);
                                b.setPosY(i);
                            }else{
                                area[j][i].unfill();
                                area[b.getPosX()][b.getPosY()].fill(b);
                            }
                        
                            bb.starve();
                        }
                        break;
                        
                        //exactly 1 adjacent ant
                        case 1:{
                            int xPos = prey.get(0).getPosX();
                            int yPos = prey.get(0).getPosY();
                            
                            area[j][i].unfill();
                            area[xPos][yPos].fill(b);
                            b.setPosX(xPos);
                            b.setPosY(yPos);
                            
                            bb.eat();
                        }
                        break;
                        
                        //more than 1 adjacent ant
                        default:{
                            Random rand = new Random();
                            int r = rand.nextInt(prey.size());
                            int xPos = prey.get(r).getPosX();
                            int yPos = prey.get(r).getPosY();
                            
                            area[j][i].unfill();
                            area[xPos][yPos].fill(b);
                            b.setPosX(xPos);
                            b.setPosY(yPos);
                           
                            bb.eat();
                        }
                    }
                    
                    //check if bug dead
                    if(bb.die()){
                        area[b.getPosX()][b.getPosY()].unfill();
                    }
                    
                    //bug breeding process
                    if(b.getAge()%8==0){
                        Organism offSpring=b.breed(gameStep);
                        if(offSpring!=null){
                            if(!area[offSpring.getPosX()][offSpring.getPosY()].isFilled())
                            {
                                area[offSpring.getPosX()][offSpring.getPosY()].fill(offSpring);
                            }
                        }
                    }
               }
            }
        }
        
        //ant turn
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                Organism a=area[j][i].getOrganism(); 
                if(a instanceof Ant&&a.isMoved(gameStep)){
                    a.addAge();
                    a.move();
                    
                    //ant moving process
                    if(area[a.getPosX()][a.getPosY()].isFilled()){
                        a.setPosX(j);
                        a.setPosY(i);
                    }else{  
                        area[j][i].unfill();
                        area[a.getPosX()][a.getPosY()].fill(a);
                    }
                    
                    //ant breeding process
                    if(a.getAge()%3==0){
                        Organism offSpring=a.breed(gameStep);
                        
                        if(offSpring!=null){
                            if(!area[offSpring.getPosX()][offSpring.getPosY()].isFilled()){
                                area[offSpring.getPosX()][offSpring.getPosY()].fill(offSpring);
                               
                            }
                        }
                    }
                }
            }
        }
        
        //head count ant and bug for displaying score
        ant=0;
        bug=0;
        for(int i=0; i<20; i++){
            for(int j=0; j<20; j++){
                Organism headCount=area[j][i].getOrganism();
                if(headCount instanceof Ant){
                    ant++;
                }
                if(headCount instanceof Bug){
                    bug++;
                }
            }
        }
        
        score.setText("ant: "+ant+"   "+"bug: " +bug+"   ");
        turn.setText("Turn: "+gameStep);
    }
    
    //checking if any ant around the bug
    private ArrayList<Organism> getAdjacentPrey(int i, int j){
        ArrayList<Organism> prey = new ArrayList<Organism>(); 
        
        if(i-1<20&&i-1>=0&&area[i-1][j].getOrganism() instanceof Ant){
            prey.add(area[i-1][j].getOrganism());
        }
        if(i+1<20&&i+1>=0&&area[i+1][j].getOrganism() instanceof Ant){
            prey.add(area[i+1][j].getOrganism());
        }
        if(j-1<20&&j-1>=0&&area[i][j-1].getOrganism() instanceof Ant){
            prey.add(area[i][j-1].getOrganism());
        }
        if(j+1<20&&j+1>=0&&area[i][j+1].getOrganism() instanceof Ant){
            prey.add(area[i][j+1].getOrganism());
        }
       
        return prey;
    }
}
