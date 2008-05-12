package es.si.FASearcherServer.ejb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.sql.DataSource;

import org.jboss.wsf.spi.annotation.WebContext;

import es.si.FASearcherServer.data.*;

@Stateless
@WebService(endpointInterface="es.si.FASearcherServer.ejb.FASearcher")
@WebContext(contextRoot="/fasearcher")
public class FASearcherBean implements FASearcher {
	
	@Resource(mappedName="java:/MySqlDS")
	DataSource ds;
	
    @WebMethod
    @WebResult(name="getProblemResponse")
	public GetProblemaResponse getProblema(@WebParam(name="getProblemaRequest") GetProblemaRequest request) {
		GetProblemaResponse response = new GetProblemaResponse();

		try {
			String id = "0";
			Connection connection = ds.getConnection();
			
			Statement stmt0 = connection.createStatement();
			ResultSet rs0 = stmt0.executeQuery("SELECT id, Soluciones" +
											   " FROM Problema" +
											   " ORDER BY Soluciones");
			if (rs0.next()) {
				id = rs0.getString("id");
			}
			
			Statement stmtAceptadas = connection.createStatement();
			ResultSet rsAceptadas = stmtAceptadas.executeQuery("SELECT Aceptadas.Cadena as \"Aceptadas\"" +
											 " FROM Problema, Aceptadas" +
											 " WHERE Problema.id = \"" + id + "\" AND Problema.id = Aceptadas.id");

			String aceptadas = "";
			while (rsAceptadas.next()) {
				if (aceptadas.equals(""))
					aceptadas = rsAceptadas.getString("Aceptadas");
				else
					aceptadas += "," + rsAceptadas.getString("Aceptadas");
				System.out.println("aceptadas = " + rsAceptadas.getString("Aceptadas"));
			}

			Statement stmtRechazadas = connection.createStatement();
			ResultSet rsRechazadas = stmtRechazadas.executeQuery("SELECT Rechazadas.Cadena as \"Rechazadas\"" +
											 " FROM Problema, Rechazadas" +
											 " WHERE Problema.id = \"" + id + "\" AND Problema.id = Rechazadas.id");

			String rechazadas = "";
			while (rsRechazadas.next()) {
				if (rechazadas.equals(""))
					rechazadas = rsRechazadas.getString("Rechazadas");
				else
					rechazadas += "," + rsRechazadas.getString("Rechazadas");
				System.out.println("rechazadas = " + rsRechazadas.getString("Rechazadas"));
			}

			
			Statement stmtConfig1 = connection.createStatement();
			ResultSet rsConfig1 = stmtConfig1.executeQuery("SELECT configuraciones.Estados as \"Estados\", " +
																  "configuraciones.PobMax as \"PobMax\", " +
																  "configuraciones.Muestras as \"Muestras\", " +
																  "configuraciones.calculadorBondad as \"calculadorBondad\", " +
																  "configuraciones.cruzador as \"cruzador\", " +
																  "configuraciones.mutador as \"mutador\", " +
																  "configuraciones.resolver as \"resolver\" " + 
														   "FROM configuraciones " +
														   "WHERE configuraciones.id = \"" + id + "\"");

			if (!rsConfig1.next()) {
				rsConfig1 = stmtConfig1.executeQuery("SELECT configuraciones.Estados as \"Estados\", " +
						  "configuraciones.PobMax as \"PobMax\", " +
						  "configuraciones.Muestras as \"Muestras\", " +
						  "configuraciones.calculadorBondad as \"calculadorBondad\"," +
						  "configuraciones.cruzador as \"cruzador\", " +
						  "configuraciones.mutador as \"mutador\", " +
						  "configuraciones.resolver as \"resolver\" " + 
				   "FROM configuraciones " +
				   "WHERE configuraciones.id = \"-1\"");
				rsConfig1.next();
			}
			
			List<Configuracion> configs = new ArrayList<Configuracion>();
			
			do {
				int muestras = rsConfig1.getInt("Muestras");
				int estados = rsConfig1.getInt("Estados");
				int pobMax = rsConfig1.getInt("PobMax");
				int calculadorBondad = rsConfig1.getInt("calculadorBondad");
				int cruzador = rsConfig1.getInt("cruzador");
				int mutador = rsConfig1.getInt("mutador");
				int resolver = rsConfig1.getInt("resolver");
				configs.add(new Configuracion(muestras, pobMax, estados, calculadorBondad, cruzador, mutador, resolver));
			} while (rsConfig1.next()); 
			
			Random rand = new Random();
			int i = rand.nextInt(configs.size());
			response.setMuestras(configs.get(i).getMuestras());
			response.setPobMax(configs.get(i).getPobMax());
			response.setEstados(configs.get(i).getEstados());
			response.setCalculadorBondad(configs.get(i).getCalculadorBondad());
			response.setCruzador(configs.get(i).getCruzador());
			response.setMutador(configs.get(i).getMutador());
			response.setResolver(configs.get(i).getResolver());
			
			response.setAceptadas(aceptadas);
			response.setRechazadas(rechazadas);
			response.setId(id);
			response.setTipoAutomata("AFP");
			
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return response;
	};
    
    @WebMethod
    @WebResult(name="setSolucionResponse")
   public SetSolucionResponse setSolucion(@WebParam(name="setSolucionRequest") SetSolucionRequest request) {
    	SetSolucionResponse response = new SetSolucionResponse();
    	response.setResult("error");
    	
		try {
			String id = request.getId();
			Connection connection = ds.getConnection();
			
			Statement stmt0 = connection.createStatement();
			ResultSet rs0 = stmt0.executeQuery("SELECT id, Soluciones" +
											   " FROM Problema" +
											   " WHERE id = " + id);
			
			if (rs0.next()) {
				int sol = rs0.getInt("Soluciones");
				Statement stmt1 = connection.createStatement();
				stmt1.executeUpdate("UPDATE Problema" +
									" SET Soluciones = " + (sol + 1) +
									" WHERE id = " + id);
				stmt1.close();
			}
			rs0.close();
			stmt0.close();
			
			es.si.ProgramadorGenetico.ProblemaAFP.AFP automata = new es.si.ProgramadorGenetico.ProblemaAFP.AFP(request.getAfp().getEstados().intValue());
			
			double[][][] transiciones = automata.getTransiciones();
			
			List<String> lista = request.getAfp().getTransiciones();
			Iterator<String> it = lista.iterator();
			while (it.hasNext()) {
				String temp = it.next();
				String[] partes = temp.split(":");
				int entrada = Integer.parseInt(partes[1]);
				int estado = Integer.parseInt(partes[0]);
				
				String[] valores = partes[2].split(";");
				for (int i = 0; i < valores.length; i++) {
					System.out.println("Entrada: " + entrada + " Estado: " + estado + " Valor: " + valores[i]);
					transiciones[estado][entrada][i] = Double.parseDouble(valores[i]);
				}
			}
			automata.setTransiciones(transiciones);
			
			double[] finales = automata.getProbabilidadesFinal();
			String[] probs = request.getAfp().getProbFinales().split(";");
			for (int i = 0; i < probs.length; i++) {
				finales[i] = Double.parseDouble(probs[i]);
			}
			automata.setProbabilidadFinal(finales);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(automata);
			} catch (IOException e) {
				e.printStackTrace();
			}
			

			
			String sql = "INSERT INTO Solucion" +
						 " VALUES(null,'" + id + "'," + request.getAfp().getEstados() + "," +
						 request.getReconocimiento() + "," + request.getParecidoAF() +",'" + 
						 request.getAfp().getTipo() + "',?," +
						 request.getPasos() + ",'" + request.getMutador() + "','" + request.getFuncbondad() +
						 "','" + request.getCruzador() + "','" + request.getMetodoRes() + "'," + 
						 request.getPobmax() + "," + request.getMuestras() + "," + request.getParticiones() + ")";
			System.out.println(sql);
			byte[] automataAsBytes = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(automataAsBytes);
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setBinaryStream(1, bais, automataAsBytes.length);
			pstmt.executeUpdate();
			//connection.commit();
			
			pstmt.close();
			connection.close();
		
			response.setResult("OK");
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//retriveFromDataBase();
 	
    	return response;
    };

    private void retriveFromDataBase() {
    	try {
			Connection connection = ds.getConnection();
    		
    		Statement stmt = connection.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT * FROM Solucion");
    		
    		es.si.ProgramadorGenetico.ProblemaAFP.AFP automata;
    		if (rs.next()) {
    			try {
    				byte[] st = rs.getBytes("Automata");
    				ByteArrayInputStream baip = new ByteArrayInputStream(st);
    				ObjectInputStream ois = new ObjectInputStream(baip);
    				automata = (es.si.ProgramadorGenetico.ProblemaAFP.AFP)ois.readObject();
    				System.out.print(automata.toString());
    			} catch (IOException e) {
    				e.printStackTrace();
    			} catch (ClassNotFoundException e) {
    				e.printStackTrace();
    			}
    		}

    		rs.close();
    		stmt.close();
    		connection.close();
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}
