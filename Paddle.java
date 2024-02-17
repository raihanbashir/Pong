package Pong;

import java.awt.*;
import java.awt.event.*;
// import java.util.*;
// import javax.swing.*;

public class Paddle extends Rectangle{

    int yVelocity;
    int speed = 10;

    Paddle(int x, int y, int Paddle_Width, int Paddle_Height){
        super(x,y,Paddle_Width,Paddle_Height);
    }

    public void setYDirection(int YDirection){
        yVelocity = YDirection; 
    }
    public void move(){
        y = y + yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);

    }

    public void keyPressed(KeyEvent e) {
        System.out.println("lemon");
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("lemon");
    }


}
