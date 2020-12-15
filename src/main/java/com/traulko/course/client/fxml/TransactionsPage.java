package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PropertyTableValueFactory;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.EntityTransaction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TransactionsPage {
    @FXML
    private Button goBackButton;

    @FXML
    private TableView<EntityTransaction> transactionsTable;

    @FXML
    private TableColumn<EntityTransaction, String> transactionFromAccountColumn;

    @FXML
    private TableColumn<EntityTransaction, String> transactionToAccountColumn;

    @FXML
    private TableColumn<EntityTransaction, Double> transactionMoneyAmountColumn;

    @FXML
    private TableColumn<EntityTransaction, String> transactionDateColumn;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.ADMIN_HOME);
        });

        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                RequestParameter.FIND_ALL_TRANSACTIONS);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        List<EntityTransaction> transactionList = (List<EntityTransaction>) responseBatch
                .getBatchMap().get(RequestParameter.TRANSACTION_LIST);

        updateTransactionsTable(transactionList);
    }

    private void updateTransactionsTable(List<EntityTransaction> transactionList) {
        transactionFromAccountColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFromAccount().getAccountId().toString()));
        transactionToAccountColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getToAccount().getAccountId().toString()));
        transactionMoneyAmountColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory
                .TRANSACTION_MONEY_AMOUNT));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory
                .TRANSACTION_DATE));
        ObservableList<EntityTransaction> transactions = FXCollections.observableArrayList();
        transactions.setAll(transactionList);
        transactionsTable.setItems(transactions);
    }
}
