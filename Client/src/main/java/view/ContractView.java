package view;

import helper.DataHelper;
import helper.InterfaceHelper;
import http.RequestController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import model.Contract;
import utils.BackgroundTask;
import view.cell.BooleanTableCell;
import view.common.ReferenceModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContractView extends ReferenceModule<Contract> {

    public ContractView() {
        super(Contract.class);
        initToolbar();
        Platform.runLater(this::loadItems);
    }

    private void initToolbar() {
        Button btnAdd = InterfaceHelper.setupToolbarButton("plus-button", 16);
        Button btnDelete = InterfaceHelper.setupToolbarButton("delete-button", 16);
        btnAdd.setOnAction(event -> addItem());
        btnDelete.setOnAction(event -> deleteItem());
        toolbar.getChildren().addAll(btnAdd, btnDelete);
    }

    @Override
    protected boolean filter(Contract contract, String newValue) {
        if (newValue == null || newValue.isEmpty()) {
            return true;
        }
        newValue = newValue.toLowerCase();
        if (InterfaceHelper.formatLongToDate(contract.getDate()).contains(newValue)) {
            return true;
        } else if (InterfaceHelper.formatLongToDate(contract.getChangeDate()).contains(newValue)) {
            return true;
        } else return contract.getData().toLowerCase().contains(newValue);
    }

    @Override
    protected void initColumns() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        table.getColumns().add(createColumn("isActual",
                param -> String.valueOf(param.getValue().isActual()),
                callback -> new BooleanTableCell(),
                null));
        table.getColumns().add(createColumn("date",
                param -> InterfaceHelper.formatLongToDate(param.getValue().getDate()),
                null,
                null));
        table.getColumns().add(createColumn("changeDate",
                param -> InterfaceHelper.formatLongToDate(param.getValue().getChangeDate()),
                null,
                null));
        table.getColumns().add(createColumn("data",
                param -> param.getValue().getData(),
                null,
                null));
    }

    private void loadItems() {
        List<List<Contract>> contracts = new ArrayList<>();
        BackgroundTask.DBTask(getScene(),
                () -> contracts.add(RequestController.getAll()),
                () -> setTableRows(contracts.get(0)));
    }

    private void addItem() {
        Contract contract = DataHelper.createRandomContract();
        BackgroundTask.DBTask(getScene(),
                () -> RequestController.create(contract),
                () -> addTableRow(contract));
    }

    private void deleteItem() {
        Contract contract = table.getSelectionModel().getSelectedItem();
        BackgroundTask.DBTask(getScene(),
                () -> RequestController.delete(contract),
                () -> removeTableRow(contract));
    }

    private void setTableRows(List<Contract> contracts) {
        DataHelper.checkActuality(contracts);
        data = FXCollections.observableList(contracts);
        data.sort(Comparator.comparing(Contract::getDate));
        initFilter();
    }

    private void addTableRow(Contract contract) {
        DataHelper.checkActuality(Collections.singletonList(contract));
        data.add(contract);
        data.sort(Comparator.comparing(Contract::getDate));
        initFilter();
    }

    private void removeTableRow(Contract contract) {
        data.remove(contract);
        data.sort(Comparator.comparing(Contract::getDate));
        initFilter();
    }

}
