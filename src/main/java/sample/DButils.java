package sample;
//TEST dud
//Proiect cu robert:)
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class DButils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title,String username) {
        Parent root = null;
        if(username != null ) {
            try {
                FXMLLoader loader = new FXMLLoader(DButils.class.getResource(fxmlFile));
                root = loader.load();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DButils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 400,300));
        stage.show();
    }
   /* public static void singUpUser(ActionEvent event, String username, String password):
    Connection connection=null;
    PreparedStatment psInsert=null;
    PreparedStatment psCheckUserExists=null;
     ResultSet resultSet=null;
try{
    connection=DriverManager.getConnection("")
    }

*/
}
