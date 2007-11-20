package es.si.ProgramadorGenetico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
