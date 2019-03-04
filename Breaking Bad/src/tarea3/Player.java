/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author adria
 */
public class Player extends Item{

    private int width;
    private int height;
    private Game game;
    private int speed;
    private int vidas;
    private int score = 0;
    private boolean pause = false; 
    
    /**
     * Player constructor
     * @param x
     * @param y
     * @param direction
     * @param width
     * @param height
     * @param game 
     */
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.speed = 9;
        vidas = 5;
    }

    /**
     * Get the Height
     * @return an <code>integer</code> with the Width value
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the Height
     * @return an <code>integer</code> with the Height value
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the Width
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Set the Height
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public boolean getPause() {
        return pause;
    }
    
    public void setPause(boolean pause) {
        this.pause = pause;
    }
    
    /**
     * Control the player movement 
     */
    @Override
    public void tick() {
        
        if (game.pause)
            return;
            
        // moving player depending on flags
        if (game.getKeyManager().left && getX()>=0) {
           setX(getX() - speed);
        }
        if (game.getKeyManager().right && getX()+getWidth()<=game.getWidth()) {
           setX(getX() + speed);
        }
    }
    
    /**
     * Calculates the perimeter of the player according to the Width and
     * Height of it. 
     * @return Rectangle perimeter
     */
    public Rectangle getPerimetro() {
        return new Rectangle (getX(), getY(), getWidth(), getHeight());
    }
    
    
    /**
     * Validate if there was an intersection 
     * @param obj
     * @return intersection
     */
    public boolean intersecta(Object obj) {
                                                                //Castea
        return obj instanceof Bad && getPerimetro().intersects(((Bad) obj).getPerimetro());
    }
    
    public Rectangle2D[] getBordes(){
        Rectangle2D[] bordes = new Rectangle2D[4];
        bordes[0] = new Rectangle2D.Double(getX()+getWidth(),getY(),1,getHeight());
        bordes[1] = new Rectangle2D.Double(getX(),getY()-1,getWidth(),1);
        bordes[2] = new Rectangle2D.Double(getX()-1,getY(),1,getHeight());
        bordes[3] = new Rectangle2D.Double(getX(),getY()+getHeight(),getWidth(),1);
        return bordes;
    }

    /**
     * render the image of the player 
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.barra, getX(), getY(), getWidth(), getHeight(), null);
    }
}
