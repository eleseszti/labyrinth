package labyrinth;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * @author Éles Eszter
 * @version 1.0.0 Dec 13, 2022.
 */
public class Level {

    private final int FIELD_SIZE = 30;
    private ArrayList<Field> fields;
    private int[][] fieldsArray;

    public Level(String levelPath) throws IOException {
        loadLevel(levelPath);
    }
    
    /**
     * This method reads the new labyrinth's data from a file and stores it in an
     * ArrayList and an array.
     * @param levelPath
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void loadLevel(String levelPath) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(levelPath));
        fields = new ArrayList<>();
        fieldsArray = new int[21][21];
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char blockType : line.toCharArray()) {
                Image image = new ImageIcon("data/images/field" + blockType + ".png").getImage();
                fields.add(new Field(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, image));
                fieldsArray[y][x] = Integer.parseInt(String.valueOf(blockType));
                x++;
            }
            y++;
        }
    }

    /**
     * This method draws the new labyrinth.
     * @param g 
     */
    public void draw(Graphics g) {
        for (Field field : fields) {
            field.draw(g);
        }
    }
    
    /**
     * This method draws a cover on the maze, it leaves a circle with a 180 pixels 
     * radius around the player.
     * @param g
     * @param x
     * @param y 
     */
    public void drawCover(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;
        Area a = new Area(new Rectangle(0, 0, 644, 690));
        a.subtract(new Area(new Ellipse2D.Double(x - 75, y - 75, 180, 180)));
        g2d.fill(a);
    }
    
    /**
     * This method checks if the coordinate is on a wall in a maze.
     * @param x
     * @param y
     * @return 
     */
    public boolean onWall(int x, int y) {
        if(fieldsArray[x][y] == 0) {
            return true;
        }
        return false;
    }

}
