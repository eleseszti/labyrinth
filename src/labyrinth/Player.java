/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinth;

import java.awt.Image;

/**
 * @author Éles Eszter
 * @version 1.0.0 Dec 13, 2022.
 */
public class Player extends Character {
    
    public Player(int x, int y, Image image, Level level) {
        super(x, y, image, level);
    }
    
    /**
     * This method moves the player until it meets a wall.
     */
    public void move() {
        if(((velx < 0 && x > 0) && ((x % 30 == 0 && !level.onWall(y / 30, x / 30 - 1)) || x % 30 != 0))
                || (velx > 0 && x < 600) && ((x % 30 == 0 && !level.onWall(y / 30, x / 30 + 1)) || x % 30 != 0)) {
            x += velx;
        }
        if((vely < 0 && y > 0) && ((y % 30 == 0 && !level.onWall(y / 30 - 1, x / 30)) || y % 30 != 0)
                || (vely > 0 && y < 600)  && ((y % 30 == 0 && !level.onWall(y / 30 + 1, x / 30)) || y % 30 != 0)) {
            y += vely;
        }
    }
}
