package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

    static final int Game_Width = 1000;
    static final int Game_Height = (int)(Game_Width * (0.5555));
    static final Dimension Screen_Size = new Dimension(Game_Width,Game_Height);
    static final int Ball_Diameter = 20;
    static final int Paddle_Width = 25;
    static final int Paddle_Height = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball ;
    Score score;
    SoundManager bounceSound = new SoundManager("Pong/plastic-ball-bounce-14790.wav");
    ScoreManager scoreManager;
    boolean isGameOver;

    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(Game_Width,Game_Height);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(Screen_Size);
        scoreManager = new ScoreManager(5, "highscores.txt"); // Example: Score limit is 5
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall(){
        random = new Random();
        ball = new Ball((Game_Width/2)- (Ball_Diameter/2),random.nextInt(Game_Height-Ball_Diameter),(Ball_Diameter/2),(Ball_Diameter/2));
    }
    public void newPaddles(){
        paddle1 = new PlayerPaddle(0,(Game_Height/2)-(Paddle_Height/2),Paddle_Width,Paddle_Height);
        paddle2 = new OpponentPaddel(Game_Width - Paddle_Width,(Game_Height/2)-(Paddle_Height/2),Paddle_Width,Paddle_Height);

    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        if (isGameOver) {
            // Draw game over message
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", getWidth() / 2 - 120, getHeight() / 2);
        }
    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollision(){

        //bounce ball off the top and bottom window edges
        if(ball.y <=0)
            ball.setYDirection(-ball.yVelocity);
        if(ball.y >= Game_Height-Ball_Diameter)
            ball.setYDirection(-ball.yVelocity);

        //bounce ball off the paddles
        if (ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //for more dificulty
            if(ball.yVelocity > 0)
                ball.yVelocity++;
            if(ball.yVelocity <= 0)
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if (ball.intersects(paddle2)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; 
            if(ball.yVelocity > 0)
                 ball.yVelocity++;
            if(ball.yVelocity <= 0)
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
       

        // stops paddles at window borders2
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y >= (Game_Height - Paddle_Height))
            paddle1.y = Game_Height - Paddle_Height;
        
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y >= (Game_Height - Paddle_Height))
            paddle2.y = Game_Height - Paddle_Height;

        //give a player 1 point and creates new paddles & ball
        if (ball.x <= 0){
            score.player2 ++;
            newPaddles();
            newBall();            
            scoreManager.increasePlayer2Score();

            //System.out.println("Player 2: "+score.player2);
        }
        if (ball.x >= Game_Width-Ball_Diameter){
            score.player1 ++;
            newPaddles();
            newBall();
            scoreManager.increasePlayer1Score();

            //System.out.println("Player 1: "+score.player1);
        }
        if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
            if(bounceSound!=null){
                bounceSound.play();
            }
        }
        if (isGameOver) {
            return;
        }
        if (scoreManager.isGameOver()) {
            String winner = scoreManager.getWinner();
            System.out.println("Game over! " + winner + " wins!");
            scoreManager.saveHighScores(); // Save high scores
            isGameOver = true;
        }

    }
    public void run(){
        // Game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
    
        while(true && !isGameOver){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now; // Update lastTime for the next iteration
    
            while (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}