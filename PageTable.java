/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  CLASSE DA TABELA DE PÁGINAS
 *
*******************************************************************************/

public class PageTable {
	private int size;
	private boolean[] bitR;
	private int[] address;

    public PageTable(int size) {
    	this.size = size;
    	bitR = new boolean[size];
    	address = new int[size];

    	for (int i = 0; i < size; i++) {
    		address[i] = -1;
    	}
    }

    public void include (int virtualAddress, int physicalAddress) {
    	address[virtualAddress] = physicalAddress;
    	fillBitR(virtualAddress);
    }

    public int query (int virtualAddress) {
        return address[virtualAddress];
    }

    public void remove (int physicalAddress) {
    	int virtualAddress;
    	for (virtualAddress = 0; virtualAddress < size; virtualAddress++) {
  			if (address[virtualAddress] == physicalAddress) break;
    	}
    	address[virtualAddress] = -1;
    	clearBitR(virtualAddress);
    }

    public boolean bitR (int virtualAddress) {
    	return bitR[virtualAddress];
    }

    public void fillBitR (int virtualAddress) {
    	bitR[virtualAddress] = true;
    }

    public void clearBitR (int virtualAddress) {
    	bitR[virtualAddress] = false;
    }

    public void update () {
        for (int i = 0; i < size; i++) clearBitR(i);
    }
}
