package com.halls.ui;

import com.halls.store.Store;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Main entry point for the Halls Administration System.
 * Sets up the JavaFX window with tabs for each feature.
 *
 * @author Your Name
 * @version 1.0
 */
public class MainApp extends Application {

    /** Shared data store used by all tabs */
    public static Store store = new Store();

    /**
     * Builds and shows the main application window.
     *
     * @param stage the primary JavaFX stage
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("University Halls Administration System");

        TabPane tabPane = new TabPane();

        // Create all tabs
        Tab studentTab  = new Tab("Add Student",   new StudentForm(store));
        Tab employeeTab = new Tab("Add Employee",  new EmployeeForm(store));
        Tab browseTab   = new Tab("Browse",        new BrowsePane(store));
        Tab searchTab   = new Tab("Search",        new SearchPane(store));
        Tab hallsTab    = new Tab("Halls",         new HallsPane(store));
        Tab fileTab     = new Tab("Save / Load",   new FilePane(store));

        //Added to automatically show next student and update record numbers when tab is selected
        browseTab.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                ((BrowsePane) browseTab.getContent()).showNext();
            }
        });

        // Prevent tabs from being closed
        for (Tab t : new Tab[]{studentTab, employeeTab,
                browseTab, searchTab,
                hallsTab, fileTab}) {
            t.setClosable(false);
        }

        tabPane.getTabs().addAll(
                studentTab, employeeTab, browseTab,
                searchTab, hallsTab, fileTab
        );

        Scene scene = new Scene(tabPane, 820, 640);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the JavaFX application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}