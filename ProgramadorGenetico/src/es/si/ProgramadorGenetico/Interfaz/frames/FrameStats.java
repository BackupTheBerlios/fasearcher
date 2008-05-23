package es.si.ProgramadorGenetico.Interfaz.frames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.Interfaz.data.Stats;
import es.si.ProgramadorGenetico.Interfaz.paneles.PanelHistograma;
import es.si.ProgramadorGenetico.StatsWS.FASearcherStats;
import es.si.ProgramadorGenetico.StatsWS.FASearcherStatsBeanService;
import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetBasicStatsRequest;
import es.si.fasearcherserver.GetBasicStatsResponse;

public class FrameStats extends JFrame {

	private static final long serialVersionUID = -5085114999247625745L;
	
	Stats stats;
	
	public FrameStats() {
		this(null);
	}
	
	public FrameStats(Stats stats2) {
		super("Estadisticas");
		
		this.stats = stats2;

		if (stats == null)
			getStats();
		
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2,1));

			setLayout(new BorderLayout());
			
			panel.add(new PanelHistograma(stats.getHistReconocimiento(), "Reconocimiento"), BorderLayout.CENTER);

			panel.add(new PanelHistograma(stats.getHistParecido(), "Parecido"), BorderLayout.CENTER);
			
			add(panel, BorderLayout.CENTER);
			
			panel = new JPanel();
			
			panel.add(new JLabel("Problemas: "));
			
			panel.add(new JLabel("" + stats.getNumProblemas() + "  "));
			
			panel.add(new JLabel("Media soluciones: "));
			
			panel.add(new JLabel("" + stats.getMediaSoluciones()));
			
			add(panel, BorderLayout.NORTH);
			
			setSize(600,600);
			
			setVisible(true);
	}

	private void getStats() {
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

			stats = new Stats();
			
			stats.setNumProblemas(gpresponse.getNumProblemas());
			
			stats.setMediaSoluciones(gpresponse.getMediaSoluciones());

			stats.setHistParecido(gpresponse.getHistParecido());
			
			stats.setHistReconocimiento(gpresponse.getHistReconocimiento());

			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
