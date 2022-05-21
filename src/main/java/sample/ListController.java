package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ListController implements Initializable {

    @FXML
    private Button select;

    @FXML
    private TableView<ModelTable> table;

    @FXML
    private TableColumn<ModelTable, String> pn_column;

    @FXML
    private TableColumn<ModelTable, String> price_column;

    @FXML
    private TableColumn<ModelTable, String> desc_column;
    @FXML
    private TableColumn<ModelTable, String> phone_column;
    @FXML
    private TableColumn<ModelTable, String> sname_column;

    @FXML
    private TextField tf_search;
    Parent root;
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection con = null;
        ResultSet rs = null;
        try {
            con = DButils.getConnection();
            rs = con.createStatement().executeQuery("SELECT * FROM items");

            while (rs.next()) {
                oblist.add(new ModelTable(rs.getString("product_name"), rs.getString("price"),
                        rs.getString("description"), rs.getString("seller_name"), rs.getString("phone_number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pn_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
        desc_column.setCellValueFactory(new PropertyValueFactory<>("desc"));
        sname_column.setCellValueFactory((new PropertyValueFactory<>("sname")));
        phone_column.setCellValueFactory((new PropertyValueFactory<>("phone")));

        table.setItems(oblist);

        FilteredList<ModelTable> filteredTable = new FilteredList<>(oblist, b -> true);
        tf_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTable.setPredicate(modeltable -> {
                if (newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();

                if (modeltable.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (modeltable.getPrice().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (modeltable.getDesc().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if(modeltable.getSname().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if(modeltable.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else
                    return false;
            });
        });

        SortedList<ModelTable> sortedTable = new SortedList<>(filteredTable);
        sortedTable.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedTable);

        select.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ModelTable shop;
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(DButils.class.getResource("/shopping-cart.fxml"));
                try{
                    root=loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                shop=table.getSelectionModel().getSelectedItem();
                CartController cartController=loader.getController();
                cartController.setItem(shop);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            }
        });

    }
    public static void Update(){
     //   table.refresh();
    }


}