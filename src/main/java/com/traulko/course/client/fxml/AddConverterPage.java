package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.CustomConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddConverterPage {

    @FXML
    private TextField eurField;

    @FXML
    private Button addButton;

    @FXML
    private TextField usdField;

    @FXML
    private TextField rubField;

    @FXML
    private Button goBackButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
        });

        updateLatestValues();

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String usdValue = usdField.getText().trim();
                String eurValue = eurField.getText().trim();
                String rubValue = rubField.getText().trim();
                if ((!usdValue.isEmpty() && !eurValue.isEmpty() && !rubValue.isEmpty())) {
                    Batch requestBatch = new Batch();
                    requestBatch.getBatchMap().put(RequestParameter.USD_CURRENCY, usdValue);
                    requestBatch.getBatchMap().put(RequestParameter.EUR_CURRENCY, eurValue);
                    requestBatch.getBatchMap().put(RequestParameter.RUB_CURRENCY, rubValue);
                    requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.ADD_CONVERTER);
                    Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                    if ((boolean) responseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.SUCCESS);
                        alert.setContentText(PromptMessages.CONVERTER_ADD_SUCCESS);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.CONVERTER_ADD_ERROR);
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

    private void updateLatestValues() {
        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.FIND_LATEST_CONVERTER);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        CustomConverter converter = (CustomConverter) responseBatch
                .getBatchMap().get(RequestParameter.CONVERTER);
        if (converter != null) {
            usdField.setText(String.valueOf(converter.getUsdValue()));
            eurField.setText(String.valueOf(converter.getEurValue()));
            rubField.setText(String.valueOf(converter.getRubValue()));
        }
    }
}
