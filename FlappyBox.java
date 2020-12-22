package flappyBox;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Font;
/**
 * 
 *
 * @author (Devon Biancarelli)
 * @version (0.1)
 */
public class FlappyBox implements ActionListener, MouseListener, KeyListener 
{
  public static FlappyBox flappybox;
  public final int WIDTH = 800, HEIGHT = 800;
  public Renderer renderer;
  public Rectangle box;
  public Random rand;
  public int ticks,yMotion, score; 
  public ArrayList<Rectangle> columns;
  public boolean gameOver, started;
  public FlappyBox()
  {
      JFrame jframe = new JFrame();
      
      renderer = new Renderer();
      rand = new Random();
      Timer timer = new Timer(20, this);
      
      jframe.add(renderer);
      jframe.setTitle("Flappy Box");
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(WIDTH, HEIGHT);
      jframe.addMouseListener(this);
      jframe.addKeyListener(this);
      jframe.setResizable(false);
      jframe.setVisible(true);
      
      
      box = new Rectangle(WIDTH / 2 -10, HEIGHT / 2-10, 20, 20);
      columns = new ArrayList<Rectangle>();
      
      addColumn(true);
      addColumn(true);
      addColumn(true);
      addColumn(true);
      
      
      
      timer.start();
      
  }
  public void jump()
  {
      if (gameOver)
      {
          box = new Rectangle(WIDTH / 2 -10, HEIGHT / 2-10, 20, 20);
          columns.clear();
      
          addColumn(true);
          addColumn(true);
          addColumn(true);
          addColumn(true);
          
          gameOver = false; 
        }
      if (!started)
      {
          started = true;
        }
        else if (!gameOver)
        {
            if (yMotion > 0)
            {
                yMotion = 0;
            }
            yMotion -= 10;
        }
   }
  @Override
  public void actionPerformed(ActionEvent e)
  {
     int speed = 10;
     ticks++;
     
     if (started) 
     {
         for (int i = 0; i < columns.size(); i++)
         {
             Rectangle column = columns.get(i);
             column.x -= speed;
          }    
          if (ticks % 2 == 0 && yMotion < 15)
          {
              yMotion += 2;
            }
            for (int i = 0; i < columns.size(); i++)
            {
                Rectangle column = columns.get(i);
         
                if (column.x + column.width < 0)
                {
                    columns.remove(column);
          
                    if (column.y == 0)
                    {    
                        addColumn(false);
                    }
                }
            }
            box.y += yMotion;
            for (Rectangle column : columns)
            {
                if (column.y == 0 && box.x + box.width / 2 > column.x + column.width / 2 - 10 && box.x + box.width / 2 < column.x + column.width / 2 + 10)
                {
                    score++;
                }
                if (column.intersects(box))
                {
                    gameOver = true;
                    if (box.x <= column.x)
                    {
                    box.x = column.x - box.width;
                  }
                  else 
                  {
                     if (column.y != 0)
                     {
                         box.y = column.y - box.height;
                        }
                        else if (box.y < column.height)
                        {
                            box.y = column.height;
                        }
                }
            }
            if (box.y > HEIGHT - 120 || box.y < 0)
            {
                gameOver = true;
            }
            
            if (box.y + yMotion >= HEIGHT - 120)
            {
                box.y = HEIGHT - 120 - box.height;
            }
        }
    }
         renderer.repaint();
}
  public void addColumn(boolean start)
  {
      int space = 300; 
      int width = 100;
      int height = 50 + rand.nextInt(300);
      
      if (start)
    {
      columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120,width, height));
      columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300,0,width,HEIGHT - height - space));
    }
    else 
    {
      columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120,width, height));
      columns.add(new Rectangle(columns.get(columns.size() - 1).x,0,width,HEIGHT - height - space));
    }
  }
  public void paintColumn(Graphics g, Rectangle column)
  {
      g.setColor(Color.green.darker());
      g.fillRect(column.x,column.y,column.width,column.height);
  }
  public void repaint(Graphics g)
  {
      g.setColor(Color.cyan);
      g.fillRect(0, 0, WIDTH, HEIGHT);
      
      g.setColor(Color.orange);
      g.fillRect(0, HEIGHT - 120, WIDTH, 120);
      
      g.setColor(Color.green);
      g.fillRect(0, HEIGHT - 120, WIDTH, 20);
      
      g.setColor(Color.red);
      g.fillRect(box.x, box.y, box.width, box.height);
      
      for (Rectangle column : columns)
      {
        paintColumn(g, column);
      }
      g.setColor(Color.white);
      g.setFont(new Font("Arial", 1, 100));
      if (!started)
      {
          g.drawString("Click to start!", 100, HEIGHT / 2 - 50);
      }
      if (gameOver)
      {
          g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
       }
      if (!gameOver && started)
      {
         g.drawString(String.valueOf(score),WIDTH / 2 -25, 100); 
        }
  }  
  public static void main(String[] args)
  {
        flappybox = new FlappyBox();
     
  }
   @Override 
   public void mouseClicked(MouseEvent e)
   {
       jump();
   }
   @Override 
   public void mousePressed(MouseEvent e)
   {
       
   }
   @Override 
   public void mouseReleased(MouseEvent e)
   {
       
   }
   @Override 
   public void mouseEntered(MouseEvent e)
   {
       
   }
   @Override 
   public void mouseExited(MouseEvent e)
   {
       
   }
   @Override 
   public void keyTyped(KeyEvent e)
   {
       
   }
   @Override 
   public void keyPressed(KeyEvent e)
   {
       
   }
   @Override 
   public void keyReleased(KeyEvent e)
   {
       if (e.getKeyCode() == KeyEvent.VK_SPACE)
       {
           jump();
        }
   }
}