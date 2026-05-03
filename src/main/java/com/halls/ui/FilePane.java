package com.halls.ui;

import com.halls.store.Store;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Lets the user save all records to a file and load them back.
 *
 * @author Your Name
 * @version 1.0
 */
public class FilePane extends VBox {

    private final Store store;
    private TextField tfPath;
    private Label lblStatus;

    /**
     * Creates the save/load panel.
     * @param store the shared data store
     */
    public FilePane(Store store) {
        this.store = store;
        setSpacing(12);
        setPadding(new Insets(20));
        buildUI();
    }

    private void buildUI() {
        Text heading = new Text("Save and Load Data");
        heading.getStyleClass().add("heading");

        // File path row
        HBox pathRow = new HBox(10);
        pathRow.setAlignment(Pos.CENTER_LEFT);
        tfPath = new TextField("halls_data.csv");
        tfPath.setPrefWidth(300);
        Button btnBrowse = new Button("Browse...");
        btnBrowse.getStyleClass().add("btn-secondary");
        btnBrowse.setOnAction(e -> browseFile());
        pathRow.getChildren().addAll(new Label("File:"), tfPath, btnBrowse);

        // Save and load buttons
        Button btnSave = new Button("Save to File");
        Button btnLoad = new Button("Load from File");
        btnSave.getStyleClass().add("btn-primary");
        btnLoad.getStyleClass().add("btn-primary");
        btnSave.setOnAction(e -> saveData());
        btnLoad.setOnAction(e -> loadData());
        HBox buttons = new HBox(10, btnSave, btnLoad);

        lblStatus = new Label();

        Label info = new Label(
                "Save stores all records to a text file.\n"
                        + "Load replaces current records with those from the file."
        );
        info.setStyle("-fx-text-fill: #888888; -fx-font-size: 12px;");

        getChildren().addAll(heading, info, pathRow, buttons, lblStatus);
    }

    /** Opens a file chooser dialog. */
    private void browseFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose File");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt")
        );

        // Show all common data file types
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Supported Files", "*.txt", "*.csv")
        );
        File f = fc.showSaveDialog(getScene().getWindow());
        if (f != null) tfPath.setText(f.getAbsolutePath());
    }

    /** Saves all records to the chosen file. */
    private void saveData() {
        lblStatus.getStyleClass().removeAll("ok", "error");
        String path = tfPath.getText().trim();
        if (path.isEmpty()) {
            lblStatus.getStyleClass().add("error");
            lblStatus.setText("Please enter a file path.");
            return;
        }
        try {
            store.saveToFile(path);
            lblStatus.getStyleClass().add("ok");
            lblStatus.setText("Saved successfully to: " + path);
        } catch (Exception ex) {
            lblStatus.getStyleClass().add("error");
            lblStatus.setText("Save failed: " + ex.getMessage());
        }
    }

    /** Loads records from the chosen file. */
    private void loadData() {
        lblStatus.getStyleClass().removeAll("ok", "error");
        String path = tfPath.getText().trim();
        if (path.isEmpty()) {
            lblStatus.getStyleClass().add("error");
            lblStatus.setText("Please enter a file path.");
            return;
        }

        // Ask user to confirm before wiping current data
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Load Data");
        alert.setContentText(
                "This will replace all current records. Continue?"
        );
        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                try {
                    store.loadFromFile(path);
                    lblStatus.getStyleClass().add("ok");
                    lblStatus.setText("Loaded " + store.getCount()
                            + " records from: " + path);
                } catch (Exception ex) {
                    lblStatus.getStyleClass().add("error");
                    lblStatus.setText("Load failed: " + ex.getMessage());
                }
            }
        });
    }
}