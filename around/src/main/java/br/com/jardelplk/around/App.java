package br.com.jardelplk.around;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.jardelplk.around.db.UserDAO;
import br.com.jardelplk.around.db.UtilDB;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
    	
    	this.stage = stage;
    	stage.setScene(FXMLUtil.loadScene("login"));
    	stage.setTitle("Around");
    	this.changeResizable();
    	stage.show();
    }

    public static void setRoot(String fxml) {
        stage.setScene(FXMLUtil.loadScene(fxml));
        //Quando seta o root sai da tela de login para essa
        //Troca a cena e não cria outra cena
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