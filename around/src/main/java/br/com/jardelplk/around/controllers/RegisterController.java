package br.com.jardelplk.around.controllers;

import br.com.jardelplk.around.AlertUtil;
import br.com.jardelplk.around.db.UserDAO;
import br.com.jardelplk.around.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private void close() {
		Stage stage = (Stage) txtEmail.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void register() {
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
		User u = new UserDAO().get(email);
		if (u != null) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "E-mail já em uso!", null);
			alert.showAndWait();
			return;
		}
		new UserDAO().persist(new User(email, password));

		AlertUtil.info("Sucesso", "Sucesso", "Cadastro realizado com sucesso").show();
		close();
	}

}
