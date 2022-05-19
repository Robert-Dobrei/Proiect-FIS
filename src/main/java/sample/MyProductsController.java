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


public class MyProductsController implements Initializable {
    @FXML
    private TextField item_name;

    @FXML
    private TextField item_price;

    @FXML
    private TextArea item_desc;

    @FXML
    private Button button_add;

    public void initialize(URL location, ResourceBundle resources){

        button_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!item_name.getText().trim().isEmpty() && !item_price.getText().trim().isEmpty()){
                    DButils.addItem(event, item_name.getText(), item_price.getText(), item_desc.getText());
                    item_name.setText(null);
                    item_price.setText(null);
                    item_desc.setText(null);
                }
                else {
                    System.out.println("Please fill in all information!");
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all the item imformation! ");
                    alert.show();
                }
            }
        });

    }}
