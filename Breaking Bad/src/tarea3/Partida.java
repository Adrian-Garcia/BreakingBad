/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea3;

import java.util.LinkedList;

/**
 *
 * @author Diego
 */
public class Partida {
    
    private Player player;
    private Proyectil  proyectil;
    private LinkedList<Bad> camiones;
    
    public Partida(Player player, Proyectil proyectil, LinkedList<Bad> camiones){
        this.player = player;
        this.proyectil = proyectil;
        this.camiones = camiones;
    }
    
    public Player getPlayer(){
        return  this.player;
    }
    
    public Proyectil getProyectil(){
        return  this.proyectil;
    }
    
    public LinkedList<Bad> getCamiones(){
        return  this.camiones;
    }
}
