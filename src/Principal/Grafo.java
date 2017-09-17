/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author venturini
 */
public class Grafo{

    HashMap<Vertice, ArrayList<Vertice>> grafo;

        public Grafo(){
            grafo = new HashMap<>();
	}

    public HashMap<Vertice, ArrayList<Vertice>> getGrafo(){
        return grafo;
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
        if(get(vertice) != null){
            return;
        }
	grafo.put(new Vertice(vertice), new ArrayList<>());
    }

//    public boolean removeVertice(String vertice){
//
//        if(grafo.containsKey(vertice)){
//
//            if(grafo.containsValue(vertice)){
//
//                String[] mapped = keys();
//
//                for(String value : mapped){
//                    removeAresta(vertice, value);
//                }
//            }
//
//            grafo.remove(vertice);
//            return true;
//        }
//
//       return false; 
//    }

    public boolean addAresta(String v1, String v2){
        
        Vertice v = get(v1);
        if(v1 == null){
            return false;
	}

        Vertice vv = get(v2);
        if(vv == null){
            return false;
	}

	if(grafo.get(v).contains(vv)){
            return false;
        }

        grafo.get(v).add(vv);
	grafo.get(vv).add(v);

        return true;
    }

    public boolean removeAresta(String v1, String v2){

        Vertice v = get(v1);
        Vertice vv= get(v2);

        if(v == null){
            return false;
        }

        if(grafo.get(v).contains(vv)){
            grafo.get(v).remove(vv);
            grafo.get(vv).remove(v);
            return true;
        }

        return false;
    }

    public void print(){
        for(Vertice key : grafo.keySet()){
            System.out.print(key.getId() + " -> [ ");
            for(Vertice v : grafo.get(key)){
                System.out.print(v.getId() + " ");
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

            ArrayList<Vertice> collection = grafo.get(first);

            first.setCor("P");

            if(collection.contains(key)){
                int value = first.getDistancia()+1;

                resetColor();
                return value;
            }

            for(Vertice v : collection){

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

    private void resetColor(){
        for (Vertice vertice : grafo.keySet()) {
            vertice.setCor("B");
            vertice.setDistancia(-1);
        }
    }

}