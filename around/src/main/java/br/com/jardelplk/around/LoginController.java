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
	
	private static Stage stage;
	
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
		this.stage = new Stage();//Criação da nova janela de cadastro
		//Abaixo são comandos para carregar o fxml
	    stage.setScene(FXMLUtil.loadScene("cadastro"));
	    stage.setResizable(true);//Tornar reposicionavel
	    stage.show(); 
	}
	
	@FXML
	private void logout() {
		App.changeResizable();
		this.stage.close();
	}
	
	@FXML
	private void tarefaConcluida() {
	    stage.setScene(FXMLUtil.loadScene("tarefaConcluida"));
	    stage.setResizable(false);
	    stage.show(); 
	}
}
