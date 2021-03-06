/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercicios;

import java.io.FileNotFoundException;
import java.util.Scanner;
import Grafo.Grafo;
import Grafo.Vertice;
import Principal.ToGraphviz;

/**
 *
 * @author venturini
 */
public class Erdos{

    private Grafo g;

        public Erdos() throws FileNotFoundException{
            try{
                g = new Grafo(true);
                confGrafos(openArchive.openArchive("erdos.txt"));
                //g.print();
            }catch (FileNotFoundException fn) {
                System.out.println("Arquivo 'erdos.txt' nao encontrado/existe");
            }
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

                    g = new Grafo(true);
                    continue;
                }

                s += (" " + arquivo.next());

                artigo[i] = s.substring(0, s.length()-1);

                if(s.substring(4, s.length()).length() >= 15){
                    System.out.println("Sobrenome de um autor maior do que o permitido.");
                    return;
                }
                if(s.substring(s.length()-1, s.length()).equals(".")){
                    adicionaArestas(artigo, i+1);
                    break;
                }
            }
            ctn = true;
        }
    }

    private void adicionaArestas(String[] artigo, int length){
        
        if(length == 1){
            g.addVertice(artigo[0]);
            return;
        }

        for(int i = 0; i < length; i ++){
            for(int j = i+1; j < length; j ++){
                g.addAresta(artigo[i], artigo[j], 0);
            }
        }
    }

    private void printErdos(){
        //g.print();

        for(Vertice s : g.getGrafo().keySet()){
            if(s.getId().equals("P. Erdos")){
                continue;
            }

            int num = g.buscaLargura("P. Erdos", s.getId());
            System.out.println(s.getId() + ": " + (num == -1 ? "infinito." : num));
        }
        System.out.println("");
    }
}
