package Control;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBConnect.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {

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
        DatabaseHandler dbHandler = new DatabaseHandler();

        regButton.setOnAction(actionEvent -> {
            try {
                dbHandler.regUser(loginText.getText(),
                        passwordText.getText(),
                        nameText.getText(),
                        familyText.getText(),
                        phoneText.getText(),
                        emailText.getText());

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
