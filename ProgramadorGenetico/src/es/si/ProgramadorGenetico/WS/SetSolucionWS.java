package es.si.ProgramadorGenetico.WS;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javax.xml.namespace.QName;

import es.si.ProgramadorGenetico.util.Config;
import es.si.fasearcherserver.SetSolucionRequest;
import es.si.fasearcherserver.SetSolucionResponse;


public class SetSolucionWS {

	private String id;
	
	private String reconocimiento;
	
	private String parecidoAF;
	
	private Afp afp;
	
	private Integer pasos;
	
	private String mutador;
	
	private String metodoRes;
	
	private String funcbondad;
	
	private String cruzador;
	
	private Integer pobmax;
	
	private Integer muestras;
	
	private Integer particiones;
	
	public void setParticiones(Integer particiones) {
		this.particiones = particiones;
	}

	public boolean ejecutar() {
		
		try {
			QName service = new QName("http://ejb.FASearcherServer.si.es/", "FASearcherBeanService");
			URL server = new URL(Config.getInstance().getProperty("FASearcherServer"));

			FASearcherBeanService fasbs = new FASearcherBeanService(server, service);
			FASearcher fas = fasbs.getFASearcherBeanPort();
			
			SetSolucionRequest ssr = new SetSolucionRequest();

			ssr.setId(id);
			ssr.setReconocimiento(reconocimiento);
			ssr.setParecidoAF(parecidoAF);
			ssr.setAfp(afp);
			ssr.setPasos(pasos);
			ssr.setMutador(mutador);
			ssr.setFuncbondad(funcbondad);
			ssr.setCruzador(cruzador);
			ssr.setMetodoRes(metodoRes);
			ssr.setPobmax(pobmax);
			ssr.setMuestras(muestras);
			ssr.setParticiones(particiones);
			
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

	public void setReconocimiento(String reconocimiento) {
		this.reconocimiento = reconocimiento;
	}

	public void setParecidoAF(String parecidoAF) {
		this.parecidoAF = parecidoAF;
	}
	
	public void setAfp(Afp afp) {
		this.afp = afp;
	}
	
	public void setAFP(es.si.ProgramadorGenetico.ProblemaAFP.AFP afp) {
		int estados = afp.getEstados();
		this.afp = new Afp();
		this.afp.setEstados(afp.getEstados());
		
		String probFinales = "";
		float[] prob = afp.getProbabilidadesFinal();
		for (int i = 0; i < estados; i ++)
			probFinales += (probFinales.equals("") ? prob[i] : ";" + prob[i]);
		this.afp.setProbFinales(probFinales);
		
		String[] trans = new String[estados*2];
		float[][][] transiciones = afp.getTransiciones();
		for (int i = 0; i < estados; i ++) {
			for (int j = 0; j < 2; j ++) {
				trans[i*2 +j] = "" + i + ":" + j + ":";
				for (int k = 0; k < estados+1; k++) {
					double temp = (transiciones[i][j][k] < 0.0001 ? 0 : transiciones[i][j][k]);
					temp = (temp > 0.9999 ? 1 : temp);
					NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
					format.setMaximumFractionDigits(4);
					String temp2 = format.format(temp);
					trans[i*2+j] += (k == 0 ? temp2 : ";" + temp2);
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

	public void setMutador(String mutador) {
		this.mutador = mutador;
	}

	public void setMetodoRes(String metodoRes) {
		this.metodoRes = metodoRes;
	}
	
	
}
