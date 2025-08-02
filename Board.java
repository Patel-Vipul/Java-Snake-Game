package projects.Snake_Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//board class   [to capture actions we use a interface]
public class Board extends JPanel implements ActionListener {
    private int dots;
    private Image apple;
    private Image dot;
    private Image head;

    private final int ALL_DOTS = 1000;                  //Maximum size snake can be grown
    private final int dotSize = 10;                     //size of one dot

    //coordinates of dots
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    //for the location of apple
    private final int RANDOM_POSITION = 34;
    private int apple_x;
    private int apple_y;

    private Timer timer ;

    //properties for the direction of the snake
    private boolean left_direction = false;
    private boolean right_direction = true;
    private boolean up_direction = false;
    private boolean down_direction = false;

    private boolean inGame = true;          //for game over

    private int highScore = 0;
    private final String HIGHSCORE_FILE = "highscore.txt";

    //constructor
    Board(){
        addKeyListener(new TAdapter());          //call the class tAdpater

        setBackground(Color.BLACK);             //set the background color of frame
        setFocusable(true);

        initiateGame();
        loadImages();
    }

    //method to store(load) the images
    public void loadImages(){
        ImageIcon a = new ImageIcon(ClassLoader.getSystemResource("projects/icons/apple.png"));
        apple = a.getImage();
        ImageIcon d = new ImageIcon(ClassLoader.getSystemResource("projects/icons/dot.png"));
        dot = d.getImage();
        ImageIcon h = new ImageIcon(ClassLoader.getSystemResource("projects/icons/head.png"));
        head = h.getImage();
    }

    //initialization of game
    public void initiateGame(){
        dots = 3;

        for(int i=0; i<dots; i++){
            y[i] = 50;
            x[i] = 50 - i * dotSize;
        }
        locateApple();

        timer = new Timer(150,this);
        timer.start();
    }

    //method to generate apple at local places
    public void locateApple(){
        int r = (int) (Math.random() * RANDOM_POSITION);        //generate random number of x
        apple_x = r * dotSize;

        r = (int) (Math.random() * RANDOM_POSITION);            //generate random number of y
        apple_y = r * dotSize;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }

    //show images on screen
    public void draw(Graphics g){
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }

    //Function to display the game over on frame
    public void gameOver(Graphics g){
        String msg = "OOPS! Game Over";
        String restartMsg = "Press 'R' to Restart the Game";

        Font font = new Font("SERIF",Font.BOLD,40);
        Font smallFont = new Font("Serif",Font.PLAIN,20);
        FontMetrics met = getFontMetrics(font);
        FontMetrics met2 = getFontMetrics(smallFont);

        //showing the Game Over on Frame
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(msg,(getWidth() - met.stringWidth(msg))/2,getHeight()/2 - 20);

        //Showing the Restart on Frame
        g.setColor(Color.WHITE);
        g.setFont(smallFont);
        g.drawString(restartMsg,(getWidth() - met2.stringWidth(restartMsg))/2, getHeight()/2 + 20);

    }

    //function to move the Snake
    public void move(){
        for(int i=dots; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(left_direction){
            x[0] = x[0] - dotSize;
        }
        if(right_direction){
            x[0] = x[0] + dotSize;
        }
        if(up_direction){
            y[0] = y[0] - dotSize;
        }
        if(down_direction){
            y[0] = y[0] + dotSize;
        }

    }

    //detect the apple position and increase the size of snake and relocate the position of apple
    public void checkApple(){
        if(x[0] == apple_x && y[0] == apple_y){
            dots++;
            locateApple();
        }
    }

    public void checkCollison(){
        for(int i=dots; i>0; i--){
            //collision with itself
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }

            //collision with border
            if(x[0] < 0 || x[0] > 480){
                inGame = false;
            }

            else if(y[0] < 0 || y[0] > 450){
                inGame = false;
            }

        }
        if(!inGame){
            timer.stop();
        }
    }

    public void actionPerformed (ActionEvent ae){
        if(inGame) {
            checkApple();
            checkCollison();
            move();
        }

        repaint();            //refresh the screen (same as pack())
    }

    public class TAdapter extends KeyAdapter{
        @Override
        //invoke when key is pressed
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && (!right_direction)){
                left_direction = true;
                up_direction = false;
                down_direction = false;
            }
            else if(key == KeyEvent.VK_RIGHT && (!left_direction)){
                right_direction = true;
                up_direction = false;
                down_direction = false;
            }
            else if(key == KeyEvent.VK_UP && (!down_direction)){
                up_direction = true;
                left_direction = false;
                right_direction = false;
            }
            else if(key == KeyEvent.VK_DOWN && (!up_direction)){
                down_direction = true;
                right_direction = false;
                left_direction = false;
            }
            else if(key == KeyEvent.VK_R){
                restartGame();
            }

        }

        //class to restart the game
        public void restartGame(){
            inGame = true;
            left_direction = false;
            right_direction = true;
            up_direction = false;
            down_direction = false;
            initiateGame();
            repaint();
        }

        //function to store the score of Game
//        public void loadHighScore(){
//            try{
//                java.io.File file = new java.io.File(HIGHSCORE_FILE);
//                if(file.exists()){
//                    java.util.Scanner sc = new java.util.Scanner(file);
//                    if(sc.hasNext()){
//                        highScore = sc.nextInt();
//                    }
//                    sc.close();
//                }
//                else{
//                    file.createNewFile();
//                }
//            }
//        }

    }

}
