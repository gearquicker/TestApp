package utils;

import helper.InterfaceHelper;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Constants;

import java.io.IOException;

public class BackgroundTask {

    private final int WAIT_PANE_PING = 200;
    private boolean showWaitPane = true;
    protected boolean success;

    public static void DBTask(Scene scene, Task runner, Task finisher) {
        new BackgroundTask(scene, Constants.HTTP_ERROR, runner, finisher) {};
    }

    private BackgroundTask(Scene scene, String errorMessage, Task runner, Task finisher) {
        Runnable pingRunnable = () -> {
            try {
                Thread.sleep(WAIT_PANE_PING);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                if (isShowWaitPane()) {
                    setShowWaitPaneFalse();
                    InterfaceHelper.showWaitPane(scene);
                }
            });
        };
        Thread pingThread = new Thread(pingRunnable);
        pingThread.setDaemon(true);
        pingThread.start();

        Runnable mainRunnable = () -> {
            try {
                runner.run();
                success = true;
            } catch (Exception ignore) {
                success = false;
            }
            Platform.runLater(() -> {
                if (success) {
                    try {
                        finisher.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    InterfaceHelper.showToast((Stage) scene.getWindow(), errorMessage);
                }
                if (isShowWaitPane()) {
                    setShowWaitPaneFalse();
                } else {
                    InterfaceHelper.hideWaitPane(scene);
                }
            });
        };
        Thread mainThread = new Thread(mainRunnable);
        mainThread.setDaemon(true);
        mainThread.start();
    }

    private synchronized boolean isShowWaitPane() {
        return showWaitPane;
    }

    private synchronized void setShowWaitPaneFalse() {
        showWaitPane = false;
    }

    public interface Task {
        void run() throws IOException;
    }

}
