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
import es.si.ProgramadorGenetico.ProblemaAFP.AFP;

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
		Connection connection = null;
		try {
			String id = "0";
			connection = ds.getConnection();
			
			id = getIdFromDB(connection);
			
			String aceptadas = getCadenasFromDB(connection, "Aceptadas", id);
			
			String rechazadas = getCadenasFromDB(connection, "Rechazadas", id);

			Integer id_config = getIdConfigFromDB(connection, id);
			
			Configuracion config = getConfiguracionFromDB(connection, id_config);
			
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
			close(connection);
			e.printStackTrace();
		}

		return response;
	};
	
	@WebMethod
    @WebResult(name="setSolucionResponse")
   public SetSolucionResponse setSolucion(@WebParam(name="setSolucionRequest") SetSolucionRequest request) {
    	SetSolucionResponse response = new SetSolucionResponse();
    	response.setResult("error");
    	Connection connection = null;
    	PreparedStatement pstmt = null;
		try {
			String id = request.getId();
			connection = ds.getConnection();
			
			updateSolucionesInDB(connection, id);
			
			ByteArrayOutputStream baos = ponerValoresAutomata(request);
			
			String sql = "INSERT INTO Solucion" +
						 " VALUES(null,'" + id + "'," + request.getAfp().getEstados() + "," +
						 request.getReconocimiento() + "," + request.getParecidoAF() +",'" + 
						 request.getAfp().getTipo() + "',?," +
						 request.getPasos() + ",'" + request.getMutador() + "','" + request.getFuncbondad() +
						 "','" + request.getCruzador() + "','" + request.getMetodoRes() + "'," + 
						 request.getPobmax() + "," + request.getMuestras() + "," + request.getParticiones() + ", "+
						 request.getId_configuracion() + ",null)";
			byte[] automataAsBytes = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(automataAsBytes);
			pstmt = connection.prepareStatement(sql);
			pstmt.setBinaryStream(1, bais, automataAsBytes.length);
			pstmt.executeUpdate();
			//connection.commit();
			
			close(pstmt);
			close(connection);
			response.setResult("OK");
		} catch (SQLException e) {
			close(pstmt);
			close(connection);
			e.printStackTrace();
		}
		
		//retriveFromDataBase();
 	
    	return response;
    };
	
	private ByteArrayOutputStream ponerValoresAutomata(SetSolucionRequest request) {
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
		return baos;
	}

	private void updateSolucionesInDB(Connection connection, String id) {
		Statement stmt0 = null;
		ResultSet rs0 = null;
		try {
			stmt0 = connection.createStatement();
			rs0 = stmt0.executeQuery("SELECT id, Soluciones" +
											   " FROM Problema" +
											   " WHERE id = " + id);
			if (rs0.next()) {
				int sol = rs0.getInt("Soluciones");
				Statement stmt1 = connection.createStatement();
				stmt1.executeUpdate("UPDATE Problema" +
									" SET Soluciones = " + (sol + 1) +
									" WHERE id = " + id);
				close(stmt1);
			}
			close(rs0);
			close(stmt0);
		} catch (SQLException e) {
			close(rs0);
			close(stmt0);
			e.printStackTrace();
		}
	}

	private Configuracion getConfiguracionFromDB(Connection connection,
			Integer id_config) {
		Configuracion config = null;
		Statement stmtConfig1 = null;
		ResultSet rsConfig1 = null;
		try {
			stmtConfig1 = connection.createStatement();
			rsConfig1 = stmtConfig1.executeQuery("SELECT * " + 
												 "FROM configuraciones " +
												 "WHERE configuraciones.id_configuracion = " + id_config);
			while (rsConfig1.next()) {
				int muestras = rsConfig1.getInt("Muestras");
				int estados = rsConfig1.getInt("Estados");
				int pobMax = rsConfig1.getInt("PobMax");
				int calculadorBondad = rsConfig1.getInt("calculadorBondad");
				int cruzador = rsConfig1.getInt("cruzador");
				int mutador = rsConfig1.getInt("mutador");
				int resolver = rsConfig1.getInt("resolver");
				int id_config2 = rsConfig1.getInt("id_configuracion");
				config = new Configuracion(id_config2, muestras, pobMax, estados, calculadorBondad, cruzador, mutador, resolver);
			}
			close(rsConfig1);
			close(stmtConfig1);
		} catch (SQLException e) {
			close(rsConfig1);
			close(stmtConfig1);
			e.printStackTrace();
		}
		return config;
	}

	private Integer getIdConfigFromDB(Connection connection, String id) {
		Integer id_config = -1;
		Statement stmtConfig0 = null;
		ResultSet rsConfig0 = null;
		try {
			stmtConfig0 = connection.createStatement();
			rsConfig0 = stmtConfig0.executeQuery("SELECT configuraciones.id_configuracion, " +
													   "IFNULL((SELECT COUNT(*) FROM solucion WHERE solucion.id_configuracion = configuraciones.id_configuracion),0) AS cuenta " +
													   "FROM  configuraciones WHERE configuraciones.id = \"" + id + "\" " +
													   "GROUP BY configuraciones.id_configuracion ORDER BY cuenta");
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
			close(stmtConfig0);
			close(rsConfig0);
		} catch (SQLException e) {
			close(stmtConfig0);
			close(rsConfig0);
			e.printStackTrace();
		}
		return id_config;
	}

	private String getCadenasFromDB(Connection connection, String tipo, String id) {
		Statement stmtCadenas = null;
		ResultSet rsCadenas = null;
		String cadenas = "";
		try {
			stmtCadenas = connection.createStatement();
			rsCadenas = stmtCadenas.executeQuery("SELECT " + tipo + ".Cadena as \"" + tipo + "\"" +
											 	" FROM Problema, "+tipo +
											 	" WHERE Problema.id = \"" + id + "\" AND Problema.id = "+tipo+".id");
			while (rsCadenas.next()) {
				if (cadenas.equals(""))
					cadenas = rsCadenas.getString(tipo);
				else
					cadenas += "," + rsCadenas.getString(tipo);
			}
			close(stmtCadenas);
			close(rsCadenas);
		} catch (SQLException e) {
			close(stmtCadenas);
			close(rsCadenas);
			e.printStackTrace();
		}
		return cadenas;
	}

    private String getIdFromDB(Connection connection) {
		String id = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT id, Soluciones FROM Problema ORDER BY Soluciones");
			if (rs.next()) {
				id = rs.getString("id");
			}
			close(rs);
			close(stmt);
		} catch (SQLException e) {
			close(rs);
			close(stmt);
			e.printStackTrace();
		}
		return id;
	}

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
