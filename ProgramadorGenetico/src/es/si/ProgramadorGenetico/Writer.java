package es.si.ProgramadorGenetico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase utilizada para mostrar texto en la pantalla o en un fichero
 * de texo.
 *
 */
public class Writer {
	private static BufferedWriter bw;
	
	public static void write(String text) {
		try {
			System.out.print(text);
			if (bw == null)
				bw = new BufferedWriter(new FileWriter("c:\\salida.txt", true));
			bw.write(text);
			bw.flush();
		} catch (IOException e) {
				
		}
	}
	
}
