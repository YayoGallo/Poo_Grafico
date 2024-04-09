package udelp.edu.PooGrafico.process;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import udelp.edu.PooGrafico.model.Carrera;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.Materia;
import udelp.edu.PooGrafico.model.MateriaAlumno;
import udelp.edu.PooGrafico.model.Profesor;


public class ControlEscolar {

	private List<Carrera> carreras;
	private List<Materia> materias;
	private List<Profesor> profesores;
	private List<Estudiante> estudiantes;

	public Carrera existeCarrera(String nombre) {

		if (carreras != null) {
			for (int i = 0; i < carreras.size(); i++) {
				if (carreras.get(i).getNombre().equals(nombre)) {

					return carreras.get(i);
				}
			}
		}
		return null;
	}

	public Estudiante existeEstiudiante(String id) {

		if (estudiantes != null) {
			for (int i = 0; i < estudiantes.size(); i++) {
				if (estudiantes.get(i).getId().equals(id)) {

					return estudiantes.get(i);
				}
			}
		}
		return null;
	}

	public void altaCarrera(Carrera carrera) {

		if (null != carreras) {
			carreras.add(carrera);
		} else {
			carreras = new ArrayList<Carrera>();
			carreras.add(carrera);
		}

	}

	public void altaMateria(Materia materia) {
		if (null != materias) {
			materias.add(materia);
		} else {
			materias = new ArrayList<Materia>();
			materias.add(materia);
		}
	}

	public void altaProfesor(Profesor profesor) {
		if (null != profesores) {
			profesores.add(profesor);
		} else {
			profesores = new ArrayList<Profesor>();
			profesores.add(profesor);
		}
	}

	public void altaEstudiante(Estudiante estudiante) {
		if (null != estudiantes) {
			estudiante.setGeneracion(
					String.valueOf(LocalDate.now().getYear()) + "-" + String.valueOf((LocalDate.now().getYear()) + 4));
			estudiantes.add(estudiante);
		} else {
			estudiante.setGeneracion(
					String.valueOf(LocalDate.now().getYear()) + "-" + String.valueOf((LocalDate.now().getYear()) + 4));
			estudiantes = new ArrayList<Estudiante>();
			estudiantes.add(estudiante);
		}
	}

	public boolean asignarMateria(String idProfesor, String idMateria) {
		boolean respuesta = false;
		if (null != materias && null != profesores) {
			for (int i = 0; i < materias.size(); i++) {
				if (materias.get(i).getId().equals(idMateria)) {
					for (int j = 0; j < profesores.size(); j++) {
						if (profesores.get(j).getId().equals(idProfesor)) {
							profesores.get(j).addMateria(materias.get(i));
							respuesta = true;
						}
					}
				}
			}
		}
		return respuesta;
	}

	public boolean asignarCarrera(String idEstudiante, String nombre) {
		boolean respuesta = false;
		if (null != carreras && null != estudiantes) {
			for (int i = 0; i < carreras.size(); i++) {
				if (carreras.get(i).getNombre().equals(nombre)) {
					for (int j = 0; j < estudiantes.size(); j++) {
						if (estudiantes.get(j).getId().equals(idEstudiante)) {
							estudiantes.get(j).setCarrera(carreras.get(i));
							materiasAlumno(estudiantes.get(j));
							respuesta = true;
						}
					}
				}
			}
		}
		return respuesta;
	}

	public void materiasAlumno(Estudiante estudiante) {

		List<MateriaAlumno> auxList = new ArrayList<MateriaAlumno>();
		Carrera aux = estudiante.getCarrera();
		for (int i = 0; i < materias.size(); i++) {
			if (materias.get(i).getCarrera().equals(aux)) {
				MateriaAlumno aux2 = new MateriaAlumno(materias.get(i));
				aux2.setId(materias.get(i).getId());
				auxList.add(aux2);
			}
		}
		estudiante.setMaterias(auxList);

	}

	public boolean calificarMateria(Estudiante estudiante, String idMateria, Double primerParcial,
			Double segundoParcial, Double proyecto, Double examenFinal) {
		boolean respuesta = false;
		List<MateriaAlumno> aux = estudiante.getMaterias();
		for (int j = 0; j < aux.size(); j++) {
			if (aux.get(j).getId().equals(idMateria)) {

				aux.get(j).setPrimerParcial(primerParcial);
				aux.get(j).setSegundoParcial(segundoParcial);
				aux.get(j).setProyecto(proyecto);
				aux.get(j).setExamenFinal(examenFinal);
				respuesta = true;
			}
		}
		return respuesta;
	}

