package br.com.jardelplk.around;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button btnLogin;
	
	@FXML
	private void login() {
		App.changeResizable();//Estava em falso vai pra true
		App.setRoot("main");
	}
	
	@FXML
	private void logoutLogin() {
		App.logoutInit();//Para sair do programa
	}
	
	@FXML
	private void register() {
		Stage stage = new Stage();//Criação da nova janela de registro
		//Abaixo são comandos para carregar o fxml
	    stage.setScene(FXMLUtil.loadScene("register"));
	    stage.setResizable(false);
	    stage.show(); 
	}
}
