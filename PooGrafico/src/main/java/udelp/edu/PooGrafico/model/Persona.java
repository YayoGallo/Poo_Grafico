package udelp.edu.PooGrafico.model;

import lombok.Data;
import udelp.edu.PooGrafico.utility.Constantes;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Random;

@Data
public class Persona {

	private String nombre;

	private Date fechaNacimiento;

	private String id;

	private Character sexo;

	private Double peso;

	private Double altura;
	
	private Integer añoIngreso;

	public Persona() {

		this.id = "0";

	}

	public Persona(String nombre, Date nacimiento, Character sexo, String cuenta) throws ParseException {

		this.nombre = nombre;
		this.fechaNacimiento = nacimiento;
		this.sexo = sexo;
		this.añoIngreso= LocalDate.now().getYear();
		this.id = generarID(cuenta);

	}

	public Persona(String nombre, String fechaNacimiento, Character sexo, Double peso, Double altura)
			throws ParseException {

		this.nombre = nombre;
		SimpleDateFormat formatoFecha = new SimpleDateFormat(Constantes.FORMATO);
		this.fechaNacimiento = formatoFecha.parse(fechaNacimiento);
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;

	}

	private String generarID(String cuenta) {
	    String respuesta = añoIngreso + "-";
	    int cuentaInt = Integer.parseInt(cuenta); // Convertir la cadena a entero
	    String aux = String.format("%05d", cuentaInt); // Formatear el entero con ceros a la izquierda
	    respuesta += aux;
	    return respuesta;
	}

}
