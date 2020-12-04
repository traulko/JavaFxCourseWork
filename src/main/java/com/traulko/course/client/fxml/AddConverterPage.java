package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.client.fxml.util.PropertyTableValueFactory;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.CustomConverter;
import com.traulko.course.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                    // TODO: 04.12.2020  
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
