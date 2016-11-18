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
import java.util.Scanner;

public class Simulator {
    static Simulator sim; // objeto da classe Simulator
    int total, virtual, s, p; // valores dados na primeira linha do trace
    static BinaryOut outTot, outVir; // arquivos binários
    long[] memTot, memVir; // vetores que representam o conteúdos dos arquivos
    Process[] plist;
    int num_process;

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

        num_process = 2;
        plist = new Process[num_process];

        int i;
        for (i = 0; !in.isEmpty(); i++) {
            if (i >= num_process) {
                Process[] temp = new Process[2 * num_process];
                for (int j = 0; j < num_process; j++) {
                    temp[j] = plist[j];
                }
                plist = temp;
                num_process *= 2;
            }

            double t0 = in.readDouble();
            String nome = in.readString();
            double tf = in.readDouble();
            long b = in.readLong();

            plist[i] = new Process(i, t0, nome, tf, b);
            String line = in.readLine();
            Scanner s = new Scanner(line);
            while (s.hasNextLong()) {
                long p = s.nextLong();
                double t = s.nextDouble();
                plist[i].newAccess(p, t);
            }
        }

        num_process = i;
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
