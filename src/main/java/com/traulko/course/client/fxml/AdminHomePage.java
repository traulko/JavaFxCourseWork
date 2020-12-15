package com.traulko.course.client.fxml;

import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.controller.PagePath;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminHomePage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button userManageButton;

    @FXML
    private Button converterManageButton;

    @FXML
    private Button accountManageButton;

    @FXML
    private Button findAllTransactionsButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.AUTHORIZATION);
        });

        userManageButton.setOnAction(actionEvent -> {
            userManageButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.USERS_MANAGE);
        });

        converterManageButton.setOnAction(actionEvent -> {
            converterManageButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.CONVERTER_MANAGE);
        });

        accountManageButton.setOnAction(actionEvent -> {
            accountManageButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.ACCOUNTS_MANAGE);
        });

        findAllTransactionsButton.setOnAction(actionEvent -> {
            findAllTransactionsButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.TRANSACTIONS);
        });
    }
}
