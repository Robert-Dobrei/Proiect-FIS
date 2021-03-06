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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    private Label total;

    @FXML
    private Button order;

    private String name, price, sname, snumber;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        order.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event, "/order-placed.fxml","Order placed!", null,null);
                try {
                    Connection con = DButils.getConnection();
                    PreparedStatement preparedStatement = con.prepareStatement("insert into orders (item_name, price, buyer_name, bphone_number, seller_name, sphone_number) values (?, ?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, price);
                    preparedStatement.setString(3, DButils.getName());
                    preparedStatement.setString(4, DButils.getPhone_nr());
                    preparedStatement.setString(5, sname);
                    preparedStatement.setString(6, snumber);
                    preparedStatement.executeUpdate();

                    DButils.Delete(name);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void setItem(ModelTable shop) {

        this.total.setText(" Item: "+shop.getName()+ "\n Item description: " + shop.getDesc() + "\n Price:  "
                + shop.getPrice() + " lei\n Seller's name: "+shop.getSname()+"\n Seller's phone number: " + shop.getPhone());

        this.name = shop.getName();
        this.price = shop.getPrice();
        this.sname = shop.getSname();
        this.snumber = shop.getPhone();
    }

    Parent root;
}