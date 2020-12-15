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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;

public class AccountPage {
    private static final String MONEY_AMOUNT_REGEX = "^[0-9]\\d{0,6}(\\.\\d{0,2})?$";
    private static final String ACCOUNT_ID_REGEX = "^[1-9]\\d{0,9}$";
    private static final int OPACITY_ZERO = 0;

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
    private Button blockAccountButton;

    @FXML
    private Button unblockAccountButton;

    @FXML
    private Button closeButton;

    @FXML
    private TextField tokenTextField;

    @FXML
    private Text tokenField;

    @FXML
    private Text accountClosedText;

    @FXML
    private TextField moneyAmountTextField;

    @FXML
    private Button fillUpBalanceButton;

    @FXML
    private TextField transferToAccountTextField;

    @FXML
    private TextField transferMoneyAmountTextField;

    @FXML
    private Button makeTransferButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.USER_ACCOUNTS);
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

        makeTransferButton.setOnAction(actionEvent -> {
            if (transferToAccountTextField.getText().trim().matches(ACCOUNT_ID_REGEX) &&
                    transferMoneyAmountTextField.getText().trim().matches(MONEY_AMOUNT_REGEX)) {
                requestBatch.getBatchMap().put(RequestParameter.ACCOUNT_ID, account.getAccountId());
                requestBatch.getBatchMap().put(RequestParameter.TO_ACCOUNT_ID, transferToAccountTextField.getText().trim());
                requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                        RequestParameter.TRANSFER_TO_ACCOUNT);
                requestBatch.getBatchMap().put(RequestParameter.MONEY_AMOUNT, transferMoneyAmountTextField.getText().trim());
                Batch fourthResponseBatch = ClientConnection.getConnectionResult(requestBatch);
                if ((boolean) fourthResponseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.SUCCESS);
                    alert.setContentText(PromptMessages.TRANSFER_SUCCESS);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.TRANSFER_ERROR);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.ACCOUNT_ID_OR_MONEY_AMOUNT_INCORRECT);
                alert.showAndWait();
            }
        });

        unblockAccountButton.setOnAction(actionEvent -> {
            if (account.getStatus() == Account.Status.BLOCKED) {
                String token = tokenTextField.getText().trim();
                requestBatch.getBatchMap().put(RequestParameter.ACCOUNT_ID, account.getAccountId());
                requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                        RequestParameter.UNBLOCK_ACCOUNT);
                requestBatch.getBatchMap().put(RequestParameter.ACCOUNT_TOKEN, token);
                Batch secondResponseBatch = ClientConnection.getConnectionResult(requestBatch);
                if ((boolean) secondResponseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.SUCCESS);
                    alert.setContentText(PromptMessages.UNBLOCK_ACCOUNT_SUCCESS);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.UNBLOCK_ACCOUNT_INCORRECT_TOKEN);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.UNBLOCK_ACCOUNT_ERROR);
                alert.showAndWait();
            }
            initUserAndAccountAndTransactionInfo(user, account, transactionList);
        });

        fillUpBalanceButton.setOnAction(actionEvent -> {
            if (account.getStatus() == Account.Status.ENABLE) {
                if (!moneyAmountTextField.getText().trim().matches(MONEY_AMOUNT_REGEX)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.MONEY_VALUE_INCORRECT);
                    alert.showAndWait();
                } else {
                    requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                            RequestParameter.FILL_ACCOUNT_BALANCE);
                    requestBatch.getBatchMap().put(RequestParameter.ACCOUNT_ID, account.getAccountId());
                    requestBatch.getBatchMap().put(RequestParameter.MONEY_AMOUNT, moneyAmountTextField.getText());
                    Batch thirdResponseBatch = ClientConnection.getConnectionResult(requestBatch);
                    if ((boolean) thirdResponseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.SUCCESS);
                        alert.setContentText(PromptMessages.FILL_UP_BALANCE_SUCCESS);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.FILL_UP_BALANCE_ERROR);
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.NOT_ENABLE_ACCOUNT);
                alert.showAndWait();
            }
            initUserAndAccountAndTransactionInfo(user, account, transactionList);
        });

        blockAccountButton.setOnAction(actionEvent -> {
            if (account.getStatus() == Account.Status.ENABLE) {
                requestBatch.getBatchMap().put(RequestParameter.ACCOUNT_ID, account.getAccountId());
                requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                        RequestParameter.BLOCK_ACCOUNT);
                Batch secondResponseBatch = ClientConnection.getConnectionResult(requestBatch);
                if ((boolean) secondResponseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.SUCCESS);
                    alert.setContentText(PromptMessages.BLOCK_ACCOUNT_SUCCESS);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.BLOCK_ACCOUNT_ERROR);
                alert.showAndWait();
            }
            initUserAndAccountAndTransactionInfo(user, account, transactionList);
        });

        closeButton.setOnAction(actionEvent -> {
            if (account.getStatus() != Account.Status.CLOSED) {
                requestBatch.getBatchMap().put(RequestParameter.ACCOUNT_ID, account.getAccountId());
                requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                        RequestParameter.CLOSE_ACCOUNT);
                Batch secondResponseBatch = ClientConnection.getConnectionResult(requestBatch);
                if ((boolean) secondResponseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.SUCCESS);
                    alert.setContentText(PromptMessages.CLOSE_ACCOUNT_SUCCESS);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.CLOSE_ACCOUNT_ERROR);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.ACCOUNT_ALREADY_CLOSED);
                alert.showAndWait();
            }
            initUserAndAccountAndTransactionInfo(user, account, transactionList);
        });
    }

    private void initUserAndAccountAndTransactionInfo(User user, Account account,
                                                      List<EntityTransaction> transactionList) {
        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                RequestParameter.FIND_FULL_ACCOUNT_MANAGE_INFO);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        account = (Account) responseBatch.getBatchMap().get(RequestParameter.ACCOUNT);
        user = (User) responseBatch.getBatchMap().get(RequestParameter.USER);
        transactionList = (List<EntityTransaction>) responseBatch
                .getBatchMap().get(RequestParameter.TRANSACTION_LIST);
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
        if (account.getStatus() != Account.Status.CLOSED) {
            accountClosedText.setOpacity(OPACITY_ZERO);
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
