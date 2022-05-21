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

public class DButils{

    private static String s_name;
    private static String phone_nr;

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

    public static void signUpUser(ActionEvent event, String username, String password, String role, String name, String phone)  {
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
        psInsert = connection.prepareStatement("INSERT INTO users (username, password, role, name, phone_nr) VALUES (?, ?, ?, ?, ?)");
        psInsert.setString(1,username);
        psInsert.setString(2,encodePassword(username, password));
        psInsert.setString(3,role);
        psInsert.setString(4,name);
        psInsert.setString(5,phone);
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
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "proiectFIS");
        preparedStatement = connection.prepareStatement("SELECT password, role, name, phone_nr FROM users WHERE username = ?");
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
                String retrievedName = resultSet.getString("name");
                String retrievedNumber = resultSet.getString("phone_nr");
            if (retrievedPassword.equals(encodePassword(username,password))){
                if(retrievedRole.equals("Buyer")) {
                    changeScene(event, "/dashboard-buyer.fxml", "OnlineShop", null, null);
                } else {
                    s_name=retrievedName;
                    phone_nr=retrievedNumber;
                    changeScene(event, "/dashboard-seller.fxml", "OnlineShop", null, null);
                }
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
    public static void addItem(ActionEvent event, String name, String price, String desc)  {
        Connection connection=null;
        PreparedStatement psInsert=null;
        PreparedStatement psCheckItemExists=null;
        ResultSet resultSet=null;
        try{
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "proiectFIS");
            psCheckItemExists = connection.prepareStatement("SELECT * FROM items WHERE product_name = ?");
            psCheckItemExists.setString(1, name);
            resultSet=psCheckItemExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("Item already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("An item with the inserted name already exists.");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO items (product_name, price, description, seller_name, phone_number) VALUES (?, ?, ?, ?, ?)");
                psInsert.setString(1,name);
                psInsert.setString(2,price);
                psInsert.setString(3,desc);
                psInsert.setString(4,s_name);
                psInsert.setString(5,phone_nr);
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
            if (psCheckItemExists!=null){
                try{
                    psCheckItemExists.close();
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

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root", "proiectFIS");
        return connection;
    }

    public static void Delete(String name) throws SQLException {
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/schemafis", "root","proiectFIS");
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("delete from items where product_name = ?");
        preparedStatement.setString(1,name);
        preparedStatement.execute();
    }


}

