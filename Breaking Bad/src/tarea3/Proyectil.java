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
    private int stun;
    private Animation animation;    // to store the animation of the ball
    private boolean pause = false;  
    
    /**
     * Proyectil constructor
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
        this.stun = 0;
        this.animation = new Animation(Assets.proyectil, 100);
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
     * Getter of Stun
     * @return stun
     */
    public int getStun(){
        return stun;
    }
    
    /**
     * Setter of stun
     * @param stun 
     */
    public void setStun(int stun){
        this.stun = stun;
    }
    
    
    /**
     * Getter for pause
     * @return pause 
     */
    public boolean getPause() {
        return pause;
    }

    /**
     * Setter for pause
     * @param pause 
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }
    
    /**
     * Control bad movement
     */
    @Override
    public void tick() {
        
        if (game.pause)
            return;
        
        this.animation.tick();
        
        if(stun <= 0){
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
        } else {
            stun -= 1;
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
                        // Finalizar el juego
                        game.setVidas(game.getVidas()-1);
                        x = game.getPlayer().getX() + game.getPlayer().getWidth()/2;
                        y = game.getPlayer().getY() - 50;
                        direction = DiagDirection.UPRIGHT;
                        stun = 50;
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
    
    // Guarda la información del objeto en un string
    public String toString(){
        return (x+" "+y+" "+width+" "+height+" "+speed);
    }
    
    // Se encarga de guardar en un archivo toda la informacion de nuestra partida
    public void loadFromString(String[] datos) {
        this.x = Integer.parseInt(datos[0]);
        this.y = Integer.parseInt(datos[1]);
        this.width = Integer.parseInt(datos[2]);
        this.height = Integer.parseInt(datos[3]);
        this.speed = Integer.parseInt(datos[4]);
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
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
}
