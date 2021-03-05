package br.com.jardelplk.around;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class MainController {
	
	private static Stage stage;
	
	@FXML
	private void logout() {
		App.changeResizable();
		App.setRoot("login");
	}
	
	@FXML
	private void information() {
		this.stage = new Stage();//Criação da nova janela de registro
		//Abaixo são comandos para carregar o fxml
	    stage.setScene(FXMLUtil.loadScene("userInfo"));
	    stage.setResizable(false);
	    stage.show(); 
	}
	
	@FXML
	private void logoutInfo() {
		this.stage.close();
	}
	
	@FXML
	private void logoutContato() {
		App.changeResizable();
		App.setRoot("main");
	}
	
	@FXML
	private void contato() {
		App.setRoot("contato");
	}
	
	@FXML
	private void email() {
		App.setRoot("email");
	}
	
	@FXML
	private void emailEnviado() {
		App.changeResizable();
		App.setRoot("emailEnviado");
	}
}
