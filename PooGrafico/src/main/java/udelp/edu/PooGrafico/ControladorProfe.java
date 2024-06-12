package udelp.edu.PooGrafico;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import udelp.edu.PooGrafico.model.Carrera;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.Materia;
import udelp.edu.PooGrafico.model.Profesor;
import udelp.edu.PooGrafico.model.Rol;
import udelp.edu.PooGrafico.model.Usuario;
import udelp.edu.PooGrafico.process.ControlEscolar;
import udelp.edu.PooGrafico.process.ControlUsuarios;
import udelp.edu.PooGrafico.utility.Validaciones;

public class ControladorProfe {

	// Recursos calificar
	@FXML
	private ChoiceBox<Materia> elegirMaterias;
	@FXML
	private ChoiceBox<Estudiante> elegirAlumno;
	@FXML
	private TextField primerParcial;
	@FXML
	private TextField segundoParcial;
	@FXML
	private TextField examen;
	@FXML
	private TextField proyecto;
	@FXML
	private Label sinDatos;
	@FXML
	private Label datosIncorrectos;
	@FXML
	private Tab altaMateria;
	@FXML
	private Tab altaAlumn;
	@FXML
	private Tab cerrarSem;

	// Recursos ingresar materia
	@FXML
	private TextField txtMateria;
	@FXML
	private ChoiceBox<String> elegirSemestre;
	@FXML
	private TextField txtHorario;
	@FXML
	private Label sinDatosMateria;
	@FXML
	private Label falloHorario;
	@FXML
	private Label exitoMateria;

	// Recursos ingresar estudiante
	@FXML
	private DatePicker fechaNacimientoEstudiante;
	@FXML
	private TextField txtNombreEstudiante;
	@FXML
	private ChoiceBox<String> generoEstudiante;
	@FXML
	private Label sinDatosEstudiante;
	@FXML
	private Label exitoEstudiante;
	@FXML
	private Label malUsuarioAlumno;
	@FXML
	private TextField usuarioAlumno;
	@FXML
	private PasswordField contraAlumno;
	@FXML
	private ChoiceBox<String> elegirSemestreAlumn;

	// Recursos cerrar semestre
	@FXML
	private ChoiceBox<Estudiante> cerrarAlumnos;
	@FXML
	private TextArea verEstado;

	// Recursos reporte profe
	@FXML
	private ChoiceBox<Estudiante> alumnoRepProf;
	@FXML
	private ChoiceBox<Materia> materiaRepProf;
	@FXML
	private ChoiceBox<String> semestreRepProf;
	@FXML
	private ChoiceBox<Carrera> carreraRepProf;
	@FXML
	private TextArea reporte;

	// Recursos asignar materia
	@FXML
	private ChoiceBox<Materia> elegirMateriasAsign;
	@FXML
	private ChoiceBox<Profesor> elegirProfe;
	@FXML
	private Label noDatosAsign;
	@FXML
	private Label exitoAsign;

	private boolean director;

	private Profesor profe;

	Validaciones valida = new Validaciones();

	ControlEscolar movimientos = ControlEscolar.getInstance();

	private ControlUsuarios users = ControlUsuarios.getInstance();

