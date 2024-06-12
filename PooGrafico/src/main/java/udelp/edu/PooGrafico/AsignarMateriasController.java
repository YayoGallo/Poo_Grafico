package udelp.edu.PooGrafico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import udelp.edu.PooGrafico.model.Carrera;
import udelp.edu.PooGrafico.model.Materia;
import udelp.edu.PooGrafico.model.Profesor;
import udelp.edu.PooGrafico.process.ControlEscolar;

public class AsignarMateriasController {
	

	private Profesor profesor;
	@FXML
    private ChoiceBox<Materia> elegirMaterias;
	@FXML
    private Label  noMateria;
	@FXML
    private Label  error;
	@FXML
	private Label txtSelecMat;
	@FXML
	private Label txtSelecCarrera;
	@FXML
    private ChoiceBox<Carrera> elegirCarrera;
	@FXML
	private Button asignarCarrera;
	@FXML
	private Button guardarMateria;
	@FXML
	private Button pasarDirector;
	@FXML
	private Button pasarMateria;
	private ObservableList<Carrera> carrerasDisponibles;
    private ObservableList<Materia> materiasDisponibles; 
    ControlEscolar movimientos= ControlEscolar.getInstance();

    public void initData(Profesor profesor) {
        this.profesor = profesor;
        materiasDisponibles = FXCollections.observableArrayList(); // Inicializar materiasDisponibles
        materiasDisponibles.addAll(movimientos.materiasDisponibles());
        elegirMaterias.setItems(materiasDisponibles);
    }

	
	@FXML
    private void guardar(ActionEvent event) {
        if(null!=elegirMaterias.getValue()) {
        	profesor.addMateria(elegirMaterias.getValue());
        	elegirMaterias.getValue().setAsignada(true);;
        }else {
        	noMateria.setVisible(true);
        }
    }
	
	@FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
	
	@FXML
    private void cambiarAsignarDirec(ActionEvent event) {
        txtSelecCarrera.setVisible(true);
        txtSelecMat.setVisible(false);
        elegirMaterias.setVisible(false);
        elegirCarrera.setVisible(true);
        pasarDirector.setVisible(false);
        pasarMateria.setVisible(true);
        asignarCarrera.setVisible(true);
        guardarMateria.setVisible(false);
        carrerasDisponibles = FXCollections.observableArrayList(); 
        carrerasDisponibles.addAll(movimientos.carrerasDisponibles());
        elegirCarrera.setItems(carrerasDisponibles);
        
    }
	
	@FXML
    private void asignar(ActionEvent event) {
        if(null!=elegirCarrera.getValue()&& null==profesor.getEsDirector()) {
        	profesor.setEsDirector(elegirCarrera.getValue());
        	movimientos.actualizarDirecMateria(elegirCarrera.getValue());
        	elegirCarrera.getValue().setConDirecCarrera(true);
        }else {
        	error.setVisible(true);
        }
    }
	
	@FXML
    private void cambiarAsignarMate(ActionEvent event) {
        txtSelecCarrera.setVisible(false);
        txtSelecMat.setVisible(true);
        elegirMaterias.setVisible(true);
        elegirCarrera.setVisible(false);
        pasarDirector.setVisible(true);
        pasarMateria.setVisible(false);
        asignarCarrera.setVisible(false);
        guardarMateria.setVisible(true);
        materiasDisponibles = FXCollections.observableArrayList();
        materiasDisponibles.addAll(movimientos.materiasDisponibles());
        elegirMaterias.setItems(materiasDisponibles);
        
    }
	
}
