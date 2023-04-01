package snake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIRDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIRDTH)/UNIT_SIZE;
    static final int SPEED = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6; //the snake size starts from 6
    int applesEaten;
    int appleX;
    int appleY;
    char direction='R';
    boolean running= false;
    Timer timer;
    Random random;

    GamePanel(){
        //it sets everything ready and the game begins
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIRDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        //creates a new apple at the beginning
        newApple();
        //the snake starts moving
        running=true;
        //the game begins in specific speed
        timer= new Timer(SPEED,this);
        timer.start();
    }
    public void paintComponent(Graphics g ) {
        //generates new state for every move
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running) {
            //draw the apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //draw the snake
            for(int i=0; i<bodyParts; i++) {
                if(i==0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i],UNIT_SIZE , UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i],UNIT_SIZE , UNIT_SIZE);
                }
            }
            //draw the score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score"+applesEaten, (SCREEN_WIRDTH-metrics.stringWidth("Score"+applesEaten))/2,g.getFont().getSize());
        }
        else {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,75));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Game Over", (SCREEN_WIRDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        }
    }
    public void newApple() {
        //set the new  apple in random position
        appleX=random.nextInt((int)(SCREEN_WIRDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move() {
        //for every body part move by one every time
        for(int i=bodyParts; i>0; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        //every time a button is pressed move to this direction Based on Cartesian coordinate system
        switch(direction) {
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }
    public void checkApple() {
        //checks if snake has eat the apple, increase its size and create a new one
        if((x[0]==appleX)&& (y[0]==appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions() {
        for(int i=bodyParts; i>0; i--) {
            //head collides with body
            if((x[0]==x[i])&& (y[0]==y[i])) {
                running=false;
            }
        }
        //left border
        if(x[0]<0) {
            running =false;
        }//RIGHT border
        if(x[0]>SCREEN_WIRDTH) {
            running =false;
        }
        //down border
        if(y[0]>SCREEN_HEIGHT) {
            running =false;
        }
        //top border
        if(y[0]<0) {
            running =false;
        }
        if(running==false) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    //set the direction depending on key pressed
    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction!='R') {
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L') {
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D') {
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U') {
                        direction='D';
                    }
            }
        }
    }

}
