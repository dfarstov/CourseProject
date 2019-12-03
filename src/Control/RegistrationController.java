package Control;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnect.DatabaseHandler;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button regButton;

    @FXML
    private TextField nameText;

    @FXML
    private TextField familyText;

    @FXML
    private TextField phoneText;

    @FXML
    private TextField emailText;

    @FXML
    void initialize() {
        regButton.setOnAction(actionEvent -> {
            regNewUser();
        });
    }

    private void regNewUser(){
        try {
            DatabaseHandler dbHandler = new DatabaseHandler();

            String login = loginText.getText();
            String password = passwordText.getText();
            String name = nameText.getText();
            String lastname = familyText.getText();
            String phone = phoneText.getText();
            String email = emailText.getText();

            User user = new User("",login, password, name, lastname, phone, email);

            dbHandler.regUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
