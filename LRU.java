/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  CLASSE LRU TABLE
 *
*******************************************************************************/

public class LRU {
    private int[] LRUtable;
    private int size;

    public LRU(int size) {
        this.size = size;
        LRUtable = new int[size];

        for (int i = 0; i < size; i++) {
            LRUtable[i] = 0;
        }
    }

    public int value (int virtualAddress) {
        return LRUtable[virtualAddress];
    }

    public void update (PageTable table) {
        for (int i = 0; i < size; i++) {
            int R = (table.bitR(i)) ? 1 : 0;
            LRUtable[i] = (LRUtable[i] >> 1) + (R << 16);
        }
    }
}
