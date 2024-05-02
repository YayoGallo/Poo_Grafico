package udelp.edu.PooGrafico.model;

import lombok.Data;

@Data
public class Rol {
	private String nombre;
	private String codigo;
	
	public Rol(String nombre, String codigo) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
	}
	
	
}
