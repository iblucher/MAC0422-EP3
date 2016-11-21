/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  CLASSE OPTIMAL LIST
 *
*******************************************************************************/

public class OptimalList {
    private RedBlackBST<Integer, Queue<Double>>[] labelTree;

    public OptimalList(Process[] plist, int num_process) {
        labelTree =  new RedBlackBST[num_process];

        for (int i = 0; i < num_process; i++) {
            labelTree[(int)plist[i].PID()] = plist[(int)plist[i].PID()].labelTree();
        }
    }

    public double label(long PID, int p) {
        if (labelTree[(int)PID].isEmpty()) return -1;
        if (labelTree[(int)PID].get(p).isEmpty()) return -1;
        return labelTree[(int)PID].get(p).peek();
    }

    public void remove(long PID, int p) {
        labelTree[(int)PID].get(p).dequeue();
    }

    public void free(long PID) {
        labelTree[(int)PID] = new RedBlackBST<Integer, Queue<Double>>();
    }
}
