/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author adria
 */
public class KeyManager implements KeyListener {
    
    public boolean left;    // flag to move left the player
    public boolean right;   // flag to move right the player
    public boolean p;       // flag to pause the game
    public boolean g;       // flag to save the game
    public boolean c;       // flag to load the game

    private boolean keys[];  // to store all the flags for every key
    
    public KeyManager() {
        keys = new boolean[256];
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        keys[e.getKeyCode()] = false;
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        left = keys[KeyEvent.VK_LEFT]||keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_RIGHT]||keys[KeyEvent.VK_D];
        p = keys[KeyEvent.VK_P]||keys[KeyEvent.VK_ENTER]||keys[KeyEvent.VK_SPACE];
        g = keys[KeyEvent.VK_G];
        c = keys[KeyEvent.VK_C];
    }
}
