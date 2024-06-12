package udelp.edu.PooGrafico.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;


@Data
public class Profesor extends Persona {

	private Date fechaDeIngreso;
	private List<Materia> materias;
	private Carrera esDirector;
	
	public Profesor(String nombre,Date nacimiento, Character sexo, Date ingreso, String cuenta) throws ParseException {
		
		super(nombre, nacimiento, sexo, cuenta);
        this.fechaDeIngreso = ingreso;
		
	}
	public void addMateria (Materia materia) {
		if (null != materias) {
			this.materias.add(materia);
		} else {
			this.materias = new ArrayList<Materia>();
			this.materias.add(materia);
		}
	}
}