package br.com.jardelplk.around;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class FXMLUtil {

	public static Scene loadScene(String fxml) {
		//O try catch serve para tratar excessões, porque como abaixo estamos criando auma janela
		//pode acontecer uma infinidade de erros, e para não utilizar vários ifs, usamos o try catch
		//que trata as mais variadas excessões, como se o arquivo estiver com o nome errado, se ele 
		//não existir, e assim vai...
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			return scene;
		} catch (IOException eIO) {
			Alert alert = AlertUtil.error("Erro", "Erro ao carregar um componente", "Erro ao tentar carregar a janela "+ fxml, eIO);
			alert.showAndWait();
			return null;
		} catch(IllegalStateException eIllegalState) {
			Alert alert = AlertUtil.error("Erro", "Erro - Arquivo inexistente", "Erro ao tentar carregar a janela "+ fxml, eIllegalState);
			alert.showAndWait();
			return null;
		}
	}
	
}
