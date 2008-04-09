package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;

import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.GetProblemaRequest;
import es.si.fasearcherserver.GetProblemaResponse;
import es.si.fasearcherserver.SetSolucionRequest;
import es.si.fasearcherserver.SetSolucionResponse;


public class SetSolucionWS {

	private String id;
	
	private String mejorValor;
	
	private Afp afp;
	
	private Integer pasos;
	
	private String algoritmo;
	
	private String funcbondad;
	
	private String cruzador;
	
	private Integer pobmax;
	
	private Integer muestras;
	
	public boolean ejecutar() {
		
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherBeanService");
			URL server = new URL(Config.getInstance().getProperty("FASearcherServer"));

			FASearcherBeanService fasbs = new FASearcherBeanService(server, service);
			FASearcher fas = fasbs.getFASearcherBeanPort();
			
			SetSolucionRequest ssr = new SetSolucionRequest();

			ssr.setId(id);
			ssr.setMejorValor(mejorValor);
			ssr.setAfp(afp);
			ssr.setPasos(pasos);
			ssr.setAlgoritmo(algoritmo);
			ssr.setFuncbondad(funcbondad);
			ssr.setCruzador(cruzador);
			ssr.setPobmax(pobmax);
			ssr.setMuestras(muestras);
			
			
			SetSolucionResponse ssresponse = fas.setSolucion(ssr);

			if (ssresponse.getResult().equals("OK"))
				return true;
			else
				return false;
			
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMejorValor(String mejorValor) {
		this.mejorValor = mejorValor;
	}

	public void setAfp(Afp afp) {
		this.afp = afp;
	}
	
	public void setAFP(es.si.ProgramadorGenetico.ProblemaAFP.AFP afp) {
		int estados = afp.getEstados();
		this.afp = new Afp();
		this.afp.setEstados(afp.getEstados());
		
		String probFinales = "";
		double[] prob = afp.getProbabilidadesFinal();
		for (int i = 0; i < estados; i ++)
			probFinales += (probFinales.equals("") ? prob[i] : ";" + prob[i]);
		this.afp.setProbFinales(probFinales);
		
		String[] trans = new String[estados*2];
		double[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			for (int j = 0; j < 2; j ++) {
				trans[i*2 +j] = "" + i + ":" + j + ":";
				for (int k = 0; k < estados+1; k++) {
					trans[i*2+j] += (k == 0 ? transiciones[i][j][k] : ";" + transiciones[i][j][k]);
				}
				System.out.println(trans[i*2+j]);
			}
		}
		
		for (int i = 0; i < trans.length; i++)
			this.afp.getTransiciones().add(trans[i]);
	}
  
	public void setPasos(Integer pasos) {
		this.pasos = pasos;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

	public void setFuncbondad(String funcbondad) {
		this.funcbondad = funcbondad;
	}

	public void setCruzador(String cruzador) {
		this.cruzador = cruzador;
	}

	public void setPobmax(Integer pobmax) {
		this.pobmax = pobmax;
	}

	public void setMuestras(Integer muestras) {
		this.muestras = muestras;
	}
	
	
}
