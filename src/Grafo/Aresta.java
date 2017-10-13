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
public class Aresta {

    private Vertice adjacente;
    private int peso;

    public Aresta(Vertice verticeAdjacente, int peso) {
        this.adjacente = verticeAdjacente;
        this.peso = peso;
    }

    public Vertice getAdjacente() {
        return adjacente;
    }

    public void setAdjacente(Vertice adjacente) {
        this.adjacente = adjacente;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
}
