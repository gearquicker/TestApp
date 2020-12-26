package helper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import view.common.FrameModule;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InterfaceHelper {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final int TOAST_OPEN_TIME = 500;
    private static final int TOAST_SHOW_TIME = 3000;
    private static final int MENU_BUTTON_SIZE = 20;
    private static final int MENU_TOOLBAR_SIZE = 15;

    public static void openStage(Parent node, String title, String icon, Stage owner) {
        Stage stage = new Stage();
        Scene scene = new Scene(node);

        if (title != null) {
            stage.setTitle(title);
        }
        if (icon != null) {
            stage.getIcons().add(new Image(InterfaceHelper.class.getResourceAsStream(icon)));
        }
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        scene.getStylesheets().add("/css/classic.css");

        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (stage.isMaximized()) {
                Screen screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()).get(0);
                Rectangle2D bounds = screen.getVisualBounds();
                BoundingBox boundingBox = new BoundingBox(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
                stage.setX(boundingBox.getMinX());
                stage.setY(boundingBox.getMinY());
                stage.setWidth(boundingBox.getWidth());
                stage.setHeight(boundingBox.getHeight());
            }
        });

        stage.setScene(scene);
        if (owner == null) {
            stage.show();
        } else {
            stage.initOwner(owner);
            stage.showAndWait();
        }
    }

    public static void openWindow(Region node, String title, String icon, Stage owner) {
        openStage(new FrameModule(node, new Label(title), FrameModule.ButtonSetType.DEFAULT, true), title, icon, owner);
    }

    public static void showWaitPane(Scene scene) {
        try {
            StackPane pane = new StackPane();
            pane.getChildren().add(new ProgressIndicator());
            pane.setStyle("-fx-background-color: #00000044");
            pane.setId("wait-pane");
            addToAnchorPane((AnchorPane) scene.getRoot(), pane, 0, 0, 0, 0);
        } catch (Exception ignore) {
        }
    }

    public static void hideWaitPane(Scene scene) {
        try {
            ((AnchorPane) scene.getRoot()).getChildren().remove(((AnchorPane) scene.getRoot()).getChildren().size() - 1);
        } catch (Exception ignore) {
        }
    }

    public static void showToast(Stage stage, String message) {
        Stage toastStage = new Stage();
        toastStage.initOwner(stage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(message);
        text.setId("toast-text");

        StackPane pane = new StackPane(text);
        pane.setId("toast-pane");
        pane.setOpacity(0);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/css/classic.css");
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey = new KeyFrame(Duration.millis(TOAST_OPEN_TIME),
                new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey);
        fadeInTimeline.setOnFinished((event) -> new Thread(() -> {
            try {
                Thread.sleep(TOAST_SHOW_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Timeline fadeOutTimeline = new Timeline();
            KeyFrame fadeOutKey = new KeyFrame(Duration.millis(TOAST_OPEN_TIME),
                    new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
            fadeOutTimeline.getKeyFrames().add(fadeOutKey);
            fadeOutTimeline.setOnFinished((event1) -> toastStage.close());
            fadeOutTimeline.play();
        }).start());
        fadeInTimeline.play();
    }

    public static void addToAnchorPane(AnchorPane parent, Node node, double left, double right, double top, double bottom) {
        parent.getChildren().add(node);
        if (!Double.isNaN(left)) AnchorPane.setLeftAnchor(node, left);
        if (!Double.isNaN(right)) AnchorPane.setRightAnchor(node, right);
        if (!Double.isNaN(top)) AnchorPane.setTopAnchor(node, top);
        if (!Double.isNaN(bottom)) AnchorPane.setBottomAnchor(node, bottom);
    }

    public static void copyNodeSize(Region node, Region target) {
        target.setPrefWidth(node.getPrefWidth());
        target.setPrefHeight(node.getPrefHeight());
    }

    public static Button setupMenuButton(String icon, double size) {
        if (Double.isNaN(size)) size = MENU_BUTTON_SIZE;

        Button button = new Button();
        button.setId(icon);
        button.getStyleClass().add("custom-button");
        button.setMinWidth(size);
        button.setPrefWidth(size);
        button.setMinHeight(size);
        button.setPrefHeight(size);

        return button;
    }

    public static Button setupToolbarButton(String icon, double size) {
        if (Double.isNaN(size)) size = MENU_TOOLBAR_SIZE;

        Button button = new Button();
        button.setId(icon);
        button.getStyleClass().add("scene-button");
        button.setMinWidth(size);
        button.setPrefWidth(size);
        button.setMinHeight(size);
        button.setPrefHeight(size);
        button.setPadding(new Insets(0, 0, 0, 10));

        return button;
    }

    public static String formatLongToDate(long date) {
        return DATE_FORMAT.format(new Date(date));
    }

}