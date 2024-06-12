package udelp.edu.PooGrafico;
/*
Put header here


 */

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import udelp.edu.PooGrafico.model.Carrera;
import udelp.edu.PooGrafico.model.Estudiante;
import udelp.edu.PooGrafico.model.Materia;
import udelp.edu.PooGrafico.model.Profesor;
import udelp.edu.PooGrafico.model.Rol;
import udelp.edu.PooGrafico.model.Usuario;
import udelp.edu.PooGrafico.process.ControlEscolar;
import udelp.edu.PooGrafico.process.ControlUsuarios;
import udelp.edu.PooGrafico.utility.Validaciones;

public class FXMLController implements Initializable {

	private ControlEscolar movimientos = ControlEscolar.getInstance();
	Validaciones valida = new Validaciones();
	private ControlUsuarios users = ControlUsuarios.getInstance();

	// Recursos ingresar carrera
	@FXML
	private Label errorCarrera;
	@FXML
	private Label exitoCarrera;
	@FXML
	private TextField txtCarrera;
	@FXML
	private Button btnCarrera;
	// Recursos ingresar materia
	@FXML
	private ChoiceBox<String> elegirCarrera;
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
	// Recursos ingresar profesor
	@FXML
	private ChoiceBox<String> generoProfe;
	@FXML
	private TextField txtNombreProfe;
	@FXML
	private DatePicker fechaNacimientoProfe;
	@FXML
	private DatePicker fechaIngresoProfe;
	@FXML
	private Label sinDatosProfe;
	@FXML
	private Label malUsuarioProfe;
	@FXML
	private Label exitoProfe;
	@FXML
	private TextField usuarioProfe;
	@FXML
	private PasswordField contraProfe;
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

