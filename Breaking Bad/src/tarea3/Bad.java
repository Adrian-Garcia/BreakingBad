/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.awt.Graphics;
import java.awt.Rectangle;

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

    /**
     * Control bad movement
     */
    @Override
    public void tick() {
        
        //Goes down
        setY(getY()+getSpeed());
        
        // reset x position and y position if colision
        if (getX() + 60 >= game.getWidth()) {                   //Right
            setX(game.getWidth() - 60);
            side = true;
        }
        else if (getX() <= -30) {                               //Left
            setX(-30);
            side = true;
        }
        else if (getY() + 80 >= game.getHeight()) {             //Down
            setY(game.getHeight() - 80);
            floor = true;
        }
    }
    
    /**
     * Get perimeter of bad for collisions
     * @return 
     */
    public Rectangle getPerimetro() {
        return new Rectangle (getX(), getY(), getWidth(), getHeight()-30);
    }
    
    /**
     * Render the image of bad (A Pokeball)
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.pokeball, getX(), getY(), getWidth(), getHeight(), null);
    }
}
