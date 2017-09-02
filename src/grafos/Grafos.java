/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

/**
 *
 * @author Daniel
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

class Grafo{

    HashMap<String, ArrayList<String>> grafo;

        Grafo(){
            grafo = new HashMap<>();
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

class Erdos{
    
    private Grafo g;
    
        Erdos(){
            g = new Grafo();
            confGrafos(abrirArquivo());
            //g.print();
        }
        
    private void confGrafos(Scanner arquivo){

        String[] artigo = new String[10];
        boolean ctn = true;
        int teste = 1;

        String s = arquivo.nextLine();
        if(Integer.parseInt(s) < 1 || Integer.parseInt(s) > 100){
            System.out.println("Quantidade de artigos nao permitido.");
            return;
        }

        while(true){

            for(int i = 0; ctn; i ++){

                s = arquivo.next();
                if(s.equals("0")){
                    System.out.println("Teste " + teste ++);
                    printErdos();
                    return;
                } else if(Character.isDigit(s.charAt(0))){
                    System.out.println("Teste " + teste ++);
                    printErdos();
                    ctn = false;
                    
                        if(Integer.parseInt(s) < 1 || Integer.parseInt(s) > 100){
                            System.out.println("Quantidade de artigos nao permitido.");
                            return;
                        }
                    
                    g = new Grafo();
                    continue;
                }

                s += (" " + arquivo.next());

                artigo[i] = s.substring(0, s.length()-1);

                if(s.substring(4, s.length()).length() >= 15){
                    System.out.println("Sobrenome de um autor maior do que o permitido.");
                    return;
                }
                if(s.substring(s.length()-1, s.length()).equals(".")){
                    adicionaVerticesEArestas(artigo, i+1);
                    break;
                }
            }
            ctn = true;
        }
    }

    private void adicionaVerticesEArestas(String[] artigo, int length){
        for(int i = 0; i < length; i ++){
            if(!g.grafo.containsKey(artigo[i]))
                g.addVertice(artigo[i]);
        }
        
        for(int i = 0; i < length; i ++){
            for(int j = i+1; j < length; j ++){
                g.addAresta(artigo[i], artigo[j]);
            }
        }
    }

    private void printErdos(){

        for(String s : g.keys()){
            if(s.equals("P. Erdos")){
                continue;
            }

            int num = g.buscaLargura("P. Erdos", s);
            System.out.println(s + ": " + (num == -1 ? "infinito." : num));
        }
    }

    private Scanner abrirArquivo(){

        try{
            return new Scanner(new File("erdos.txt"));
        }catch (FileNotFoundException fe) {
            System.out.println("Arquivo erdos.txt nao encontrado.");
            return null;
        }
    }
}

class TransimssaoDeEnergia{

    private int e;
    private int l;
    private int testeN;
    
    private Grafo grafo;

        public TransimssaoDeEnergia() {
            testeN = 1;
            grafo = new Grafo();
            confGrafos(abrirArquivo());
            //grafo.print();
        }

    private Scanner abrirArquivo(){

        try{
            return new Scanner(new File("energia.txt"));
        }catch (FileNotFoundException ex) {
            System.out.println("Arquivo energia.txt nao encontrado");
            return null;
        }
    }

    private void confGrafos(Scanner arquivo){

        while(true){

            int e = this.e = Integer.parseInt(arquivo.next());
            int l = this.l = Integer.parseInt(arquivo.next());

            if(e == 0 && l == 0)
                return;

            if( (e < 2 || e > 100) || (l < e-1 || l > (e*(e-1)/2))){
                System.out.println("Configuracao invalida.");
                return;
            }

            while(e > 0){
                grafo.addVertice(String.valueOf(e));
                e --;
            }

            while(l > 0){
                grafo.addAresta(arquivo.next(), arquivo.next());
                l --;
            }

            testeLigacoes();
        }
    }

    public void testeLigacoes(){
        
        System.out.println("Teste " + testeN ++);

        for(int i = 0; i < e; i ++){
            for(int j = i; j < e; j ++){
                if(grafo.buscaLargura(String.valueOf(i+1), String.valueOf(j+1)) == -1){
                    System.out.println("falha");
                    System.out.println("");
                    return;
                }
            }
        }

        System.out.println("normal");
        System.out.println("");
    }

}


public class Grafos{

	public static void main(String[] args) throws FileNotFoundException{

            //new Erdos();
            new TransimssaoDeEnergia();

	}

}
