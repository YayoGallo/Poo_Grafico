package udelp.edu.PooGrafico.model;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class Estudiante extends Persona {
	
	private Integer semestre;
	private String generacion;
	private Carrera carrera;
	
	private List<MateriaAlumno> materias;
	
	public Estudiante(String nombre,Date fechaNacimiento, Character sexo, String cuenta) throws ParseException {
		
		super(nombre, fechaNacimiento, sexo, cuenta);
		
	}
	
	@Override
	public String toString() {
		return  getNombre()+" : "+carrera.getNombre();
	}
}
