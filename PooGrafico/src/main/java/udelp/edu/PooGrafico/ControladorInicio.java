package udelp.edu.PooGrafico;

import java.io.IOException;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import udelp.edu.PooGrafico.model.Usuario;
import udelp.edu.PooGrafico.process.ControlEscolar;
import udelp.edu.PooGrafico.process.ControlUsuarios;
import udelp.edu.PooGrafico.utility.Constantes;

public class ControladorInicio {

	private ControlUsuarios control = ControlUsuarios.getInstance();
	private ControlEscolar movimientos = ControlEscolar.getInstance();

	@FXML
	private Label errorUsuario;
	@FXML
	private TextField userText;
	@FXML
	private PasswordField passwordText;

	@FXML
	public void initialize() {
		control.cargarUsuarios(Constantes.NOMBRE_ARCHIVO_USUARIOS, new TypeToken<List<Usuario>>(){}.getType());
		movimientos.cargarArchivos();
	}

	@FXML
	private void iniciarSesion() throws IOException {

		if (!userText.getText().isEmpty() && !passwordText.getText().isEmpty()) {

			Usuario aux = control.coincidencias(userText.getText(), passwordText.getText());

			if (null != aux) {

				switch (aux.getRol().getCodigo()) {
				case "admin":
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SIUP_2.0.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle(aux.getNombre());
					stage.show();
					break;
				case "alumnos":
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/EstudianteGrafico.fxml"));
					Parent root1 = loader1.load();
					ControladorCalificaciones controller = loader1.getController();
					controller.initData(movimientos.estudiantePorNombre(aux.getNombre()));
					Scene scene1 = new Scene(root1);
					Stage stage1 = new Stage();
					stage1.setScene(scene1);
					stage1.setTitle(aux.getNombre());
					stage1.show();
					break;
				case "profes":
					FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/ProfeGrafico.fxml"));
					Parent root2 = loader2.load();
					ControladorProfe controlar = loader2.getController();
					controlar.initData(movimientos.profePorNombre(aux.getNombre()));
					Scene scene2 = new Scene(root2);
					Stage stage2 = new Stage();
					stage2.setScene(scene2);
					stage2.setTitle(aux.getNombre());
					stage2.show();
					break;
				}

			} else {
				errorUsuario.setVisible(true);
				userText.setText("");
				passwordText.setText("");
			}

		} else {

			userText.setText("");
			passwordText.setText("");

		}

	}

}
