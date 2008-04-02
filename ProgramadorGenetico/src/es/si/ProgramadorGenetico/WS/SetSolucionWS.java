package es.si.ProgramadorGenetico.WS;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import es.si.ProgramadorGenetico.WS.FASearcherBeanServiceStub.*;


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
			FASearcherBeanServiceStub fasbss = new FASearcherBeanServiceStub("http://169.254.140.126:18080/fasearcher/FASearcherBean");

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
			
			
			SetSolucion ss = new SetSolucion();
			ss.setSetSolucionRequest(ssr);
			SetSolucion4 ss4 = new SetSolucion4();
			ss4.setSetSolucion(ss);
			SetSolucionResponse5 ssresponse5 = fasbss.setSolucion(ss4);
			
			if (ssresponse5.getSetSolucionResponse().getSetSolucionResponse().getResult().equals("OK"))
				return true;
			else
				return false;
			
		} catch (AxisFault e) {
			e.printStackTrace();
			return false;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
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
					trans[i*2+j] += (k == 0 ? transiciones[i] : ";" + transiciones[i]);
				}
			}
		}
		this.afp.setTransiciones(trans);
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
