package projects.Snake_Game;

import javax.swing.*;

//main class inherits to JFrame for frame
public class SnakeGame extends JFrame {
    SnakeGame(){
        super("Snake Game");           //sets the title of frame
        add(new Board());                   //shows error if added class is not panel or component of JPanel
        pack();
        setVisible(true);                  // makes the frame visible
        setSize(500,500);     //sets the size of frame(length and width)
        setLocationRelativeTo(null);        //bring frame at the centre of screen
        setResizable(false);

        //setLocation(400,400);            //sets the location of frame manually


    }

    //main method
    public static void main(String[] args) {
        new SnakeGame();
    }
}