	// Recursos lista profesores
	@FXML
	private TableView<Profesor> tablaProfe;
	@FXML
	private TableColumn<Profesor, String> colNombreProfe;
	@FXML
	private TableColumn<Profesor, String> colIDProfe;
	// Recursos lista estudiantes
	@FXML
	private TableView<Estudiante> tablaEstudiantes;
	@FXML
	private TableColumn<Estudiante, String> colNombreEstudiantes;
	@FXML
	private TableColumn<Estudiante, String> colIDEstudiantes;
	@FXML
	private TableColumn<Estudiante, String> colCarrera;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		ObservableList<String> semestres = FXCollections.observableArrayList();
		semestres.addAll("1", "2", "3", "4", "5", "6", "7", "8");
		elegirSemestre.setItems(semestres);
		ObservableList<String> genero = FXCollections.observableArrayList();
		genero.addAll("M", "F");
		generoProfe.setItems(genero);
		generoEstudiante.setItems(genero);
		cargarArchivo();

	}

	@FXML
	private void btnCarreraClicked(ActionEvent event) {
		if (!txtCarrera.getText().isEmpty() && null == movimientos.existeCarrera(txtCarrera.getText())) {
			Carrera carrera = new Carrera();
			carrera.setNombre(txtCarrera.getText());
			movimientos.altaCarrera(carrera);
			exitoCarrera.setVisible(true);
			actualizarCarrera();
			txtCarrera.setText("");
		} else {
			errorCarrera.setVisible(true);
		}
	}

	private void actualizarCarrera() {

		if (null != movimientos.getCarreras()) {
			ObservableList<String> carreras = FXCollections.observableArrayList();
			for (Carrera carrera : movimientos.getCarreras()) {
				carreras.add(carrera.getNombre());
			}
			elegirCarrera.setItems(carreras);
		}

	}

	@FXML
	private void btnMateriaClicked(ActionEvent event) {
		if (!txtMateria.getText().isEmpty() && !txtHorario.getText().isEmpty() && !elegirCarrera.getValue().isEmpty()
				&& !elegirSemestre.getValue().isEmpty()) {
			if (null != valida.validarHorario(txtHorario.getText())) {
				int semestre = Integer.parseInt(elegirSemestre.getValue());
				Carrera carrera = movimientos.existeCarrera(elegirCarrera.getValue());
				Materia materia = new Materia(txtMateria.getText(), semestre, carrera, txtHorario.getText());
				movimientos.altaMateria(materia);
				exitoMateria.setVisible(true);
			} else {
				falloHorario.setVisible(true);
			}
		} else {
			sinDatosMateria.setVisible(true);
		}
	}

	@FXML
	private void btnProfeClicked(ActionEvent event) throws ParseException {
		if (!txtNombreProfe.getText().isEmpty() && !generoProfe.getValue().isEmpty()
				&& null != fechaNacimientoProfe.getValue() && null != fechaIngresoProfe.getValue()
				&& !usuarioProfe.getText().isEmpty() && !contraProfe.getText().isEmpty()) {
			if (valida.validaContraseña(contraProfe.getText()) && valida.validaUsuario(usuarioProfe.getText()) && valida.validaTiene30(fechaNacimientoProfe.getValue())) {
				String nombre = txtNombreProfe.getText();
				Instant instant = fechaNacimientoProfe.getValue().atStartOfDay().atZone(ZoneId.systemDefault())
						.toInstant();
				Date nacimiento = Date.from(instant);
				instant = fechaIngresoProfe.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
				Date ingreso = Date.from(instant);
				Character sexo = generoProfe.getValue().equalsIgnoreCase("m") ? 'M' : 'F';
				Profesor profe = new Profesor(nombre, nacimiento, sexo, ingreso, movimientos.cuenta());
				movimientos.altaProfesor(profe);
				exitoProfe.setVisible(true);
				Rol rol = new Rol(nombre, "profes");
				Usuario usuario = new Usuario(nombre, usuarioProfe.getText(), contraProfe.getText(), rol);
				users.alta(usuario);
			} else {
				malUsuarioProfe.setVisible(true);
			}
		} else {
			sinDatosProfe.setVisible(!sinDatosProfe.visibleProperty().get());
		}
	}

	@FXML
	private void btnEstudianteClicked(ActionEvent event) throws ParseException {
		if (!txtNombreEstudiante.getText().isEmpty() && !generoEstudiante.getValue().isEmpty()
				&& null != fechaNacimientoEstudiante.getValue() && !usuarioAlumno.getText().isEmpty() && !contraAlumno.getText().isEmpty()) {
			if (valida.validaContraseña(contraAlumno.getText()) && valida.validaUsuario(usuarioAlumno.getText()) && valida.validaTiene17(fechaNacimientoEstudiante.getValue())) {
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
			}else {
				malUsuarioAlumno.setVisible(true);
			}
		} else {
			sinDatosEstudiante.setVisible(!sinDatosEstudiante.visibleProperty().get());
		}
	}

	@FXML
	private void actualizarTablaProfe(ActionEvent event) {
		if (null != movimientos.getProfesores()) {
			ObservableList<Profesor> profesores = FXCollections.observableArrayList();
			profesores.setAll(movimientos.getProfesores());
			tablaProfe.setItems(profesores);
			colNombreProfe.setCellValueFactory(new PropertyValueFactory<Profesor, String>("nombre"));
			colIDProfe.setCellValueFactory(new PropertyValueFactory<Profesor, String>("id"));
			tablaProfe.refresh();
		}
	}

	@FXML
	private void handleSeleccionarProfesor(MouseEvent event) throws IOException {
		Profesor profesorSeleccionado = tablaProfe.getSelectionModel().getSelectedItem();
		if (profesorSeleccionado != null) {
			
			tablaProfe.setDisable(true);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AsignarMaterias.fxml"));
			Parent root = loader.load();
			AsignarMateriasController controller = loader.getController();
			controller.initData(profesorSeleccionado);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Asignar Materias a " + profesorSeleccionado.getNombre());
			stage.show();
			
			stage.setOnHidden(e -> tablaProfe.setDisable(false));
		}
	}

	@FXML
	private void actualizarTablaEstudiante(ActionEvent event) {
		if (null != movimientos.getEstudiantes()) {
			ObservableList<Estudiante> estudiantes = FXCollections.observableArrayList();
			estudiantes.setAll(movimientos.getEstudiantes());
			tablaEstudiantes.setItems(estudiantes);
			colNombreEstudiantes.setCellValueFactory(new PropertyValueFactory<Estudiante, String>("nombre"));
			colIDEstudiantes.setCellValueFactory(new PropertyValueFactory<Estudiante, String>("id"));
			colCarrera.setCellValueFactory(cellData -> {
				String carreraStr = (cellData.getValue().getCarrera() != null)
						? cellData.getValue().getCarrera().getNombre()
						: "No asignado";
				return new SimpleStringProperty(carreraStr);
			});
			tablaEstudiantes.refresh();
		}
	}

	@FXML
	private void seleccionarEstudiante(MouseEvent event) throws IOException {
		Estudiante estudianteSeleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
		if (estudianteSeleccionado != null) {
			
			tablaEstudiantes.setDisable(true);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ControlEstudiantes.fxml"));
			Parent root = loader.load();
			ControladorEstudiante controller = loader.getController();
			controller.initData(estudianteSeleccionado);
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Perfil educativo: " + estudianteSeleccionado.getNombre());
			stage.show();
			
			stage.setOnHidden(e -> tablaEstudiantes.setDisable(false));
			
		}
	}


	@FXML
	private void guardarArchivo() {

		movimientos.guardarDatos();

	}

	@FXML
	private void cargarArchivo() {

		movimientos.cargarArchivos();
		actualizarCarrera();

	}

}
