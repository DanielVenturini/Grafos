/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercicios;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/**
 *
 * @author venturini
 */
class openArchive {

    static Scanner openArchive(String name) throws FileNotFoundException{
            return new Scanner(new File(name));
    }
}
