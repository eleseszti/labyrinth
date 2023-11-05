package labyrinth;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

/**
 * @author Éles Eszter
 * @version 1.0.0 Dec 13, 2022.
 */
public class LabyrinthGUI {
    private JFrame frame;
    private GameEngine gameArea;
    
    public LabyrinthGUI() {
        frame = new JFrame("Labyrinth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon img = new ImageIcon("data/images/icon.png");
        frame.setIconImage(img.getImage());
        
        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);
        
        frame.setPreferredSize(new Dimension(644, 690));
        frame.setResizable(false);
        
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu = new JMenu("Game");
        menubar.add(menu);
        menu.setFocusable(false);
        
        JMenuItem newGame = new JMenuItem("New game");
        menu.add(newGame);
        
        menubar.add(Box.createHorizontalGlue());
        
        JLabel levelLabel = new JLabel();
        levelLabel.setFocusable(false);
        menubar.add(levelLabel);
        gameArea.setLevelLabel(levelLabel);
        
        JLabel timeLabel = new JLabel();
        timeLabel.setFocusable(false);
        menubar.add(timeLabel);
        gameArea.setTimeLabel(timeLabel);
        gameArea.startTimer();
        
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameArea.stopTimer();
                frame.getContentPane().remove(gameArea);
                gameArea = new GameEngine();
                frame.getContentPane().add(gameArea);
                gameArea.setTimeLabel(timeLabel);
                gameArea.setLevelLabel(levelLabel);
                gameArea.startTimer();
                gameArea.requestFocus();
                
                frame.pack();
                frame.setLocationRelativeTo(null);
            }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
