package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardSeller implements Initializable {

    @FXML
    private Button my_products;
    @FXML
    private Button my_orders;

    @FXML
    private Button logout_seller;

    @FXML
    private BorderPane bp_seller;

    private void loadPage(String page) throws IOException {
        Parent root = null;
        try {
            root = FXMLLoader.load(DashboardSeller.class.getResource(page));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp_seller.setCenter(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       my_orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadPage("/my_orders.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        logout_seller.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event, "/main.fxml", "Login", null, null);
            }
        });

    }
}


