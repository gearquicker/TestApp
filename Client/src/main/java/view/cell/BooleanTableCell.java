package view.cell;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import model.Contract;

public class BooleanTableCell extends TableCell<Contract, String> {
    private CheckBox checkBox;

    public BooleanTableCell() {
        checkBox = new CheckBox();
        checkBox.setDisable(true);
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);
    }


    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
            this.setGraphic(checkBox);
            checkBox.setSelected(Boolean.parseBoolean(item));
        }else{
            this.setGraphic(null);
        }
    }
}