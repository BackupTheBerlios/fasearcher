package es.si.FASearcherServer.ejb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import org.jboss.wsf.spi.annotation.WebContext;
import org.jboss.ws.annotation.EndpointConfig;

import es.si.FASearcherServer.data.stats.GetAdvancedStatsRequest;
import es.si.FASearcherServer.data.stats.GetAdvancedStatsResponse;
import es.si.FASearcherServer.data.stats.GetBasicStatsRequest;
import es.si.FASearcherServer.data.stats.GetBasicStatsResponse;
import es.si.FASearcherServer.data.stats.GetValidValuesRequest;
import es.si.FASearcherServer.data.stats.GetValidValuesResponse;

@Stateless
@WebService(endpointInterface="es.si.FASearcherServer.ejb.FASearcherStats")
@WebContext(contextRoot="/fasearcher")
public class FASearcherStatsBean implements FASearcherStats {

	@Resource(mappedName="java:/MySqlDS")
	DataSource ds;
	
	@WebResult(name="getBasicStatsResponse")
	public GetBasicStatsResponse getBasicStats(@WebParam(name="getBasicStatsRequest") GetBasicStatsRequest request) {
		GetBasicStatsResponse respuesta = new GetBasicStatsResponse();
		try {
			Connection connection = ds.getConnection();
	
			Statement stmt0 = connection.createStatement();
			ResultSet rs0 = stmt0.executeQuery("SELECT id, soluciones" +
											   " FROM Problema");
			int cont = 0;
			int sols = 0;
			while (rs0.next()) {
				cont++;
				sols += rs0.getInt("soluciones");
			}
			respuesta.setNumProblemas(cont);
			respuesta.setMediaSoluciones((float) sols / cont);
			
			stmt0 = connection.createStatement();
			rs0 = stmt0.executeQuery("SELECT id, Reconocimiento, ParecidoAF" +
									 " FROM Solucion");
			
			int[] histRec = new int[20];
			int[] histPar = new int[20];
			while (rs0.next()) {
				int temp = (int) (rs0.getDouble("Reconocimiento") * 20);
				if (temp == 20)
					temp = 19;
				histRec[temp]++;
				
				temp = (int) (rs0.getDouble("ParecidoAF") * 20);
				if (temp == 20)
					temp = 19;
				histPar[temp]++;
			}
			
			List<Double> listRec = new ArrayList<Double>();
			List<Double> listPar = new ArrayList<Double>();
			for (int i = 0; i < 20; i++) {
				listRec.add((double) histRec[i] / cont);
				listPar.add((double) histPar[i] / cont);
			}
			respuesta.setHistReconocimiento(listRec);
			respuesta.setHistParecido(listPar);
			
			rs0.close();
			stmt0.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return respuesta;
	}

    @WebResult(name="getValidValuesResponse")
    public GetValidValuesResponse getValidValues(@WebParam(name="getValidValuesRequest") GetValidValuesRequest request) {
    	GetValidValuesResponse response = new GetValidValuesResponse();
    	
		try {
			Connection connection = ds.getConnection();
			
			String problemas = null;
			if (request.getId_problemas() != null && request.getId_problemas().size() != 0) {
				if (request.getId_problemas().size() == 1) {
					problemas = "id = \"" + request.getId_problemas().get(0) + "\"";
				} else if (request.getId_problemas().size() > 1) {
					problemas = "(";
					int cont = 0;
					for (String id : request.getId_problemas()){
						if (cont != 0)
							problemas += " OR ";
						cont++;
						problemas += "id = \"" + id + "\"";
					}
					problemas += ")";
				}
			}
			
			String configuraciones = null;
			if (request.getId_problemas() != null && request.getId_problemas().size() == 1) {
				if (request.getId_config() != null)
					configuraciones = "id_configuracion=" + request.getId_config();
			}
			
			
			String where = null;
			
			if (problemas != null) {
				where = " WHERE " + problemas;
			}
			if (configuraciones != null) {
				where += " AND " + configuraciones;
			}
			
			response.setEstados(getIntegers(connection, "Estados", "Solucion", where));

			response.setPobMax(getIntegers(connection, "PobMax", "Solucion", where));

			response.setMuestras(getIntegers(connection, "Muestras", "Solucion", where));

			response.setFuncBondad(getStrings(connection, "FuncBondad", "Solucion", where));
			
			response.setCruzadores(getStrings(connection, "Cruzador", "Solucion", where));
			
			response.setMutadores(getStrings(connection, "Mutador", "Solucion", where));
			
			response.setPasos(getIntegers(connection, "Pasos", "Solucion", where));

			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	return response;
    }
    
    
    @WebResult(name="getAdvancedStatsResponse")
    public GetAdvancedStatsResponse getAdvancedStats(@WebParam(name="getAdvancedStatsRequest") GetAdvancedStatsRequest request) {
    	GetAdvancedStatsResponse response = new GetAdvancedStatsResponse();
		
    	try {
			Connection connection = ds.getConnection();

	    	String [] condiciones = new String[9];
	    	
			condiciones[0] = condiciones_String("id", request.getId_problemas());
	    	
			condiciones[1] = condiciones_Integer("id_configuracion", request.getId_config());
	
			condiciones[2] = condiciones_String("Mutador", request.getMutadores());
			
			condiciones[3] = condiciones_String("Cruzador", request.getCruzadores());
	
			condiciones[4] = condiciones_String("FuncBondad", request.getFuncBondad());
	
			condiciones[5] = condiciones_Integer("PobMax", request.getPobmax());
			
			condiciones[6] = condiciones_Integer("Muestras", request.getMuestras());
			
			condiciones[7] = condiciones_Integer("Pasos", request.getPasos());
	
			condiciones[8] = condiciones_Integer("Estados", request.getEstados());
			
			boolean hay_condiciones = false;
			for (int i = 0; i < 9; i++) 
				if (condiciones[i] != null)
					hay_condiciones = true;
			
			String where = null;
			if (hay_condiciones) {
				for (int i = 0; i < 9; i++) {
					if (condiciones[i] != null) {
						if (where == null) {
							where = " WHERE " + condiciones[i];
						} else {
							where += " AND " + condiciones[i];
						}
					}
				}
			}
		
	
			Statement stmt0 = connection.createStatement();
			ResultSet rs0 = stmt0.executeQuery("SELECT id, soluciones" +
											   " FROM Problema" + (condiciones[0] != null ? " WHERE " + condiciones[0] : "") );
			int cont = 0;
			int sols = 0;
			while (rs0.next()) {
				cont++;
				sols += rs0.getInt("soluciones");
			}
			response.setNumProblemas(cont);
			response.setMediaSoluciones((float) sols / cont);
			
			stmt0 = connection.createStatement();
			rs0 = stmt0.executeQuery("SELECT id, Reconocimiento, ParecidoAF" +
									 " FROM Solucion" + (where != null ? where : ""));
			System.out.println("adv stats: " + "SELECT id, Reconocimiento, ParecidoAF FROM Solucion" + (where != null ? where : ""));
			
			int[] histRec = new int[20];
			int[] histPar = new int[20];
			while (rs0.next()) {
				int temp = (int) (rs0.getDouble("Reconocimiento") * 20);
				if (temp == 20)
					temp = 19;
				histRec[temp]++;
				
				temp = (int) (rs0.getDouble("ParecidoAF") * 20);
				if (temp == 20)
					temp = 19;
				histPar[temp]++;
			}
			
			List<Double> listRec = new ArrayList<Double>();
			List<Double> listPar = new ArrayList<Double>();
			for (int i = 0; i < 20; i++) {
				listRec.add((double) histRec[i] / cont);
				listPar.add((double) histPar[i] / cont);
			}
			response.setHistReconocimiento(listRec);
			response.setHistParecido(listPar);
			
			rs0.close();
			stmt0.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    	return response;
    }

    String condiciones_String(String columna, List<String> lista) throws SQLException {
		String condiciones = null;
		if (lista != null && lista.size() != 0) {
			if (lista.size() == 1) {
				condiciones = columna + " = \"" + lista.get(0) + "\"";
			} else if (lista.size() > 1) {
				condiciones = "(";
				int cont = 0;
				for (String id : lista){
					if (cont != 0)
						condiciones += " OR ";
					cont++;
					condiciones += columna + " = \"" + id + "\"";
				}
				condiciones += ")";
			}
		}
		return condiciones;
    }

    String condiciones_Integer(String columna, List<Integer> lista) throws SQLException {
		String condiciones = null;
		if (lista != null && lista.size() != 0) {
			if (lista.size() == 1) {
				condiciones = columna + " = \"" + lista.get(0) + "\"";
			} else if (lista.size() > 1) {
				condiciones = "(";
				int cont = 0;
				for (Integer id : lista){
					if (cont != 0)
						condiciones += " OR ";
					cont++;
					condiciones += columna + " = \"" + id + "\"";
				}
				condiciones += ")";
			}
		}
		return condiciones;
    }

    List<Integer> getIntegers(Connection connection, String columna, String tabla, String where) throws SQLException {
		Statement stmt0 = connection.createStatement();
		ResultSet rs0 = stmt0.executeQuery("SELECT DISTINCTROW " + columna +
				" FROM " + tabla + (where == null? "" : where) +
				" ORDER BY " + columna);
		
		List<Integer> respuesta = new ArrayList<Integer>();
		while (rs0.next()) {
			respuesta.add(rs0.getInt(columna));
		}
		
		rs0.close();
		stmt0.close();
		return respuesta;
    }
    
    List<String> getStrings(Connection connection, String columna, String tabla, String where) throws SQLException {
		Statement stmt0 = connection.createStatement();
		ResultSet rs0 = stmt0.executeQuery("SELECT DISTINCTROW " + columna +
				" FROM " + tabla + (where == null? "" : where) +
				" ORDER BY " + columna);
		
		List<String> respuesta = new ArrayList<String>();
		while (rs0.next()) {
			respuesta.add(rs0.getString(columna));
		}
		
		rs0.close();
		stmt0.close();
		return respuesta;
    }

}
