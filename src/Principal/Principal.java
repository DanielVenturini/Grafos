/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author Daniel
 */
import Grafo.Vertice;
import Grafo.Grafo;
import Exercicios.TransmissaoDeEnergia;
import java.io.FileNotFoundException;
import Exercicios.Erdos;
import java.util.ArrayList;
import java.util.Objects;

public class Principal{

	public static void main(String[] args) throws FileNotFoundException{

            //new Erdos();
            //System.out.println("");
            //new TransmissaoDeEnergia();

            Grafo g = new Grafo(true);

                g.addAresta("A", "B", 4);
                g.addAresta("A", "C", 2);
                g.addAresta("B", "D", 5);
                g.addAresta("B", "C", 1);
                g.addAresta("C", "D", 8);
                g.addAresta("C", "E", 10);
                g.addAresta("D", "E", 2);
                g.addAresta("D", "F", 6);
                g.addAresta("E", "F", 2);

                ToGraphviz.toGraphviz(g.dijkstra("A"), true);
                //ToGraphviz.toGraphviz(g.prim(), g.getIsGrafo());
                //ToGraphviz.toGraphviz(g.getGrafo(), g.getIsGrafo()); //se for um digrafo, é necessário alterar um parametro no arquivo .dot
                //costaDoMosquito(g);

	}

    static private void costaDoMosquito(Grafo g){

        ArrayList<Vertice> lista = new ArrayList<>();
        lista.addAll(g.getGrafo().keySet());
        
        int[] vetor = new int[g.getGrafo().size()];

        for(int i = 0; i < lista.size()-1; i ++){
            vetor[i] = 0;
            for(int j = i+1; j < lista.size(); j ++){
                int retorno = 0;//g.buscaLargura(lista.get(i).getId(), lista.get(j).getId());

                if(retorno > vetor[i])
                    vetor[i] = retorno;
            } 
        }

        int mim = vetor[0];
        int pos = 0;
        for(int i = 1; i < lista.size(); i ++){
            System.out.println(lista.get(i).getId() + " ");
            if(vetor[i] < mim){
                mim = vetor[i];
                pos = i;
            }
        }

        System.out.println("Valor minimo: " + mim);
        System.out.println("A melhor ilha eh a ilha " + lista.get(pos).getId());
    }

}
