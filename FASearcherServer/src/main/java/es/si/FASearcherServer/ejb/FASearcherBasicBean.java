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
import es.si.FASearcherServer.data.basic.GetProblemaBasicRequest;
import es.si.FASearcherServer.data.basic.GetProblemaBasicResponse;
import es.si.FASearcherServer.data.basic.SetSolucionRequest;
import es.si.FASearcherServer.data.basic.SetSolucionResponse;

@Stateless
@WebService(endpointInterface="es.si.FASearcherServer.ejb.FASearcherBasic")
@WebContext(contextRoot="/fasearcher")
public class FASearcherBasicBean implements FASearcherBasic {
	
	@Resource(mappedName="java:/MySqlDS")
	DataSource ds;
	
    @WebMethod
    @WebResult(name="getProblemBasicResponse")
	public GetProblemaBasicResponse getProblemaBasic(@WebParam(name="getProblemaBasicRequest") GetProblemaBasicRequest request) {
		GetProblemaBasicResponse response = new GetProblemaBasicResponse();

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

			
			Statement stmtConfig0 = connection.createStatement();
			ResultSet rsConfig0 = stmtConfig0.executeQuery("SELECT configuraciones.id_configuracion, " +
														   "IFNULL((SELECT COUNT(*) FROM solucion WHERE solucion.id_configuracion = configuraciones.id_configuracion),0) AS cuenta " +
														   "FROM  configuraciones WHERE configuraciones.id = \"" + id + "\" " +
														   "GROUP BY configuraciones.id_configuracion ORDER BY cuenta");

			Integer id_config = -1;
			List<Integer> ids = new ArrayList<Integer>();
			int min = -1;
			int cuenta = -1;
			while (rsConfig0.next() && (min == -1 || cuenta <= min)) {
				if (min == -1)
					min = rsConfig0.getInt("cuenta");
				cuenta = rsConfig0.getInt("cuenta");
				ids.add(rsConfig0.getInt("id_configuracion"));
			}
			Random rand = new Random();
			id_config = ids.get(rand.nextInt(ids.size() - 1));
			
			
			Statement stmtConfig1 = connection.createStatement();
			ResultSet rsConfig1 = stmtConfig1.executeQuery("SELECT configuraciones.id_configuracion as \"id\", " +
																  "configuraciones.Estados as \"Estados\", " +
																  "configuraciones.PobMax as \"PobMax\", " +
																  "configuraciones.Muestras as \"Muestras\", " +
																  "configuraciones.calculadorBondad as \"calculadorBondad\", " +
																  "configuraciones.cruzador as \"cruzador\", " +
																  "configuraciones.mutador as \"mutador\", " +
																  "configuraciones.resolver as \"resolver\" " + 
														   "FROM configuraciones " +
														   "WHERE configuraciones.id = \"" + id + "\"" +
														   		" AND configuraciones.id_configuracion = " + id_config);

			Configuracion config = null;
			while (rsConfig1.next()) {
				int muestras = rsConfig1.getInt("Muestras");
				int estados = rsConfig1.getInt("Estados");
				int pobMax = rsConfig1.getInt("PobMax");
				int calculadorBondad = rsConfig1.getInt("calculadorBondad");
				int cruzador = rsConfig1.getInt("cruzador");
				int mutador = rsConfig1.getInt("mutador");
				int resolver = rsConfig1.getInt("resolver");
				int id_config2 = rsConfig1.getInt("id");
				config = new Configuracion(id_config2, muestras, pobMax, estados, calculadorBondad, cruzador, mutador, resolver);
			} 
			
			response.setMuestras(config.getMuestras());
			response.setPobMax(config.getPobMax());
			response.setEstados(config.getEstados());
			response.setCalculadorBondad(config.getCalculadorBondad());
			response.setCruzador(config.getCruzador());
			response.setMutador(config.getMutador());
			response.setResolver(config.getResolver());
			response.setId_configuracion(config.getId());
			
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
			
			float[][][] transiciones = automata.getTransiciones();
			
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
					transiciones[estado][entrada][i] = Float.parseFloat(valores[i]);
				}
			}
			automata.setTransiciones(transiciones);
			
			float[] finales = automata.getProbabilidadesFinal();
			String[] probs = request.getAfp().getProbFinales().split(";");
			for (int i = 0; i < probs.length; i++) {
				finales[i] = Float.parseFloat(probs[i]);
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
						 request.getPobmax() + "," + request.getMuestras() + "," + request.getParticiones() + ", "+
						 request.getId_configuracion() + ",null)";
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
