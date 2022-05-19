package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DButils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title,String username, String role) {
        Parent root = null;
        if(username != null && role!=null) {
            try {
                FXMLLoader loader = new FXMLLoader(DButils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username,role);
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

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", "");
    }

    public static void signUpUser(ActionEvent event, String username, String password, String role)  {
    Connection connection=null;
    PreparedStatement psInsert=null;
    PreparedStatement psCheckUserExists=null;
    ResultSet resultSet=null;
try{
    connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "Inviere2018#");
    psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
    psCheckUserExists.setString(1, username);
    resultSet=psCheckUserExists.executeQuery();

    if(resultSet.isBeforeFirst()){
        System.out.println("User already exists");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You cannot use this username.");
        alert.show();
    }else{
        psInsert = connection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
        psInsert.setString(1,username);
        psInsert.setString(2,encodePassword(username, password));
        psInsert.setString(3,role);
        psInsert.executeUpdate();

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
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "Inviere2018#");
        preparedStatement = connection.prepareStatement("SELECT password, role FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        if(!resultSet.isBeforeFirst()){
            System.out.println("User not found in the database!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Provided credentials are incorrect");
            alert.show();
        }else{
            while (resultSet.next()) {
                String retrievedPassword = resultSet.getString("password");
                String retrievedRole = resultSet.getString("role");
            if (retrievedPassword.equals(encodePassword(username,password))){
                changeScene(event, "/dashboard-buyer.fxml", "Welcome!", null, null);
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

