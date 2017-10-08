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

        public ToGraphviz(HashMap<Vertice, ArrayList<Aresta>> grafo){

            File file;

            try{
                file = abrirArquivo();
                escrevendoArquivo(new FileWriter(file), grafo);
                abrirGraphviz();
            } catch (IOException ex) {
                System.out.println("Arquivo \"grafo.dot\" nao existente.");
            }
        }

    public static File abrirArquivo() throws IOException{
        return new File("grafo.dot");
    }

    public void escrevendoArquivo(FileWriter file, HashMap<Vertice, ArrayList<Aresta>> grafo) throws IOException{
        file.write("digraph BST {");                //cabeçalho do arquivo .dot
        file.write("node [fontname=\"Arial\"];");

        for (Vertice vertice : grafo.keySet()){
                for (Aresta aresta : grafo.get(vertice)) {  //aqui tem que colocar o Vertice apontanto para o outro Vertice e o peso: 'a -> b [label="4"];'

                    file.write(vertice.getId() + " -> " + aresta.getVerticeAdjacente().getId() + " [label=\"" + aresta.getPeso() + "\"];\n");
            }
        }
        
        file.write("}");
        file.close();
    }

    public void abrirGraphviz() throws IOException{
        Runtime.getRuntime().exec("gvedit grafo.dot");
    }
}