	public void mostrarAlumno(String idEstudiante) {

		for (int i = 0; i < estudiantes.size(); i++) {
			if (estudiantes.get(i).getId().equals(idEstudiante)) {
				Estudiante aux = estudiantes.get(i);
				System.out.println("Id: " + aux.getId() + "\tNombre: " + aux.getNombre() + "\tCarrera: "
						+ aux.getCarrera().getNombre() + "\tGeneracion: " + aux.getGeneracion() + "\t");
			}
		}
	}

	public Double promedioMateriaEstudiante(Estudiante estudiante, String idMateria) {

		Double promedio = null;

		List<MateriaAlumno> aux = estudiante.getMaterias();
		for (int j = 0; j < aux.size(); j++) {
			if (aux.get(j).getId().equals(idMateria)) {
				try {
					promedio = (aux.get(j).getPrimerParcial() + aux.get(j).getSegundoParcial()
							+ aux.get(j).getProyecto() + aux.get(j).getExamenFinal()) / 4;
				} catch (Exception e) {
					promedio = null;
				}

			}

		}

		return promedio;

	}

	public Double promedioEstudiante(Estudiante estudiante, Integer semestre) {
		Double promedioTotal = 0.0;
		int contadorMaterias = 0;

		List<MateriaAlumno> materias = estudiante.getMaterias();
		for (int j = 0; j < materias.size(); j++) {
			if (semestre == materias.get(j).getSemestre()) {
				try {
					MateriaAlumno materia = materias.get(j);
					Double promedioMateria = (materia.getPrimerParcial() + materia.getSegundoParcial()
							+ materia.getProyecto() + materia.getExamenFinal()) / 4.0;
					promedioTotal += promedioMateria;
					contadorMaterias++;
				} catch (Exception e) {
					System.out.println("Faltan datos para realizar la operacion");
					return null;
				}
			}
		}

		if (contadorMaterias > 0) {
			return promedioTotal / contadorMaterias;
		} else {
			return null;
		}
	}

	public Double promedioSemestre(Integer semestre, String carrera) {

		int contador = 0;
		Double promedio = 0.0;
		for (int i = 0; i < estudiantes.size(); i++) {
			if (estudiantes.get(i).getCarrera().getNombre().equals(carrera)) {
				try {
					promedio += promedioEstudiante(estudiantes.get(i), semestre);
					contador++;
				} catch (Exception e) {
					System.out.println("Faltan datos para realizar la operacion");
					return null;
				}

			}
		}

		if (contador > 0) {
			return promedio / contador;
		} else {
			return null;
		}

	}

	public Double promedioMateria(String carrera, String idMateria) {

		int contador = 0;
		Double promedio = 0.0;
		for (int i = 0; i < estudiantes.size(); i++) {
			if (estudiantes.get(i).getCarrera().getNombre().equals(carrera)) {
				try {
					promedio += promedioMateriaEstudiante(estudiantes.get(i), idMateria);
					contador++;
				} catch (Exception e) {
					System.out.println("Faltan datos para realizar la operacion");
					return null;
				}

			}
		}

		if (contador > 0) {
			return promedio / contador;
		} else {
			return null;
		}

	}

	public Double promedioCarrera(String carrera) {

		int contador = 0;
		Double promedioTotal = 0.0;
		for (int i = 1; i <= 8; i++) {
			Double promedioSemestre = promedioSemestre(i, carrera);
			if (promedioSemestre != null) {
				promedioTotal += promedioSemestre;
				contador++;
			}
		}
		if (contador == 8) {
			return promedioTotal / contador;
		} else {
			return null;
		}

	}

	public void imprimirEstudiantes() {
		if (null != estudiantes) {
			for (Estudiante estudiante : estudiantes) {
				System.out.println(estudiante + "\n");
			}
		}
	}

	public void imprimirProfesores() {
		if (null != profesores) {
			for (Profesor profesor : profesores) {
				System.out.println(profesor + "\n");
			}
		}

	}

	public void imprimirMaterias() {
		if (null != materias) {
			for (Materia materia : materias) {
				System.out.println(materia + "\n");
			}
		}

	}

	public void imprimirCarreras() {
		if (null != carreras) {
			for (Carrera carrera : carreras) {
				System.out.println(carrera + "\n");
			}
		}

	}

	public List<Carrera> getCarreras() {
		return carreras;

	}
	
	public List<Profesor> getProfesores() {
		return profesores;

	}
	
	public List<Materia> getMaterias() {
		return materias;

	}
	
	public List<Estudiante> getEstudiantes() {
		return estudiantes;

	}

}

