package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ListController implements Initializable {

    @FXML
    private Label p_name;

    @FXML
    private Label s_name;

    @FXML
    private Label price;

    @FXML
    private Label phone_nr;

    @FXML
    private Button button_add;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        p_name.setText("Verde");

    }
}