	public void initData(Profesor profe) {

		this.profe = profe;
		ObservableList<String> semestres = FXCollections.observableArrayList();
		semestres.addAll("1", "2", "3", "4", "5", "6", "7", "8");
		elegirSemestre.setItems(semestres);
		elegirSemestreAlumn.setItems(semestres);
		semestreRepProf.setItems(semestres);

		carreraRepProf.setOnAction(e -> actualizarMaterias());
		semestreRepProf.setOnAction(e -> actualizarMaterias());
		materiaRepProf.setOnAction(e -> actualizarAlumnos());

		ObservableList<Carrera> carreras = FXCollections.observableArrayList();
		carreras.addAll(movimientos.getCarreras());
		carreraRepProf.setItems(carreras);

		ObservableList<Materia> materias = FXCollections.observableArrayList();

		if (null != profe.getEsDirector()) {
			altaAlumn.setDisable(false);
			altaMateria.setDisable(false);
			cerrarSem.setDisable(false);
			this.director = true;
			ObservableList<String> genero = FXCollections.observableArrayList();
			genero.addAll("M", "F");
			generoEstudiante.setItems(genero);
			materias.addAll(movimientos.materiasCarrera(profe.getEsDirector()));
			ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
			estudiantes.addAll(movimientos.estudiantesDeCarrera(profe.getEsDirector()));
			cerrarAlumnos.setItems(estudiantes);
			elegirMateriasAsign.setItems(materias);
			ObservableList<Profesor> profesores = FXCollections.observableArrayList();
			profesores.addAll(movimientos.getProfesores());
			elegirProfe.setItems(profesores);
		} else {
			materias.addAll(profe.getMaterias());
		}

		elegirMaterias.setItems(materias);

	}
	
	@FXML
	private void actualizarMateriasYAlumnos() {
		
		ObservableList<Materia> materias = FXCollections.observableArrayList();
		
		if(null!=profe.getEsDirector()) {
			
			ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
			estudiantes.addAll(movimientos.estudiantesDeCarrera(profe.getEsDirector()));
			cerrarAlumnos.setItems(estudiantes);
			materias.addAll(movimientos.materiasCarrera(profe.getEsDirector()));
			elegirMateriasAsign.setItems(materias);

		}else {
			
			materias.addAll(profe.getMaterias());
			
		}
		
		elegirMaterias.setItems(materias);
		
	}

	@FXML
	private void guardarCalificacion(ActionEvent event) {

		if (null != elegirAlumno.getValue() && null != elegirMaterias.getValue() && !primerParcial.getText().isEmpty()
				&& !segundoParcial.getText().isEmpty() && !examen.getText().isEmpty()
				&& !proyecto.getText().isEmpty()) {
			Double primer = valida.esDecimal(primerParcial.getText());
			Double segundo = valida.esDecimal(segundoParcial.getText());
			Double ex = valida.esDecimal(examen.getText());
			Double pr = valida.esDecimal(proyecto.getText());
			if (primer >= 0 && segundo >= 0 && ex >= 0 && pr >= 0) {
				movimientos.calificarMateria(elegirAlumno.getValue(), elegirMaterias.getValue().getId(), primer,
						segundo, pr, ex);
			} else {
				datosIncorrectos.setVisible(true);
			}
		} else {
			sinDatos.setVisible(true);
		}

	}

	@FXML
	private void actualizar() {
		List<Estudiante> estudiantes = movimientos.estudiantesDeMateria(elegirMaterias.getValue(), director);
		if (estudiantes != null) {
			ObservableList<Estudiante> alumnos = FXCollections.observableArrayList(estudiantes);
			elegirAlumno.setItems(alumnos);
		} else {
			elegirAlumno.setItems(FXCollections.emptyObservableList());
		}
	}

	@FXML
	private void guardarArchivo() {

		movimientos.guardarDatos();

	}

	@FXML
	private void btnMateriaClicked(ActionEvent event) {
		if (!txtMateria.getText().isEmpty() && !txtHorario.getText().isEmpty()
				&& !elegirSemestre.getValue().isEmpty()) {
			if (null != valida.validarHorario(txtHorario.getText())) {
				int semestre = Integer.parseInt(elegirSemestre.getValue());
				Carrera carrera = profe.getEsDirector();
				Materia materia = new Materia(txtMateria.getText(), semestre, carrera, txtHorario.getText());
				movimientos.altaMateria(materia);
				exitoMateria.setVisible(true);
				actualizarMateriasYAlumnos();
			} else {
				falloHorario.setVisible(true);
			}
		} else {
			sinDatosMateria.setVisible(true);
		}
	}

	@FXML
	private void limpiarMateria(ActionEvent event) {
		sinDatosMateria.setVisible(false);
		falloHorario.setVisible(false);
		exitoMateria.setVisible(false);
		txtMateria.clear();
		txtHorario.clear();
		elegirSemestre.getSelectionModel().clearSelection();
	}

