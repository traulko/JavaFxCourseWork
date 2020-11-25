package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationPage {

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> {
            signUpButton.getScene().getWindow().hide();
            PageManager.goToPage("/SignUp.fxml");
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String login = loginField.getText().trim();
                String password = passwordField.getText().trim();
                if ((!loginField.getText().isEmpty() && !passwordField.getText().isEmpty())) {
                    Map<String, Object> batchMap = new HashMap<>();
                    batchMap.put(RequestParameter.EMAIL, login);
                    batchMap.put(RequestParameter.PASSWORD, password);
                    batchMap.put(RequestParameter.COMMAND_NAME, RequestParameter.SIGN_IN);
                    Batch requestBatch = new Batch(batchMap);
                    String pagePath = ClientConnection.getConnectionResult(requestBatch);
                    if (pagePath.equals(PagePath.AUTHORIZATION)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Incorrect data");
                        alert.setContentText("Login or password incorrect");
                        alert.showAndWait();
                    } else {
                        PageManager.goToPage(pagePath);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Input fields empty");
                    alert.setContentText("Please fill all input fields");
                    alert.showAndWait();
                }
            }
        });
    }
}
