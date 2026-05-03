package com.halls.ui;

import com.halls.model.Employee;
import com.halls.store.Store;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalDate;

/**
 * Form for adding a new employee record to the system.
 *
 * @author Your Name
 * @version 1.0
 */
public class EmployeeForm extends ScrollPane {

    private final Store store;

    private TextField tfName, tfDob, tfAddress,
            tfNationality, tfHealth,
            tfJobTitle, tfDept, tfSalary;
    private ComboBox<String> cbGender;
    private CheckBox chkLives;
    private Label lblStatus;

    /**
     * Creates the employee entry form.
     * @param store the shared data store
     */
    public EmployeeForm(Store store) {
        this.store = store;
        buildUI();
        setFitToWidth(true);
    }

    private void buildUI() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(20));

        Text heading = new Text("Add New Employee");
        heading.getStyleClass().add("heading");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        grid.add(label("Full Name:"), 0, 0);
        tfName = field("e.g. Dr. Alan Jones");
        grid.add(tfName, 1, 0);

        grid.add(label("Gender:"), 0, 1);
        cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female", "Other");
        cbGender.setValue("Male");
        grid.add(cbGender, 1, 1);

        grid.add(label("Date of Birth:"), 0, 2);
        tfDob = field("YYYY-MM-DD");
        grid.add(tfDob, 1, 2);

        grid.add(label("Home Address:"), 0, 3);
        tfAddress = field("e.g. 5 Park Lane");
        grid.add(tfAddress, 1, 3);

        grid.add(label("Nationality:"), 0, 4);
        tfNationality = field("e.g. British");
        grid.add(tfNationality, 1, 4);

        grid.add(label("Health Conditions:"), 0, 5);
        tfHealth = field("e.g. None");
        grid.add(tfHealth, 1, 5);

        grid.add(label("Job Title:"), 0, 6);
        tfJobTitle = field("e.g. Hall Warden");
        grid.add(tfJobTitle, 1, 6);

        grid.add(label("Department:"), 0, 7);
        tfDept = field("e.g. Student Services");
        grid.add(tfDept, 1, 7);

        grid.add(label("Annual Salary (£):"), 0, 8);
        tfSalary = field("e.g. 28000");
        grid.add(tfSalary, 1, 8);

        chkLives = new CheckBox("Lives in halls");
        grid.add(chkLives, 1, 9);

        Button btnSave  = new Button("Save Employee");
        Button btnClear = new Button("Clear");
        btnSave.getStyleClass().add("btn-primary");
        btnClear.getStyleClass().add("btn-secondary");
        btnSave.setOnAction(e -> saveEmployee());
        btnClear.setOnAction(e -> clearForm());
        HBox buttons = new HBox(10, btnSave, btnClear);
        buttons.setAlignment(Pos.CENTER_LEFT);

        lblStatus = new Label();

        root.getChildren().addAll(heading, grid, buttons, lblStatus);
        setContent(root);
    }

    /** Validates fields and saves a new Employee to the store. */
    private void saveEmployee() {
        lblStatus.getStyleClass().removeAll("ok", "error");
        try {
            String name = tfName.getText().trim();
            if (name.isEmpty())
                throw new IllegalArgumentException("Name cannot be empty.");

            LocalDate dob;
            try {
                dob = LocalDate.parse(tfDob.getText().trim());
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Date of birth must be YYYY-MM-DD format.");
            }
            if (dob.getYear() < 1900 || dob.getYear() > 2006)
                throw new IllegalArgumentException("Please enter a valid birth year.");

            String address = tfAddress.getText().trim();
            if (address.isEmpty())
                throw new IllegalArgumentException("Address cannot be empty.");

            String nationality = tfNationality.getText().trim();
            if (nationality.isEmpty())
                throw new IllegalArgumentException("Nationality cannot be empty.");

            String jobTitle = tfJobTitle.getText().trim();
            if (jobTitle.isEmpty())
                throw new IllegalArgumentException("Job title cannot be empty.");

            String dept = tfDept.getText().trim();
            if (dept.isEmpty())
                throw new IllegalArgumentException("Department cannot be empty.");

            double salary;
            try {
                salary = Double.parseDouble(tfSalary.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Salary must be a number.");
            }
            if (salary < 0)
                throw new IllegalArgumentException("Salary cannot be negative.");

            String id = "E" + (System.currentTimeMillis() % 100000);

            Employee emp = new Employee(
                    id, name, cbGender.getValue(), dob,
                    address, nationality,
                    tfHealth.getText().trim(),
                    LocalDate.now(), jobTitle, dept,
                    salary, chkLives.isSelected()
            );

            store.addPerson(emp);
            lblStatus.getStyleClass().add("ok");
            lblStatus.setText("Employee saved! ID: " + id);
            clearForm();

        } catch (IllegalArgumentException ex) {
            lblStatus.getStyleClass().add("error");
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    private void clearForm() {
        tfName.clear(); tfDob.clear(); tfAddress.clear();
        tfNationality.clear(); tfHealth.clear();
        tfJobTitle.clear(); tfDept.clear(); tfSalary.clear();
        cbGender.setValue("Male");
        chkLives.setSelected(false);
    }

    private Label label(String text) {
        Label l = new Label(text);
        l.setMinWidth(160);
        return l;
    }

    private TextField field(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(260);
        return tf;
    }
}