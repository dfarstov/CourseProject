package Control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

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
                loginUser(login, password);
            }
            else{
                //add alert
            }
        });
        regButton.setOnAction(actionEvent -> {
            regButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../View/registration.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
        });
    }

    private void loginUser(String login, String password){

    }
}
