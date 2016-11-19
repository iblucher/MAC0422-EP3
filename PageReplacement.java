/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  ALGORITMOS DE SUBSTITUIÇAO DE PÁGINAS
 *
*******************************************************************************/

public class PageReplacement {
	private final int method;

	public PageReplacement(int method) {
		this.method = method;
	}

	public void insert(long[] memory, boolean[] bitmap, Process proc, long p) {
		switch (method) {
			case 1:
				optimal(memory, bitmap, proc, p);
				break;
			case 2:
				secondChance(memory, bitmap, proc, p);
				break;
			case 3:
				clock(memory, bitmap, proc, p);
				break;
			case 4:
				leastRecentlyUsed(memory, bitmap, proc, p);
				break;
		}
	}

	private void optimal(long[] memory, boolean[] bitmap, Process proc, long p) {
	}

	private void secondChance(long[] memory, boolean[] bitmap, Process proc, long p) {
	}

	private void clock(long[] memory, boolean[] bitmap, Process proc, long p) {
	}

	private void leastRecentlyUsed(long[] memory, boolean[] bitmap, Process proc, long p) {
	}
}