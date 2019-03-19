/*
*Brick game
*
*@Diza
*/
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;


public class BrickBoard  extends JPanel implements Runnable, MouseListener
{
    boolean ingame = false;//you can set to false to control start of game
    private Dimension d;
    int BOARD_WIDTH=500;
    int BOARD_HEIGHT=500;
    int ballx = 250; //positioning of ball
    int bally = 400;
    int r = 5; 
    int brickx = 0; //"positioning" of the 50 slot brick grid
    int bricky = 0;
    int lives = 3;
    int score = 0;
    int brickCount = 50; //overall brick total
    int sliderx = 250; //positioning of slider
    int ydirection = 1; //direction for ball movement in y
    int xdirection = 0; //direction for ball movement in x
    boolean[][] bricks = new boolean[10][5];
    BufferedImage img;
    String message = "Click anywhere to start game.";
    String lifeCount = "Lives: " + lives;
    String myScore = "Score: " + score;
     private Thread animator;
    
     
        public BrickBoard()
        {   
           
             addKeyListener(new TAdapter());
             addMouseListener(this);
            setFocusable(true);
            d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
            setBackground(Color.black);
            
           
           //remove comments below if you'd like to add an image to your game
               /*         
                 try {
                    img = ImageIO.read(this.getClass().getResource("myImage.jpg"));
                } catch (IOException e) {
                     System.out.println("Image could not be read");
                // System.exit(1);
                }
                */
                if (animator == null || !ingame) {
                animator = new Thread(this);
                animator.start();
                }
                
      
            setDoubleBuffered(true);
        }
        
        public void paint(Graphics g)
        {
                super.paint(g);
                
                g.setColor(Color.white);
                g.fillRect(0, 0, d.width, d.height);
                
                
                        Font small = new Font("Helvetica", Font.BOLD, 14);
                        FontMetrics metr = this.getFontMetrics(small);
                        g.setColor(Color.black);
                       g.setFont(small);
                        g.drawString(message, 10, d.height-60);
                
                    if (ingame) {//active board appears in these brackets.
                             
                            
                            g.drawString(lifeCount, 10, 20); //displays life count and score live
                            g.drawString(myScore, d.width - 100, 20);
                            
                            
                            
                            g.setColor(Color.red); //draws ball 
                            g.fillOval(ballx - r,bally,2*r,2*r);
                            
                            
                            g.setColor(Color.blue); //draws paddle
                            g.fillRect(sliderx, d.height - 100 + r, 60, 10);
                           
                            
                            message = " ";
                            
                            
                            for(int bricky = 0; bricky < 5; bricky++) {
                                for(int brickx = 0; brickx < 10; brickx++){
                                //for every brick in the 10 x 5 grid
                             
                                
                                if(bricks[brickx][bricky]){
                                    g.setColor(Color.yellow);
                                    g.fillRect(10 + brickx*50, 50 + bricky*20, 30, 10);
                                        if(ballx > 10 + brickx*50 && ballx < 40 + brickx*50  && bally > 50 + bricky*20 && bally < 60 + bricky*20){
                                        bricks[brickx][bricky] = false;
                                        g.setColor(Color.white);
                                        g.fillRect(10 + brickx*50, 50 + bricky*20, 30, 10);
                                        brickCount --;
                                        score += 50;
                                        ydirection = -1;
                                        myScore = "Score: " + score;
                                        //when ball hits a brick, the brick is returned false, erased, score is added to, 
                                        //and ydirection changes
                                    }
                                } 
                                }
                            }
                            
                            if(bally <= 0){
                                ydirection = -1; //ball hits ceiling
                            }
                            if(ballx <= 0){
                                xdirection = 1; //ball deflects off left wall
                            }
                            if(ballx >= 500){
                                xdirection = -1; //ball deflects off right wall
                            }
                            
                            if(sliderx < 0){
                                sliderx = 0; //paddle can't slide off board
                            }
                            if(sliderx > 500){
                                sliderx = 500;
                            }
                           
                            if(bally >= 400 && bally <= 410){
                                if (ballx < (sliderx + 65) && ballx > (sliderx - 5)){
                                     ydirection = 1;
                                     double d = Math.ceil(3*((ballx-sliderx-5) / 70.0)); //finds left, center, or right of 
                                     //paddle to determine xdirection
                                     System.out.println(d);
                                     score += 5;
                                     xdirection = (int) d-2;
                                     myScore = "Score: " + score;
                                     //ball reacts to paddle movement, 5 pts for every time ball hits paddle
                                }
                                
                                
                              
                               
                                
                            }
                           
                           
                            
                            if(bally >= 500){
                                lives --;
                                lifeCount = "Lives: " + lives;
                                bally = 150;
                                xdirection = 0;
                                ballx = sliderx + 30;
                                //life count goes down when ball falls off the board
                            }
                        
                            if(score >= 100){
                                message = "Press spacebar to trade 100 pts for one life.";
                                //ADDITIONAL FEATURE: "Buy" extra lives with 100 pts per life.
                            }
                            
                            if(score <= 0){
                                score = 0;
                                //prevents score from being negative
                            }
                        
                    // g.drawImage(img,0,0,200,200 ,null);//if you want to display an image
                     
                    
                   
                    }
                Toolkit.getDefaultToolkit().sync();
                g.dispose();
        }
    private class TAdapter extends KeyAdapter {
    
