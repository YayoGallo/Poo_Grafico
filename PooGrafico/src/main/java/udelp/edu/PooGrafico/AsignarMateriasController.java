package udelp.edu.PooGrafico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import udelp.edu.PooGrafico.model.Materia;
import udelp.edu.PooGrafico.model.Profesor;
import udelp.edu.PooGrafico.process.ControlEscolar;

public class AsignarMateriasController {
	

	private Profesor profesor;
	@FXML
    private ChoiceBox<Materia> elegirMaterias;
	@FXML
    private Label  noMateria;

    private ObservableList<Materia> materiasDisponibles;

    public void initData(Profesor profesor, ControlEscolar movimientos) {
        this.profesor = profesor;
        materiasDisponibles = FXCollections.observableArrayList(); // Inicializar materiasDisponibles
        materiasDisponibles.addAll(movimientos.getMaterias());
        elegirMaterias.setItems(materiasDisponibles);
    }

	
	@FXML
    private void guardar(ActionEvent event) {
        if(null!=elegirMaterias.getValue()) {
        	profesor.addMateria(elegirMaterias.getValue());
        }else {
        	noMateria.setVisible(true);
        }
    }
	
	@FXML
    private void volver(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
