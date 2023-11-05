/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * @author Éles Eszter
 * @version 1.0.0 Dec 13, 2022.
 */
public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;

    public Sprite(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * This method rounds a number to the closest number dividable by 30.
     * @param x
     * @return 
     */
    public int roundto30s(int x) {
        int m = x % 30;
        if(m < 15) {
            x -=m;
        }
        else {
            x = x + 30 - m;
        }
        return x;
    }
}
