package com.halls.ui;

import com.halls.model.Student;
import com.halls.store.Store;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalDate;

/**
 * Form for adding a new student record to the system.
 * Includes input validation and automatic hall assignment.
 *
 * @author Your Name
 * @version 1.0
 */
public class StudentForm extends ScrollPane {

    private final Store store;

    // Form fields
    private TextField tfName, tfDob, tfAddress,
            tfNationality, tfHealth,
            tfCourse, tfYear, tfRent;
    private ComboBox<String> cbGender, cbDiet;
    private CheckBox chkGround, chkSenior;
    private Label lblStatus;

    /**
     * Creates the student entry form.
     * @param store the shared data store
     */
    public StudentForm(Store store) {
        this.store = store;
        buildUI();
        setFitToWidth(true);
    }

    /** Lays out all form components. */
    private void buildUI() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(20));

        Text heading = new Text("Add New Student");
        heading.getStyleClass().add("heading");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);

        // Row 0 – Name
        grid.add(label("Full Name:"), 0, 0);
        tfName = field("e.g. Jane Smith");
        grid.add(tfName, 1, 0);

        // Row 1 – Gender
        grid.add(label("Gender:"), 0, 1);
        cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female", "Other");
        cbGender.setValue("Male");
        grid.add(cbGender, 1, 1);

        // Row 2 – DOB
        grid.add(label("Date of Birth:"), 0, 2);
        tfDob = field("YYYY-MM-DD");
        grid.add(tfDob, 1, 2);

        // Row 3 – Address
        grid.add(label("Home Address:"), 0, 3);
        tfAddress = field("e.g. 12 High Street, London");
        grid.add(tfAddress, 1, 3);

        // Row 4 – Nationality
        grid.add(label("Nationality:"), 0, 4);
        tfNationality = field("e.g. British");
        grid.add(tfNationality, 1, 4);

        // Row 5 – Health
        grid.add(label("Health Conditions:"), 0, 5);
        tfHealth = field("e.g. None");
        grid.add(tfHealth, 1, 5);

        // Row 6 – Course
        grid.add(label("Course:"), 0, 6);
        tfCourse = field("e.g. Computer Science");
        grid.add(tfCourse, 1, 6);

        // Row 7 – Year
        grid.add(label("Year of Study:"), 0, 7);
        tfYear = field("1 to 4");
        grid.add(tfYear, 1, 7);

        // Row 8 – Diet
        grid.add(label("Dietary Requirement:"), 0, 8);
        cbDiet = new ComboBox<>();
        cbDiet.getItems().addAll("None", "Vegetarian", "Vegan", "Halal");
        cbDiet.setValue("None");
        grid.add(cbDiet, 1, 8);

        // Row 9 – Rent
        grid.add(label("Weekly Rent (£):"), 0, 9);
        tfRent = field("e.g. 100.00");
        grid.add(tfRent, 1, 9);

        // Row 10 – Checkboxes
        chkGround = new CheckBox("Needs ground floor room");
        chkSenior = new CheckBox("Senior student");
        HBox checks = new HBox(16, chkGround, chkSenior);
        grid.add(checks, 1, 10);

        // Buttons
        Button btnSave  = new Button("Save Student");
        Button btnClear = new Button("Clear");
        btnSave.getStyleClass().add("btn-primary");
        btnClear.getStyleClass().add("btn-secondary");
        btnSave.setOnAction(e -> saveStudent());
        btnClear.setOnAction(e -> clearForm());
        HBox buttons = new HBox(10, btnSave, btnClear);
        buttons.setAlignment(Pos.CENTER_LEFT);

        lblStatus = new Label();

        root.getChildren().addAll(heading, grid, buttons, lblStatus);
        setContent(root);
    }

    /**
     * Reads and validates all form fields, then creates and saves
     * a Student to the store. Shows success or error message.
     */
    private void saveStudent() {
        // Clear previous status
        lblStatus.getStyleClass().removeAll("ok", "error");
        lblStatus.setText("");

        try {
            // Validate name
            String name = tfName.getText().trim();
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty.");
            }

            // Validate date of birth
            String dobStr = tfDob.getText().trim();
            if (dobStr.isEmpty()) {
                throw new IllegalArgumentException("Date of birth cannot be empty.");
            }
            LocalDate dob;
            try {
                dob = LocalDate.parse(dobStr);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Date of birth must be in YYYY-MM-DD format.");
            }
            if (dob.getYear() < 1900 || dob.getYear() > 2009) {
                throw new IllegalArgumentException(
                        "Please enter a valid birth year.");
            }

            // Validate address
            String address = tfAddress.getText().trim();
            if (address.isEmpty()) {
                throw new IllegalArgumentException("Address cannot be empty.");
            }

            // Validate nationality
            String nationality = tfNationality.getText().trim();
            if (nationality.isEmpty()) {
                throw new IllegalArgumentException("Nationality cannot be empty.");
            }

            // Validate course
            String course = tfCourse.getText().trim();
            if (course.isEmpty()) {
                throw new IllegalArgumentException("Course cannot be empty.");
            }

            // Validate year of study
            int year;
            try {
                year = Integer.parseInt(tfYear.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Year of study must be a number.");
            }
            if (year < 1 || year > 8) {
                throw new IllegalArgumentException(
                        "Year of study must be between 1 and 8.");
            }

            // Validate rent
            double rent;
            try {
                rent = Double.parseDouble(tfRent.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Weekly rent must be a number.");
            }
            if (rent < 0) {
                throw new IllegalArgumentException(
                        "Weekly rent cannot be negative.");
            }

            // Generate a simple ID from the timestamp
            String id = "S" + (System.currentTimeMillis() % 100000);

            // Create and save the student
            Student student = new Student(
                    id, name, cbGender.getValue(), dob,
                    address, nationality,
                    tfHealth.getText().trim(),
                    LocalDate.now(), course, year,
                    cbDiet.getValue(),
                    chkGround.isSelected(),
                    chkSenior.isSelected(), rent
            );

            store.addPerson(student);

            lblStatus.getStyleClass().add("ok");
            lblStatus.setText("Student saved! ID: " + id + " Hall: "
                    + student.getAssignedHall()
                    + ", Room: " + student.getRoomNumber());

            clearForm();

        } catch (IllegalArgumentException ex) {
            lblStatus.getStyleClass().add("error");
            lblStatus.setText("Error: " + ex.getMessage());
        }
    }

    /** Resets all form fields to their defaults. */
    private void clearForm() {
        tfName.clear(); tfDob.clear(); tfAddress.clear();
        tfNationality.clear(); tfHealth.clear();
        tfCourse.clear(); tfYear.clear(); tfRent.clear();
        cbGender.setValue("Male");
        cbDiet.setValue("None");
        chkGround.setSelected(false);
        chkSenior.setSelected(false);
    }

    /** Helper to create a right-aligned label. */
    private Label label(String text) {
        Label l = new Label(text);
        l.setMinWidth(160);
        return l;
    }

    /** Helper to create a text field with prompt text. */
    private TextField field(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(260);
        return tf;
    }
}