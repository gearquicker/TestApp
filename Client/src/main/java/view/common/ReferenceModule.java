package view.common;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public abstract class ReferenceModule<T> extends TableModule<T> {

    @FXML
    protected HBox toolbar;
    @FXML
    protected TextField tfSearch;

    public ReferenceModule(Class<T> tableClass) {
        super(tableClass, "/fxml/ReferenceModule.fxml");
    }


    protected void initFilter() {
        FilteredList<T> filtered = new FilteredList<>(data, p -> true);
        SortedList<T> sortedData = new SortedList<>(filtered);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(t -> filter(t, newValue)));
    }


    protected abstract boolean filter(T t, String newValue);

}
