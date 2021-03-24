package br.com.jardelplk.around;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stge) {
		stage = stge;
		stage.setScene(FXMLUtil.loadScene("login"));
		changeResizable();
		stage.setTitle("Around");
		stage.show();
	}

	public static void setRoot(String fxml) {
		stage.setScene(FXMLUtil.loadScene(fxml));
	}

	public static void changeResizable() {
		if (stage.isResizable())
			stage.setResizable(false);
		else
			stage.setResizable(true);
	}

}