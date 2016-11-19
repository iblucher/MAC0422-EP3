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
    static int total, virtual, s, p; // valores dados na primeira linha do trace
    static BinaryOut outTot, outVir; // arquivos binários
    long[] memVir; // vetores que representam o conteúdos dos arquivo
    long[][] memTot;
    boolean[] bitTot, bitVir; // bitmap das memórias
    Process[] plist; // lista de processos
    int num_process; // número de processos

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

        StdOut.println(total + " " + virtual + " " + s + " " + p);

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
            int b = in.readInt();

            plist[i] = new Process(i, t0, nome, tf, b);
            String line = in.readLine();
            Scanner s = new Scanner(line);
            while (s.hasNextInt()) {
                int p = s.nextInt();
                double t = s.nextDouble();
                plist[i].newAccess(p, t);
            }
        }

        num_process = i;
    }

    public void initMemory () {
        memTot = new long[total/s][2];
        memVir = new long[virtual/p];
        bitTot = new boolean[total/s];
        bitVir = new boolean[virtual/p];

        for (int i = 0; i < total/s; i++) {
            memTot[i][0] = memTot[i][1] =  -1;
        }

        for (int i = 0; i < virtual/p; i++) {
            memVir[i] = -1;
        }

        for (int i = 0; i < total; i++) {
            outTot.write(-1);
        }

        for (int i = 0; i < virtual; i++) {
            outVir.write(-1);
        }

        outTot.flush();
        outVir.flush();
    }

    public void simulate (int m, int r, int interval) {
        RedBlackBST<Integer, Integer> set = new RedBlackBST<Integer, Integer>();
        SpaceManagement virtualMemory = new SpaceManagement(m, virtual, p);
        PageReplacement physicalMemory = new PageReplacement(r, total, s, p, plist, num_process);
        PageTable table = new PageTable(virtual/p);
        long startTime = System.nanoTime();
        
        for (int i = 0; i < num_process; i++) {
            while ((double)(System.nanoTime() - startTime)/10e+9 < plist[i].t0()) {
                for (int j : set.keys()) {
                    double t = plist[j].nextAccessTime();

                    if (t != -1 && (double)(System.nanoTime() - startTime)/10e+9 < t) {
                        int p = plist[j].nextAccessPage();
                        physicalMemory.insert(memTot, bitTot, plist[j], p, table);
                    }

                    if ((double)(System.nanoTime() - startTime)/10e+9 < plist[j].tf()) {
                        virtualMemory.remove(memVir, bitVir, plist[j]);
                        set.delete(j);
                    }
                }
            }

            virtualMemory.insert(memVir, bitVir, plist[i]);
            set.put(i, i);
        }

        while (!set.isEmpty()) {
            for (int j : set.keys()) {
                double t = plist[j].nextAccessTime();

                if ((double)(System.nanoTime() - startTime)/10e+9 < t) {
                    int p = plist[j].nextAccessPage();
                    physicalMemory.insert(memTot, bitTot, plist[j], p, table);
                }

                if ((double)(System.nanoTime() - startTime)/10e+9 < plist[j].tf()) {
                    virtualMemory.remove(memVir, bitVir, plist[j]);
                    set.delete(j);
                }
            }
        }
    }
    

    public static void main (String[] args) throws java.io.IOException {

        sim = new Simulator();
        In in = new In();

        String line;
        String[] command = null;
        int m = 0, r = 0;

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
                m = Integer.parseInt(command[1]);
            else if (command[0].equals("substitui"))
                r = Integer.parseInt(command[1]);
            else if (command[0].equals("executa"))
                sim.simulate (m, r, Integer.parseInt(command[1]));
                 
        }
    }
}
