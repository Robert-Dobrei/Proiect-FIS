package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ResourceBundle;
import java.net.URL;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_logout;
    @FXML
    private Label loggedin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event, "/main.fxml", "Log in!", null, null);
            }
        });
    }

    public void setUserInformation(String username, String role) {
        loggedin.setText("Logged in successfully!");
    }
}
