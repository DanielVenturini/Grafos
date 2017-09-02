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

    HashMap<String, ArrayList<String>> grafo;

        public Grafo(){
            grafo = new HashMap<>();
	}

    public HashMap getGrafo(){
        return grafo;
    }

    public void addVertice(String vertice){
	grafo.put(vertice, new ArrayList<>());
    }

    public boolean addAresta(String v1, String v2){
        if(!grafo.containsKey(v1)){
            return false;
	}
		
        if(!grafo.containsKey(v2)){
            return false;
	}

	if(grafo.get(v1).contains(v2)){
            return false;
        }

        grafo.get(v1).add(v2);
	grafo.get(v2).add(v1);
	
        return true;
    }

    public void print(){
        for(String key : grafo.keySet()){
            System.out.println(key + " -> " + grafo.get(key).toString());
	}
    }

    public int buscaLargura(String first, String key){

        int pos = 1;

        int[] erdos = new int[grafo.size()];
        for(int i = 0; i < grafo.size(); i ++){
            erdos[i] = 0;
        }

        HashMap<String, String> cor = new HashMap<>();
        LinkedList<String> list = new LinkedList<>();

        String[] keys = keys();

        for(String s : keys){
            cor.put(s, "B");
        }

        list.add(first);
        String head;

        while(!list.isEmpty()){

            head = list.removeFirst();

            ArrayList<String> collection = grafo.get(head);
            erdos[pos] += collection.size();
            erdos[pos-1] --;


            cor.put(head, "P");

            if(collection.contains(key))
                return pos;

            if(erdos[pos-1] < 1)
                pos ++;

            for(String str : collection){

                if(cor.get(str).equals("B")){
                    cor.put(str, "C");
                    list.addLast(str);
                }
            }
        }

        return -1;
    }

    public String[] keys(){
        int lenght = grafo.size();
        String[] keys = new String[lenght];
        
        lenght = 0;
        for(String str : grafo.keySet()){
            keys[lenght ++] = str;
        }

        return keys;
    }

}