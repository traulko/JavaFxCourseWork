package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.client.fxml.util.PropertyTableValueFactory;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import com.traulko.course.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UsersManagePage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button blockUserButton;

    @FXML
    private Button unblockUserButton;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> surnameColumn;

    @FXML
    private TableColumn<User, String> patronymicColumn;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.ADMIN_HOME);
        });

        updateUsersTable();

        blockUserButton.setOnAction(event -> {
            ObservableList<User> selectedRows;
            selectedRows = usersTable.getSelectionModel().getSelectedItems();
            for (User user : selectedRows) {
                if (user.getStatus() == User.Status.BLOCKED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.BLOCKING_ERROR);
                    alert.showAndWait();
                } else {
                    Batch requestBatch = new Batch();
                    requestBatch.getBatchMap().put(RequestParameter.USER_EMAIL, user.getEmail());
                    requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.BLOCK_USER);
                    Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                    if ((boolean) responseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.SUCCESS);
                        alert.setContentText(PromptMessages.BLOCKING_SUCCESS);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.UNBLOCKING_ERROR);
                        alert.showAndWait();
                    }
                }
            }
            updateUsersTable();
        });

        unblockUserButton.setOnAction(event -> {
            ObservableList<User> selectedRows;
            selectedRows = usersTable.getSelectionModel().getSelectedItems();
            for (User user : selectedRows) {
                if (user.getStatus() == User.Status.ENABLE) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.UNBLOCKING_ERROR);
                    alert.showAndWait();
                } else {
                    Batch requestBatch = new Batch();
                    requestBatch.getBatchMap().put(RequestParameter.USER_EMAIL, user.getEmail());
                    requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.UNBLOCK_USER);
                    Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                    if ((boolean) responseBatch.getBatchMap().get(RequestParameter.BOOLEAN_RESULT)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.SUCCESS);
                        alert.setContentText(PromptMessages.UNBLOCKING_SUCCESS);
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.UNBLOCKING_ERROR);
                        alert.showAndWait();
                    }
                }
            }
            updateUsersTable();
        });
    }

    private void updateUsersTable() {
        emailColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_EMAIL));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_ROLE));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_STATUS));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_NAME));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_SURNAME));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>(PropertyTableValueFactory.USER_PATRONYMIC));
        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.FIND_USERS);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        List<User> userList = (List<User>) responseBatch.getBatchMap()
                .get(RequestParameter.USER_LIST);
        ObservableList<User> users = FXCollections.observableArrayList();
        users.setAll(userList);
        usersTable.setItems(users);
    }
}
