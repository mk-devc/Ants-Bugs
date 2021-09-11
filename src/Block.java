import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.Image;

public class Block extends JLabel implements MouseListener
{
    private Organism o= null;
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        if(o!=null){
            JOptionPane.showMessageDialog(null, o.toString(), "Organism Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //initialize coordinates (named Block) of the game board
    public Block()
    {
        setIcon(null);
        addMouseListener(this);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        setBorder(border);
    }
   
    //fill the block with the Organism
    public void fill(Organism o){
        this.o=o;
        
        ImageIcon imageIcon = o.getIcon();
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg); 
 
        setIcon(imageIcon);
    }
    
    //unfill the block from the Organism
    public void unfill(){
        o=null;
        setIcon(null);
    }
    
    //check whether the block is already occupied with Organism
    public boolean isFilled(){
        return o!=null;
    }
    
    //get the Organism occupied in the Block
    public Organism getOrganism(){
        return o;
    }
}
