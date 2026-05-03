package com.halls.ui;

import com.halls.model.Person;
import com.halls.model.Student;
import com.halls.store.Store;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Allows the user to step through all records one at a time.
 * Also lets the user mark a student's rent as paid.
 *
 * @author Your Name
 * @version 1.0
 */
public class BrowsePane extends VBox {

    private final Store store;
    private TextArea taRecord;
    private Label lblCounter;

    /**
     * Creates the browse panel.
     * @param store the shared data store
     */
    public BrowsePane(Store store) {
        this.store = store;
        setSpacing(12);
        setPadding(new Insets(20));
        buildUI();
    }

    private void buildUI() {
        Text heading = new Text("Browse Records");
        heading.getStyleClass().add("heading");

        lblCounter = new Label("No records yet.");

        taRecord = new TextArea();
        taRecord.setEditable(false);
        taRecord.setPrefHeight(320);
        taRecord.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");

        // Navigation buttons
        Button btnNext  = new Button("Next Record");
        Button btnReset = new Button("Reset to Start");
        btnNext.getStyleClass().add("btn-primary");
        btnReset.getStyleClass().add("btn-secondary");

        btnNext.setOnAction(e -> showNext());
        btnReset.setOnAction(e -> {
            store.resetIndex();
            taRecord.clear();
            updateCounter();
        });

        // Rent paid button
        Button btnRentPaid = new Button("Mark Rent as Paid");
        btnRentPaid.getStyleClass().add("btn-primary");
        btnRentPaid.setOnAction(e -> markRentPaid());

        HBox buttons = new HBox(10, btnNext, btnReset, btnRentPaid);
        buttons.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(heading, lblCounter, taRecord, buttons);
    }

    /** Shows the next record, wrapping at the end of the list. */
    public void showNext() {
        Person p = store.getNextPerson();
        if (p == null) {
            taRecord.setText("No records stored yet.");
        } else {
            taRecord.setText(p.getSummary());
            updateCounter();
        }
    }

    /**
     * Marks the currently displayed student's rent as paid,
     * then refreshes the display.
     */
    private void markRentPaid() {
        int index = store.getCurrentIndex();
        if (index < 0 || index >= store.getPeople().size()) {
            taRecord.setText("Please browse to a record first.");
            return;
        }

        Person p = store.getPeople().get(index);
        if (p instanceof Student) {
            ((Student) p).setRentPaid(true);
            taRecord.setText(p.getSummary());
        } else {
            taRecord.appendText("\n\n(Rent tracking only applies to students.)");
        }
    }

    /** Updates the counter label. */
    private void updateCounter() {
        int total = store.getCount();
        int pos   = store.getCurrentIndex() + 1;
        lblCounter.setText("Record " + pos + " of " + total);
    }
}