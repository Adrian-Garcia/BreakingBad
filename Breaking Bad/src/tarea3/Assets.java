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
    public static BufferedImage background;    // to store background image
    public static BufferedImage sprites;        // to store the sprites
    public static BufferedImage proyectil[];     // to store the player image
    public static BufferedImage camion;        // to store the bad image
    public static BufferedImage barra;         // to store the pokemon image
    public static BufferedImage box;           // to store box image 
    public static BufferedImage gameOver;      // to store image of gameOver
    public static BufferedImage gameWin;      // to store image of gameOver
    public static SoundClip bomb;              // to store the sound of fall
    public static SoundClip coin;              // to store the sound of catch
    

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/Background.png");
        camion = ImageLoader.loadImage("/images/Truck.png");         //Camion
        barra = ImageLoader.loadImage("/images/Paddle.png");          //Barra
        box = ImageLoader.loadImage("/images/box.png");
        gameOver = ImageLoader.loadImage("/images/gameOver.jpg");
        gameWin = ImageLoader.loadImage("/images/Win.jpg");
        bomb = new SoundClip("/sounds/Metal.wav");
        coin = new SoundClip("/sounds/Coin.wav");
        
        // getting sprites from the picture
        sprites = ImageLoader.loadImage("/images/Ball.png");
        
        // creating array of images before animations
        SpriteSheet spritesheet = new SpriteSheet(sprites);
        proyectil = new BufferedImage[4];
        
        for (int i=0; i<4; i++) {
            proyectil[i] = spritesheet.crop(i*32, 0, 32, 32);
        }
    }
}
