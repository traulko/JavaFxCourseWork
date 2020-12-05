package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import com.traulko.course.validator.UserValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordPage {

    @FXML
    private TextField emailField;

    @FXML
    private Button restoreAccessButton;

    @FXML
    private TextField tokenField;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField passwordRepeatField;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.AUTHORIZATION);
        });

        restoreAccessButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailField.getText().trim();
                String token = tokenField.getText().trim();
                String password = passwordField.getText().trim();
                String passwordRepeat = passwordRepeatField.getText().trim();
                if ((!email.isEmpty() && !token.isEmpty())) {
                    if (email.matches(UserValidator.EMAIL_REGEX)) {
                        if (password.matches(UserValidator.PASSWORD_REGEX) && password.equals(passwordRepeat)) {
                            Map<String, Object> batchMap = new HashMap<>();
                            batchMap.put(RequestParameter.USER_EMAIL, email);
                            batchMap.put(RequestParameter.REGISTRATION_TOKEN, token);
                            batchMap.put(RequestParameter.USER_PASSWORD, password);
                            batchMap.put(RequestParameter.USER_PASSWORD_REPEAT, passwordRepeat);
                            batchMap.put(RequestParameter.COMMAND_NAME, RequestParameter.FORGOT_PASSWORD);
                            Batch requestBatch = new Batch(batchMap);
                            Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                            if ((boolean) responseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle(PromptMessages.SUCCESS);
                                alert.setContentText(PromptMessages.CHANGE_PASSWORD_SUCCESS);
                                alert.showAndWait();
                                PageManager.goToPage(PagePath.AUTHORIZATION);
                                restoreAccessButton.getScene().getWindow().hide();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle(PromptMessages.ERROR);
                                alert.setContentText(PromptMessages.INCORRECT_EMAIL_OR_TOKEN);
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(PromptMessages.ERROR);
                            alert.setContentText(PromptMessages.INCORRECT_PASSWORDS);
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.INCORRECT_EMAIL);
                        alert.showAndWait();
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
