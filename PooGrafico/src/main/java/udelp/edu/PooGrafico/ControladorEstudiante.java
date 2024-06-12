package udelp.edu.PooGrafico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import udelp.edu.PooGrafico.model.Carrera;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.process.ControlEscolar;
import udelp.edu.PooGrafico.utility.Validaciones;

public class ControladorEstudiante {
	
	
	Validaciones valida=new Validaciones();
	private Estudiante estudiante;
	@FXML
	private ChoiceBox<Carrera> elegirCarrera;
	
	@FXML
	private ChoiceBox<String> elegirSemestre;
	@FXML
	private Label noCarrera;
	private ObservableList<Carrera> carrerasDisponibles;
	ControlEscolar movimientos= ControlEscolar.getInstance();	

	public void initData(Estudiante estudiante) {
		ObservableList<String> semestres = FXCollections.observableArrayList();
		semestres.addAll("1", "2", "3", "4", "5", "6", "7", "8");
		elegirSemestre.setItems(semestres);
		this.estudiante = estudiante;
		carrerasDisponibles = FXCollections.observableArrayList(); 
		carrerasDisponibles.addAll(movimientos.getCarreras());
		elegirCarrera.setItems(carrerasDisponibles);
	}

	@FXML
	private void guardarCarrera(ActionEvent event) {

		if (null != elegirCarrera.getValue()&& null!=elegirSemestre.getValue()) {
			estudiante.setCarrera(elegirCarrera.getValue());
			movimientos.materiasAlumno(estudiante);
			estudiante.setSemestre(Integer.parseInt(elegirSemestre.getValue()));
			if(estudiante.getSemestre()>1) {
				movimientos.revalidar(estudiante);
			}
		} else {
			noCarrera.setVisible(true);
		}

	}

	
}
