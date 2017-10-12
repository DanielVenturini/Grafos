/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Grafo.Aresta;
import Grafo.Vertice;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Daniel
 */
public class ToGraphviz {

    public static void toGraphviz(HashMap<Vertice, ArrayList<Aresta>> estrutura, boolean isGrafo){   //pode ser um grafo ou uma arvore

            File file;

            try{
                file = abrirArquivo();
                escrevendoArquivo(new FileWriter(file), estrutura, isGrafo);
                abrirGraphviz();
            } catch (IOException ex) {
                System.out.println("Erro ao manipular arquivo");
            }
    }

    private static File abrirArquivo() throws IOException{
        return new File("execute.dot");
    }

    private static void escrevendoArquivo(FileWriter file, HashMap<Vertice, ArrayList<Aresta>> grafo, boolean isGrafo) throws IOException{
        file.write("digraph BST {");                //cabeçalho do arquivo .dot
        file.write("node [fontname=\"Arial\"];\n");

        for (Vertice vertice : grafo.keySet()){
                for (Aresta aresta : grafo.get(vertice)) {  //aqui tem que colocar o Vertice apontanto para o outro Vertice e o peso: 'a -> b [label="4"];'

//                    if(isGrafo){    //como é grafo, nao é bom colocar duas arestas na hora de desenhar no Graphviz
//                        for (Aresta a : grafo.get(aresta.getVerticeAdjacente())) {
//                            if(a.getVerticeAdjacente().equals(vertice)){
//                                grafo.get(aresta.getVerticeAdjacente()).remove(aresta);
//                            }
//                        }
//                    }

                    file.write("    " + vertice.getId() + " -> " + aresta.getVerticeAdjacente().getId() + 
                               " [" + (isGrafo?"dir=none ": "") + "label=\"" + aresta.getPeso() + "\"];\n");
            }
        }

        file.write("}");
        file.close();
    }

    private static void abrirGraphviz() throws IOException{
        Runtime.getRuntime().exec("gvedit execute.dot");
    }
}
