package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MyProductsController implements Initializable {

    @FXML
    public TableView<MyTable> table;

    @FXML
    public TableColumn<MyTable, String> item_column;

    @FXML
    public TableColumn<MyTable, String> price_column;

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
