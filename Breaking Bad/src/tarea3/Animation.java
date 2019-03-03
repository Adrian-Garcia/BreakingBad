/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.awt.image.BufferedImage;

/**
 *
 * @author adria
 */
public class Animation {
    private int speed;          // for the speed of evey frame
    private int index;          // to get the index of next frame to paint
    private long lastTime;      // to save the previous time of the animation
    private long timer;         // to store every image - frame
    private BufferedImage[] frames;     //to store every image
    
    /**
     * Creating the animation with all the frames and the speed for each
     * @param frames an <code>array</code> of images
     * @param speed an <code>int</code> value for the speed of every frame
     */
    public Animation(BufferedImage[] frames, int speed) {
        this.frames = frames;   // storing frames 
        this.speed = speed;     // storing speed
        index = 0;              // initializing index
        timer = 0;              // initializing timer
        lastTime = System.currentTimeMillis();  // getting the initial time
    }
    
    /**
     * Getting the current frame to paint
     * @return the <code>BufferedImage</code> to the corresponding frame to paint
     */
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
    
    /**
     * To update the animation to get the right index of the frame to paint
     */
    public void tick() {
        
        // acumulating time from the previous tick to this one
        timer += System.currentTimeMillis() - lastTime;
        // updating the lastTime for the next tick
        lastTime = System.currentTimeMillis();
        // check the timer to increase the index
        if (timer > speed) {
            index++;
            timer = 0;
            
            //check index not to get out of the bounds
            if (index >= frames.length) {
                index = 0;
            }
        }
    }
}
