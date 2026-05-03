# JavaFX Application

A Java-based desktop application built using JavaFX for the user interface.

## Prerequisites

Before running the application, ensure you have the following installed:

*   **OpenJDK 25:** [Download from Microsoft](https://learn.microsoft.com/en-us/java/openjdk/download)
*   **JavaFX SDK 25:** [Download from java.net](https://jdk.java.net/javafx25/)
*   **IntelliJ IDEA:** [Download here](https://www.jetbrains.com/idea/download/?section=windows)

## Getting Started

1.  **Open Project:** Load the project folder in IntelliJ IDEA.
2.  **Sync Maven:** Upon the first load, ensure you sync the Maven projects to download all necessary dependencies.
3.  **Configure Run Options:**
    *   Go to **Run > Edit Configurations**.
    *   Select your Application configuration (or create a new one pointing to `com.halls.ui.MainApp`).
    *   If the **VM Options** field is not visible, click **Modify options** (or press `Alt+M`) and select **Add VM options** from the dropdown.
    *   In the **VM Options** field, paste the following command (adjust the path if your SDK is in a different location):
    ```bash
    --module-path "C:\Program Files\javafx\javafx-sdk-25.0.2\lib" --add-modules javafx.controls,javafx.fxml
    ```

## Running the Application

The entry point for the application is:
`com.halls.ui.MainApp`

Simply right-click the `MainApp` class and select **Run**.
