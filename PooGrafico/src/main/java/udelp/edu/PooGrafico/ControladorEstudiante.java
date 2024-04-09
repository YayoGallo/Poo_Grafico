package udelp.edu.PooGrafico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import udelp.edu.PooGrafico.model.Carrera;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.MateriaAlumno;
import udelp.edu.PooGrafico.process.ControlEscolar;
import udelp.edu.PooGrafico.utility.Validaciones;

public class ControladorEstudiante {
	
	
	Validaciones valida=new Validaciones();
	private Estudiante estudiante;
	private ControlEscolar movimientos;
	@FXML
	private ChoiceBox<Carrera> elegirCarrera;
	@FXML
	private Label noCarrera;
	@FXML
	private Label sinDatos;
	@FXML
	private ChoiceBox<MateriaAlumno> elegirMaterias;
	@FXML
	private TextField primerParcial;
	@FXML
	private TextField segundoParcial;
	@FXML
	private TextField examen;
	@FXML
	private TextField proyecto;
	@FXML
	private ChoiceBox<MateriaAlumno> materiaPromedio;
	@FXML
	private ChoiceBox<String> semestrePromedio;
	@FXML
	private Label mostrarPromedio;
	
	ObservableList<MateriaAlumno> materiasCalificadas = FXCollections.observableArrayList();

	private ObservableList<Carrera> carrerasDisponibles;

	public void initData(Estudiante estudiante, ControlEscolar movimientos) {
		this.estudiante = estudiante;
		this.movimientos = movimientos;
		carrerasDisponibles = FXCollections.observableArrayList(); // Inicializar materiasDisponibles
		carrerasDisponibles.addAll(movimientos.getCarreras());
		elegirCarrera.setItems(carrerasDisponibles);
		ObservableList<String> semestres = FXCollections.observableArrayList();
		semestres.addAll("1", "2", "3", "4", "5", "6");
		semestrePromedio.setItems(semestres);
	}

	@FXML
	private void guardarCarrera(ActionEvent event) {

		if (null != elegirCarrera.getValue()) {
			estudiante.setCarrera(elegirCarrera.getValue());
		} else {
			noCarrera.setVisible(true);
		}

	}

	@FXML
	private void actualizarMaterias(ActionEvent event) {

		if (null != estudiante.getCarrera()) {

			movimientos.materiasAlumno(estudiante);
			ObservableList<MateriaAlumno> materias = FXCollections.observableArrayList();
			materias.addAll(estudiante.getMaterias());
			elegirMaterias.setItems(materias);
			
			
		}

	}

	@FXML
	private void guardarCalificacion(ActionEvent event) {

		if (null!=elegirMaterias.getValue()&&!primerParcial.getText().isEmpty() && !segundoParcial.getText().isEmpty() && !examen.getText().isEmpty()
				&& !proyecto.getText().isEmpty()) {
			Double primer= valida.esDecimal(primerParcial.getText());
			Double segundo= valida.esDecimal(segundoParcial.getText());
			Double ex= valida.esDecimal(examen.getText());
			Double pr= valida.esDecimal(proyecto.getText());
			movimientos.calificarMateria(estudiante, elegirMaterias.getValue().getId(), primer, segundo, pr, ex);
			materiasCalificadas.add(elegirMaterias.getValue());
			materiaPromedio.setItems(materiasCalificadas);
		} else {
			sinDatos.setVisible(true);
		}

	}
	
	@FXML
	private void promedio(ActionEvent event) {
		String respuesta;
		if(null==materiaPromedio.getValue()&&null!=semestrePromedio.getValue()) {
			int semestre=valida.esInt(semestrePromedio.getValue());
			Double promedioSemestre=movimientos.promedioEstudiante(estudiante, semestre);
			respuesta=promedioSemestre==null?"Faltan datos para promediar":String.valueOf(promedioSemestre);
			mostrarPromedio.setText(respuesta);
		}else if(null!=materiaPromedio.getValue()&&null!=semestrePromedio.getValue()) {
			Double promedioMateria=movimientos.promedioMateriaEstudiante(estudiante, materiaPromedio.getValue().getId());
			respuesta=promedioMateria==null?"Faltan datos para promediar":String.valueOf(promedioMateria);
			mostrarPromedio.setText(respuesta);
		}
		
	}
	
	
	
}
