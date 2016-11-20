/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  CLASSE DE ATIVIDADES PERIÓDICAS
 *
*******************************************************************************/

import java.util.Timer;
import java.util.TimerTask;

public class ScheduledActs {
    Timer printTimer;
    Timer bitRTimer;
    Timer lruTimer;
    int m;

    public scheduledActs(Simulator sim, double int1, PageTable table, double int2, PageReplacement pr, double int3, int m) {
        printTimer = new Timer();
        printTimer.schedule(new printTask(sim), 0, round(int1, 3) * 1000);

        bitRTimer = new Timer();
        bitRTimer.schedule(new bitRTask(table), 0, round(int2, 3) * 1000);

        this.m = m;
        if (m == 4) {
            lruTimer = new Timer();
            lruTimer.schedule(new lruTask(table), 0, round(int2, 3) * 1000);
        }
    }


    private static class printTask extends TimerTask {
        public bitRTask(PageTable table) {
            this.table = table;
        }

        public void run() {
            printMemory();
        }
    }

    private static class bitRTask extends TimerTask {
        private PageTable table;

        public bitRTask(PageTable table) {
            this.table = table;
        }

        public void run() {
            table.update();
        }
    }

    private static class urlTask extends TimerTask {
        private PageTable table;

        public bitRTask(PageTable table) {
            this.table = table;
        }

        public void run() {
            table.update();
        }
    }
        
        Timer bitRTimer = new Timer();
        bitRTimer.schedule(new bitRTask(), 0, 1*1000);

        Timer bitRTimer = new Timer();
        bitRTimer.schedule(new bitRTask(), 0, 1*1000);

    public void cancel () {

    }

    // from stack overflow
    private static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
}
