package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;





public class CartController implements Initializable {

    @FXML
    private Label total;

    @FXML
    private Button order;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

    }

    public void setItem(ModelTable shop) {

        this.total.setText(" Produsul: "+shop.getName()+ "\n Descrierea produsului: " + shop.getDesc() + "\nPretul:  "
                + shop.getPrice() + "\nNumar de telefon: " + shop.getPhone());

    }

    Parent root;
}