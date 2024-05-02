package udelp.edu.PooGrafico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
	private Label mostrarPromedio;
	Validaciones valida=new Validaciones();
	private Estudiante estudiante;
	private ControlEscolar movimientos= ControlEscolar.getInstance();
	
	public void initData(Estudiante estudiante) {
		this.estudiante = estudiante;
		ObservableList<String> semestres = FXCollections.observableArrayList();
		semestres.addAll("1", "2", "3", "4", "5", "6");
		semestrePromedio.setItems(semestres);
		ObservableList<MateriaAlumno> materias = FXCollections.observableArrayList();
		materias.addAll(estudiante.getMaterias());
		materiaPromedio.setItems(materias);
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
