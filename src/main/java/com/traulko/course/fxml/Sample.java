package com.traulko.course.fxml;

import com.traulko.course.client.ClientConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Sample {
    @FXML
    private Text TextField;

    @FXML
    private TextField InputField;

    @FXML
    private Button Button;

    @FXML
    void initialize() {
        Button.setOnAction(event -> {
            String text = InputField.getText();
            ClientConnection.getConnection();
            Button.getScene().getWindow().hide();
        });
    }
}
