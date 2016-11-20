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
    private Queue<Integer> fifo;
    private Clock<Integer> clock;

	public PageReplacement(int method, int total, int s, int p, Process[] plist, int num_process) {
		this.method = method;
		this.total = total;
		this.s = s;
		this.p = p;

        switch (method) {
			case 1:
				olist = new OptimalList(plist, num_process);
				break;
			case 2:
				fifo = new Queue<Integer>();
				break;
			case 3:
		        clock = new Clock<Integer>();
				break;
			case 4:
				// dunno
				break;
		}
	}

	public void insert(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
        if (table.query(proc.base() + page) != -1) return;

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

		int len = memory.length;
		double max = -1;
		int address = 0;

		for (int i = 0; i < len; i++) {
			double label = olist.label(memory[i][0], (int)memory[i][1]);

            if (label == -1) {
				address = i;
                break;
			} else if (max < label) {
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
        if (num_pages < total/p) {
			for (int i = num_pages * p/s; i < (num_pages + 1) * p/s; i ++) {
				memory[i][0] = proc.PID();
				memory[i][1] = page;
				bitmap[i] = true;
			}
			table.include(proc.base() + page, num_pages * p/s);
            fifo.enqueue(proc.base() + page);
			num_pages++;
			return;
		}

        int address = fifo.dequeue();
        while (table.bitR(address)) {
            table.clearBitR(address);
            fifo.enqueue(address);
            address = fifo.dequeue();
        }

        address = table.query(address);
        table.remove(address);
        memory[address][0] = proc.PID();
		memory[address][1] = page;
		bitmap[address] = true;
		table.include(proc.base() + page, address);
        fifo.enqueue(proc.base() + page);
	}

	private void clock(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
        if (num_pages < total/p) {
			for (int i = num_pages * p/s; i < (num_pages + 1) * p/s; i ++) {
				memory[i][0] = proc.PID();
				memory[i][1] = page;
				bitmap[i] = true;
			}
			table.include(proc.base() + page, num_pages * p/s);
            clock.insert(proc.base() + page);
			num_pages++;
			return;
		}

        int address = clock.pointedItem();
        while (table.bitR(address)) {
            table.clearBitR(address);
            clock.nextPoint();
            address = clock.pointedItem();
        }

        address = table.query(address);
        table.remove(address);
        memory[address][0] = proc.PID();
		memory[address][1] = page;
		bitmap[address] = true;
		table.include(proc.base() + page, address);
        clock.changeItem(proc.base() + page);
	}

	private void leastRecentlyUsed(long[][] memory, boolean[] bitmap, Process proc, int page, PageTable table) {
	}
}
