package flappyBox;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Write a description of class Renderer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Renderer extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    @Override 
    protected void paintComponent(Graphics g) 
     {
         super.paintComponent(g);
         FlappyBox.flappybox.repaint(g);
         
         
     }
    
}
