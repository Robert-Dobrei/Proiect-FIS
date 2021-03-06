package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.DButils;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {
    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField phone_nr;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private RadioButton buyer;

    @FXML
    private RadioButton seller;

    @FXML
    private Button finish;

    @FXML
    private Button ret;


    public void initialize(URL location, ResourceBundle resources){
        ToggleGroup togglegroup=new ToggleGroup();
        buyer.setToggleGroup(togglegroup);
        seller.setToggleGroup(togglegroup);

        buyer.setSelected(true);

        finish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) togglegroup.getSelectedToggle()).getText();

                if(!username.getText().trim().isEmpty() && !password.getText().trim().isEmpty() && !firstname.getText().trim().isEmpty() && !lastname.getText().trim().isEmpty() && !phone_nr.getText().trim().isEmpty()){
                        DButils.signUpUser(event, username.getText(), password.getText(), toggleName, firstname.getText()+" "+lastname.getText(), phone_nr.getText());
                        DButils.changeScene(event,"/main.fxml","Login",null, null);
                    }
                 else {
                    System.out.println("Please fill in all information!");
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up! ");
                    alert.show();
                }
            }
        });

        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event, "/main.fxml", "Log in", null, null);
            }
        });

    }}
