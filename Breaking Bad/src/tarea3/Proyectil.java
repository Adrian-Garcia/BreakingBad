/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Diego
 */
public class Proyectil extends Item{
    
    private DiagDirection direction;
    private int width;
    private int height;
    private Game game;
    private int speed;
    
    /**
     * Bad constructor
     * @param x
     * @param y
     * @param direction
     * @param width
     * @param height
     * @param game 
     */
    public Proyectil(int x, int y, DiagDirection direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.direction = direction;
        this.speed = 15;
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
        
        switch(direction){
            case UPRIGHT:
                setX(getX()+speed);
                setY(getY()-speed);
                break;
            case UPLEFT:
                setX(getX()-speed);
                setY(getY()-speed);
                break;
            case DOWNLEFT:
                setX(getX()-speed);
                setY(getY()+speed);
                break;
            case DOWNRIGHT:
                setX(getX()+speed);
                setY(getY()+speed);
                break;
        }
        
        Rectangle2D[] paredes = game.getParedes();
        for(int i=0; i<paredes.length; i++){
            if(getShape().intersects(paredes[i])){
                switch(i){
                    case 0:
                        if(direction == DiagDirection.UPRIGHT){
                            direction = DiagDirection.UPLEFT;
                        } else if(direction == DiagDirection.DOWNRIGHT){
                            direction = DiagDirection.DOWNLEFT;
                        }
                        break;
                    case 1:
                        if(direction == DiagDirection.UPLEFT){
                            direction = DiagDirection.DOWNLEFT;
                        } else if(direction == DiagDirection.UPRIGHT){
                            direction = DiagDirection.DOWNRIGHT;
                        }
                        break;
                    case 2:
                        if(direction == DiagDirection.UPLEFT){
                            direction = DiagDirection.UPRIGHT;
                        } else if(direction == DiagDirection.DOWNLEFT){
                            direction = DiagDirection.DOWNRIGHT;
                        }
                        break;
                    case 3:
                        if(direction == DiagDirection.DOWNLEFT){
                            direction = DiagDirection.UPLEFT;
                        } else if(direction == DiagDirection.DOWNRIGHT){
                            direction = DiagDirection.UPRIGHT;
                        }
                        break;
                }
            }
        }
    }
    
    // ¿Qué hacemos cuando nuestro proyectil choca?
    public void chocar(RectDirection impact){
        switch(direction){
            case UPRIGHT:
                if(impact == RectDirection.UP){
                    direction = DiagDirection.DOWNRIGHT;
                } else if(impact == RectDirection.RIGHT){
                    direction = DiagDirection.UPLEFT;
                }
                break;
            case UPLEFT:
                if(impact == RectDirection.UP){
                    direction = DiagDirection.DOWNLEFT;
                } else if(impact == RectDirection.LEFT){
                    direction = DiagDirection.UPRIGHT;
                }
                break;
            case DOWNLEFT:
                if(impact == RectDirection.DOWN){
                    direction = DiagDirection.UPLEFT;
                } else if(impact == RectDirection.LEFT){
                    direction = DiagDirection.DOWNRIGHT;
                }
                break;
            case DOWNRIGHT:
                if(impact == RectDirection.DOWN){
                    direction = DiagDirection.UPRIGHT;
                } else if(impact == RectDirection.RIGHT){
                    direction = DiagDirection.DOWNLEFT;
                }
                break;
        }
    }
    
    /**
     * Get perimeter of bad for collisions
     * @return 
     */
    public Ellipse2D.Double getShape() {
        return new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * Render the image of bad (A Pokeball)
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.pikachu, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
