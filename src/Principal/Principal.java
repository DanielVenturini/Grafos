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

            new Erdos();
            System.out.println("");
            new TransmissaoDeEnergia();

	}

}
