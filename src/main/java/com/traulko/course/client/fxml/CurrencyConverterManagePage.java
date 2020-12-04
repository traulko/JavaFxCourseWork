package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PropertyTableValueFactory;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.CustomConverter;
import com.traulko.course.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CurrencyConverterManagePage {
    @FXML
    private Button goBackButton;

    @FXML
    private TableView<CustomConverter> converterTable;

    @FXML
    private TableColumn<CustomConverter, String> bynColumn;

    @FXML
    private TableColumn<CustomConverter, String> usdColumn;

    @FXML
    private TableColumn<CustomConverter, String> eurColumn;

    @FXML
    private TableColumn<CustomConverter, String> rubColumn;

    @FXML
    private TableColumn<CustomConverter, String> dateColumn;

    @FXML
    private Button addNewConverter;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.ADMIN_HOME);
        });

        addNewConverter.setOnAction(event -> {
            PageManager.goToPage(PagePath.ADD_CONVERTER);
        });

        updateConverterTable();
    }

    private void updateConverterTable() {
        bynColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.CONVERTER_BYN));
        usdColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.CONVERTER_USD));
        eurColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.CONVERTER_EUR));
        rubColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.CONVERTER_RUB));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.CONVERTER_CREATION_DATE));
        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.FIND_CONVERTERS);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        List<CustomConverter> converterList = (List<CustomConverter>) responseBatch.getBatchMap()
                .get(RequestParameter.CONVERTER_LIST);
        ObservableList<CustomConverter> converters = FXCollections.observableArrayList();
        converters.setAll(converterList);
        converterTable.setItems(converters);
    }
}