	@FXML
	private void btnEstudianteClicked(ActionEvent event) throws ParseException {
		if (!txtNombreEstudiante.getText().isEmpty() && !generoEstudiante.getValue().isEmpty()
				&& null != fechaNacimientoEstudiante.getValue() && !usuarioAlumno.getText().isEmpty()
				&& !contraAlumno.getText().isEmpty() && null != elegirSemestreAlumn.getValue()) {
			if (valida.validaContraseña(contraAlumno.getText()) && valida.validaUsuario(usuarioAlumno.getText())
					&& valida.validaTiene17(fechaNacimientoEstudiante.getValue())) {
				String nombre = txtNombreEstudiante.getText();
				Instant instant = fechaNacimientoEstudiante.getValue().atStartOfDay().atZone(ZoneId.systemDefault())
						.toInstant();
				Date nacimiento = Date.from(instant);
				Character sexo = generoEstudiante.getValue().equalsIgnoreCase("m") ? 'M' : 'F';
				Estudiante estudiante = new Estudiante(nombre, nacimiento, sexo, movimientos.cuenta());
				movimientos.altaEstudiante(estudiante);
				exitoEstudiante.setVisible(true);
				Rol rol = new Rol(nombre, "alumnos");
				Usuario usuario = new Usuario(nombre, usuarioAlumno.getText(), contraAlumno.getText(), rol);
				users.alta(usuario);
				estudiante.setCarrera(profe.getEsDirector());
				movimientos.materiasAlumno(estudiante);
				estudiante.setSemestre(Integer.parseInt(elegirSemestreAlumn.getValue()));
				if (estudiante.getSemestre() > 1) {
					movimientos.revalidar(estudiante);
				}
				actualizarMateriasYAlumnos();
			} else {
				sinDatosEstudiante.setVisible(true);
			}
		} else {
			sinDatosEstudiante.setVisible(true);
		}
	}

	@FXML
	private void cerrarSemestre(ActionEvent event) {

		if (null != cerrarAlumnos.getValue()) {
			Double promedio = 0D;
			int semestre = cerrarAlumnos.getValue().getSemestre();
			if (semestre == -1) {

				verEstado.setText("El alumno ya esta titulado");

			} else if (semestre == 8) {

				Double aux;
				boolean paso = false;
				for (int i = 1; i <= 8; i++) {
					aux = movimientos.promedioEstudiante(cerrarAlumnos.getValue(), i);
					if (null != aux && aux > 6) {
						promedio += aux;
						paso = true;
					} else {
						verEstado.setText("El semestre " + i + "no se ha calificado o esta reprobado");
						paso = false;
						break;
					}
				}
				if (paso) {
					verEstado.setText("El alumno se ha titulado");
					cerrarAlumnos.getValue().setSemestre(-1);
				}

			} else if (semestre < 8) {

				promedio = movimientos.promedioEstudiante(cerrarAlumnos.getValue(), semestre);
				if (null != promedio && promedio >= 6) {
					cerrarAlumnos.getValue().setSemestre(semestre++);
					verEstado.setText(
							"El alumno " + cerrarAlumnos.getValue().getNombre() + " paso al semestre: " + semestre);
				} else {
					verEstado.setText("Faltan calificaciones para cerrar el semestre o reprobo el semestre");
				}

			}

		} else {

			verEstado.setText("Error: No se ha elegido ningun alumno");

		}
	}

