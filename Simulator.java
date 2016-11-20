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
import java.util.Timer;
import java.util.TimerTask;

public class Simulator {
    static Simulator sim; // objeto da classe Simulator
    static int total, virtual, s, p; // valores dados na primeira linha do trace
    static BinaryOut outTot, outVir; // arquivos binários
    static double quantum = 8.0;

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
    }

    public void loadFile (String name) {
        In in = new In(name);
        total = in.readInt();
        virtual = in.readInt();
        s = in.readInt();
        p = in.readInt();

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
        // criar arquivos binários
        outTot = new BinaryOut("/tmp/ep3.mem");
        outVir = new BinaryOut("/tmp/ep3.vir");

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

        outTot.close();
        outVir.close();
    }

    public void updateMemory () {
        outTot = new BinaryOut("/tmp/ep3.mem");
        outVir = new BinaryOut("/tmp/ep3.vir");

        for (int i = 0; i < total; i++) {
            outTot.write((int)memTot[i/s][0]);
        }

        for (int i = 0; i < virtual; i++) {
            outVir.write((int)memVir[i/p]);
        }

        outTot.close();
        outVir.close();
    }

    public void printMemory () {
        StdOut.println("Memória física:");
        for (int i = 0; i < total; i++) {
            int b = (bitTot[i/s]) ? 1 : 0;
            if (memTot[i/s][0] != -1) {
                StdOut.println(b + "  " + plist[(int)memTot[i/s][0]].nome());
            } else {
                StdOut.println(b + "  -");
            }
        }
        
        StdOut.println("Memória virtual:");
        for (int i = 0; i < virtual; i++) {
            int b = (bitVir[i/p]) ? 1 : 0;
            if (memVir[i/p] != -1) {
                StdOut.println(b + "  " + plist[(int)memVir[i/p]].nome());
            } else {
                StdOut.println(b + "  -");
            }
        }
        StdOut.println();
    }

    public void simulate (int m, int r, int interval) {
        RedBlackBST<Integer, Integer> set = new RedBlackBST<Integer, Integer>();
        SpaceManagement virtualMemory = new SpaceManagement(m, virtual, p);
        PageReplacement physicalMemory = new PageReplacement(r, total, virtual, s, p, plist, num_process);
        PageTable table = new PageTable(virtual/p);
        long startTime = System.currentTimeMillis();
        
        long lastPrint = System.currentTimeMillis();
        printMemory();
        long lastBitUpdate = System.currentTimeMillis();
        table.update();
        long lastLruUpdate;
        if (m == 4) {
            lastLruUpdate = System.currentTimeMillis();
            physicalMemory.LRUupdate(table);
        }

        for (int i = 0; i < num_process; i++) {
            while ((double)(System.currentTimeMillis() - startTime)/1000 < plist[i].t0()) {
                for (int j : set.keys()) {
                    if ((double)(System.currentTimeMillis() - lastPrint)/1000 > interval) {
                        lastPrint = System.currentTimeMillis();
                        printMemory();
                    }
                    if ((double)(System.currentTimeMillis() - lastPrint)/1000 > quantum) {
                        lastBitUpdate = System.currentTimeMillis();
                        table.update();
                    }
                    if (m == 4 && (double)(System.currentTimeMillis() - lastPrint)/1000 > quantum/8) {
                        lastLruUpdate = System.currentTimeMillis();
                        physicalMemory.LRUupdate(table);
                    }

                    double t = plist[j].nextAccessTime();

                    if (t != -1 && (double)(System.currentTimeMillis() - startTime)/1000 > t) {
                        int p = plist[j].nextAccessPage();
                        physicalMemory.insert(memTot, bitTot, plist[j], p, table);
                        updateMemory();
                        plist[j].accessDone();
                    }

                    if ((double)(System.currentTimeMillis() - startTime)/1000 > plist[j].tf()) {
                        virtualMemory.remove(memVir, bitVir, plist[j]);
                        set.delete(j);
                        updateMemory();
                    }
                }
            }
            
            virtualMemory.insert(memVir, bitVir, plist[i]);
            set.put(i, i);
            updateMemory();
        }

        while (!set.isEmpty()) {
            for (int j : set.keys()) {
                if ((double)(System.currentTimeMillis() - lastPrint)/1000 > interval) {
                    lastPrint = System.currentTimeMillis();
                    printMemory();
                }
                if ((double)(System.currentTimeMillis() - lastPrint)/1000 > quantum) {
                    lastBitUpdate = System.currentTimeMillis();
                    table.update();
                }
                if (m == 4 && (double)(System.currentTimeMillis() - lastPrint)/1000 > quantum/8) {
                    lastLruUpdate = System.currentTimeMillis();
                    physicalMemory.LRUupdate(table);
                }
                
                double t = plist[j].nextAccessTime();

                if (t != -1 && (double)(System.currentTimeMillis() - startTime)/1000 > t) {
                    int p = plist[j].nextAccessPage();
                    physicalMemory.insert(memTot, bitTot, plist[j], p, table);
                    updateMemory();
                    plist[j].accessDone();
                }

                if ((double)(System.currentTimeMillis() - startTime)/1000 > plist[j].tf()) {
                    virtualMemory.remove(memVir, bitVir, plist[j]);
                    set.delete(j);
                    updateMemory();
                }
            }
        }

        StdOut.println("Tempo total de busca por espaço livre: " + virtualMemory.time_spent() + "s");
        StdOut.println("Número de page faults: " + physicalMemory.page_faults());
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