    public void keyReleased(KeyEvent e) {
         int key = e.getKeyCode();
         
    }
    
    public void keyPressed(KeyEvent e) {
    //System.out.println( e.getKeyCode());
       // message = "Key Pressed: " + e.getKeyCode();
        int key = e.getKeyCode();
       //message = "key pressed: " + key;
            if(key == 39){
              sliderx += 7;
     
            }
            if(key==37){
               sliderx -= 7;
            }
            if(key==32 && score >= 100){
                lives += 1;
                score -= 100;
                lifeCount = "Lives: " + lives;
                //when score >= 100 and spacebar pressed, up to 100 pts of score can be traded for one extra life
            }
            
    }
    
    }
    
    
    
    
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
         int y = e.getY();
         //message = "mousePressed: x: " + x + " y: " + y;
    if(e.getButton() == MouseEvent.BUTTON1) {
        ingame = true;
    }
    if(ingame == false && e.getButton() == MouseEvent.BUTTON1){
        ingame = true;
        myScore = "Score: " + score;
        message = " ";
        //restart game with a click
    }
    
    }
    
    public void mouseReleased(MouseEvent e) {
    
    }
    
    public void mouseEntered(MouseEvent e) {
    
    }
    
    public void mouseExited(MouseEvent e) {
    
    }
    
    public void mouseClicked(MouseEvent e) {
    
    }
    
    public void run() {
    
    long beforeTime, timeDiff, sleep;
    
    beforeTime = System.currentTimeMillis();
     int animationDelay = 10;//control FPS of board
     long time = 
                System.currentTimeMillis();
                
        for (int i = 0; i < 10; i++) {//creates a matrix of bricks, defines their "existence"
              for (int j = 0; j < 5; j++) {
                 bricks[i][j] = true;
               }
         }
        while (true) {//infinite loop
         // spriteManager.update();
          repaint();
          bally = bally - (2*ydirection);
          ballx = ballx + (int)(xdirection);
          if (lives == 0){
                    ingame = false;
                    message = ("You lost with a score of " + score + "! Click to play again.");
                    //ADDITIONAL FEATURE: Displays score at Game Over
                    ballx = 250;
                    bally = 400;
                    r = 5; 
                    brickx = 0;
                    bricky = 0;
                    lives = 3;
                    score = 0;
                    brickCount = 50;
                    sliderx = 250;
                    ydirection = 1;
                    xdirection = 0;
                    myScore = "Score: " + score;
                    //board is reset for next game start
                    for (int i = 0; i < 10; i++) {//resets brick grid
                          for (int j = 0; j < 5; j++) {
                             bricks[i][j] = true;
                           }
                     }
        
                } 
          if (brickCount == 0){
                    ingame = false;
                    message = ("Congrats, you won with a score of " + score + "! Click to play again");
                    //ADDITIONAL FEATURE: Displays score at Game Win
                    ballx = 250;
                    bally = 400;
                    r = 5; 
                    brickx = 0;
                    bricky = 0;
                    lives = 3;
                    score = 0;
                    brickCount = 50;
                    sliderx = 250;
                    ydirection = 1;
                    xdirection = 0;
                    myScore = "Score: " + score;
                    //board is reset for next game start
                    for (int i = 0; i < 10; i++) {//resets brick grid
                      for (int j = 0; j < 5; j++) {
                          bricks[i][j] = true;
                       }
                 }
        
                }
          
          try {
            time += animationDelay;
            Thread.sleep(Math.max(0,time - 
              System.currentTimeMillis()));
          }catch (InterruptedException e) {
            System.out.println(e);
          }//end catch
         
        }//end while loop
    
        
    
    
    }//end of run

}//end of class
