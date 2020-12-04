package com.traulko.course.client.fxml;

import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.controller.PagePath;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserHomePage {

    @FXML
    private Button goBackButton;

    @FXML
    private Button currencyConverterButton;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.AUTHORIZATION);
        });

        currencyConverterButton.setOnAction(actionEvent -> {
            currencyConverterButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.CURRENCY_CONVERTER);
        });
    }
}
