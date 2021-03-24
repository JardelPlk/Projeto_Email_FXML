package br.com.jardelplk.around.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.jardelplk.around.AlertUtil;
import br.com.jardelplk.around.App;
import br.com.jardelplk.around.db.AplicativoDAO;
import br.com.jardelplk.around.db.EmailDAO;
import br.com.jardelplk.around.db.UserDAO;
import br.com.jardelplk.around.entities.Aplicativo;
import br.com.jardelplk.around.entities.Email;
import br.com.jardelplk.around.entities.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;

public class MainController {

	private User user;

	@FXML
	private TilePane tileApps;

	@FXML
	private ListView<String> userAppsList;

	@FXML
	private Button btnAcessar;

	@FXML
	private Button btnRemove;

	@FXML
	private Label lblAppDescription;

	@FXML
	private Label lblUserInfo;
	
	@FXML
	private TextArea txtMensagem;
	
	@FXML
	private TextArea txtAssunto;
	
	@FXML
	private TextArea txtDestinatario;
	
	@FXML
	private Button btnEnviar;
	
	@FXML
	private ListView<String> userEmailsList;
	
	@FXML
	private Label lblEmailDescription;
	
	@FXML
	private void updateEmails() {
		if (user == null)
			return;
		List<String> userEmails = new ArrayList<>();
		for (Email e : user.getEmails())
			userEmails.add(e.getAssunto() +"\n"+ e.getDestinatario() +"\n"+ e.getMensagem());
		userEmailsList.setItems(FXCollections.observableArrayList(userEmails));
	}
	
	public void enviarEmail() {
		String assunto = txtAssunto.getText();
		String destinatario = txtDestinatario.getText();
		String mensagem = txtMensagem.getText();
		
		Email email = new Email(assunto, destinatario, mensagem);
		new EmailDAO().persist(email);
		user.getEmails().add(email);
		new UserDAO().persist(user);
		
		AlertUtil.info("Sucesso", "Sucesso", "Email enviado com sucesso").show();
		
		updateEmails();
	}

	public void updateUserInfo(User u) {
		this.user = u;
		updateApps();
		lblUserInfo.setText("Olá " + user.getUsername());
	}

	@FXML
	private void updateDescription() {
		String nomeApp = userAppsList.getSelectionModel().getSelectedItem();
		Aplicativo app = new AplicativoDAO().get(nomeApp);
		lblAppDescription.setText(app.getDescricao());
	}

	@FXML
	private void logout() {
		user = null;
		App.changeResizable();
		App.setRoot("login");
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	@FXML
	private void updateAppsStore() {
		tileApps.getChildren().clear();
		for (Aplicativo g : new AplicativoDAO().getAll())
			if (!user.getAplicativos().contains(g)) {
				Button btn = new Button(g.getNome());
				btn.setOnAction(adquirirApp());
				tileApps.getChildren().add(btn);
			}
	}

	@FXML
	private void updateApps() {
		if (user == null)
			return;
		List<String> userApps = new ArrayList<>();
		for (Aplicativo g : user.getAplicativos())
			userApps.add(g.getNome());
		userAppsList.setItems(FXCollections.observableArrayList(userApps));

		if (user.getAplicativos().isEmpty()) {
			lblAppDescription.setText("Você ainda não possui nenhum aplicativo, adquira agora mesmo na nossa loja!");
			btnRemove.setDisable(true);
			btnAcessar.setDisable(true);
		} else {
			userAppsList.getSelectionModel().select(0);
			btnRemove.setDisable(false);
			btnAcessar.setDisable(false);
			updateDescription();
		}
	}

	@FXML
	private void remove() {
		String appName = userAppsList.getSelectionModel().getSelectedItem();
		Aplicativo app = new AplicativoDAO().get(appName);
		user.getAplicativos().remove(app);
		new UserDAO().persist(user);
		updateApps();
	}

	private EventHandler<ActionEvent> adquirirApp() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btn = (Button) event.getSource();
				String appName = btn.getText();
				user.getAplicativos().add(new AplicativoDAO().get(appName));
				new UserDAO().persist(user);
				updateAppsStore();
			}
		};
	}

}
