package udelp.edu.PooGrafico.model;

import lombok.Data;

@Data
public class Carrera {
	private String nombre;
	private boolean conDirecCarrera;

	@Override
	public String toString() {
		return  nombre;
	}
	
	
}

