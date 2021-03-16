package br.com.jardelplk.around;

import java.io.IOException;

import br.com.jardelplk.around.db.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button btnLogin;
	
	private static Stage stage;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private void login() {
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
		
		if(u == null) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Usuário e/ou senha inválido(s)!", null);
			alert.showAndWait();
			return;
		}
		
		if(!u.getPassword().contentEquals(password)) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Usuário e/ou senha inválido(s)!", null);
			alert.showAndWait();
			return;
		}
		
		MainController.setUser(u);
		App.changeResizable();//Estava em falso vai pra true
		App.setRoot("main2");
	}
	
	@FXML
	private void logoutLogin() {
		App.logoutInit();//Para sair do programa
	}
	
	@FXML
	private void register() {
		Stage stage = new Stage();//Criação da nova janela de cadastro
		//Abaixo são comandos para carregar o fxml
	    stage.setScene(FXMLUtil.loadScene("register"));
	    stage.setResizable(true);//Tornar reposicionavel
	    stage.show(); 
	}
	
	@FXML
	private void logout() {
		App.changeResizable();
		this.stage.close();
	}
	
	
	@FXML
	private void exit() {
		Platform.exit();
	}
}
