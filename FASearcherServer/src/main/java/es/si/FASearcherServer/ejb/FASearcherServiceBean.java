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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import org.jboss.wsf.spi.annotation.WebContext;
import org.jboss.ws.annotation.EndpointConfig;

import es.si.FASearcherServer.data.AFP;
import es.si.FASearcherServer.data.Configuracion;
import es.si.FASearcherServer.data.Problema;
import es.si.FASearcherServer.data.Solucion;
import es.si.FASearcherServer.data.service.AddProblemaRequest;
import es.si.FASearcherServer.data.service.AddProblemaResponse;
import es.si.FASearcherServer.data.service.GetProblemaRequest;
import es.si.FASearcherServer.data.service.GetProblemaResponse;
import es.si.FASearcherServer.data.service.GetProblemasRequest;
import es.si.FASearcherServer.data.service.GetProblemasResponse;
import es.si.FASearcherServer.data.service.GetSolucionRequest;
import es.si.FASearcherServer.data.service.GetSolucionResponse;
import es.si.FASearcherServer.data.service.GetSolucionesRequest;
import es.si.FASearcherServer.data.service.GetSolucionesResponse;
import es.si.FASearcherServer.data.service.RemoveProblemaRequest;
import es.si.FASearcherServer.data.service.RemoveProblemaResponse;

@Stateless
@WebService(endpointInterface="es.si.FASearcherServer.ejb.FASearcherService")
@WebContext(contextRoot="/fasearcher")
public class FASearcherServiceBean implements FASearcherService {

	@Resource(mappedName="java:/MySqlDS")
	DataSource ds;

