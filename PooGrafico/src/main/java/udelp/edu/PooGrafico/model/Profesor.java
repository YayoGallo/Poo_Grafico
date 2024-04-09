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
	public Profesor(String nombre,Date nacimiento, Character sexo, Date ingreso) throws ParseException {
		
		super(nombre, nacimiento, sexo);
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