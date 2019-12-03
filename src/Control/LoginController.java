package Control;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnect.DatabaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button loginButton;

    @FXML
    private Button regButton;

    @FXML
    void initialize() {
        loginButton.setOnAction(actionEvent -> {
            String login = loginText.getText().trim();
            String password = passwordText.getText().trim();

            if(!login.equals("") && !password.equals("")){
                try {
                    loginUser(login, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                showError("Вы не заполнили все поля правильно!", "Проверьте заполнены ли все поля и выбран ли английский язык.");
            }
        });
        regButton.setOnAction(actionEvent -> {
            regButton.getScene().getWindow().hide();
            Stage stage = openNewScene("../View/registration.fxml", "Регистрация");
                stage.show();
        });
    }

    private void loginUser(String login, String password) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
            user.setLogin(login);
            user.setPassword(password);
        ResultSet resultSet = dbHandler.getUser(user);

        boolean exists = false;

        while(resultSet.next())  exists = true;

        if(exists) {
            dbHandler.loginUser(user);

            loginButton.getScene().getWindow().hide();

            Stage stage = openNewScene("../View/main.fxml", "Главная");
                stage.setOnCloseRequest(windowEvent -> {
                    try {
                        dbHandler.logOutUser();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

                stage.show();
        }
        else {
            showError("Пользователь не найден.", "Проверьте заполнены ли все поля правильно и убедитесь, что Caps Lock выключен.");
        }
    }
}
