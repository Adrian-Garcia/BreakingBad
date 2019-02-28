/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
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
    private Proyectil proyectil;        // proyectil que se usará en el juego
    private LinkedList<Bad> camiones;   // the ammount of blocks to destroy
    private KeyManager keyManager;      // to manage the keyboard
    private MouseManager mouseManager;  // to manage the mouse
    private int vidas;                  // oportunidades de que la bola caiga

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
        vidas = 5;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        player = new Player(0, getHeight() - 30, 1, 200, 20, this);
        proyectil = new Proyectil(50,350,DiagDirection.UPRIGHT,50,50,this);
        
        // creando nuestros camiones
        camiones = new LinkedList<Bad>();
        int camWidth = 100;
        int camHeight = 50;
        for (int i = 1; i <= 50; i++) {
            int iPosX = (camWidth*i)%getWidth();
            int iPosY = camHeight*((camHeight*i)/getWidth());
            camiones.add(new Bad(iPosX, iPosY, 1, camWidth, camHeight, this));
        }
        display.getJframe().addKeyListener(keyManager);
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
    
    public Rectangle2D[] getParedes(){
        Rectangle2D[] paredes = new Rectangle2D[4];
        int padding = 10;
        paredes[0] = new Rectangle2D.Double(getWidth()-padding,padding,padding,getHeight()-2*padding);
        paredes[1] = new Rectangle2D.Double(padding,0,getWidth()-2*padding,padding);
        paredes[2] = new Rectangle2D.Double(0,padding,padding,getHeight()-2*padding);
        paredes[3] = new Rectangle2D.Double(padding,getHeight()-padding,getWidth()-2*padding,padding);
        return paredes;
    }
    
    public Player getPlayer(){
        return player;
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
    
    public int getVidas(){
        return vidas;
    }
    
    public void setVidas(int vidas){
        this.vidas = vidas;
    }

    /**
     * Control movement of all instances of the game
     */
    private void tick() {
        keyManager.tick();
        // avancing player and bad with walls collision
        player.tick();
        
        // tickeando los camiones para animarlos
        
        for(Bad camion : camiones){
            camion.tick();
            
            // revisemos que este camion no esté colisionando con nuestro proyectil
            if(proyectil.getShape().intersects(camion.getPerimetro())){
                // hay una colisión, ¿qué hacemos?
                
                Rectangle2D[] bordes = camion.getBordes();
                for(int i=0; i<bordes.length; i++){
                    if(proyectil.getShape().intersects(bordes[i])){
                        switch(i){
                            case 2:
                                proyectil.chocar(RectDirection.RIGHT);
                                break;
                            case 3:
                                proyectil.chocar(RectDirection.UP);
                                break;
                            case 0:
                                proyectil.chocar(RectDirection.LEFT);
                                break;
                            case 1:
                                proyectil.chocar(RectDirection.DOWN);
                                break;
                        }
                    }
                }
                
                camion.explotar();
            }
        }
        
        if(proyectil.getShape().intersects(player.getPerimetro())){
            Rectangle2D[] bordes = player.getBordes();
            for(int i=0; i<bordes.length; i++){
                if(proyectil.getShape().intersects(bordes[i])){
                    switch(i){
                        case 2:
                            proyectil.chocar(RectDirection.RIGHT);
                            break;
                        case 3:
                            proyectil.chocar(RectDirection.UP);
                            break;
                        case 0:
                            proyectil.chocar(RectDirection.LEFT);
                            break;
                        case 1:
                            proyectil.chocar(RectDirection.DOWN);
                            break;
                    }
                }
            }
        }
        
        proyectil.tick();
        
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

            for(Bad camion : camiones){
                camion.render(g);
            }
            
            proyectil.render(g);

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
