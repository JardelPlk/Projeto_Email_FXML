package br.com.jardelplk.around;

import java.util.ArrayList;
import java.util.List;

import br.com.jardelplk.around.db.EmailDAO;
import br.com.jardelplk.around.db.UserDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class MainController {
	
	private static Stage stage;
	
	private static User user;

	@FXML
	private TilePane tileTrash;

	@FXML
	private ListView<String> userEmailList;

	@FXML
	private Button btnOpen;

	@FXML
	private Button btnRemove;
	
	public static void setUser(User u) {
		user = u;
	}
	
	@FXML
	private void logout() {
		user = null;
		App.changeResizable();
		App.setRoot("login");
	}
	
	@FXML 
	private void updateTrash() {
		tileTrash.getChildren().clear();
		for(Email e : new EmailDAO().getAll())
			if(!user.getEmails().contains(e)) {
				Button btn = new Button(e.getAssunto());
				btn.setOnAction(restaurarEmail());
				tileTrash.getChildren().add(btn);
			}
	}
	 
	
	private EventHandler<ActionEvent> restaurarEmail() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btn = (Button) event.getSource();
				String assuntoEmail = btn.getText();
				user.getEmails().add(new EmailDAO().get(assuntoEmail));
				new UserDAO().persist(user);
				updateTrash();
			}
			
		};
	}

	@FXML
	private void updateEmail() {
		List<String> userEmails = new ArrayList<>();
		for(Email e : user.getEmails())
			userEmails.add(e.getAssunto());
		userEmailList.setItems(FXCollections.observableArrayList(userEmails));
	}
	
	@FXML
	private void remove() {
		String assuntoEmail = userEmailList.getSelectionModel().getSelectedItem();
		Email email = new EmailDAO().get(assuntoEmail);
		user.getEmails().remove(email);
		new UserDAO().persist(user);
		updateEmail();
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
	private void exit() {
		Platform.exit();
	}
}