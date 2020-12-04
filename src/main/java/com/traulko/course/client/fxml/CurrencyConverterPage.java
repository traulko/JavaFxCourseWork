package com.traulko.course.client.fxml;

import com.traulko.course.client.ClientConnection;
import com.traulko.course.client.fxml.util.PageManager;
import com.traulko.course.client.fxml.util.PromptMessages;
import com.traulko.course.controller.PagePath;
import com.traulko.course.controller.RequestParameter;
import com.traulko.course.entity.Batch;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class CurrencyConverterPage {
    private static final int ZERO = 0;
    private static final int FULL_OPACITY = 100;
    private static final int DEFAULT_STEP = 1;
    private static final String CONVERT_RESULT = "Результат конвертации: ";

    @FXML
    private Button goBackButton;

    @FXML
    private ChoiceBox<?> fromCurrencyChoiceBox;

    @FXML
    private ChoiceBox<?> toCurrencyChoiceBox;

    @FXML
    private TextField valueField;

    @FXML
    private Text resultField;

    @FXML
    private Button convertButton;

    @FXML
    private Pane currencyPane;

    @FXML
    void initialize() {
        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
            PageManager.goToPage(PagePath.USER_HOME);
        });

        resultField.setOpacity(ZERO);
        fillCurrencyFromChoiceBox(fromCurrencyChoiceBox);
        fillCurrencyToChoiceBox(toCurrencyChoiceBox);
        loadCurrencyPaneData(fromCurrencyChoiceBox.getValue().toString(), toCurrencyChoiceBox.getValue().toString());

        convertButton.setOnAction(actionEvent -> {
            String value = valueField.getText().trim();
            String fromCurrencyParameter = fromCurrencyChoiceBox.getValue().toString();
            String toCurrencyParameter = toCurrencyChoiceBox.getValue().toString();
            if (!value.isEmpty()) {
                if (fromCurrencyParameter.equals(toCurrencyParameter)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(PromptMessages.ERROR);
                    alert.setContentText(PromptMessages.CURRENCIES_IS_EQUALS);
                    alert.showAndWait();
                } else {
                    Batch requestBatch = new Batch();
                    requestBatch.getBatchMap().put(RequestParameter.CONVERTER_VALUE, value);
                    requestBatch.getBatchMap().put(RequestParameter.FROM_CURRENCY, fromCurrencyParameter);
                    requestBatch.getBatchMap().put(RequestParameter.TO_CURRENCY, toCurrencyParameter);
                    loadCurrencyPaneData(fromCurrencyParameter, toCurrencyParameter);
                    requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.CONVERT_VALUE);
                    Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
                    String converterResult = responseBatch.getBatchMap().get(RequestParameter.CONVERTER_RESULT).toString();
                    if (Double.parseDouble(converterResult) == ZERO) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(PromptMessages.ERROR);
                        alert.setContentText(PromptMessages.INCORRECT_NUMBER_OR_NO_INFO_IN_DB);
                        alert.showAndWait();
                    }
                    resultField.setText(CONVERT_RESULT + converterResult);
                    resultField.setOpacity(FULL_OPACITY);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(PromptMessages.ERROR);
                alert.setContentText(PromptMessages.EMPTY_FIELD);
                alert.showAndWait();
            }
        });
    }

    private void fillCurrencyFromChoiceBox(ChoiceBox choiceBox) {
        choiceBox.getItems().add(RequestParameter.BYN_CURRENCY);
        choiceBox.getItems().add(RequestParameter.USD_CURRENCY);
        choiceBox.getItems().add(RequestParameter.EUR_CURRENCY);
        choiceBox.getItems().add(RequestParameter.RUB_CURRENCY);
        choiceBox.setValue(RequestParameter.BYN_CURRENCY);
    }

    private void fillCurrencyToChoiceBox(ChoiceBox choiceBox) {
        choiceBox.getItems().add(RequestParameter.BYN_CURRENCY);
        choiceBox.getItems().add(RequestParameter.USD_CURRENCY);
        choiceBox.getItems().add(RequestParameter.EUR_CURRENCY);
        choiceBox.getItems().add(RequestParameter.RUB_CURRENCY);
        choiceBox.setValue(RequestParameter.EUR_CURRENCY);
    }

    private void loadCurrencyPaneData(String fromCurrencyParameter, String toCurrencyParameter) {
        Batch requestBatch = new Batch();
        requestBatch.getBatchMap().put(RequestParameter.FROM_CURRENCY, fromCurrencyParameter);
        requestBatch.getBatchMap().put(RequestParameter.TO_CURRENCY, toCurrencyParameter);
        requestBatch.getBatchMap().put(RequestParameter.COMMAND_NAME, RequestParameter.CONVERTER_RATIOS);
        Batch responseBatch = ClientConnection.getConnectionResult(requestBatch);
        List<Double> converterRatios = (List<Double>) responseBatch.getBatchMap()
                .get(RequestParameter.CONVERTER_RATIOS_LIST);
        double minRatio = (double) responseBatch.getBatchMap().get(RequestParameter.CONVERTER_MIN_RATIO);
        double maxRatio = (double) responseBatch.getBatchMap().get(RequestParameter.CONVERTER_MAX_RATIO);
        double ratioStep = (double) responseBatch.getBatchMap().get(RequestParameter.CONVERTER_RATIO_STEP);
        currencyPane.getChildren().clear();
        NumberAxis xAxis = new NumberAxis(ZERO, converterRatios.size(), DEFAULT_STEP);
        NumberAxis yAxis = new NumberAxis(minRatio, maxRatio, ratioStep);
        LineChart<Number, Number> markChart = new LineChart(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        series.setName(PromptMessages.CURRENCY_CHART_NAME);
        int index = 0;
        for (double ratio : converterRatios) {
            series.getData().add(new XYChart.Data<>(index, ratio));
            index++;
        }
        markChart.getData().add(series);
        markChart.setMaxWidth(500);
        markChart.setMaxHeight(233);
        currencyPane.getChildren().add(markChart);
    }
}
