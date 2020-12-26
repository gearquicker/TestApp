package view.common;

import annotations.Column;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import utils.BackgroundTask;

public abstract class TableModule<T> extends SimpleModule {

    @FXML
    protected TableView<T> table;

    protected Class<T> tableClass;
    protected ObservableList<T> data;

    public TableModule(Class<T> tableClass) {
        super("/fxml/TableModule.fxml");
        this.tableClass = tableClass;
        Platform.runLater(() -> BackgroundTask.DBTask(getScene(), this::loadData, this::init));
    }

    public TableModule(Class<T> tableClass, String fxml) {
        super(fxml);
        this.tableClass = tableClass;
        Platform.runLater(() -> BackgroundTask.DBTask(getScene(), this::loadData, this::init));
    }

    protected void init() {
        initColumns();
    }

    protected TableColumn<T, String> createColumn(String name, ValueFactory<T> valueFactory, Callback<TableColumn<T, String>, TableCell<T, String>> callback, CommitEndAction<T> action) {
        TableColumn<T, String> column = new TableColumn<>();
        column.setCellValueFactory(param -> Bindings.createObjectBinding(() -> valueFactory.callback(param)));
        if (callback != null) column.setCellFactory(callback);
        if (action != null) column.setOnEditCommit(action::commit);
        column.setText(getColumnName(name));
        column.setSortable(false);
        return column;
    }

    private String getColumnName(String key) {
        try {
            return tableClass.getDeclaredField(key).getAnnotation(Column.class).name();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected interface ValueFactory<T> {
        String callback(TableColumn.CellDataFeatures<T, String> param);
    }

    protected interface CommitEndAction<T> {
        void commit(TableColumn.CellEditEvent<T, String> event);
    }

    protected void loadData() {

    }

    protected abstract void initColumns();
}