	@WebResult(name="addProblemaResponse")
    public AddProblemaResponse addProblema(@WebParam(name="addProblemaRequest") AddProblemaRequest request) {
		AddProblemaResponse respuesta = new AddProblemaResponse();
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql;
		String id = "-1";
		try {
			Connection connection = ds.getConnection();

			id = getNewIdFromDB(connection);
			
			insertarInfoInDB(connection, id, request);
			
			insertarExtraInfoInDB(connection, id, request);

			if (request.getConfiguraciones() != null) {
				for(Configuracion config : request.getConfiguraciones()) {
					sql = "INSERT INTO configuraciones VALUES(null, '" + id + "'," + config.getEstados() +"," +
															  config.getPobMax() + "," + config.getMuestras() + "," +
															  config.getCalculadorBondad() + "," + config.getCruzador() +
															  "," + config.getMutador() + "," + config.getResolver() +")";
					pstmt = connection.prepareStatement(sql);
					pstmt.executeUpdate();
					pstmt.close();			
				}
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		respuesta.setId(id);
		return respuesta;
	}
	
	@WebResult(name="getProblemaResponse")
    public GetProblemaResponse getProblema(@WebParam(name="getProblemaRequest") GetProblemaRequest request) {
    	GetProblemaResponse response = new GetProblemaResponse();
    	String id = request.getId();
    	
		try {
			Connection connection = ds.getConnection();

			ResultSet rs = null;
			
			PreparedStatement pstmt = null;
			String sql = "SELECT Descripcion FROM Problema WHERE id = '" + id + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			response.setDescripcion(rs.getString("Descripcion"));
			rs.close();
			pstmt.close();

			sql = "SELECT Cadena FROM Aceptadas WHERE id = '" + id + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
				response.getAceptadas().add(rs.getString("Cadena"));
			rs.close();
			pstmt.close();
			
			sql = "SELECT Cadena FROM Rechazadas WHERE id = '" + id + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
				response.getRechazadas().add(rs.getString("Cadena"));
			rs.close();
			pstmt.close();
			
			sql = "SELECT id_configuracion, Estados, PobMax, Muestras, calculadorBondad, cruzador, mutador, resolver FROM Configuraciones WHERE id = '" + id + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Configuracion config = new Configuracion();
				config.setId(rs.getInt("id_configuracion"));
				config.setEstados(rs.getInt("Estados"));
				config.setPobMax(rs.getInt("PobMax"));
				config.setMuestras(rs.getInt("Muestras"));
				config.setCalculadorBondad(rs.getInt("calculadorBondad"));
				config.setCruzador(rs.getInt("cruzador"));
				config.setMutador(rs.getInt("mutador"));
				config.setResolver(rs.getInt("resolver"));
				
				response.getConfiguraciones().add(config);
			}
			rs.close();
			pstmt.close();
			
			sql = "SELECT TipoAutomata, AutomataOriginal FROM ExtraInfo WHERE id = '" + id + "'";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			response.setTipoAutomata(rs.getString("TipoAutomata"));
			
    		es.si.ProgramadorGenetico.ProblemaAFP.AFP automata;
   			try {
   				byte[] st = rs.getBytes("AutomataOriginal");
   				ByteArrayInputStream baip = new ByteArrayInputStream(st);
   				ObjectInputStream ois = new ObjectInputStream(baip);
   				automata = (es.si.ProgramadorGenetico.ProblemaAFP.AFP)ois.readObject();
   			
	   			AFP afp = AFPfromAutomata(automata);
	   			
				response.setAfp(afp);
   			} catch (IOException e) {
   				e.printStackTrace();
   			} catch (ClassNotFoundException e) {
   				e.printStackTrace();
   			}
   			
			rs.close();
			pstmt.close();
			
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return response;
    }
	
	@WebResult(name="getProblemasResponse")
	public GetProblemasResponse getProblemas(@WebParam(name="getProblemasRequest") GetProblemasRequest request) {
		GetProblemasResponse response = new GetProblemasResponse();
		
		try {

			Connection connection = ds.getConnection();

			ResultSet rs = null;
			
			PreparedStatement pstmt = null;
			String sql = "SELECT id, Soluciones, Descripcion FROM Problema";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Problema problema = new Problema();
				problema.setId(rs.getString("id"));
				problema.setDescripcion(rs.getString("Descripcion"));
				problema.setSoluciones(rs.getInt("Soluciones"));
				response.getProblemas().add(problema);
			}
			
			rs.close();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return response;
	}

    @WebResult(name="getSolucionesResponse")
    public GetSolucionesResponse getSoluciones(@WebParam(name="getSolucionesRequest") GetSolucionesRequest request) {
    	GetSolucionesResponse response = new GetSolucionesResponse();
    	
		try {
			Connection connection = ds.getConnection();

			ResultSet rs = null;
			PreparedStatement pstmt = null;
			String sql = "SELECT * FROM Solucion WHERE id = " + request.getId();
			if (request.getId_config() != null)
				sql += " AND id_configuracion = " + request.getId_config();
			System.out.println("Get soluciones " + sql );
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				Solucion sol = new Solucion();
				sol.setCruzador(rs.getString("cruzador"));
				sol.setEstados(rs.getInt("Estados"));
				sol.setFuncBondad(rs.getString("FuncBondad"));
				sol.setId(rs.getString("id_solucion"));
				sol.setMuestras(rs.getString("muestras"));
				sol.setMutador(rs.getString("mutador"));
				sol.setParecidoAF(rs.getDouble("ParecidoAF"));
				sol.setPasos(rs.getInt("pasos"));
				sol.setPobMax(rs.getInt("PobMax"));
				sol.setReconocimiento(rs.getDouble("Reconocimiento"));
				sol.setTipoAutomata(rs.getString("TipoAutomata"));
				sol.setId_configuracion(rs.getInt("id_configuracion"));
				response.getSoluciones().add(sol);
			}
			
			rs.close();
			pstmt.close();
			
			if (request.getId_config() == null) {
				response.setConfiguraciones(new ArrayList<Configuracion>());
				sql = "SELECT * FROM configuraciones WHERE id = " + request.getId();
				pstmt = connection.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if (!rs.next()) {
					rs.close();
					pstmt.close();

					sql = "SELECT * FROM configuraciones WHERE id = -1";
					pstmt = connection.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					rs.next();
				}
	
				do {
					Configuracion config = new Configuracion();
					config.setCalculadorBondad(rs.getInt("calculadorBondad"));
					config.setCruzador(rs.getInt("cruzador"));
					config.setEstados(rs.getInt("Estados"));
					config.setId(rs.getInt("id_configuracion"));
					config.setMuestras(rs.getInt("Muestras"));
					config.setMutador(rs.getInt("mutador"));
					config.setPobMax(rs.getInt("PobMax"));
					config.setResolver(rs.getInt("resolver"));
					response.getConfiguraciones().add(config);
				} while(rs.next());
				
				rs.close();
				pstmt.close();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	return response;
    }

    @WebResult(name="getSolucionResponse")
    public GetSolucionResponse getSolucion(@WebParam(name="getSolucionRequest") GetSolucionRequest request)  {
    	GetSolucionResponse response = new GetSolucionResponse();
    	String id = request.getId();
		try {
			Connection connection = ds.getConnection();
			ResultSet rs = null;
			PreparedStatement pstmt = null;
				
			String sql = "SELECT TipoAutomata, Automata FROM Solucion WHERE id_solucion = " + id;
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			response.setTipoAutomata(rs.getString("TipoAutomata"));
			
    		es.si.ProgramadorGenetico.ProblemaAFP.AFP automata;
   			try {
   				byte[] st = rs.getBytes("Automata");
   				ByteArrayInputStream baip = new ByteArrayInputStream(st);
   				ObjectInputStream ois = new ObjectInputStream(baip);
   				automata = (es.si.ProgramadorGenetico.ProblemaAFP.AFP)ois.readObject();
   			
	   			AFP afp = AFPfromAutomata(automata);
	   			
				response.setAfp(afp);
   			} catch (IOException e) {
   				e.printStackTrace();
   			} catch (ClassNotFoundException e) {
   				e.printStackTrace();
   			}

			rs.close();
			pstmt.close();
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return response;
    }

    
    @WebResult(name="removeProblemaResponse")
    public RemoveProblemaResponse removeProblema(@WebParam(name="removeProblemaRequest") RemoveProblemaRequest request)  {
		try {
			Connection connection = ds.getConnection();

			String id = request.getId();
			
			PreparedStatement pstmt = null;

			deleteFromDB(connection, "Aceptadas", id);
			deleteFromDB(connection, "Rechazadas", id);
			deleteFromDB(connection, "Configuraciones", id);
			deleteFromDB(connection, "ExtraInfo", id);
			deleteFromDB(connection, "Solucion", id);
			deleteFromDB(connection, "Problema", id);
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new RemoveProblemaResponse();
    }
    
    private void deleteFromDB(Connection connection, String table, String id) {
		String sql = "DELETE FROM " + table + " WHERE id = '" + id + "'";
		PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			close(pstmt);
			e.printStackTrace();
		}
    }
    
    private void insertarExtraInfoInDB(Connection connection, String id,
			AddProblemaRequest request) {
		String sql;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			sql = "INSERT INTO ExtraInfo VALUES('" + id + "'" +
			(request.getEstados() != null ? "," + request.getEstados() : ",null") +
			(request.getPobMax() != null ? "," + request.getPobMax() : ",null") +
			(request.getTipoAutomata() != null ? ",'" + request.getTipoAutomata() + "'": ",null") +
			(request.getAfp() != null ? ",?)" : ",null)");
			pstmt = connection.prepareStatement(sql);
			if (request.getAfp() != null) {
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
	
				byte[] automataAsBytes = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(automataAsBytes);
				pstmt.setBinaryStream(1, bais, automataAsBytes.length);
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		}
	}

	private void insertarInfoInDB(Connection connection, String id,
			AddProblemaRequest request) {
		String sql;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
	    	if (request.getDescripcion() != null)
				sql = "INSERT INTO Problema VALUES('" + id + "',0,'" + request.getDescripcion()  + "')";
			else
				sql = "INSERT INTO Problema VALUES('" + id + "',0)";
			pstmt = connection.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
	
			Iterator<String> it = request.getAceptadas().iterator();
			while(it.hasNext()) {
				sql = "INSERT INTO Aceptadas VALUES('" + id + "','" + it.next() +"')";
				pstmt = connection.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close();			
			}
	
			it = request.getRechazadas().iterator();
			while(it.hasNext()) {
				sql = "INSERT INTO Rechazadas VALUES('" + id + "','" + it.next() +"')";
				pstmt = connection.prepareStatement(sql);
				pstmt.executeUpdate();
				pstmt.close();			
			}
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		}
	}

	private String getNewIdFromDB(Connection connection) {
		Random random = new Random();
		String id = "" + random.nextInt(10000000);
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			String sql;
			do {
				id = "" + random.nextInt(10000000);
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				sql = "SELECT id FROM Problema WHERE id = " + id;
				pstmt = connection.prepareStatement(sql);
				rs = pstmt.executeQuery();
			} while (rs.next());
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		}
		return id;
	}



	private AFP AFPfromAutomata(es.si.ProgramadorGenetico.ProblemaAFP.AFP automata) {
			AFP afp = new AFP();
   			afp.setEstados(automata.getEstados());
   			int estados = automata.getEstados();
   			
			String probFinales = "";
			float[] prob = automata.getProbabilidadesFinal();
			for (int i = 0; i < estados; i ++)
				probFinales += (probFinales.equals("") ? prob[i] : ";" + prob[i]);
			afp.setProbFinales(probFinales);
			
			String[] trans = new String[estados*2];
			float[][][] transiciones = automata.getTransiciones();
			for (int i = 0; i < estados; i ++) {
				for (int j = 0; j < 2; j ++) {
					trans[i*2 +j] = "" + i + ":" + j + ":";
					for (int k = 0; k < estados+1; k++) {
						float temp = (transiciones[i][j][k] < 0.0001 ? 0 : transiciones[i][j][k]);
						temp = (temp > 0.9999 ? 1 : temp);
						NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
						format.setMaximumFractionDigits(4);
						String temp2 = format.format(temp);
						trans[i*2+j] += (k == 0 ? temp2 : ";" + temp2);
					}
				}
			}
			if (afp.getTransiciones() == null)
				afp.setTransiciones(new ArrayList<String>());
			for (int i = 0; i < trans.length; i++)
				afp.getTransiciones().add(trans[i]);
			
			return afp;
	}


    

	private void close(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
		}
	}
	
	private void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		}
	}

	private void close(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
		}
	}


}
