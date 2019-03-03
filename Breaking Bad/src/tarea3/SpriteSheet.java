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
public class SpriteSheet {
    private BufferedImage sheet;
    
    /**
     * Create a new SpriteSheet
     * @Param sheet the <code>image</code> with the sprites
     */
    public SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }
    
    /**
     * Crop a sprite from the SpriteSheet
     * @param x an <code> int </code> value with the x coordinate
     * @param y an <code> int </code> value with the y coordinate
     * @param width an <code> int </code> value with the width coordinate
     * @param height an <code> int </code> value with the height coordinate
     */
    public BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}
