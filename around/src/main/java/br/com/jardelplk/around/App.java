package br.com.jardelplk.around;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
    	stage.setScene(FXMLUtil.loadScene("login"));
    	this.changeResizable();
    	stage.show();
    }

    static void setRoot(String fxml) {
        stage.setScene(FXMLUtil.loadScene(fxml));
        //Quando seta o root sai da tela de login para essa
        //Troca a cena e não cria outra cena
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void changeResizable() {
    	//Se for redimensionavel coloca false 
    	//Se não coloca true
    	if(stage.isResizable())
    		stage.setResizable(false);
    	else
    		stage.setResizable(true);
    }
    
    public static void logoutInit() {
    	stage.close();
    }

}