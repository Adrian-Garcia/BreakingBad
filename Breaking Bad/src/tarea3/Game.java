/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 *
 * @author adria
 */
public class Game implements Runnable {

    private BufferStrategy bs;          // to have several buffers when displaying
    private Graphics g;                 // to paint objects
    private Display display;            // to display in the game
    String title;                       // title of the window
    private int width;                  // width of the window
    private int height;                 // height of the window
    private Thread thread;              // thread to create the game
    private boolean running;            // to set the game
    private Player player;              // to use a player
    private Box box;                    // to use collisions
    private LinkedList<Bad> bads;       // to use bad guys
    private KeyManager keyManager;      // to manage the keyboard
    private MouseManager mouseManager;  // to manage the mouse
    public int lives = 5;               // lives of the game
    public int counter = 0;             // suportive counter of the game
    public int score = 0;               // score of the game
    public boolean b = false;           // to accelerate the bads

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        bads = new LinkedList<Bad>();
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set title of the game
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        player = new Player(0, getHeight() - 100, 1, 100, 100, this);
        box = new Box(20, getHeight() - 100, 1, 40, 1, this);
        display.setTitle("Lives = " + lives + "     Score = " + score);
        //adding elements to bads
        int iNum = (int) (Math.random() * 8 + 5);
        for (int i = 1; i <= iNum; i++) {
            int iPosX = (int) (Math.random() * getWidth() - 70);
            int iPosY = (int) (Math.random() * getHeight() - 500);
            bads.add(new Bad(iPosX, iPosY, 1, 100, 100, this));
        }
        display.getJframe().addKeyListener(keyManager);
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }

    /**
     * Run the game 
     */
    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }
    
    /**
     * Used to control keys
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * Used to control mouse
     * @return 
     */
    public MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     * Control movement of all instances of the game
     */
    private void tick() {
        keyManager.tick();
        // avancing player and bad with walls collision
        player.tick();
        box.tick();
        display.getJframe().setTitle("Lives: " + lives + "     Score: " + score);
        //ticking the bad guys
        for (int i = 0; i < bads.size(); i++) {
            
            Bad bad = bads.get(i);
            
            if (b) {
                bad.setSpeed(bad.getSpeed() + 3);
            }
            
            bad.tick();
            // checking collition between player and bad
            if (box.intersecta(bad)) {
                int iPosX = (int) (Math.random() * getWidth() - 70);
                int iPosY = (int) (Math.random() * getHeight() - 500);
                bad.setX(iPosX);
                bad.setY(iPosY);
                Assets.coin.play();
                score+=100;
            }
            
            //Bad collides with the flor
            if (bad.floor) {
                
                bad.floor = false;
                score-=20;
                counter++;
                if (counter >= 10) {
                    lives--;
                    bad.setSpeed(bad.getSpeed()+1);
                    counter = 0;
                    b = true;
                }
                
                //Change Bad Position
                int iPosX = (int) (Math.random() * getWidth() - 70);
                int iPosY = (int) (Math.random() * getHeight() - 500);
                bad.setX(iPosX);
                bad.setY(iPosY);
                
                //If we ran out of lives, game is over
                if (lives <= 0) {
                    stop();
                }

                //Bomb sound
                Assets.bomb.play();
            }

            //Bad collides with a "wall"
            if (bad.side) {
                bad.side = false;
                int iPosX = (int) (Math.random() * getWidth() - 70);
                int iPosY = (int) (Math.random() * getHeight() - 500);
                bad.setX(iPosX);
                bad.setY(iPosY);
            }
        }
        b = false;  
    }

    /**
     * render de game, display images
     */
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            box.render(g);

            for (int i = 0; i < bads.size(); i++) {
                Bad bad = bads.get(i);
                bad.render(g);
            }

            bs.show();
            g.dispose();
        }

    }

    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            System.exit(0);
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
