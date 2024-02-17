package Pong;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.*;
import java.awt.event.*;
//import javax.swing.*;

public class OpponentPaddel extends Paddle {

    OpponentPaddel(int x, int y, int Paddle_Width, int Paddle_Height) {
        super(x, y, Paddle_Width, Paddle_Height);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            setYDirection(-speed);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            setYDirection(speed);
            move();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            setYDirection(0);
            move();
        }
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