	@FXML
	private void actualizarMaterias() {

		materiaRepProf.getItems().clear();
		alumnoRepProf.getItems().clear();

		Carrera carrera = carreraRepProf.getValue();
		ObservableList<Materia> materias = FXCollections.observableArrayList();

		if (null != semestreRepProf.getValue() && null != carrera) {

			if (null != profe.getEsDirector() && carrera.equals(profe.getEsDirector())) {

				materias.addAll(
						movimientos.materiasCarreraSemestre(carrera, Integer.parseInt(semestreRepProf.getValue())));
				materiaRepProf.setItems(materias);

			} else {

				materias.addAll(movimientos.materiasCarreraSemestreProfe(carrera,
						Integer.parseInt(semestreRepProf.getValue()), profe));
				materiaRepProf.setItems(materias);

			}

		} else if (null != carrera && null == semestreRepProf.getValue()) {

			if (null != profe.getEsDirector() && carrera.equals(profe.getEsDirector())) {

				materias.addAll(movimientos.materiasCarrera(carrera));
				materiaRepProf.setItems(materias);

			} else {

				materias.addAll(movimientos.materiasCarreraProfe(carrera, profe));
				materiaRepProf.setItems(materias);

			}

		} else if (null != semestreRepProf.getValue() && null == carrera) {

			materias.addAll(movimientos.materiasSemestreProfe(Integer.parseInt(semestreRepProf.getValue()), profe));
			materiaRepProf.setItems(materias);

		}

	}

	@FXML
	private void actualizarAlumnos() {

		alumnoRepProf.getItems().clear();

		if (null != materiaRepProf.getValue() && null != carreraRepProf.getValue()) {
			ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
			estudiantes.addAll(movimientos.estudiantesDeMateria(materiaRepProf.getValue(), director));
			alumnoRepProf.setItems(estudiantes);
		}

	}

	@FXML
	private void buscarRepProfe(ActionEvent event) {

		Estudiante estudiante = alumnoRepProf.getValue();
		Materia materia = materiaRepProf.getValue();
		Carrera carrera = carreraRepProf.getValue();
		String semestre = semestreRepProf.getValue();
		String respuesta = "";

		if (null != estudiante && null != carrera && null != semestre && null != materia) {

			respuesta = movimientos.mostrarCalificacionesMateria(estudiante, materia);
			reporte.setText(respuesta);

		} else if (null == estudiante && null != carrera && null != semestre && null != materia) {

			respuesta = movimientos.mostrarCalificacionesMaterias(materia);
			reporte.setText(respuesta);

		} else if (null == estudiante && null != carrera && null != semestre && null == materia) {

			respuesta = movimientos.mostrarCalificacionesCarreraSemestre(carrera, Integer.parseInt(semestre), profe);
			reporte.setText(respuesta);

		} else if (null == estudiante && null == carrera && null != semestre && null == materia) {

			respuesta = movimientos.mostrarCalificacionesSemestre(Integer.parseInt(semestre), profe);
			reporte.setText(respuesta);

		} else if (null != estudiante && null == carrera && null != semestre && null != materia) {

			respuesta = movimientos.mostrarCalificacionesMateria(estudiante, materia);
			reporte.setText(respuesta);
			
		} else if (null == estudiante && null == carrera && null != semestre && null != materia) {

			respuesta = movimientos.mostrarCalificacionesMaterias(materia);
			reporte.setText(respuesta);

		} else if (null == estudiante && null != carrera && null == semestre && null == materia) {

			respuesta = movimientos.mostrarCalificacionesCarrera(carrera, profe);
			reporte.setText(respuesta);

		} else if (null == estudiante && null == carrera && null == semestre && null == materia) {

			reporte.setText("No se ha seleccionado ningun filtro");

		}

	}

	@FXML
	private void limpiarRepProfe(ActionEvent event) {
		alumnoRepProf.setValue(null);
		materiaRepProf.setValue(null);
		semestreRepProf.setValue(null);
		carreraRepProf.setValue(null);
		reporte.setText("");
		materiaRepProf.getItems().clear();
		alumnoRepProf.getItems().clear();
	}

	@FXML
	private void asignarProf(ActionEvent event) {
		if (null != elegirMateriasAsign.getValue()) {
			elegirProfe.getValue().addMateria(elegirMateriasAsign.getValue());
			elegirMateriasAsign.getValue().setAsignada(true);
			exitoAsign.setVisible(true);
		} else {
			noDatosAsign.setVisible(true);
		}
	}

}
