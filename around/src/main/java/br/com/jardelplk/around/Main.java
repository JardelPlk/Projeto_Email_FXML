package br.com.jardelplk.around;

import br.com.jardelplk.around.db.UtilDB;
import javafx.application.Application;

public class Main {

	public static void main(String[] args) {
		UtilDB.initDB();
		Application.launch(App.class);
		UtilDB.closeConn();
	}
}
