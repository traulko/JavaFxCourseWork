package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.CustomTooltip;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.HashMap;
import java.util.Map;

public class AuthorizationPage {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    void initialize() {
        emailField.setTooltip(CustomTooltip.makeBubblePrompt(new Tooltip(PromptMessages.CORRECT_EMAIL)));
        passwordField.setTooltip(CustomTooltip.makeBubblePrompt(new Tooltip(PromptMessages.CORRECT_PASSWORD)));

        signUpButton.setOnAction(event -> {
            signUpButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.REGISTRATION);
        });

        forgotPasswordButton.setOnAction(actionEvent -> {
            forgotPasswordButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.FORGOT_PASSWORD);
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();
                if ((!email.isEmpty() && !password.isEmpty())) {
                    Map<String, Object> batchMap = new HashMap<>();
                    batchMap.put(RequestParameter.USER_EMAIL, email);
                    batchMap.put(RequestParameter.USER_PASSWORD, password);
                    batchMap.put(RequestParameter.COMMAND_NAME, RequestParameter.SIGN_IN);
                    Batch requestBatch = new Batch(batchMap);
                    Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                    String pagePath = responseBatch.getBatchMap().get(RequestParameter.PAGE_PATH).toString();
                    if (pagePath.equals(PagePath.AUTHORIZATION)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.INCORRECT_LOGIN_OR_PASSWORD);
                        alert.showAndWait();
                    } else {
                        PageManager.goToPage(pagePath);
                        signInButton.getScene().getWindow().hide();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.EMPTY_FIELDS);
                    alert.showAndWait();
                }
            }
        });
    }
}
