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
public class Bad extends Item{

    private int direction;
    private int width;
    private int height;
    private Game game;
    private int speed = 3;
    public boolean floor = false;
    public boolean side = false;
    
    /**
     * Bad constructor
     * @param x
     * @param y
     * @param direction
     * @param width
     * @param height
     * @param game 
     */
    public Bad(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
    }
    
    /**
     * Getter of direction
     * @return direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Getter of width
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter of Height
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Getter of Speed
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Setter of direction
     * @param direction 
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Setter of Width
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Setter of Height
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * Setter of speed
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
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
     * Control bad movement
     */
    @Override
    public void tick() {
    }
    
    
    // Este método se activa cuando el camión explota
    public void explotar(){
        setX(-50);
        setY(-50);
    }
    
    /**
     * Get perimeter of bad for collisions
     * @return 
     */
    public Rectangle getPerimetro() {
        return new Rectangle (getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * Render the image of bad (A Pokeball)
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.camion, getX(), getY(), getWidth(), getHeight(), null);
    }
}
