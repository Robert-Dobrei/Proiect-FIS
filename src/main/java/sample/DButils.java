package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


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
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    public static void signUpUser(ActionEvent event, String username, String password) throws SQLException {
    Connection connection=null;
    PreparedStatement psInsert=null;
    PreparedStatement psCheckUserExists=null;
    ResultSet resultSet=null;
try{
    connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "proiectFIS");
    psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
    psCheckUserExists.setString(1, username);
    resultSet=psCheckUserExists.executeQuery();

    if(resultSet.isBeforeFirst()){
        System.out.println("User already exists");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You cannot use this username.");
        alert.show();
    }else{
        psInsert = connection.prepareStatement("INSERT INTO users (username, password) VALUES (? ?)");
        psInsert.setString(1,username);
        psInsert.setString(2,password);
        psInsert.executeUpdate();

        changeScene(event, "logged-in.fxml", "Welcome!", username);
    }
    }catch (SQLException e){
    e.printStackTrace();
    } finally {
    if (resultSet!=null){
        try{
            resultSet.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    if (psCheckUserExists!=null){
        try{
            psCheckUserExists.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    if (psInsert!=null){
        try{
            psInsert.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    if (connection!=null){
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    }


}
public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

    try {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "proiectFIS");
        preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        if(resultSet.isBeforeFirst()){
            System.out.println("User not found in the database!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Provided credentials are incorrect");
            alert.show();
        }else{
            while (resultSet.next()) {
                String retrievedPassword = resultSet.getString("password");
            if (retrievedPassword.equals(password)){
                changeScene(event, "logged-in.fxml", "Welcome!", username);
            }else{
                System.out.println("Passwords did not match!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The provided credentials are incorrect!");
                alert.show();
            }
            }
        }
    }catch(SQLException e){
        e.printStackTrace();
    } finally {
        if (resultSet!=null){
            try{
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if (preparedStatement!=null){
            try{
                preparedStatement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        }
        if (connection!=null){
            try{
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}

