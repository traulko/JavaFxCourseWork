package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
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

import java.util.HashMap;
import java.util.Map;

public class RegistrationPage {
    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField patronymicField;

    @FXML
    private TextField passwordRepeatField;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.AUTHORIZATION);
        });

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = nameField.getText().trim();
                String surname = surnameField.getText().trim();
                String patronymic = patronymicField.getText().trim();
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();
                String passwordRepeat = passwordRepeatField.getText().trim();
                if ((!name.isEmpty() && !surname.isEmpty() && !patronymic.isEmpty() &&
                        !email.isEmpty() && !password.isEmpty()) && !passwordRepeat.isEmpty()) {
                    Map<String, Object> batchMap = new HashMap<>();
                    batchMap.put(RequestParameter.USER_NAME, name);
                    batchMap.put(RequestParameter.USER_SURNAME, surname);
                    batchMap.put(RequestParameter.USER_PATRONYMIC, patronymic);
                    batchMap.put(RequestParameter.USER_EMAIL, email);
                    batchMap.put(RequestParameter.USER_PASSWORD, password);
                    batchMap.put(RequestParameter.USER_PASSWORD_REPEAT, passwordRepeat);
                    batchMap.put(RequestParameter.COMMAND_NAME, RequestParameter.SIGN_UP);
                    Batch requestBatch = new Batch(batchMap);
                    Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                    String pagePath = responseBatch.getBatchMap()
                            .get(RequestParameter.PAGE_PATH).toString();
                    if (pagePath.equals(PagePath.REGISTRATION)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.INCORRECT_REGISTRATION_DATA);
                        alert.showAndWait();
                        Map<String, Object> updatedRegistrationParameters =
                                (Map<String, Object>) responseBatch.getBatchMap()
                                .get(RequestParameter.REGISTRATION_PARAMETERS);
                        nameField.setText(updatedRegistrationParameters
                                .get(RequestParameter.USER_NAME).toString());
                        surnameField.setText(updatedRegistrationParameters
                                .get(RequestParameter.USER_SURNAME).toString());
                        patronymicField.setText(updatedRegistrationParameters
                                .get(RequestParameter.USER_PATRONYMIC).toString());
                        emailField.setText(updatedRegistrationParameters
                                .get(RequestParameter.USER_EMAIL).toString());
                        passwordField.setText(updatedRegistrationParameters
                                .get(RequestParameter.USER_PASSWORD).toString());
                        passwordRepeatField.setText(updatedRegistrationParameters
                                .get(RequestParameter.USER_PASSWORD_REPEAT).toString());
                    } else {
                        Map<String, Object> registrationParameters =
                                (Map<String, Object>) responseBatch.getBatchMap()
                                        .get(RequestParameter.REGISTRATION_PARAMETERS);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.SUCCESS);
                        alert.setContentText(PromptMessages.REGISTRATION_SUCCESS + "\n" + "Токен для восстановления: "
                                + "\n" + registrationParameters.get(RequestParameter.REGISTRATION_TOKEN));
                        alert.showAndWait();
                        PageManager.goToPage(pagePath);
                        registerButton.getScene().getWindow().hide();
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
