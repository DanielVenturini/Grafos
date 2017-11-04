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
                System.out.print(aresta.getAdjacente().getId() + " ");
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
                if(aresta.getAdjacente().getId().equals(k)){
                    int value = first.getDistancia()+1;
                    resetColor(null);
                    return value;
                }
            }

            for(Aresta aresta : collection){
                Vertice v = aresta.getAdjacente();

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
            Vertice v = aresta.getAdjacente();
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

            Vertice vertice = aresta.getAdjacente();

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
                Vertice v = aresta.getAdjacente();

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
                            vertice2 = aresta1.getAdjacente();   //recuperando o vértice adjacente

                            for (Aresta aresta : grafo.get(vertice2)) { //recuperando a outra aresta
                                if(aresta.getAdjacente().equals(vertice1)){
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

    private Vertice getNextPrim(LinkedList<Vertice> list){

        Vertice next = list.get(0);
        int posNext = 0;
        int pos = 0;
        for (Vertice vertice : list) {
            if(vertice.getPrim() < next.getPrim()){
                next = vertice;
                posNext = pos;
            }

            pos ++;
        }

        list.remove(posNext);
        return next;
    }

    private Vertice getAdjacentePrim(HashMap<Vertice, ArrayList<Aresta>> mst, Vertice vertice){
        for (Vertice v : mst.keySet()) {
            for(Aresta aresta : grafo.get(v)){
                if(aresta.getAdjacente().equals(vertice) && aresta.getPeso() == vertice.getPrim()){ //tem que saber o valor da aresta, pois o vertice pode ter mais de um adjacente e acontecer de recuperar o errado
                    return v;
                }
            }
        }

        return null;
    }

    public HashMap<Vertice, ArrayList<Aresta>> prim(){
        HashMap<Vertice, ArrayList<Aresta>> mst = new HashMap<>();

        LinkedList<Vertice> list = new LinkedList<>();
        for (Vertice vertice : grafo.keySet()) {
            list.add(vertice);
        }

        System.out.println(list);
        int n = list.size();
        Vertice vertice1 = list.removeFirst();
        vertice1.setPrim(0);

        while(true){         //como o proximo vertice é recuperado lá em baixo, aqui é melhor deixar como true

            mst.put(vertice1, new ArrayList<>());                   //adicionando o vertice na MST
            System.out.println("ADICIONADO O VERTICE " + vertice1);

            //atualizando todos os adjacentes do 'vertice1'
            System.out.println("  ATUALIZANDO OS ADJACENTES");
            for (Aresta aresta : grafo.get(vertice1)) {             //iterando em todos os adjacentes
                if(mst.containsKey(aresta.getAdjacente())){         //se já tiver na MST
                    continue;
                }

                if(aresta.getPeso() < aresta.getAdjacente().getPrim()){     //se o valor for menor do que o atual
                    aresta.getAdjacente().setPrim(aresta.getPeso());        //atualiza o valor
                }
            }

            if(mst.size() == 1){            //quando size é igual a 1, não tem mais o que fazer, somente voltar ao inicio
                vertice1 = getNextPrim(list);
                continue;
            }

            //na verdade, o vertice2 é que mapeia o vertice1
            Vertice vertice2 = getAdjacentePrim(mst, vertice1);     //recuperando o vertice adjacente ao 'vertice1' para o adicionar na 
            System.out.println("  RECUPERADO O ADJACENTE " + vertice2);
            if(vertice2 == null){       //quando for um digrafo, pode ser que ninguem esteja mapeando o vertice2
                if(list.size() == 0){
                    return mst;
                }
                vertice1 = getNextPrim(list);
                continue;
            }

            if(!mst.containsKey(vertice2)){
                mst.put(vertice2, new ArrayList<>());
            }
 
            Aresta aresta1 = null;
            Aresta aresta2 = null;
            //quando é um digrafo, as arestas estão invertidas, então sempre vamos precisar da aresta2
            for (Aresta aresta : grafo.get(vertice2)) {
                if(aresta.getAdjacente().equals(vertice1)){
                    aresta2 = aresta;
                    break;
                }
            }

            mst.get(vertice2).add(aresta2);     //já adiciona a aresta aqui

            if(isGrafo){        //se for um grafo, já recupera a aresta que é a mesma, só que mapeando do 'outro lado'
                for (Aresta aresta : grafo.get(vertice1)) {     //recuperando a primeira aresta, pois mesmo se for um digrafo, precisaremos dela
                    if(aresta.getAdjacente().equals(vertice2)){
                        aresta1 = aresta;
                        break;
                    }
                }
                mst.get(vertice1).add(aresta1);         //adicionando a primeira aresta
            }

            System.out.println("  ADICIONADO A ARESTA [" + vertice2.getId() + "," + vertice1.getId() + "]");
            if(containsCycle(vertice2, vertice1, mst)){     //se gerou um ciclo, então desfaz
                mst.get(vertice2).remove(aresta2);

                if(isGrafo){
                    mst.get(vertice1).remove(aresta1);
                }
            }

            if(mst.size() == n){    //não tem mais vertice para recuperar
                return mst;
            }

            vertice1 = getNextPrim(list);       //recupera o proximo vertice e volta para o começo
        }
    }

    private HashMap<Vertice, Integer> getDistancias(){
        HashMap<Vertice, Integer> distancias = new HashMap<>();

        for (Vertice vertice : grafo.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
        }

        return distancias;
    }

    private void printDijkstra(HashMap<Vertice, ArrayList> dijkstra){
        for (Vertice vertice : dijkstra.keySet()) {
            System.out.println(vertice + "-> distancia: " + dijkstra.get(vertice) + "; predecessor: " + vertice.getPredecessor());
        }
    }

    private Vertice getNextDijkstra(HashMap<Vertice, Integer> dijkstra){

        int minimo = Integer.MAX_VALUE;
        Vertice v = null;
        for (Vertice vertice : dijkstra.keySet()) {
            if(!vertice.getCor().equals("P") && dijkstra.get(vertice) <= minimo){
                minimo = dijkstra.get(vertice);
                v = vertice;
            }
        }

        return v;
    }
    
    private HashMap<Vertice, ArrayList<Aresta>> makeDijkstra(HashMap<Vertice, Integer> dijkstra){

        HashMap<Vertice, ArrayList<Aresta>> mst = new HashMap<>();

        for (Vertice vertice : grafo.keySet()) {

            if(vertice.getPredecessor() == null){
                continue;
            }

            if(!mst.containsKey(vertice.getPredecessor())){
                mst.put(vertice.getPredecessor(), new ArrayList<>());
            }

            mst.get(vertice.getPredecessor()).add(new Aresta(vertice, dijkstra.get(vertice)));
        }

        return mst;
    }

    public HashMap<Vertice, ArrayList<Aresta>> dijkstra(String v){

        Vertice inicial = null;
        for (Vertice vertice : grafo.keySet()) {
            if(vertice.getId().equals(v)){
                inicial = vertice;
                break;
            }
        }

        if(inicial == null){
            return new HashMap<>();
        }

        HashMap<Vertice, Integer> dijkstra = getDistancias();
        Vertice ultimo;

        dijkstra.put(inicial, 0);           //setando o tempo
        Vertice u = inicial;

        for(int i = 0; i < grafo.size(); i ++){
            u.setCor("P");

            for (Aresta aresta : grafo.get(u)) {                //iterando nos adjacentes
                if(aresta.getAdjacente().getCor().equals("P")){
                    continue;
                }

                int peso = dijkstra.get(u)+aresta.getPeso();          //realilzando a soma do peso do vertice
                if(peso < dijkstra.get(aresta.getAdjacente())){       //relaxando o vertice

                    dijkstra.put(aresta.getAdjacente(), peso);                  //atualizando o peso
                    aresta.getAdjacente().setPredecessor(u);                    //setando o novo predecessor
                }
            }

            ultimo = u;
            u = getNextDijkstra(dijkstra);
            if(u == null){
                return makeDijkstra(dijkstra);
            }
        }

    return null;
    }
}