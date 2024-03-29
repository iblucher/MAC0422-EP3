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
	private final int virtual, p;
	private int pointer;
    private long time_spent;

	public SpaceManagement(int method, int virtual, int p) {
		this.method = method;
		this.virtual = virtual;
		this.p = p;
        this.pointer = 0;
        this.time_spent = 0;
	}

	// função que insere na memória
	public void insert(long[] memory, boolean[] bitmap, Process proc) {
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

	public void remove(long[] memory, boolean[] bitmap, Process proc) {
		for (int j = proc.base(); j < proc.limit(); j++) {
			memory[j] = -1;
			bitmap[j] = false;
		}
		proc.freed();
	}

	private void firstFit(long[] memory, boolean[] bitmap, Process proc) {
		int nbits = bitmap.length;

        long startTime = System.currentTimeMillis();

		for (int i = 0; i < nbits; i++) {
			int base = i;
			
			while (i < nbits && !bitmap[i++]);

			if (proc.b() <= (i - base) * p) {

                time_spent += System.currentTimeMillis() - startTime;

				int size = (int) Math.ceil((double)proc.b()/p);
				proc.allocated(base, base + size);

				for (int j = proc.base(); j < proc.limit(); j++) {
					memory[j] = proc.PID();
					bitmap[j] = true;
				}

				break;
			}
		}
	}

	private void nextFit(long[] memory, boolean[] bitmap, Process proc) {
		int nbits = bitmap.length;
		boolean done = false;

        long startTime = System.currentTimeMillis();

		for (int i = pointer; i < nbits; i++) {
			int base = i;
			
			while (i < nbits && !bitmap[i++]);

			if (proc.b() <= (i - base) * p) {

                time_spent += System.currentTimeMillis() - startTime;

				int size = (int) Math.ceil((double)proc.b()/p);
				proc.allocated(base, base + size);

				for (int j = proc.base(); j < proc.limit(); j++) {
					memory[j] = proc.PID();
					bitmap[j] = true;
				}
				
				pointer = proc.limit();
				done = true;
				break;
			}
		}

		if (done) return;

		for (int i = 0; i < pointer; i++) {
			int base = i;
			
			while (i < pointer && !bitmap[i++]);

			if (proc.b() <= (i - base) * p) {

                time_spent += System.currentTimeMillis() - startTime;

				int size = (int) Math.ceil((double)proc.b()/p);
				proc.allocated(base, base + size);

				for (int j = proc.base(); j < proc.limit(); j++) {
					memory[j] = proc.PID();
					bitmap[j] = true;
				}
				
				pointer = proc.limit();
				break;
			}
		}
	}

	private void bestFit(long[] memory, boolean[] bitmap, Process proc) {
		int nbits = bitmap.length;
		int min = nbits + 1;
		int mindex = -1;

        long startTime = System.currentTimeMillis();

		for (int i = 0; i < nbits; i++) {
			int base = i;
			
			while (i < nbits && !bitmap[i++]);

			if (proc.b() <= (i - base) * p && i - base < min) {
				mindex = base;
				min = i - mindex;
			}
		}

        time_spent += System.currentTimeMillis() - startTime;

		int size = (int) Math.ceil((double)proc.b()/p);
		proc.allocated(mindex, mindex + size);

		for (int j = proc.base(); j < proc.limit(); j++) {
			memory[j] = proc.PID();
			bitmap[j] = true;
		}
	}

	private void worstFit(long[] memory, boolean[] bitmap, Process proc) {
		int nbits = bitmap.length;
		int max = 0;
		int mindex = -1;

        long startTime = System.currentTimeMillis();

		for (int i = 0; i < nbits; i++) {
			int base = i;
			
			while (i < nbits && !bitmap[i++]);

			if (proc.b() <= (i - base) * p && max < i - base) {
				mindex = base;
				max = i - mindex;
			}
		}

        time_spent += System.currentTimeMillis() - startTime;

		int size = (int) Math.ceil((double)proc.b()/p);
		proc.allocated(mindex, mindex + size);

		for (int j = proc.base(); j < proc.limit(); j++) {
			memory[j] = proc.PID();
			bitmap[j] = true;
		}
	}

    public double time_spent() {
        return this.time_spent/1000;
    }
}
