package br.com.jardelplk.around.controllers;

import br.com.jardelplk.around.AlertUtil;
import br.com.jardelplk.around.App;
import br.com.jardelplk.around.FXMLUtil;
import br.com.jardelplk.around.db.UserDAO;
import br.com.jardelplk.around.entities.Email;
import br.com.jardelplk.around.entities.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button btnLogin;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPassword;
	
	
	@FXML
	private void login() {
		String email = txtEmail.getText();
		String password = txtPassword.getText();

		if (email.isBlank()) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Digite o e-mail!", null);
			alert.showAndWait();
			return;
		}
		if (password.isBlank()) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Digite a senha!", null);
			alert.showAndWait();
			return;
		}
		User user = new UserDAO().get(email);
		if (user == null) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "E-mail ou senha inválido(s)!", null);
			alert.showAndWait();
			return;
		}
		if (!user.getPassword().contentEquals(password)) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "E-mail ou senha inválido(s)!", null);
			alert.showAndWait();
			return;
		}
		
		new UserDAO().persist(user);
		App.changeResizable();
		App.setRoot("main");
		MainController controller = FXMLUtil.getMainController();
		controller.updateUserInfo(user);
	}

	@FXML
	private void register() {
		Stage stage = new Stage();
		stage.setScene(FXMLUtil.loadScene("register"));
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	private void exit() {
		Platform.exit();
	}
}
