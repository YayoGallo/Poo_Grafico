package udelp.edu.PooGrafico.utility;

import java.time.LocalDate;
import java.time.LocalTime;

public class Validaciones {
	public boolean validarFecha(String fecha) {
		boolean esFecha = false;
		String añoCadena;
		String[] cadena;
		int año;
		if (fecha != null & fecha.length() == 10 & fecha.charAt(2) == '-' & fecha.charAt(5) == '-') {
			cadena = fecha.split("-");
			añoCadena = cadena[2];
			año = validaAño(añoCadena);
			if (año != -1) {
				esFecha = true;
			}
		}
		return esFecha;
	}

	public int validaAño(String año) {
		int esValido;
		try {
			if (Integer.valueOf(año) >= 1900 & Integer.valueOf(año) <= LocalDate.now().getYear()) {
				esValido = Integer.valueOf(año);
			} else {
				esValido = -1;
			}
		} catch (NumberFormatException ex) {
			esValido = -1;
		}
		return esValido;
	}

	public byte esByte(String numero) {

		byte respuesta;
		try {
			respuesta = Byte.parseByte(numero);
		} catch (Exception e) {
			respuesta = -1;
		}
		return respuesta;
	}

	public Double validarPeso(String peso) {
		try {
			double aux = Double.parseDouble(peso);
			return (aux > 0) ? aux : null;
		} catch (Exception e) {
			return null;
		}
	}

	public Double validarEstatura(String estatura) {
		try {
			double aux = Double.parseDouble(estatura);
			return (aux > 0 && aux <= 3) ? aux : null;
		} catch (Exception e) {
			return null;
		}
	}

	public Integer esInt(String numero) {

		int respuesta;
		try {
			respuesta = Integer.parseInt(numero);
		} catch (Exception e) {
			respuesta = -1;
		}
		return respuesta;

	}

	public String validarHorario(String horario) {

		if (horario.length() == 11) {
			String[] partes = horario.split("-");
			String horaInicio = partes[0];
			String horaFin = partes[1]; 

			try {

				LocalTime inicio = LocalTime.parse(horaInicio);
				LocalTime fin = LocalTime.parse(horaFin);

				if (inicio.isBefore(fin)) {
					return horario;
				}
			} catch (Exception e) {

				return null;
			}
		}

		return null;
	}

	public Double esDecimal(String numero) {

		double respuesta;
		try {
			respuesta = Double.parseDouble(numero);
		} catch (Exception e) {
			respuesta = 0;
		}
		return respuesta;

	}

}
