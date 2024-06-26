package udelp.edu.PooGrafico.process;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import udelp.edu.PooGrafico.model.Carrera;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.Materia;
import udelp.edu.PooGrafico.model.MateriaAlumno;
import udelp.edu.PooGrafico.model.Profesor;
import udelp.edu.PooGrafico.utility.Constantes;
import udelp.edu.PooGrafico.utility.Validaciones;

public class ControlEscolar {

	private List<Carrera> carreras;
	private List<Materia> materias;
	private List<Profesor> profesores;
	private List<Estudiante> estudiantes;
	Archivos archivo = new Archivos();
	ControlUsuarios control = ControlUsuarios.getInstance();
	Validaciones valida = new Validaciones();
	private static ControlEscolar instancia;

	private ControlEscolar() {
		carreras = new ArrayList<Carrera>();
		materias = new ArrayList<Materia>();
		profesores = new ArrayList<Profesor>();
		estudiantes = new ArrayList<Estudiante>();

	}

	public static ControlEscolar getInstance() {
		if (null == instancia) {
			instancia = new ControlEscolar();
		}
		return instancia;
	}

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
		List<MateriaAlumno> auxList = new ArrayList<>();
		Carrera aux = estudiante.getCarrera();
		for (Materia materia : materias) {
			if (materia.getCarrera().equals(aux)) {
				MateriaAlumno aux2 = new MateriaAlumno(materia);
				aux2.setId(materia.getId());
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

	public <T> void cargarArchivo(String archivo, Type tipoDeLista, List<T> lista) {
		if (this.archivo.existeArchivo(archivo)) {
			System.out.println("El archivo existe");
			List<T> datos = this.archivo.leerArchivo(archivo, tipoDeLista);
			if (datos != null) {
				lista.clear(); // Vaciar la lista original
				lista.addAll(datos); // Agregar todos los elementos de la lista cargada
			}
		}
	}

	public void cargarArchivos() {
		cargarArchivo(Constantes.NOMBRE_ARCHIVO_CARRERAS, new TypeToken<List<Carrera>>() {
		}.getType(), carreras);
		cargarArchivo(Constantes.NOMBRE_ARCHIVO_ALUMNOS, new TypeToken<List<Estudiante>>() {
		}.getType(), estudiantes);
		cargarArchivo(Constantes.NOMBRE_ARCHIVO_MATERIAS, new TypeToken<List<Materia>>() {
		}.getType(), materias);
		cargarArchivo(Constantes.NOMBRE_ARCHIVO_PROFESORES, new TypeToken<List<Profesor>>() {
		}.getType(), profesores);
	}

	public void guardarDatos() {
		archivo.guardaArchivo(Constantes.NOMBRE_ARCHIVO_CARRERAS, carreras);
		archivo.guardaArchivo(Constantes.NOMBRE_ARCHIVO_ALUMNOS, estudiantes);
		archivo.guardaArchivo(Constantes.NOMBRE_ARCHIVO_MATERIAS, materias);
		archivo.guardaArchivo(Constantes.NOMBRE_ARCHIVO_PROFESORES, profesores);
		archivo.guardaArchivo(Constantes.NOMBRE_ARCHIVO_USUARIOS, control.getUsuarios());
	}

	public Estudiante estudiantePorNombre(String nombre) {

		if (estudiantes != null) {
			for (int i = 0; i < estudiantes.size(); i++) {
				if (estudiantes.get(i).getNombre().equals(nombre)) {

					return estudiantes.get(i);
				}
			}
		}
		return null;
	}

	public List<Estudiante> estudiantesDeMateria(Materia materia, boolean director) {
		List<Estudiante> estudiantesMateria = new ArrayList<Estudiante>();
		if (false == director) {
			for (Estudiante estudiante : estudiantes) {
				if (null != estudiante.getMaterias()) {
					for (MateriaAlumno auxMateria : estudiante.getMaterias()) {
						if (auxMateria.getId().equals(materia.getId())
								&& estudiante.getSemestre() == auxMateria.getSemestre()) {
							estudiantesMateria.add(estudiante);
						}
					}
				}
			}
		} else {
			for (Estudiante estudiante : estudiantes) {
				if (null != estudiante.getMaterias()) {
					for (MateriaAlumno auxMateria : estudiante.getMaterias()) {
						if (auxMateria.getId().equals(materia.getId())
								&& estudiante.getSemestre() >= auxMateria.getSemestre()) {
							estudiantesMateria.add(estudiante);
						}
					}
				}
			}
		}
		return estudiantesMateria;
	}

	public Profesor profePorNombre(String nombre) {

		if (profesores != null) {
			for (int i = 0; i < profesores.size(); i++) {
				if (profesores.get(i).getNombre().equals(nombre)) {

					return profesores.get(i);
				}
			}
		}
		return null;
	}

	public List<Materia> materiasDisponibles() {
		List<Materia> aux = new ArrayList<Materia>();
		if (materias != null) {
			for (int i = 0; i < materias.size(); i++) {
				if (!materias.get(i).isAsignada()) {

					aux.add(materias.get(i));
				}
			}
		}
		return aux;
	}

	public List<Carrera> carrerasDisponibles() {
		List<Carrera> aux = new ArrayList<Carrera>();
		if (carreras != null) {
			for (int i = 0; i < carreras.size(); i++) {
				if (false == carreras.get(i).isConDirecCarrera()) {

					aux.add(carreras.get(i));
				}
			}
		}
		return aux;
	}

	public String cuenta() {
		int aux = estudiantes.size() + profesores.size();
		return String.valueOf(aux);
	}

	public void revalidar(Estudiante estudiante) {
		for (int i = 0; i < estudiante.getMaterias().size(); i++) {
			if (estudiante.getSemestre() > materias.get(i).getSemestre()) {
				estudiante.getMaterias().get(i).setPrimerParcial(8D);
				estudiante.getMaterias().get(i).setSegundoParcial(8D);
				estudiante.getMaterias().get(i).setProyecto(8D);
				estudiante.getMaterias().get(i).setExamenFinal(8D);
			}
		}
	}

	public void actualizarDirecMateria(Carrera carrera) {

		for (int i = 0; i < materias.size(); i++) {

			if (materias.get(i).getCarrera().equals(carrera)) {

				materias.get(i).getCarrera().setConDirecCarrera(true);

			}
		}
	}

	public List<Materia> materiasCarrera(Carrera carrera) {
		List<Materia> auxList = new ArrayList<>();
		for (Materia materia : materias) {
			if (materia.getCarrera().equals(carrera)) {
				auxList.add(materia);
			}
		}

		return auxList;
	}

	public List<Materia> materiasCarreraSemestre(Carrera carrera, Integer semestre) {
		List<Materia> auxList = new ArrayList<>();
		for (Materia materia : materias) {
			if (materia.getCarrera().equals(carrera) && materia.getSemestre().equals(semestre)) {
				auxList.add(materia);
			}
		}

		return auxList;
	}

	public List<Estudiante> estudiantesDeCarrera(Carrera carrera) {
		List<Estudiante> auxList = new ArrayList<>();
		for (Estudiante estudiante : estudiantes) {
			if (estudiante.getCarrera().equals(carrera)) {
				auxList.add(estudiante);
			}
		}

		return auxList;
	}

	public List<Materia> materiasCarreraSemestreProfe(Carrera carrera, Integer semestre, Profesor profe) {
		List<Materia> auxList = new ArrayList<>();
		for (Materia materia : profe.getMaterias()) {
			if (materia.getCarrera().equals(carrera) && materia.getSemestre().equals(semestre)) {
				auxList.add(materia);
			}
		}

		return auxList;
	}

	public List<Materia> materiasCarreraProfe(Carrera carrera, Profesor profe) {
		List<Materia> auxList = new ArrayList<>();
		for (Materia materia : profe.getMaterias()) {
			if (materia.getCarrera().equals(carrera)) {
				auxList.add(materia);
			}
		}

		return auxList;
	}

	public List<Materia> materiasSemestreProfe(Integer semestre, Profesor profe) {
		List<Materia> auxList = new ArrayList<>();
		for (Materia materia : profe.getMaterias()) {
			if (materia.getSemestre().equals(semestre)) {
				auxList.add(materia);
			}
		}

		return auxList;
	}

	public String mostrarCalificacionesMateria(Estudiante estudiante, Materia materia) {

		String calificaciones = "";
		List<MateriaAlumno> aux = estudiante.getMaterias();
		for (int j = 0; j < aux.size(); j++) {
			if (aux.get(j).getId().equals(materia.getId())) {
				calificaciones = estudiante.getNombre() + " calificaciones:\nPrimer parcial: "
						+ valida.verificarCalificacion(aux.get(j).getPrimerParcial()) + "\nSegundo parcial: "
						+ valida.verificarCalificacion(aux.get(j).getSegundoParcial()) + "\nProyecto: "
						+ valida.verificarCalificacion(aux.get(j).getProyecto()) + "\nExamen final: "
						+ valida.verificarCalificacion(aux.get(j).getExamenFinal());

			}

		}
		return calificaciones;

	}

	public String mostrarCalificacionesMaterias(Materia materia) {

		String calificaciones = materia.getNombre() + " calificaciones:";

		List<Estudiante> estudiantesAux = estudiantesDeMateria(materia, false);
		for (Estudiante estudiante : estudiantesAux) {

			calificaciones += "\n" + mostrarCalificacionesMateria(estudiante, materia);

		}

		return calificaciones;

	}

	public String mostrarCalificacionesSemestre(Integer semestre, Profesor profe) {

		String calificaciones = "";

		List<Materia> materiasAux = materiasSemestreProfe(semestre, profe);

		if (!materiasAux.isEmpty()) {

			for (Materia materia : materiasAux) {

				calificaciones += "\n" + mostrarCalificacionesMaterias(materia);

			}

		} else {

			calificaciones = "No hay coincidencias";

		}

		return calificaciones;

	}

	public String mostrarCalificacionesCarreraSemestre(Carrera carrera, Integer semestre, Profesor profe) {

		String calificaciones = "";

		List<Materia> materiasAux = materiasCarreraSemestreProfe(carrera, semestre, profe);

		if (!materiasAux.isEmpty()) {

			for (Materia materia : materiasAux) {

				calificaciones += "\n" + mostrarCalificacionesMaterias(materia);

			}

		} else {

			calificaciones = "No hay coincidencias";

		}

		return calificaciones;

	}

	public String mostrarCalificacionesCarrera(Carrera carrera, Profesor profe) {

		String calificaciones = "";

		List<Materia> materiasAux = materiasCarreraProfe(carrera, profe);

		if (!materiasAux.isEmpty()) {

			for (Materia materia : materiasAux) {

				calificaciones += "\n" + mostrarCalificacionesMaterias(materia);

			}

		} else {

			calificaciones = "No hay coincidencias";

		}

		return calificaciones;

	}

	public String promedioEstudianteSemestre(Estudiante estudiante, Integer semestre) {

		String respuesta = "";
		List<MateriaAlumno> materias = estudiante.getMaterias();
		for (int j = 0; j < materias.size(); j++) {
			if (semestre == materias.get(j).getSemestre()) {

				respuesta += mostrarCalificacionesMateria(estudiante, materias.get(j));

			}
		}

		return respuesta;

	}

	public List<MateriaAlumno> materiasSemestreEstudiante (Estudiante estudiante, Integer semestre){
		
		List<MateriaAlumno> materiasAux=new ArrayList<MateriaAlumno>() ;
		
		for (MateriaAlumno materia: estudiante.getMaterias()) {
			
			if (semestre == materia.getSemestre()) {
				
				materiasAux.add(materia);
				
			}
		}
		
		return materiasAux;
		
	}
	
	public List<String> obtenerSemestres(int semestreAlumno) {
		
        List<String> semestres = new ArrayList<>();
        semestreAlumno=(semestreAlumno==-1)?8:semestreAlumno;

            for (int i = 1; i <= semestreAlumno; i++) {
                semestres.add(String.valueOf(i));
            }
            

        return semestres;
    }

}
