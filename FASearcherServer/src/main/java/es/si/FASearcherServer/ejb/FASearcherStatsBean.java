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

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import org.jboss.wsf.spi.annotation.WebContext;
import org.jboss.ws.annotation.EndpointConfig;

import es.si.FASearcherServer.data.stats.GetBasicStatsRequest;
import es.si.FASearcherServer.data.stats.GetBasicStatsResponse;

@Stateless
@WebService(endpointInterface="es.si.FASearcherServer.ejb.FASearcherStats")
@WebContext(contextRoot="/fasearcher")
@EndpointConfig(configName="Standard WSSecurity Endpoint")
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

}
