/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;

/**
 *
 * @author Daniel
 */
public class Vertice {

    private final String id;
    private String cor;
    private int distancia;
    private int tempo;
    private int prim;
    private Vertice predecessor;

    public Vertice(String id) {
        this.id = id;
        cor = "B";
        tempo = 0;
        distancia = -1;
        prim = Integer.MAX_VALUE;
        predecessor = null;
    }

    public String getId() {
        return id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getPrim() {
        return prim;
    }

    public void setPrim(int prim) {
        this.prim = prim;
    }

    @Override
    public String toString(){
        return id;
    }

    public Vertice getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertice predecessor) {
        this.predecessor = predecessor;
    }

}
