package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.client.fxml.util.PropertyTableValueFactory;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Account;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.EntityTransaction;
import com.traulko.course.entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class AccountManagePage {
    private static final int OPACITY_ZERO = 0;
    private static final int OPACITY_FULL = 100;

    @FXML
    private Button goBackButton;

    @FXML
    private Text fioTextField;

    @FXML
    private Text statusTextField;

    @FXML
    private Text emailTextField;

    @FXML
    private Text accountIdTextField;

    @FXML
    private Text accountStatusTextField;

    @FXML
    private Text accountMoneyAmountTextField;

    @FXML
    private Text creditCardNumberTextField;

    @FXML
    private Text creditCardServiceEndTextField;

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
    private Button acceptCreatingAccountButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.ACCOUNTS_MANAGE);
        });

        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                RequestParameter.FIND_FULL_ACCOUNT_MANAGE_INFO);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        Account account = (Account) responseBatch.getBatchMap().get(RequestParameter.ACCOUNT);
        User user = (User) responseBatch.getBatchMap().get(RequestParameter.USER);
        List<EntityTransaction> transactionList = (List<EntityTransaction>) responseBatch
                .getBatchMap().get(RequestParameter.TRANSACTION_LIST);

        initUserAndAccountAndTransactionInfo(user, account, transactionList);

        acceptCreatingAccountButton.setOnAction(actionEvent -> {
            if (account.getStatus() == Account.Status.NOT_CONFIRMED) {
                requestBatch.getBatchMap().put(RequestParameter.ACCOUNT_ID, account.getAccountId());
                requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                        RequestParameter.ACCEPT_CREATION_ACCOUNT);
                Batch secondResponseBatch = ClientConnection.getConnectionResult(requestBatch);
                if ((boolean) secondResponseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.SUCCESS);
                    alert.setContentText(PromptMessages.ACCOUNT_CREATION_SUCCESS);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.ACCOUNT_CREATION_ERROR);
                alert.showAndWait();
            }
            initUserAndAccountAndTransactionInfo(user, account, transactionList);
        });
    }

    private void initUserAndAccountAndTransactionInfo(User user, Account account,
                                                      List<EntityTransaction> transactionList) {
        fioTextField.setText(user.getSurname() + PromptMessages.SPACE + user.getName()
                + PromptMessages.SPACE + user.getPatronymic());
        statusTextField.setText(user.getStatus().toString());
        emailTextField.setText(user.getEmail());
        accountIdTextField.setText(account.getAccountId().toString());
        accountStatusTextField.setText(account.getStatus().toString());
        accountMoneyAmountTextField.setText(String.valueOf(account.getMoneyAmount()));
        creditCardNumberTextField.setText(String.valueOf(account.getCreditCard().getNumber()));
        int month = account.getCreditCard().getServiceEndDate().getMonthValue();
        int year = account.getCreditCard().getServiceEndDate().getYear();
        String dateStringFormat = month + PromptMessages.SLASH + year;
        creditCardServiceEndTextField.setText(dateStringFormat);
        updateTransactionsTable(transactionList);

        if (account.getStatus() == Account.Status.NOT_CONFIRMED) {
            acceptCreatingAccountButton.setOpacity(OPACITY_FULL);
        } else {
            acceptCreatingAccountButton.setOpacity(OPACITY_ZERO);
        }
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
