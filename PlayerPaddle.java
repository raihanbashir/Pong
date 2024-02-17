package Pong;

import java.awt.*;
import java.awt.event.*;
//import javax.swing.*;

public class PlayerPaddle extends Paddle {

    PlayerPaddle(int x, int y, int Paddle_Width, int Paddle_Height) {
        super(x, y, Paddle_Width, Paddle_Height);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            setYDirection(-speed);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            setYDirection(speed);
            move();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            setYDirection(0);
            move();
        }
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}
