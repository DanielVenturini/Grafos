/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author venturini
 */
public class Grafo{

    HashMap<Vertice, ArrayList<Aresta>> grafo;
    private final boolean isGrafo;
    private int tempo;  //usado na função busca em profundidade

        public Grafo(boolean isGrafo){
            grafo = new HashMap<>();
            this.isGrafo = isGrafo;
            tempo = 0;
	}

    public HashMap<Vertice, ArrayList<Aresta>> getGrafo(){
        return grafo;
    }

    public boolean getIsGrafo(){
        return isGrafo;
    }

    private Vertice get(String vertice){
        for(Vertice v : grafo.keySet()){
            if(v.getId().equals(vertice)){
                return v;
            }
        }
        return null;
    }

    public void addVertice(String vertice){
        if(grafo.get(vertice) != null){
            return;
        }

	grafo.put(new Vertice(vertice), new ArrayList<>());
    }

    public boolean addAresta(String v1, String v2, int peso){

        Vertice v = get(v1);
        Vertice vv = get(v2);

        if(v == null){
            addVertice(v1);
            v = get(v1);
	}

        if(vv == null){
            addVertice(v2);
            vv = get(v2);
	}

        Aresta aresta = new Aresta(vv, peso);
        grafo.get(v).add(aresta);

        if(isGrafo){
            aresta = new Aresta(v, peso);
            grafo.get(vv).add(aresta);
        }

        return true;
    }

    public void print(){
        for(Vertice key : grafo.keySet()){
            System.out.print(key.getId() + " -> [ ");
            for(Aresta aresta : grafo.get(key)){
                System.out.print(aresta.getVerticeAdjacente().getId() + " ");
            }
            System.out.println("]");
	}
        System.out.println("");
    }

    public int buscaLargura(String f, String k){

        Vertice first = get(f);
        Vertice key = get(k);

        first.setDistancia(0);
        LinkedList<Vertice> list = new LinkedList<>();
        list.add(first);

        while(!list.isEmpty()){

            first = list.removeFirst();
            ArrayList<Aresta> collection = grafo.get(first);
            first.setCor("P");

            for(Aresta aresta : collection){        //Vendo se o vertice procurado é adjacente aqui
                if(aresta.getVerticeAdjacente().getId().equals(k)){
                    int value = first.getDistancia()+1;
                    resetColor();
                    return value;
                }
            }

            for(Aresta aresta : collection){
                Vertice v = aresta.getVerticeAdjacente();

                if(v.getCor().equals("B")){
                    v.setDistancia(first.getDistancia()+1);
                    v.setCor("C");
                    list.addLast(v);
                }
            }
        }

        resetColor();
        return -1;
    }

    public int buscaProfundidade(String start, String k, ArrayList<Vertice> lista){

        buscaProfundidade(get(start), null);
        for (Vertice vertice : grafo.keySet()) {
            if(vertice.getCor().equals("B")){
                buscaProfundidade(vertice, lista);    //se o terceiro parametro é igual a null, então é porque não foi chamada de uma ordnação topologica
            }
        }

        resetColor();
        return get(k).getTempo();
    }

    private int buscaProfundidade(Vertice vertice, ArrayList<Vertice> lista){    //se lista for diferente de null, então foi chamada de uma função de ordenaçao topológica
        tempo ++;
        vertice.setTempo(tempo);
        vertice.setCor("C");

        for (Aresta aresta : grafo.get(vertice)) {
            Vertice v = aresta.getVerticeAdjacente();
            if(v.getCor().equals("B"))
                tempo = buscaProfundidade(v, lista);
        }

        vertice.setCor("P");
        tempo ++;
        vertice.setTempo(tempo);

        if(lista != null)
            lista.add(0, vertice);

        return tempo;
    }

    public ArrayList<Vertice> ordenacaoTopologica(){

        ArrayList<Vertice> array = new ArrayList<>();
        for (Vertice vertice : grafo.keySet()) {
            if(vertice.getCor() != "B"){
                continue;
            }

            buscaProfundidade(vertice, array);
        }
        
        return array;
    }

    private void resetColor(){
        for (Vertice vertice : grafo.keySet()) {
            vertice.setCor("B");
            vertice.setDistancia(-1);
            vertice.setTempo(0);
            tempo = 0;
        }
    }

}