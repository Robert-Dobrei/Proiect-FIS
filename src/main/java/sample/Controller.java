package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static sample.DButils.changeScene;
import static sample.DButils.logInUser;

public class Controller implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Button button_signup;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.logInUser(event, tf_username.getText(), tf_password.getText());
                DButils.changeScene(event, "/logged-in.fxml", "Welcome!", null, null);
            }
        });
        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DButils.changeScene(event, "/sign-up.fxml", "Signup", null, null);
            }
        });
    }
}
