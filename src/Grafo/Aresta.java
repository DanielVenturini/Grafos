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

    private Vertice verticeAdjacente;
    private int peso;

    public Aresta(Vertice verticeAdjacente, int peso) {
        this.verticeAdjacente = verticeAdjacente;
        this.peso = peso;
    }

    public Vertice getVerticeAdjacente() {
        return verticeAdjacente;
    }

    public void setVerticeAdjacente(Vertice verticeAdjacente) {
        this.verticeAdjacente = verticeAdjacente;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
    
}
