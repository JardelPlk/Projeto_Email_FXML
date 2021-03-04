module br.com.jardelplk.around {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.jardelplk.around to javafx.fxml;
    exports br.com.jardelplk.around;
}