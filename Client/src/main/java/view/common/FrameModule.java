package view.common;

import helper.InterfaceHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class FrameModule extends SimpleModule {

    public enum ButtonSetType {
        DEFAULT, SHORT, DIALOG
    }

    private Stage stage;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private BorderPane titlePane;
    @FXML
    private AnchorPane lPane, lbPane, bPane, rbPane, rPane, rtPane, tPane, ltPane;

    @FXML
    private Label infoLabel;
    private HBox toolbar;
    private Button closeButton, collapseButton, minimizeButton, okButton;

    private double titleX, titleY, sizeX, sizeY, sizeV, sizeH;
    private final int MIN_WIDTH = 640;
    private final int MIN_HEIGHT = 480;
    private final boolean resizable;

    public FrameModule(Region content, Region title, ButtonSetType type, boolean resizable) {
        super("/fxml/FrameModule.fxml");
        this.resizable = resizable;
        Platform.runLater(() -> {
            stage = (Stage) mainPane.getScene().getWindow();
            InterfaceHelper.copyNodeSize(content, mainPane);
        });
        setResizeCursor(resizable);
        setupButtons(type);
        setupTitleBar(title);
        InterfaceHelper.addToAnchorPane(mainPane, content, 0, 0, 0, 0);
    }

    private void setupButtons(ButtonSetType type) {
        toolbar = new HBox();
        closeButton = InterfaceHelper.setupMenuButton("close-button", Double.NaN);
        collapseButton = InterfaceHelper.setupMenuButton("collapse-button", Double.NaN);
        minimizeButton = InterfaceHelper.setupMenuButton("minimize-button", Double.NaN);
        okButton = InterfaceHelper.setupMenuButton("ok-button", Double.NaN);

        closeButton.setOnAction(event -> onButtonCloseClick());
        collapseButton.setOnAction(event -> onButtonCollapseClick());
        minimizeButton.setOnAction(event -> onButtonMinimizeClick());

        switch (type) {
            case DEFAULT:
                setDefaultButtonSet();
                break;
            case SHORT:
                setShortButtonSet();
                break;
            case DIALOG:
                setDialogButtonSet();
                break;
            default:
                break;
        }
    }

    private void setupTitleBar(Region title) {
        titlePane.setOnMousePressed(event -> {
            titleX = event.getScreenX() - stage.getX();
            titleY = event.getScreenY() - stage.getY();
        });
        titlePane.setOnMouseDragged(event -> {
            if (!stage.isMaximized()) {
                stage.setX(event.getScreenX() - titleX);
                stage.setY(event.getScreenY() - titleY);
            }
        });
        titlePane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    stage.setMaximized(!stage.isMaximized());
                    setResizeCursor(!stage.isMaximized());
                }
            }
        });

        lPane.setOnMousePressed(event -> {
            sizeX = event.getScreenX();
            sizeV = stage.getWidth();
        });
        lPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (sizeX - event.getScreenX() + sizeV > MIN_WIDTH) {
                    stage.setX(event.getScreenX());
                    stage.setWidth(sizeX - event.getScreenX() + sizeV);
                }
            }
        });

        lbPane.setOnMousePressed(event -> {
            sizeX = event.getScreenX();
            sizeV = stage.getWidth();
            sizeY = event.getScreenY();
            sizeH = stage.getHeight();
        });
        lbPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (sizeX - event.getScreenX() + sizeV > MIN_WIDTH) {
                    stage.setX(event.getScreenX());
                    stage.setWidth(sizeX - event.getScreenX() + sizeV);
                }
                if (event.getScreenY() - sizeY + sizeH > MIN_HEIGHT) {
                    stage.setHeight(event.getScreenY() - sizeY + sizeH);
                }
            }
        });

        bPane.setOnMousePressed(event -> {
            sizeY = event.getScreenY();
            sizeH = stage.getHeight();
        });
        bPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (event.getScreenY() - sizeY + sizeH > MIN_HEIGHT) {
                    stage.setHeight(event.getScreenY() - sizeY + sizeH);
                }
            }
        });

        rbPane.setOnMousePressed(event -> {
            sizeX = event.getScreenX();
            sizeV = stage.getWidth();
            sizeY = event.getScreenY();
            sizeH = stage.getHeight();
        });
        rbPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (event.getScreenX() - sizeX + sizeV > MIN_WIDTH) {
                    stage.setWidth(event.getScreenX() - sizeX + sizeV);
                }
                if (event.getScreenY() - sizeY + sizeH > MIN_HEIGHT) {
                    stage.setHeight(event.getScreenY() - sizeY + sizeH);
                }
            }
        });

        rPane.setOnMousePressed(event -> {
            sizeX = event.getScreenX();
            sizeV = stage.getWidth();
        });
        rPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (event.getScreenX() - sizeX + sizeV > MIN_WIDTH) {
                    stage.setWidth(event.getScreenX() - sizeX + sizeV);
                }
            }
        });

        rtPane.setOnMousePressed(event -> {
            sizeX = event.getScreenX();
            sizeV = stage.getWidth();
            sizeY = event.getScreenY();
            sizeH = stage.getHeight();
        });
        rtPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (event.getScreenX() - sizeX + sizeV > MIN_WIDTH) {
                    stage.setWidth(event.getScreenX() - sizeX + sizeV);
                }
                if (sizeY - event.getScreenY() + sizeH > MIN_HEIGHT) {
                    stage.setY(event.getScreenY());
                    stage.setHeight(sizeY - event.getScreenY() + sizeH);
                }
            }
        });

        tPane.setOnMousePressed(event -> {
            sizeY = event.getScreenY();
            sizeH = stage.getHeight();
        });
        tPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (sizeY - event.getScreenY() + sizeH > MIN_HEIGHT) {
                    stage.setY(event.getScreenY());
                    stage.setHeight(sizeY - event.getScreenY() + sizeH);
                }
            }
        });

        ltPane.setOnMousePressed(event -> {
            sizeX = event.getScreenX();
            sizeV = stage.getWidth();
            sizeY = event.getScreenY();
            sizeH = stage.getHeight();
        });
        ltPane.setOnMouseDragged(event -> {
            if (!stage.isMaximized() && resizable) {
                if (sizeX - event.getScreenX() + sizeV > MIN_WIDTH) {
                    stage.setX(event.getScreenX());
                    stage.setWidth(sizeX - event.getScreenX() + sizeV);
                }
                if (sizeY - event.getScreenY() + sizeH > MIN_HEIGHT) {
                    stage.setY(event.getScreenY());
                    stage.setHeight(sizeY - event.getScreenY() + sizeH);
                }
            }
        });
        if (title != null) {
            title.setPadding(new Insets(0, 0, 3, 5));
            title.setId("title");
            titlePane.setLeft(title);
            BorderPane.setAlignment(title, Pos.CENTER_LEFT);
        }
    }

    private void setDefaultButtonSet() {
        toolbar.setPadding(new Insets(4, 0, 0, 0));
        toolbar.getChildren().addAll(minimizeButton, collapseButton, closeButton);
        titlePane.setRight(toolbar);
    }

    private void setShortButtonSet() {
        toolbar.setPadding(new Insets(4, 0, 0, 0));
        toolbar.getChildren().addAll(okButton, closeButton);
        titlePane.setRight(toolbar);
    }

    private void setDialogButtonSet() {
        toolbar.setPadding(new Insets(4, 0, 0, 0));
        toolbar.getChildren().add(closeButton);
        titlePane.setRight(toolbar);
    }

    private void onButtonCloseClick() {
        stage.close();
    }

    private void onButtonCollapseClick() {
        stage.setMaximized(!stage.isMaximized());
        setResizeCursor(!stage.isMaximized());
    }

    private void setResizeCursor(boolean resizable) {
        if (resizable) {
            lPane.setCursor(Cursor.H_RESIZE);
            lbPane.setCursor(Cursor.NE_RESIZE);
            bPane.setCursor(Cursor.V_RESIZE);
            rbPane.setCursor(Cursor.SE_RESIZE);
            rPane.setCursor(Cursor.H_RESIZE);
            rtPane.setCursor(Cursor.NE_RESIZE);
            tPane.setCursor(Cursor.V_RESIZE);
            ltPane.setCursor(Cursor.SE_RESIZE);
        } else {
            lPane.setCursor(Cursor.DEFAULT);
            lbPane.setCursor(Cursor.DEFAULT);
            bPane.setCursor(Cursor.DEFAULT);
            rbPane.setCursor(Cursor.DEFAULT);
            rPane.setCursor(Cursor.DEFAULT);
            rtPane.setCursor(Cursor.DEFAULT);
            tPane.setCursor(Cursor.DEFAULT);
            ltPane.setCursor(Cursor.DEFAULT);
        }
    }

    private void onButtonMinimizeClick() {
        stage.setIconified(true);
    }

}