package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.client.fxml.util.PropertyTableValueFactory;
import com.traulko.course.controller.PageManagerHandler;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class AccountsManagePage {

    @FXML
    private Button goBackButton;

    @FXML
    private TableView<Account> accountTable;

    @FXML
    private TableColumn<Account, Integer> idColumn;

    @FXML
    private TableColumn<Account, LocalDate> creationDateColumn;

    @FXML
    private TableColumn<Account, String> statusColumn;

    @FXML
    private TableColumn<Account, Double> moneyAmountColumn;

    @FXML
    private Button goToAccountButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.ADMIN_HOME);
        });

        goToAccountButton.setOnAction(event -> {
            ObservableList<Account> selectedRows;
            selectedRows = accountTable.getSelectionModel().getSelectedItems();
            for (Account account : selectedRows) {
                Batch requestBatch = new Batch();
                requestBatch.getBatchMap().put(RequestParameter.PAGE_MANAGER_HANDLER_VALUE, account.getAccountId());
                requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                        RequestParameter.SAVE_PAGE_MANAGER_HANDLER);
                ClientConnection.getConnectionResult(requestBatch);
            }
            goToAccountButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.ACCOUNT_MANAGE);
        });

        updateAccountsTable();

    }

    private void updateAccountsTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.ACCOUNT_ID));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.ACCOUNT_CREATION_DATE));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_STATUS));
        moneyAmountColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.ACCOUNT_MONEY_AMOUNT));
        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.FIND_ALL_ACCOUNTS);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        List<Account> accountList = (List<Account>) responseBatch.getBatchMap()
                .get(RequestParameter.ACCOUNT_LIST);
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        accounts.setAll(accountList);
        accountTable.setItems(accounts);
    }
}
