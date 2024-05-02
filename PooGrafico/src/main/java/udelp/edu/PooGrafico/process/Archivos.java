package udelp.edu.PooGrafico.process;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Archivos{
	private final static String PATH = "C:\\archivos_json\\";

	public <T> void guardaArchivo(String nombre, List<T> lista) {
		FileWriter writer = null;
		String filePath = PATH + nombre + ".json";
		try {
			writer = new FileWriter(filePath);
			Gson builder = new GsonBuilder().create();
			builder.toJson(lista, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != writer) {
					writer.close();
				}
			} catch (Exception e) {
			}
		}
	}

	public <T> List<T> leerArchivo(String nombre, Type typeOfT) {
	    List<T> list = new ArrayList<>();
	    String filePath = PATH + nombre + ".json";
	    FileReader fReader = null;
	    try {
	        fReader = new FileReader(filePath);
	        Gson gson = new Gson();
	        list = gson.fromJson(fReader, typeOfT);
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo: " + e.getMessage());
	    } finally {
	        if (fReader != null) {
	            try {
	            	System.out.println("Se cerro el archivo");
	                fReader.close();
	            } catch (IOException e) {
	                System.err.println("Error al cerrar el FileReader: " + e.getMessage());
	            }
	        }
	    }
	    return list;
	}
	
	
	 public boolean existeArchivo(String nombreArchivo) {
	        File archivo = new File(PATH + nombreArchivo + ".json");
	        return archivo.exists();
	    }
	
}