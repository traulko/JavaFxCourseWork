package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.client.fxml.util.PropertyTableValueFactory;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.Batch;
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

public class UserAccountsPage {

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
    private Button createAccountButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.USER_HOME);
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
            PageManager.goToPage(PagePath.ACCOUNT_PAGE);
        });

        updateAccountsTable();

        createAccountButton.setOnAction(actionEvent -> {
            Batch requestBatch = new Batch();
            requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.ADD_ACCOUNT);
            Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
            if ((boolean) responseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                String token = responseBatch.getBatchMap().get(RequestParameter.ACCOUNT_TOKEN).toString();
                String cvv = responseBatch.getBatchMap().get(RequestParameter.CREDIT_CARD_CVV).toString();
                String number = responseBatch.getBatchMap().get(RequestParameter.CREDIT_CARD_NUMBER).toString();
                String date = responseBatch.getBatchMap().get(RequestParameter.CREDIT_CARD_END_SERVICE_DATE).toString();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setWidth(400);
                alert.setHeight(400);
                alert.setTitle(PromptMessages.SUCCESS);
                alert.setContentText(PromptMessages.ADD_ACCOUNT_SUCCESS + PromptMessages.NEW_STRING
                        + PromptMessages.TOKEN_FOR_RESTORE_ACCESS + token + PromptMessages.NEW_STRING
                        + PromptMessages.CREDIT_CARD_NUMBER + number + PromptMessages.NEW_STRING
                        + PromptMessages.CREDIT_CARD_END_OF_SERVICE_DATE + date + PromptMessages.SPACE
                        + PromptMessages.CREDIT_CARD_CVV + cvv);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.ADD_ACCOUNT_ERROR);
                alert.showAndWait();
            }
            updateAccountsTable();
        });
    }

    private void updateAccountsTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.ACCOUNT_ID));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.ACCOUNT_CREATION_DATE));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_STATUS));
        moneyAmountColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.ACCOUNT_MONEY_AMOUNT));
        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.FIND_USER_ACCOUNTS);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        List<Account> accountList = (List<Account>) responseBatch.getBatchMap()
                .get(RequestParameter.ACCOUNT_LIST);
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        accounts.setAll(accountList);
        accountTable.setItems(accounts);
    }
}
