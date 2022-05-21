package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyProductsController implements Initializable {

    @FXML
    public TableView<MyTable> table;

    @FXML
    public TableColumn<MyTable, String> item_column;

    @FXML
    public TableColumn<MyTable, String> price_column;

    ObservableList<MyTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection con = null;
        ResultSet rs = null;
        try {
            con = DButils.getConnection();
            rs = con.createStatement().executeQuery("SELECT * FROM items");

            while (rs.next()) {
                if(rs.getString("seller_name").equals(DButils.getS_name()))
                oblist.add(new MyTable(rs.getString("product_name"), rs.getString("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        item_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_column.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.setItems(oblist);

    }
}
