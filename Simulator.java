/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  SIMULADOR DE GERÊNCIA DE MEMÓRIA
 *
*******************************************************************************/

import java.io.*;
import java.util.*;

public class Simulator {

    int total, virtual, s, p;

    public void loadFile (String name) {
        In in = new In(name);

        StdOut.println ("Arquivo é: " + name);
    }

    public static void main (String[] args) throws java.io.IOException {

        Simulator sim = new Simulator();
        In in = new In();

        String line;
        String[] command = null;
        int espaco;
        int substitui;
        while (true) {
            StdOut.print ("(ep3): ");
            line = in.readLine();

            command = line.split("-");

            // se der enter, nada acontece
            if (command[0].equals(" "))
                continue;
            // se o comando for "sai", finaliza o simulador
            else if (command[0].equals("sai"))
                break;
            else if (command[0].equals("carrega"))
                sim.loadFile (command[1]);
            else if (command[0].equals("espaco"))
                espaco = Integer.parseInt(command[1]);
            else if (command[0].equals("substitui"))
                substitui = Integer.parseInt(command[1]);
            //else if (command[0].equals("executa"))
                // execução 

        }
    }
}