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
public class Assets {
    public static BufferedImage background; // to store background image
    public static BufferedImage proyectil;     // to store the player image
    public static BufferedImage camion;        // to store the bad image
    public static BufferedImage barra;   // to store the pokemon image
    public static SoundClip bomb;           // to store the sound of fall
    public static SoundClip coin;           // to store the sound of catch

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/Background.png");
        proyectil = ImageLoader.loadImage("/images/pikachu.png");
        camion = ImageLoader.loadImage("/images/pokeball.png");
        barra = ImageLoader.loadImage("/images/Squirtle.png");
//        box = ImageLoader.loadImage("/images/box.png");
        bomb = new SoundClip("/sounds/Metal.wav");
        coin = new SoundClip("/sounds/Coin.wav");
    }
    
}
