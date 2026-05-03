package com.halls.ui;

import com.halls.model.Person;
import com.halls.store.Store;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Lets the user search for a person by name.
 * Displays all matching records in a text area.
 *
 * @author Your Name
 * @version 1.0
 */
public class SearchPane extends VBox {

    private final Store store;
    private TextField tfSearch;
    private TextArea taResults;

    /**
     * Creates the search panel.
     * @param store the shared data store
     */
    public SearchPane(Store store) {
        this.store = store;
        setSpacing(12);
        setPadding(new Insets(20));
        buildUI();
    }

    private void buildUI() {
        Text heading = new Text("Search by Name");
        heading.getStyleClass().add("heading");

        HBox searchRow = new HBox(10);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        tfSearch = new TextField();
        tfSearch.setPromptText("Enter a name...");
        tfSearch.setPrefWidth(260);

        Button btnSearch = new Button("Search");
        btnSearch.getStyleClass().add("btn-primary");
        btnSearch.setOnAction(e -> doSearch());

        // Also search when Enter is pressed in the text field
        tfSearch.setOnAction(e -> doSearch());

        searchRow.getChildren().addAll(
                new Label("Name:"), tfSearch, btnSearch
        );

        taResults = new TextArea();
        taResults.setEditable(false);
        taResults.setPrefHeight(380);
        taResults.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");
        taResults.setPromptText("Results will appear here...");

        getChildren().addAll(heading, searchRow, taResults);
    }

    /** Searches the store and displays all matching records. */
    private void doSearch() {
        String query = tfSearch.getText().trim();

        if (query.isEmpty()) {
            taResults.setText("Please enter a name to search.");
            return;
        }

        List<Person> results = store.searchByName(query);

        if (results.isEmpty()) {
            taResults.setText("No records found matching: \"" + query + "\"");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(results.size()).append(" result(s) found:\n\n");

        for (Person p : results) {
            sb.append(p.getSummary());
            sb.append("\n\n-----------------------------\n\n");
        }

        taResults.setText(sb.toString());
    }
}