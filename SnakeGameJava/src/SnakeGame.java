package snake;

import javax.swing.JFrame;
import javax.swing.JFrame;

public class SnakeGame extends JFrame {
    //create an new  game panel and start the game
    SnakeGame(){
        //add the game panel into the frame
        add(new snake.GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //the game is made for specific size
        this.setResizable(false);
        //it sizes the frame
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        //let's start the game
        new SnakeGame();
    }
}
