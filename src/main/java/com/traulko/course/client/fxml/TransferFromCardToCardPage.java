package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TransferFromCardToCardPage {
    private static final String MONEY_AMOUNT_REGEX = "^[0-9]\\d{0,6}(\\.\\d{0,2})?$";
    private static final String CARD_NUMBER_REGEX = "^[1-9]\\d{15}$";
    private static final String CARD_DATE_REGEX = "^[0-9]\\d{1,6}(/\\d{4})?$";

    @FXML
    private Button goBackButton;

    @FXML
    private TextField fromCardNumberTextField;

    @FXML
    private TextField fromCardDateTextField;

    @FXML
    private TextField fromCardCvvField;

    @FXML
    private TextField fromCardMoneyAmountField;

    @FXML
    private TextField toCardNumberTextField;

    @FXML
    private Button makeTransferButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.USER_HOME);
        });

        makeTransferButton.setOnAction(actionEvent -> {
            if (!fromCardNumberTextField.getText().isEmpty() && !fromCardDateTextField.getText().isEmpty() &&
                    !fromCardCvvField.getText().isEmpty() && !fromCardMoneyAmountField.getText().isEmpty() &&
                    !toCardNumberTextField.getText().isEmpty()) {
                if (fromCardMoneyAmountField.getText().matches(MONEY_AMOUNT_REGEX)) {
                    if (fromCardNumberTextField.getText().matches(CARD_NUMBER_REGEX) &&
                            toCardNumberTextField.getText().matches(CARD_NUMBER_REGEX)) {
                        if (fromCardDateTextField.getText().trim().matches(CARD_DATE_REGEX)) {
                            if(fromCardNumberTextField.getText().trim().equals(toCardNumberTextField.getText().trim())) {
                                Batch requestBatch = new Batch();
                                requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME,
                                        RequestParameter.TRANSFER_FROM_CARD_TO_CARD);
                                requestBatch.getBatchMap().put(RequestParameter.CREDIT_CARD_NUMBER,
                                        fromCardNumberTextField.getText().trim());
                                requestBatch.getBatchMap().put(RequestParameter.TO_CREDIT_CARD_NUMBER,
                                        toCardNumberTextField.getText().trim());
                                requestBatch.getBatchMap().put(RequestParameter.CREDIT_CARD_END_SERVICE_DATE,
                                        fromCardDateTextField.getText().trim());
                                requestBatch.getBatchMap().put(RequestParameter.CREDIT_CARD_CVV,
                                        fromCardCvvField.getText().trim());
                                requestBatch.getBatchMap().put(RequestParameter.MONEY_AMOUNT,
                                        fromCardMoneyAmountField.getText().trim());
                                Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                                if ((boolean) responseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
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
                                alert.setContentText(PromptMessages.CARD_NUMBERS_IS_EQUALS);
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle(PromptMessages.SUCCESS);
                            alert.setContentText(PromptMessages.CARD_DATE_INCORRECT);
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.CARDS_NUMBER_INCORRECT);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.MONEY_VALUE_INCORRECT);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.EMPTY_FIELDS);
                alert.showAndWait();
            }
        });

    }
}
