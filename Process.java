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
    private int b;
    private int base, limit;
    private String nome;
    private double t0, tf;
    private Queue<Integer> AccessPage;
    private Queue<Double> AccessTime;

    public Process(long PID, double t0, String nome, double tf, int b) {
        this.PID = PID;
        this.t0 = t0;
        this.nome = nome;
        this.tf = tf;
        this.b = b;
        this.base = this.limit = -1;
        AccessPage = new Queue<Integer>();
        AccessTime = new Queue<Double>();
    }

    public long PID() {
        return PID;
    }

    public int b() {
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

    public int base() {
        return base;
    }

    public int limit() {
        return limit;
    }

    public void allocated(int base, int limit) {
        this.base = base;
        this.limit = limit;
    }

    public void newAccess(int p, double t) {
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

    public int nextAccessPage() {
        return AccessPage.peek();
    }

    public int remainingSize() {
        return AccessPage.size();
    }
}
