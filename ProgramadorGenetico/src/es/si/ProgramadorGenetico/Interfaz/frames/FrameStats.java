package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.StatsWS.FASearcherStats;
import es.si.ProgramadorGenetico.StatsWS.FASearcherStatsBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetBasicStatsRequest;
import es.si.fasearcherserver.GetBasicStatsResponse;

public class FrameStats extends JFrame {

	private static final long serialVersionUID = -5085114999247625745L;
	
	Integer numProblemas;
	
	Float mediaSoluciones;
	
	List<Double> histParecidoAF;
	
	List<Double> histReconocido; 
	
	public FrameStats() {
		super("Estadisticas");
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherStatsBeanService");
			URL server;
			if (!Config.getInstance().getProperty("FASearcherStatsServer").equals("classpath"))
				server = new URL(Config.getInstance().getProperty("FASearcherStatsServer"));
			else
				server = ClassLoader.getSystemResource("FASearcherStatsBean.wsdl");
			
			FASearcherStatsBeanService fasbs = new FASearcherStatsBeanService(server, service);
			FASearcherStats fas = fasbs.getFASearcherStatsBeanPort();
			
			GetBasicStatsRequest apr = new GetBasicStatsRequest();
			
			GetBasicStatsResponse gpresponse = fas.getBasicStats(apr);

			numProblemas = gpresponse.getNumProblemas();
			
			mediaSoluciones = gpresponse.getMediaSoluciones();

			histParecidoAF = gpresponse.getHistParecido();
			
			histReconocido = gpresponse.getHistReconocimiento();
			
			//HistPanel parecidoAF = crearHistPane("Parecidos a AF", histParecidoAF);
			JPanel parecidoAF = new JPanel();
			parecidoAF.setLayout(new BoxLayout(parecidoAF, BoxLayout.PAGE_AXIS));
			
			int cont = 0;
			
			parecidoAF.add(new JLabel("Parecido AF"));
			
			for (Double d : histParecidoAF) {
				parecidoAF.add(new JLabel("" + ((float) cont / 20) + " - " + ((float) (cont+ 1) / 20) + " : " + d + "      "));
				cont++;
			}
			//HistPanel reconocido = crearHistPane("Reconocimiento", histReconocido);
			JPanel reconocido = new JPanel();
			reconocido.setLayout(new BoxLayout(reconocido, BoxLayout.PAGE_AXIS));
			
			reconocido.add(new JLabel("Reconocimiento"));
			
			cont = 0;
			for (Double d : histReconocido) {
				reconocido.add(new JLabel("" + ((float) cont / 20) + " - " + ((float) (cont+ 1) / 20) + " : " + d));
				cont++;
			}
			
			JPanel panel = new JPanel();

			setLayout(new BorderLayout());
			
			panel.add(parecidoAF);
			
			panel.add(reconocido);
			
			add(panel, BorderLayout.CENTER);
			
			panel = new JPanel();
			
			panel.add(new JLabel("Problemas: "));
			
			panel.add(new JLabel("" + numProblemas + "  "));
			
			panel.add(new JLabel("Media soluciones: "));
			
			panel.add(new JLabel("" + mediaSoluciones));
			
			add(panel, BorderLayout.NORTH);
			
			setSize(500,500);
			
			setVisible(true);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
