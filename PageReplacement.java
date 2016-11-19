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
	private final int total, s, p;
	int num_pages = 0;

	private OptimalList olist;

	public PageReplacement(int method, int total, int s, int p, Process[] plist, int num_process) {
		this.method = method;
		this.total = total;
		this.s = s;
		this.p = p;
		if (method == 1)
			olist = new OptimalList(plist, num_process);
	}

	public void insert(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
		if (num_pages < total/p) {
			for (int i = num_pages * p/s; i < (num_pages + 1) * p/s; i ++) {
				memory[i][0] = proc.PID();
				memory[i][1] = page;
				bitmap[i] = true;
			}
			table.include(proc.base() + page, num_pages * p/s);
			num_pages++;
			return;
		}

		switch (method) {
			case 1:
				optimal(memory, bitmap, proc, page, table);
				break;
			case 2:
				secondChance(memory, bitmap, proc, page, table);
				break;
			case 3:
				clock(memory, bitmap, proc, page, table);
				break;
			case 4:
				leastRecentlyUsed(memory, bitmap, proc, page, table);
				break;
		}
	}

	private void optimal(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
		int len = memory.length;
		double max = -1;
		int address = 0;

		for (int i = 0; i < len; i++) {
			double label = olist.label(memory[i][0], (int)memory[i][1]);
			if (max < label) {
				max = label;
				address = i;
			}
		}

		olist.remove(memory[address][0], (int)memory[address][1]);
		table.remove(address);
		memory[address][0] = proc.PID();
		memory[address][1] = page;
		bitmap[address] = true;
		table.include(proc.base() + page, address);
	}

	private void secondChance(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
	}

	private void clock(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
	}

	private void leastRecentlyUsed(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
	}
}