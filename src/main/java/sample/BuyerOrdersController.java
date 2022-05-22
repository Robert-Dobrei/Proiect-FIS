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

public class BuyerOrdersController implements Initializable {

    @FXML
    private TableView<BuyerTable> table;

    @FXML
    private TableColumn<BuyerTable, String> id_column;

    @FXML
    private TableColumn<BuyerTable, String> item_column;

    @FXML
    private TableColumn<BuyerTable, String> price_column;

    @FXML
    private TableColumn<BuyerTable, String> sname_column;

    @FXML
    private TableColumn<BuyerTable, String> phone_column;

    ObservableList<BuyerTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection con = null;
        ResultSet rs = null;
        try {
            con = DButils.getConnection();
            rs = con.createStatement().executeQuery("SELECT * FROM orders");

            while (rs.next()) {
                if(rs.getString("buyer_name").equals(DButils.getName()))
                    oblist.add(new BuyerTable(rs.getString("id"), rs.getString("item_name"), rs.getString("price"), rs.getString("seller_name"), rs.getString("sphone_number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        item_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
        sname_column.setCellValueFactory(new PropertyValueFactory<>("sname"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("snumber"));

        table.setItems(oblist);

    }
}
