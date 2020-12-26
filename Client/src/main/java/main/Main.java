package main;

import helper.InterfaceHelper;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ContractView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        InterfaceHelper.openWindow(new ContractView(), Constants.APP_NAME, null, null);
    }

    public static void main(String[] args) {
        launch(args);
    }

}