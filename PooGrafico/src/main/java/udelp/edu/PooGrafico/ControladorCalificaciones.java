package udelp.edu.PooGrafico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.MateriaAlumno;
import udelp.edu.PooGrafico.process.ControlEscolar;
import udelp.edu.PooGrafico.utility.Validaciones;

public class ControladorCalificaciones {
	@FXML
	private ChoiceBox<MateriaAlumno> materiaPromedio;
	@FXML
	private ChoiceBox<String> semestrePromedio;
	@FXML
	private TextArea reporte;
	
	Validaciones valida=new Validaciones();
	private Estudiante estudiante;
	private ControlEscolar movimientos= ControlEscolar.getInstance();
	
	public void initData(Estudiante estudiante) {
		this.estudiante = estudiante;
		ObservableList<String> semestres = FXCollections.observableArrayList();
		semestres.addAll(movimientos.obtenerSemestres(estudiante.getSemestre()));
		semestrePromedio.setItems(semestres);
		semestrePromedio.setOnAction(e -> actualizarMaterias());
	}
	
	@FXML
	private void promedio(ActionEvent event) {
		String respuesta;
		if(null==materiaPromedio.getValue()&&null!=semestrePromedio.getValue()) {
			
			respuesta=movimientos.promedioEstudianteSemestre(estudiante, Integer.parseInt(semestrePromedio.getValue()));
			reporte.setText(respuesta);
			
		}else if(null!=materiaPromedio.getValue()&&null!=semestrePromedio.getValue()) {
			
			respuesta=movimientos.mostrarCalificacionesMateria(estudiante, materiaPromedio.getValue());
			reporte.setText(respuesta);
			
		}
		
	}
	
	@FXML
	private void actualizarMaterias () {
		
		materiaPromedio.getItems().clear();
		
		if(null!=semestrePromedio.getValue()) {
			
			ObservableList<MateriaAlumno> materias = FXCollections.observableArrayList();
			materias.addAll(movimientos.materiasSemestreEstudiante(estudiante, Integer.parseInt(semestrePromedio.getValue())));
			materiaPromedio.setItems(materias);
			
		}
		
	}
	
	@FXML
	private void limpiar(ActionEvent event) {
		
		materiaPromedio.getItems().clear();
		materiaPromedio.setValue(null);;
		semestrePromedio.setValue(null);;
		reporte.setText("");;
		
	}
	
}
