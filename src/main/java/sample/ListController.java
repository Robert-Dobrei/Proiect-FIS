package sample;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ListController implements Initializable {

    @FXML
    private Label p_name;

    @FXML
    private Label s_name;

    @FXML
    private Label price;

    @FXML
    private Label phone_nr;

    @FXML
    private Button button_add;

    @FXML
    private TableView<ModelTable> table;

    @FXML
    private TableColumn<ModelTable, String> pn_column;

    @FXML
    private TableColumn<ModelTable, String> price_column;

    @FXML
    private TableColumn<ModelTable, String> desc_column;

    @FXML
    private TextField tf_search;

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection con = null;
        ResultSet rs = null;
        try {
            con = DButils.getConnection();
            rs = con.createStatement().executeQuery("SELECT * FROM items");

            while(rs.next()){
                oblist.add(new ModelTable(rs.getString("product_name"), rs.getString("price"), rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pn_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        price_column.setCellValueFactory(new PropertyValueFactory<>("price"));
        desc_column.setCellValueFactory(new PropertyValueFactory<>("desc"));

        table.setItems(oblist);

        FilteredList<ModelTable> filteredTable = new FilteredList<>(oblist, b->true);
        tf_search.textProperty().addListener((observable, oldValue, newValue)-> {
            filteredTable.setPredicate(modeltable -> {
            if(newValue == null || newValue.isEmpty())
                return true;

            String lowerCaseFilter = newValue.toLowerCase();

            if(modeltable.getName().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                return true;
            } else if(modeltable.getPrice().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                return true;
            } else if(modeltable.getDesc().toLowerCase().indexOf(lowerCaseFilter)!=-1){
                return true;
            } else
                return false;

            });
        });

        SortedList<ModelTable> sortedTable = new SortedList<>(filteredTable);
        sortedTable.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedTable);

    }
}