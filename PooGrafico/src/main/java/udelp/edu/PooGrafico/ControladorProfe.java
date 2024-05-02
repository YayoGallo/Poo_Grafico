package udelp.edu.PooGrafico;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.Materia;
import udelp.edu.PooGrafico.model.Profesor;
import udelp.edu.PooGrafico.process.ControlEscolar;
import udelp.edu.PooGrafico.utility.Validaciones;

public class ControladorProfe {
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
	
	Validaciones valida=new Validaciones();
	
	ControlEscolar movimientos= ControlEscolar.getInstance();	
	
	
	
	public void initData(Profesor profe) {
		ObservableList<Materia> materias = FXCollections.observableArrayList();
		materias.addAll(profe.getMaterias());
		elegirMaterias.setItems(materias);
		
	}
	
	
	@FXML
	private void guardarCalificacion(ActionEvent event) {

		if (null!=elegirAlumno.getValue()&&null!=elegirMaterias.getValue()&&!primerParcial.getText().isEmpty() && !segundoParcial.getText().isEmpty() && !examen.getText().isEmpty()
				&& !proyecto.getText().isEmpty()) {
			Double primer= valida.esDecimal(primerParcial.getText());
			Double segundo= valida.esDecimal(segundoParcial.getText());
			Double ex= valida.esDecimal(examen.getText());
			Double pr= valida.esDecimal(proyecto.getText());
			movimientos.calificarMateria(elegirAlumno.getValue(), elegirMaterias.getValue().getId(), primer, segundo, pr, ex);
		} else {
			sinDatos.setVisible(true);
		}

	}
	
	@FXML
	private void actualizar() {
	    List<Estudiante> estudiantes = movimientos.estudiantesDeMateria(elegirMaterias.getValue());
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
	
}
