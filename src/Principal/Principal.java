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
import Exercicios.TransmissaoDeEnergia;
import java.io.FileNotFoundException;
import Exercicios.Erdos;


public class Principal{

	public static void main(String[] args) throws FileNotFoundException{

            //new Erdos();
            //System.out.println("");
            //new TransmissaoDeEnergia();
            
            Grafo g = new Grafo();

        	g.addVertice("r");
		g.addVertice("s");
		g.addVertice("t");
		g.addVertice("u");
		g.addVertice("x");
		g.addVertice("v");
		g.addVertice("y");
		g.addVertice("w");

		g.addAresta("r", "v");
		g.addAresta("r", "s");
		g.addAresta("s", "w");
		g.addAresta("t", "u");
		g.addAresta("t", "w");
		g.addAresta("t", "x");
		g.addAresta("w", "x");
		g.addAresta("x", "y");
		g.addAresta("y", "u");
                
                g.removeVertice("y");

		g.print();

	}

}
