package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BdashController implements Initializable {

    @FXML
    private Button button_list;

    @FXML
    private Button past_orders;

    @FXML
    private Button button_logout;

    @FXML
    private BorderPane bp;

    private void loadPage(String page) throws IOException {
        Parent root = null;
        try {
            root = FXMLLoader.load(BdashController.class.getResource(page));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_list.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadPage("/product-list.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        past_orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadPage("/buyer-orders.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event, "/main.fxml", "Login", null, null);
            }
        });

    }
}
