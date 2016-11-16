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

    ArrayList<String> line;
    int total, virtual, s, p;

    public void loadFile () {
        String[] lineSplit = command.split(" ");

        int size = lineSplit.length;
        for (int i = 0; i < size; i++) {
            line.add(lineSplit[i]);
        }

        String filename = lineSplit[1];
        String currentLine = null;

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(FileReader);

            currentLine = br.readLine();
            String[] firstLineSplit = currentLine.split(" ");
            total = Integer.parseInt(firstLineSplit[0]);
            virtual = Integer.parseInt(firstLineSplit[1]);
            s = Integer.parseInt(firstLineSplit[2]);
            p = Integer.parseInt(firstLineSplit[3]);

            while ((currentLine = br.readLine()) != null) {
                // aqui é feita a leitura do resto do arquivo de trace
            }
        }

    }

    public static void main (String[] args) throws java.io.IOException {

        String command;
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print ("(ep3): ");
            command = console.readLine();

            line = new ArrayList<String>();
            String[] lineSplit = command.split(" ");

            int size = lineSplit.length;
            for (int i = 0; i < size; i++) {
                line.add(lineSplit[i]);
                System.out.println (lineSplit[i]);
            }

            // se der enter, nada acontece
            if (command.equals(""))
                continue;
            // se o comando for "sai", finaliza o simulador
            else if (command.equals("sai"))
                System.exit(0);
            else if (command.lineSplit[0].equals("carrega"))
                loadFile ();
            else if (command.lineSplit[0].equals("espaco"))
                int espaco = command.lineSplit[1];
            else if (command.lineSplit[0].equals("substitui"))
                int substitui = command.lineSplit[1];
            else if (command.lineSplit.equals("executa"))
                // execução 

        }
    }
}