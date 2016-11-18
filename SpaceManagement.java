/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  ALGORITMOS DE GERENCIAMENTO DE ESPAÇO LIVRE
 *
*******************************************************************************/

public class SpaceManagement {
	private final int method;

	public SpaceManagement(int method) {
		this.method = method;
	}

	public void insert(long[] memory, BitSet bitmap, Process proc) {
		switch (method) {
			case 1:
				firstFit(memory, bitmap, proc);
				break;
			case 2:
				nextFit(memory, bitmap, proc);
				break;
			case 3:
				bestFit(memory, bitmap, proc);
				break;
			case 4:
				worstFit(memory, bitmap, proc);
				break;
		}
	}

	public void remove(long[] memory, BitSet bitmap, Process proc) {
		
	}

	private void firstFit(long[] memory, BitSet bitmap, Process proc) {
	}

	private void nextFit(long[] memory, BitSet bitmap, Process proc) {
	}

	private void bestFit(long[] memory, BitSet bitmap, Process proc) {
	}

	private void worstFit(long[] memory, BitSet bitmap, Process proc) {
	}
}