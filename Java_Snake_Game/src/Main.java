import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class Main extends JPanel implements KeyListener {

    public static final int CELL_SIZE = 20;
    public static int width = 400;
    public static int height = 400;
    public static int row = height / CELL_SIZE;
    public static int column = width / CELL_SIZE;
    private Snake snake;
    private Fruit fruit;
    private Timer t;
    private int speed = 100;
    private static String direction;
    private boolean allowKeyPress; // 避免按鍵切換過快導致吃到自己
    private int score = 0;
    private int highestScore;
    String desktop = System.getProperty("user.home")+"/Desktop/";
    String myFile = desktop + "filename.txt";

    public Main(){
        read_highest_score();
        reset();
        addKeyListener(this);
    }

    private void setTimer(){
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, speed);
    }

    public void reset(){
        score = 0;
        if (snake != null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        setTimer();
    }

    public void paintComponent(Graphics g){
        // check if the snake bites itself, start from 1 because 0 is the position of head
        ArrayList<Node> snakeBody =  snake.getSnakeBody();
        Node head = snakeBody.get(0);
        for (int i=1; i< snakeBody.size(); i++) {
            if(snakeBody.get(i).x == head.x && snakeBody.get(i).y == head.y){
                allowKeyPress = false;
                t.cancel();
                t.purge();
                int response = JOptionPane.showOptionDialog(
                        this,
                        "Game Over!! Your score is " + score + ". The highest score is"+highestScore +" Would you like to start over",
                        "Game Over", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        JOptionPane.YES_OPTION
                );
                write_a_file(score);
                switch (response){
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }

        // draw a black background
        g.fillRect(0,0, width, height);
        fruit.drawFruit(g);
        snake.drawSnake(g);

        //remove snake tail and put it in
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;
        // right, x += cell
        // left, x += cell
        // down, y += cell
        // up, y -= cell
        if(direction.equals("Left")){
            snakeX -= CELL_SIZE;
        }else if(direction.equals("Up")){
            snakeY -= CELL_SIZE;
        }else if(direction.equals("Right")){
            snakeX += CELL_SIZE;
        }else if (direction.equals("Down")) {
            snakeY += CELL_SIZE;
        }
        Node newHead = new Node(snakeX, snakeY);
        // Check if the snake eats the fruit
        if (snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()){
//            System.out.println("Eating the fruit");
            // 1. set fruit to a new location
            fruit.setNewLocation(snake);
            // 2. draw Fruit
            fruit.drawFruit(g);
            // 3. score++
            score++;
        }else{ // 如果有吃到就不用隊最後一個node做刪除
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1);
        }
        snake.getSnakeBody().add(0, newHead);

        allowKeyPress = true;
        requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(allowKeyPress){
            System.out.println(e.getKeyCode());
            if(e.getKeyCode() == 37 && !direction.equals("Right")){
                direction = "Left";
            } else if (e.getKeyCode() == 38 && !direction.equals("Down")){
                direction = "Up";
            } else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
                direction = "Right";
            } else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
                direction = "Down";
            }

            allowKeyPress = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void read_highest_score(){
        try{
            File myObj = new File(myFile);
            Scanner myReader = new Scanner(myObj);
            highestScore = myReader.nextInt();
            myReader.close();
        }catch(FileNotFoundException e){
            highestScore = 0;
            try{
                File myObj = new File(myFile);
                if(myObj.createNewFile()){
                    System.out.println("File created: "+myObj.getName());
                }
                FileWriter myWriter = new FileWriter(myObj.getName());
                myWriter.write(""+0);
            } catch (IOException err) {
                System.out.println("An error occurred");
                err.printStackTrace();
            }
        }
    }

    public void write_a_file(int score){
        try{
            FileWriter myWriter = new FileWriter(myFile);
            if(score > highestScore){
                myWriter.write(""+score);
                highestScore = score;
            }else{
                myWriter.write("" + highestScore);
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
