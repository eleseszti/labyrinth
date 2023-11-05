package labyrinth;

import java.awt.Image;
import java.util.Random;

/**
 * @author Éles Eszter
 * @version 1.0.0 Dec 13, 2022.
 */
public class Dragon extends Character {
    
    public Dragon (int x, int y, Image image, Level level) {
        super(x, y, image, level);
    }
    
    /**
     * This method moves the dragon until it meets a wall - when it meets a wall,
     * the method calls the getRandomDirection method for a new direction and
     * keeps moving the dragon.
     */
    public void move() {
        if(((velx < 0 && x > 0) && ((x % 30 == 0 && !level.onWall(y / 30, x / 30 - 1)) || x % 30 != 0))
                || (velx > 0 && x < 600) && ((x % 30 == 0 && !level.onWall(y / 30, x / 30 + 1)) || x % 30 != 0)) {
            x += velx;
        }
        else if((vely < 0 && y > 0) && ((y % 30 == 0 && !level.onWall(y / 30 - 1, x / 30)) || y % 30 != 0)
                || (vely > 0 && y < 600)  && ((y % 30 == 0 && !level.onWall(y / 30 + 1, x / 30)) || y % 30 != 0)) {
            y += vely;
        }
        else {
            getRandomDirection();
        }
    }
    
    /**
     * This method gets a random direction for the dragon when a dragon meets a
     * wall of the labyrinth. The method sets the proper coordinates for the
     * new direction.
     */
    public void getRandomDirection() {
        String[] dirs = {"u", "d", "l", "r"};
        Random rand = new Random();
        int direction = rand.nextInt(4);
        switch (dirs[direction]) {
            case "u":
                this.vely = -1;
                this.velx = 0;
                break;
            case "d":
                this.vely = 1;
                this.velx = 0;
                break;
            case "l":
                this.velx = -1;
                this.vely = 0;
                break;
            case "r":
                this.velx = 1;
                this.vely = 0;
                break;
        }
    }
}
