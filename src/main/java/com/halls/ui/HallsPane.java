package com.halls.ui;

import com.halls.model.Hall;
import com.halls.store.Store;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Displays a summary of all halls showing capacity and occupancy.
 *
 * @author Your Name
 * @version 1.0
 */
public class HallsPane extends VBox {

    private final Store store;
    private TextArea taHalls;

    /**
     * Creates the halls overview panel.
     * @param store the shared data store
     */
    public HallsPane(Store store) {
        this.store = store;
        setSpacing(12);
        setPadding(new Insets(20));
        buildUI();
    }

    private void buildUI() {
        Text heading = new Text("Halls Overview");
        heading.getStyleClass().add("heading");

        taHalls = new TextArea();
        taHalls.setEditable(false);
        taHalls.setPrefHeight(420);
        taHalls.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");

        Button btnRefresh = new Button("Refresh");
        btnRefresh.getStyleClass().add("btn-secondary");
        btnRefresh.setOnAction(e -> refresh());

        getChildren().addAll(heading, taHalls, btnRefresh);
        refresh();
    }

    /** Reloads the halls summary from the store. */
    public void refresh() {
        StringBuilder sb = new StringBuilder();
        String line = "-".repeat(55) + "\n";

        sb.append(line);
        sb.append(String.format("  %-16s  %-12s  %5s  %5s%n",
                "Hall", "Diet", "Total", "Free"));
        sb.append(line);

        for (Hall h : store.getHalls()) {
            sb.append(String.format("  %-16s  %-12s  %5d  %5d%n",
                    h.getName(),
                    h.getDietarySpeciality(),
                    h.getTotalRooms(),
                    h.getAvailableRooms()));
        }
        sb.append(line);

        taHalls.setText(sb.toString());
    }
}