package br.com.jardelplk.around;

import br.com.jardelplk.around.db.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

	private static Stage stage;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	public void registerCadastro() {
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		
		if(email.isBlank()) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Digite o email!", null);
			alert.showAndWait();
			return;
		}
		
		if(password.isBlank()) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Digite a senha!", null);
			alert.showAndWait();
			return;
		}
		
		User u = new UserDAO().get(email);
		
		if(u != null) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Email já em uso!", null);
			alert.showAndWait();
			return;
		}
		
		new UserDAO().persist(new User(email, password));
	
		AlertUtil.info("Sucesso!!", "Sucesso!", "Cadastro realizado com sucesso!").show();
		close();
	}
	
	@FXML
	private void close() {
		Stage stage = (Stage) txtEmail.getScene().getWindow();
		stage.close();
	}
}
