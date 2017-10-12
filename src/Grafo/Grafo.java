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

    public void setGrafo(HashMap<Vertice, ArrayList<Aresta>> grafo){
        this.grafo = grafo;
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
                    resetColor(null);
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

        resetColor(null);
        return -1;
    }

    public int buscaProfundidade(String start, String k, ArrayList<Vertice> lista){

        buscaProfundidade(get(start), null);
        for (Vertice vertice : grafo.keySet()) {
            if(vertice.getCor().equals("B")){
                buscaProfundidade(vertice, lista);    //se o terceiro parametro é igual a null, então é porque não foi chamada de uma ordnação topologica
            }
        }

        resetColor(null);
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

    private void resetColor(HashMap<Vertice, ArrayList<Aresta>> hash){  //a hash é do proprío grafo ou não?
        HashMap<Vertice, ArrayList<Aresta>> grafo = (hash==null?this.grafo:hash);

        for (Vertice vertice : grafo.keySet()) {
            vertice.setCor("B");
            vertice.setDistancia(-1);
            vertice.setTempo(0);
        }
            tempo = 0;
    }

    private boolean containsCycle(Vertice root, Vertice adjacente, HashMap<Vertice, ArrayList<Aresta>> mst){

        LinkedList<Vertice> list = new LinkedList<>();
        root.setCor("C");
        for (Aresta aresta : mst.get(root)) {

            Vertice vertice = aresta.getVerticeAdjacente();

            if(vertice.equals(adjacente)){
                continue;
            }

            vertice.setCor("C");
            list.add(vertice);
        }

        while(!list.isEmpty()){

            Vertice vertice = list.removeFirst();

            if(!mst.containsKey(vertice)){  //se o grafo nao estiver mapeando a chave
                continue;
            }

            for (Aresta aresta : mst.get(vertice)) {
                Vertice v = aresta.getVerticeAdjacente();

                if(v.equals(adjacente)){
                    resetColor(mst);
                    return true;
                }

                if(!v.getCor().equals("B")){
                    continue;
                }

                v.setCor("C");
                list.add(v);
            } 
        }

        resetColor(mst);
        return false;
    }

    private HashMap<Integer, ArrayList<Vertice>> getVectorKruskal(){
        HashMap<Integer, ArrayList<Vertice>> vector = new HashMap<>();

        for (Vertice vertice : grafo.keySet()) {

            for (Aresta aresta : grafo.get(vertice)) {
                if(!vector.containsKey(aresta.getPeso())){
                    vector.put(aresta.getPeso(), new ArrayList<Vertice>());   
                }

                vector.get(aresta.getPeso()).add(vertice);     //todos os vertices que tem uma aresta com o peso 'i' ficam aqui
            }
        }

        for (Integer i : vector.keySet()) {
            System.out.print(i + " -> [ ");
            for(Vertice vertice : vector.get(i)){
                System.out.print(vertice.getId() + " ");
            }
            System.out.println("]");
        }

        return vector;
    }

    public HashMap<Vertice, ArrayList<Aresta>> kruskal(){

        HashMap<Integer, ArrayList<Vertice>> vector = getVectorKruskal();   //vetor com os pesos das arestas ordenados, ou mais o menos.
        HashMap<Vertice, ArrayList<Aresta>> mst = new HashMap<>();  //minimal spanning tree
        HashMap<Vertice, ArrayList<Aresta>> grafo = (HashMap<Vertice, ArrayList<Aresta>>) this.grafo.clone();
        int qtdArestas = 0;
        int n = (isGrafo?(2*this.grafo.size()-2):(this.grafo.size()));    //se for um grafo, terá uma aresta a mais para cada vertice adjacente

        for (int i = 0; qtdArestas < n;) {

            if(!vector.containsKey(i)){
                i ++;
                continue;
            }

            for (Vertice vertice1 : vector.get(i)) {                //recuperando todos os vertices que tem aresta com o peso 'i'

                for (Aresta aresta1 : grafo.get(vertice1)) {        //procurando qual aresta do vertice tem o peso 'i'

                    if(aresta1.getPeso() == i){                     //achei a aresta com o peso 'i'

                        Vertice vertice2 = null;
                        Aresta aresta2 = null;
                        if(isGrafo){                                //se for um grafo, faremos umas operacoes a mais para o algoritmo funcionar melhor. Dentre elas, a inserção das duas aresta que são mapeadas pelo para de vértices
                            vertice2 = aresta1.getVerticeAdjacente();   //recuperando o vértice adjacente

                            for (Aresta aresta : grafo.get(vertice2)) { //recuperando a outra aresta
                                if(aresta.getVerticeAdjacente().equals(vertice1)){
                                    aresta2 = aresta;
                                    break;
                                }
                            }

                            if(!mst.containsKey(vertice2)){             //mesma coisa para o vértice adjacente
                                mst.put(vertice2, new ArrayList<>());
                            }

                            mst.get(vertice2).add(aresta2);             //adicionando a outra aresta
                        }

                        if(!mst.containsKey(vertice1)){             //se nao tiver um mapeamento para o vertice, então cria
                            mst.put(vertice1, new ArrayList<>());
                        }

                        mst.get(vertice1).add(aresta1);             //adicionando a aresta no grafo

                        if(containsCycle(vertice1, vertice2, mst)){ //se criar um ciclo
                            mst.get(vertice1).remove(aresta1);      //removendo a aresta do grafo

                            if(isGrafo){
                                mst.get(vertice2).remove(aresta2);      //removendo a outra aresta
                            }
                        } else {
                            qtdArestas += (isGrafo?2:1);
                        }

                        if(isGrafo){    //se for um grafo, remove a mesma aresta
                            grafo.get(vertice2).remove(aresta2);
                        }
                    }
                }
            }
            i ++;
        }

        return mst;
    }

}