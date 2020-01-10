package main;

// Lots of imports haha
import graphics.Information;
import graphics.UserEntity;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import maps.MapMaker;
import maps.MapManager;

/**
 * SimulationPanel is the class that creates and starts the simulation. It
 * contains the event loop that keeps the simulation running.
 *
 * @author Matthew
 */
public class SimulationPanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    // number of tiles on the screen.
    // Must be a perfect square.
    public static final int SIZE = 324;

    private Thread thread;
    private boolean running = true;
    private boolean paused = false;
    private BufferedImage image;
    private Graphics2D g;
    private int fps = 1000;

    private MapManager world;
    private UserEntity user;

    // Constructor inherits constructor of its super class JPanel.
    public SimulationPanel() {
        super();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
    }

    // Thread manipulation method.
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    // Because we use a new thread for the simulation, the run method contains
    // the event loop.
    @Override
    public void run() {

        // We use key bindings instead of listeners as bindings dont require
        // focus on a certain panel or component.
        // We add the UserEntity's moveEntity function and specify which direction
        // as a parameter.
        addKeyBinding(this, KeyEvent.VK_UP, "Up", (evt) -> {
            UserEntity.moveEntity(0);
        });
        addKeyBinding(this, KeyEvent.VK_RIGHT, "Right", (evt) -> {
            UserEntity.moveEntity(1);
        });
        addKeyBinding(this, KeyEvent.VK_DOWN, "Down", (evt) -> {
            UserEntity.moveEntity(2);
        });
        addKeyBinding(this, KeyEvent.VK_LEFT, "Left", (evt) -> {
            UserEntity.moveEntity(3);
        });
        addKeyBinding(this, KeyEvent.VK_SPACE, "Space", (evt) -> {
            paused = paused ? false : true;
        });

        // The entire GUI is a Graphics2D component g, which is taken from a blank image.
        // Throughout the code, this g variable will be manipulated to display graphics.
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // variables needed for the event loop
        long startTime;
        long URDTimeMillis;
        long waitTime;
        long targetTime = 1000 / fps;

        // The backend classes such as a Map, MapManager, MapMaker and UserEntity are initialized.
        world = new MapManager(MapMaker.createTestMap(SIZE));
        user = new UserEntity();

        // The Event Loop. Will continue forever at a constant tickrate(framerate) until closed.
        while (running) {
            startTime = System.nanoTime();
            g.setColor(new Color(196, 182, 179));
            g.fillRect(0, 0, WIDTH, HEIGHT);

            // When paused is true, the game doesn't update.
            if(!paused){
                // These commands 'update' the GUI with new data every tick of the loop.
                world.updateEntities(world);
            }
            
            g = world.drawGround(g);
            g = world.drawEntities(g);
            g = user.drawUserEntity(g);
            Information.update();
            g = Information.draw(g);
            mainDraw();

            
            // Regulates the tickrate.
            URDTimeMillis = (System.nanoTime() - startTime) / 1_000_000;
            waitTime = targetTime - URDTimeMillis;
            // If the game ever freezes, comment out the sleep command below
            try {
                Thread.sleep(waitTime); // DO NOT UNCOMMENT THIS
            } catch (Exception e) {
            }
        }
    }

    // Draws g onto the GUI.
    private void mainDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    // Creates the Key Bindings. Very advanced function, not my own work. Used a tutorial for this.
    private void addKeyBinding(JComponent comp, int keyCode, String id, ActionListener actionListener) {
        InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap ap = comp.getActionMap();

        im.put(KeyStroke.getKeyStroke(keyCode, 0, false), id);

        ap.put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });
    }
}
