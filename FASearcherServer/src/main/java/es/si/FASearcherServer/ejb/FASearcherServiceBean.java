package es.si.FASearcherServer.ejb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

import es.si.FASearcherServer.data.AddProblemaRequest;
import es.si.FASearcherServer.data.AddProblemaResponse;

@Stateless
@WebService(endpointInterface="es.si.FASearcherServer.ejb.FASearcherService")
@WebContext(contextRoot="/fasearcher")
public class FASearcherServiceBean implements FASearcherService {

	@Resource(mappedName="java:/MySqlDS")
	DataSource ds;

	@WebResult(name="addProblemaResponse")
    public AddProblemaResponse addProblema(@WebParam(name="addProblemaRequest") AddProblemaRequest request) {
		AddProblemaResponse respuesta = new AddProblemaResponse();

		Random random = new Random();
		String id = "" + random.nextInt(10000000);

		try {

			Connection connection = ds.getConnection();
			String sql = "INSERT INTO Problema VALUES('" + id + "',0)";
			PreparedStatement pstmt = connection.prepareStatement(sql);
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

			sql = "INSERT INTO ExtraInfo VALUES('" + id + "'" +
			(request.getEstados() != null ? "," + request.getEstados() : ",null") +
			(request.getPobMax() != null ? "," + request.getPobMax() : ",null") +
			(request.getTipoAutomata() != null ? ",'" + request.getTipoAutomata() + "'": ",null") +
			(request.getAfp() != null ? ",?)" : ",null)");
			pstmt = connection.prepareStatement(sql);
			System.out.println("SQL: " + sql);
			if (request.getAfp() != null) {
				es.si.ProgramadorGenetico.ProblemaAFP.AFP automata = new es.si.ProgramadorGenetico.ProblemaAFP.AFP(request.getAfp().getEstados().intValue());

				double[][][] transiciones = automata.getTransiciones();

				List<String> lista = request.getAfp().getTransiciones();
				it = lista.iterator();
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

				byte[] automataAsBytes = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(automataAsBytes);
				pstmt.setBinaryStream(1, bais, automataAsBytes.length);
			}
			pstmt.executeUpdate();
			pstmt.close();


			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		respuesta.setId(id);
		return respuesta;
	}

}
