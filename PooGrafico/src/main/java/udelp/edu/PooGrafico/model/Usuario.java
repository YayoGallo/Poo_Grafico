package udelp.edu.PooGrafico.model;

import lombok.Data;

@Data
public class Usuario {
	
	private String nombre;
	private String codigo;
	private String password;
	private Rol rol;
	
	public Usuario(String nombre, String codigo, String password, Rol rol) {
		this.nombre = nombre;
		this.codigo = codigo;
		this.password = password;
		this.rol = rol;
	}
	
	

}
