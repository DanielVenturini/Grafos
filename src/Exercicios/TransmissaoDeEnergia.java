/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercicios;

import java.io.FileNotFoundException;
import java.util.Scanner;
import Principal.Grafo;

/**
 *
 * @author venturini
 */
public class TransmissaoDeEnergia{

    private int e;
    private int l;
    private int testeN;
    
    private Grafo grafo;

        public TransmissaoDeEnergia() {
            
            try{
                testeN = 1;
                grafo = new Grafo();
                confGrafos(openArchive.openArchive("energia.txt"));
                //grafo.print();
            }catch (FileNotFoundException fn) {
                System.out.println("Arquivo 'energia.txt' nao encontrado/existe");
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
