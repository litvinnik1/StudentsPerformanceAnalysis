package com.example.kursova_oop;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSignInButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {

        authSignInButton.setOnAction(event -> {
            String loginText = login_field.getText().trim();
            String loginPssword = password_field.getText().trim();
            if((!loginText.equals("") && !loginPssword.equals("")) || !loginText.equals("") || !loginPssword.equals("")){
                loginUser(loginText, loginPssword);
            }else{
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Пароль або логін не були введені!!!");
                dialog.setHeaderText("Введіть свій логін та пароль!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Пароль або логін не введено!");
                alert.showAndWait();
//                ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
//                dialog.getDialogPane().getButtonTypes().addAll(okButton);
//                dialog.show();
            }
        });
    }

    private void loginUser(String login_text, String login_password) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(login_text);
        user.setPassword(login_password);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while (true){
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        if(counter>=1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Інформаційне повідомлення");
            alert.setHeaderText(null);
            alert.setContentText("Ви успішно увійшли!");
            alert.showAndWait();

            openNewScene("/com/example/kursova_oop/dashboard.fxml");

        }else{
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Помилка !!!");
            dialog.setHeaderText("Логін або пароль введені не правильно!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Пароль або логін введено не правильно!");
            alert.showAndWait();
//            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
//            dialog.getDialogPane().getButtonTypes().addAll(okButton);
//            dialog.show();
        }
    }
    public void openNewScene(String window){
        authSignInButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LoginController.class.getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}