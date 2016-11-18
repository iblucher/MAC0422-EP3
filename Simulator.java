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

public class Simulator {

    static Simulator sim; // objeto da classe Simulator
    static BinaryOut outTot, outVir; // arquivos binários
    long[] memTot, memVir; // vetores que representam o conteúdos dos arquivos
    int total, virtual, s, p; // valores dados na primeira linha do trace

    public void createDirectory () {
        File dir = new File ("/tmp/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        // criar arquivos binários
        outTot = new BinaryOut("/tmp/ep3.mem");
        outVir = new BinaryOut("/tmp/ep3.vir");

    }

    public void loadFile (String name) {
        In in = new In(name);
        total = in.readInt();
        virtual = in.readInt();
        s = in.readInt();
        p = in.readInt();

        StdOut.printf ("%d %d %d %d\n", total, virtual, s, p);

        sim.initMemory();

        //while (!in.isEmpty()) {
            // leitura do resto do trace (existe um vector para Java)
            // criar uma estrutura que armazena as características dos processos (o que está no trace)
        //}
    }

    public void initMemory () {
        memTot = new long[total/s];
        memVir = new long[virtual/p];

        for (int i = 0; i < total/s; i++) {
            memTot[i] = -1;
            outTot.write(-1);
        }

        for (int i = 0; i < virtual/p; i++) {
            memVir[i] = -1;
            outVir.write(-1);
        }

        outTot.flush();
        outVir.flush();
    }

    public static void main (String[] args) throws java.io.IOException {

        sim = new Simulator();
        In in = new In();

        String line;
        String[] command = null;
        int espaco;
        int substitui;

        sim.createDirectory();

        while (true) {
            StdOut.print ("(ep3): ");
            line = in.readLine();

            command = line.replaceAll("^\\s+", "").split(" ", -1);
            
            // se der enter, nada acontece
            if (command[0].equals(""))
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