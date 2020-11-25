package com.traulko.course.client.fxml.util;

import javafx.scene.control.Tooltip;
import javafx.stage.PopupWindow;

public class CustomTooltip {
    private static final String SQUARE_BUBBLE =
            "M12 1c-6.628 0-12 4.573-12 10.213 0 2.39.932 4.591 2.427 6.164l-2.427 5.623" +
                    " 7.563-2.26c9.495 2.598 16.437-3.251 16.437-9.527 0-5.64-5.372-10.213-12-10.213z";

    public static Tooltip makeBubblePrompt(Tooltip tooltip) {
        tooltip.setStyle("-fx-font-size: 12px; -fx-shape: \"" + SQUARE_BUBBLE + "\";");
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);

        return tooltip;
    }
}
