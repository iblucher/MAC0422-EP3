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
    private int accessLength;
    private Queue<Integer> AccessPage;
    private Queue<Double> AccessTime;

    public Process(long PID, double t0, String nome, double tf, int b) {
        this.PID = PID;
        this.t0 = t0;
        this.nome = nome;
        this.tf = tf;
        this.b = b;
        this.base = this.limit = -1;
        this.accessLength = 0;
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

    public void freed() {
        this.base = -1;
        this.limit = -1;
    }

    public void prepareAccess() {
        accessLength = AccessPage.size();
    }

    public void newAccess(int p, double t) {
        AccessPage.enqueue(p);
        AccessTime.enqueue(t);
    }

    public void accessDone() {        
        int p = AccessPage.dequeue();
        double t = AccessTime.dequeue();
        AccessPage.enqueue(p);
        AccessTime.enqueue(t);
        accessLength--;
    }

    public double nextAccessTime() {
        if (accessLength == 0) return -1;
        return AccessTime.peek();
    }

    public int nextAccessPage() {
        return AccessPage.peek();
    }

    public RedBlackBST<Integer, Queue<Double>> labelTree() {
        int[] pageArray = new int[AccessPage.size()];
        double[] timeArray = new double[AccessTime.size()];

        int i = 0;
        for (int p : AccessPage) {
            pageArray[i++] = p;
        }

        i = 0;
        for (double t : AccessTime) {
            timeArray[i++] = t;
        }

        RedBlackBST<Integer, Queue<Double>> tree = new RedBlackBST<Integer, Queue<Double>>();

        for (i = 0; i < AccessPage.size(); i++) {
            Queue<Double> q = tree.get(pageArray[i]);
            if (q != null) {
                q.enqueue(timeArray[i]);
                tree.put(pageArray[i], q);
            } else {
                q = new Queue<Double>();
                q.enqueue(timeArray[i]);
                tree.put(pageArray[i], q);
            }
        }

        return tree;
    }

    public int remainingSize() {
        return accessLength;
    }
}