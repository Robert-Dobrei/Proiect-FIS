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

   public class SellerOrdersController implements Initializable {
        @FXML
        private TableView<SellerTable> table;
        @FXML
        private TableColumn<SellerTable, String> order_id;
        @FXML
        private TableColumn<SellerTable, String> ordered_item;
        @FXML
        private TableColumn<SellerTable, String> buyer_name;
        @FXML
        private TableColumn<SellerTable, String> buyer_phone;
        ObservableList<SellerTable> oblist = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            Connection con = null;
            ResultSet rs = null;
            try {
                con = DButils.getConnection();
                rs = con.createStatement().executeQuery("SELECT * FROM orders");

                while (rs.next()) {
                    if(rs.getString("seller_name").equals(DButils.getName()))
                        oblist.add(new SellerTable(rs.getString("id"), rs.getString("item_name"), rs.getString("buyer_name"), rs.getString("bphone_number")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            order_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            ordered_item.setCellValueFactory(new PropertyValueFactory<>("name"));
            buyer_name.setCellValueFactory(new PropertyValueFactory<>("bname"));
            buyer_phone.setCellValueFactory(new PropertyValueFactory<>("bnumber"));

            table.setItems(oblist);

        }
    }


