/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  CLASSE PROCESSO
 *
*******************************************************************************/

public class Process {
    private long PID;
    private long b;
    private String nome;
    private double t0, tf;
    private Queue<Long> AccessPage;
    private Queue<Double> AccessTime;

    public Process(long PID, double t0, String nome, double tf, long b) {
        this.PID = PID;
        this.t0 = t0;
        this.nome = nome;
        this.tf = tf;
        this.b = b;
        AccessPage = new Queue<Long>();
        AccessTime = new Queue<Double>();
    }

    public long PID() {
        return PID;
    }

    public long b() {
        return b;
    }

    public String nome() {
        return nome;
    }

    public double t0() {
        return t0;
    }

    public double tf() {
        return tf;
    }

    public void print() {
        StdOut.println(AccessPage.toString());
        StdOut.println(AccessTime.toString());
    }

    public void newAccess(long p, double t) {
        AccessPage.enqueue(p);
        AccessTime.enqueue(t);
    }

    public void accessDone() {
        AccessPage.dequeue();
        AccessTime.dequeue();
    }

    public double nextAccessTime() {
        return AccessTime.peek();
    }

    public int remainingSize() {
        return AccessPage.size();
    }
}
