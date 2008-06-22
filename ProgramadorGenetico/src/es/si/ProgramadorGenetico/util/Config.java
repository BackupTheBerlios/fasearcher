package es.si.ProgramadorGenetico.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
/**
 * Clase que almacena informacion necesario 
 * del servidor en el atributo properties
 */
public class Config {

	private static Config config;
	
	private Properties properties;
	
	public static Config getInstance() {
		if (config != null)
			return config;
		config = new Config();
		return config;
	}
	
	private Config() {
		URL url = ClassLoader.getSystemResource("config.properties");
		try {
			FileInputStream fis = new FileInputStream(new File(url.getFile()));
			properties = new Properties();
			properties.load(fis);
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getProperty(String prop) {
		return properties.getProperty(prop);
	}
}
