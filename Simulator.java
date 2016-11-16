/*******************************************************************************
 *  EP3 de MAC0422 (Sistemas Operacionais)
 *
 *  Isabela Blucher - 9298170
 *  Andre Victor dos Santos Nakazawa - 9298336
 * 
 *  SIMULADOR DE GERÊNCIA DE MEMÓRIA
 *
*******************************************************************************/

import java.io.*;
import java.util.*;

public class Simulator {

	public void typePrompt () {

	}

	public static void main (String[] args) throws java.io.IOException {

		String command;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print ("(ep3): ");
			command = console.readLine();

			if (command.equals(""))
				continue; 
		}
	}
}