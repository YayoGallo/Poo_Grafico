package udelp.edu.PooGrafico.model;

import lombok.Data;

@Data
public class Carrera {
	private String nombre;

	@Override
	public String toString() {
		return  nombre;
	}
	
	
}

