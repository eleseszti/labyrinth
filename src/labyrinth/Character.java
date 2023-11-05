package labyrinth;

import java.awt.Image;

/**
 * @author Éles Eszter
 * @version 1.0.0 Dec 13, 2022.
 */
public abstract class Character extends Sprite {
    
    protected int velx;
    protected int vely;
    protected Level level;
    
    public Character(int x, int y, Image image, Level level) {
        super(x, y, 30, 30, image);
        this.level = level;
    }
    
    public double getVelX() {
        return velx;
    }

    public void setVelX(int velx) {
        this.velx = velx;
    }
    
    public double getVelY() {
        return vely;
    }
    
    public void setVelY(int vely) {
        this.vely = vely;
    }
}
