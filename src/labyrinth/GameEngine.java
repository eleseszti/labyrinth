package labyrinth;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.sql.SQLException;

/**
 * @author Éles Eszter
 * @version 1.0.0 Dec 13, 2022.
 */
public class GameEngine extends JPanel {

    private final int FPS = 240;
    private final int PLAYER_MOVEMENT = 1;
    
    private boolean paused = false;
    private int levelNum = 0;
    private Timer newFrameTimer;
    private Player player;
    private String name;
    private Dragon dragon;
    private Level level;
    private JLabel timeLabel;
    private JLabel levelLabel;
    private int timeElapsedInSeconds = 0;

    public GameEngine() {
        super();
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.setVelX(-PLAYER_MOVEMENT);
                player.setVelY(0);
                player.setY(player.roundto30s(player.getY()));
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.setVelX(PLAYER_MOVEMENT);
                player.setVelY(0);
                player.setY(player.roundto30s(player.getY()));
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.setVelY(PLAYER_MOVEMENT);
                player.setVelX(0);
                player.setX(player.roundto30s(player.getX()));
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.setVelY(-PLAYER_MOVEMENT);
                player.setVelX(0);
                player.setX(player.roundto30s(player.getX()));
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        
        restart();
    }
    
    public String getPlayerName() {
        return name;
    }
    
    public int getLevelNum() {
        return levelNum;
    }
    
    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }
    
    public void setLevelLabel(JLabel levelLabel) {
        this.levelLabel = levelLabel;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * This method starts a new timer - starts counting the elapsed seconds.
     */
    public void startTimer() {
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }
    
    public void stopTimer() {
        newFrameTimer.stop();
    }
    
    public void contTimer() {
        newFrameTimer.start();
    }

    /**
     * This method loads the new labyrinth and places the player and the dragon 
     * in the maze.
     */
    public void restart() {
        try {
            level = new Level("data/levels/level0" + levelNum + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image playerImage = new ImageIcon("data/images/player.png").getImage();
        player = new Player(30, 600, playerImage, level);
        Image dragonImage = new ImageIcon("data/images/dragon.png").getImage();
        Node dragonPlace = placeDragon();
        dragon = new Dragon(dragonPlace.y * 30, dragonPlace.x * 30, dragonImage, level);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        level.draw(g);
        player.draw(g);
        dragon.draw(g);
        level.drawCover(g, player.getX(), player.getY());
    }
    
    /**
     * This method creates a random coordinate for the dragon to start at. It checks
     * if the selected coordinate is a wall in the labyrinth and if not, it returns
     * the coordinates.
     */
    public Node placeDragon() {
        Random rand = new Random();
        int x = 2 + rand.nextInt(19);
        int y = 2 + rand.nextInt(19);
        while (level.onWall(x, y)) {
            x = 2 + rand.nextInt(19);
            y = 2 + rand.nextInt(19);
        }
        return new Node(x, y);
    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                dragon.move();
                if (meetDragon() || levelNum == 10) {
                    levelNum = 0;
                    restart();
                    timeElapsedInSeconds = 0;
                }
                player.move();
                getElapsedTime();
            }
            if (isOver()) {
                levelNum++;
                restart();
            }
            levelLabel.setText("Level: " + Integer.toString(levelNum + 1) + " | ");
            
            repaint();
        }
    }
    
    /**
     * This method checks if the dragon is next to the player.
     */
    public boolean meetDragon() {
        if(((player.y == dragon.y) && (Math.abs(player.x - dragon.x) <= 30))
                || ((player.x == dragon.x) && (Math.abs(player.y - dragon.y) <= 30))) {
            return true;
        }
        return false;
    }
    
    /**
     * This method checks if the player escaped the maze.
     * @return 
     */
    public boolean isOver() {
        if(player.x == 570 && player.y == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * This method counts the elapsed time from the start of the game and resets
     * the JLabel where it is shown to the player.
     */
    public void getElapsedTime() {
        timeElapsedInSeconds += 1;
        int seconds = (timeElapsedInSeconds / 60) % 60;
        String secondsStr;
        if(seconds < 10) {
            secondsStr = "0" + Integer.toString(seconds);
        }
        else {
            secondsStr = Integer.toString(seconds);
        }

        int minutes = ((timeElapsedInSeconds / 60) / 60) % 60;
        String minutesStr;
        if(minutes < 10) {
            minutesStr = "0" + Integer.toString(minutes);
        }
        else {
            minutesStr = Integer.toString(minutes);
        }

        int hours = ((timeElapsedInSeconds / 60) / 60) / 60;
        String hoursStr;
        if(hours < 10) {
            hoursStr = "0" + Integer.toString(hours);
        }
        else {
            hoursStr = Integer.toString(hours);
        }

        timeLabel.setText(hoursStr + ":" + minutesStr + ":" + secondsStr + " ");
    }
}
