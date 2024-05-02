package udelp.edu.PooGrafico.process;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.Usuario;

public class ControlUsuarios {

	private static ControlUsuarios instancia;

	private List<Usuario> usuarios;

	private Archivos archivo = new Archivos();

	private ControlUsuarios() {

		usuarios = new ArrayList<Usuario>();

	}

	public static ControlUsuarios getInstance() {
		if (null == instancia) {
			instancia = new ControlUsuarios();
		}
		return instancia;
	}

	public void alta(Usuario usuario) {
		usuarios.add(usuario);
	}

	public Usuario coincidencias(String codigo, String password) {

		for (Usuario u : usuarios) {
			if (u.getCodigo().equals(codigo)) {
				if (u.getPassword().equals(password)) {
					return u;
				}
				return null;
			}
		}
		return null;
	}

	public <T> void cargarUsuarios(String archivo, Type tipoDeLista) {
    	if(this.archivo.existeArchivo(archivo)) {
            List<Usuario> datos = this.archivo.leerArchivo(archivo, tipoDeLista);
            usuarios=datos;
    	}
    }
	
	public List<Usuario> getUsuarios() {
		return usuarios;

	}

}
