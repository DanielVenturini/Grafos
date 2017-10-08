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

public class Principal{

	public static void main(String[] args) throws FileNotFoundException{

            //new Erdos();
            //System.out.println("");
            //new TransmissaoDeEnergia();

            Grafo g = new Grafo(true);

                g.addAresta("a", "b", 4);
                g.addAresta("b", "c", 8);
                g.addAresta("c", "i", 2);
                g.addAresta("c", "f", 4);
		g.addAresta("c", "d", 7);
                g.addAresta("f", "g", 2);
                g.addAresta("g", "h", 1);
                g.addAresta("d", "e", 9);

		g.print();
                new ToGraphviz(g.getGrafo());
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
