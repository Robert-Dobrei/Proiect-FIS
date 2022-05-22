package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MyProductsController implements Initializable {

    @FXML
    private Button delete;

    @FXML
    private TableView<MyTable> table;

    @FXML
    private TableColumn<MyTable, String> item_column;

    @FXML
    private TableColumn<MyTable, String> price_column;

    @FXML
    private TableColumn<MyTable, String> desc_column;

    ObservableList<MyTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection con = null;
        ResultSet rs = null;
        try {
            con = DButils.getConnection();
            rs = con.createStatement().executeQuery("SELECT * FROM items");

            while (rs.next()) {
                if(rs.getString("seller_name").equals(DButils.getName()))
                oblist.add(new MyTable(rs.getString("product_name"), rs.getString("price"), rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        item_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
        desc_column.setCellValueFactory(new PropertyValueFactory<>("desc"));

        table.setItems(oblist);
        table.setEditable(true);

        item_column.setCellFactory(TextFieldTableCell.forTableColumn());
        price_column.setCellFactory(TextFieldTableCell.forTableColumn());
        desc_column.setCellFactory(TextFieldTableCell.forTableColumn());

        item_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MyTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MyTable, String> event) {
                try {
                    Connection con = DButils.getConnection();
                    PreparedStatement preparedStatement = null;
                    preparedStatement = con.prepareStatement("update items set product_name = ? where product_name = ?");
                    preparedStatement.setString(1, event.getNewValue());
                    preparedStatement.setString(2, event.getOldValue());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        price_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MyTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MyTable, String> event) {
                try {
                    Connection con = DButils.getConnection();
                    PreparedStatement preparedStatement = null;
                    preparedStatement = con.prepareStatement("update items set price = ? where price = ?");
                    preparedStatement.setString(1, event.getNewValue());
                    preparedStatement.setString(2, event.getOldValue());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        desc_column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MyTable, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<MyTable, String> event) {
                try {
                    Connection con = DButils.getConnection();
                    PreparedStatement preparedStatement = null;
                    preparedStatement = con.prepareStatement("update items set description = ? where description = ?");
                    preparedStatement.setString(1, event.getNewValue());
                    preparedStatement.setString(2, event.getOldValue());
                    preparedStatement.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MyTable model;
                model = table.getSelectionModel().getSelectedItem();
                try {
                    DButils.Delete(model.getName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                oblist.removeAll(oblist);
                Connection con = null;
                ResultSet rs = null;
                try {
                    con = DButils.getConnection();
                    rs = con.createStatement().executeQuery("SELECT * FROM items");

                    while (rs.next()) {
                        if(rs.getString("seller_name").equals(DButils.getName()))
                            oblist.add(new MyTable(rs.getString("product_name"), rs.getString("price"), rs.getString("description")));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                item_column.setCellValueFactory(new PropertyValueFactory<>("name"));
                price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
                desc_column.setCellValueFactory(new PropertyValueFactory<>("desc"));

                table.setItems(oblist);
            }
        });

    }
}
