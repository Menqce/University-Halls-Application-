package com.halls.model;

import java.time.LocalDate;

/**
 * Represents a university employee who lives in halls.
 * Extends Person with job-related details.
 *
 * @author Your Name
 * @version 1.0
 */
public class Employee extends Person {

    private String jobTitle;
    private String department;
    private double salary;
    private boolean livesInHalls;
    private String assignedHall;
    private String roomNumber;

    /**
     * Constructor for Employee.
     *
     * @param id               unique ID
     * @param name             full name
     * @param gender           gender
     * @param dateOfBirth      date of birth
     * @param address          home address
     * @param nationality      nationality
     * @param healthConditions health conditions
     * @param dateRegistered   date added to system
     * @param jobTitle         job title
     * @param department       department
     * @param salary           annual salary
     * @param livesInHalls     true if employee lives in halls
     */
    public Employee(String id, String name, String gender,
                    LocalDate dateOfBirth, String address,
                    String nationality, String healthConditions,
                    LocalDate dateRegistered, String jobTitle,
                    String department, double salary,
                    boolean livesInHalls) {

        super(id, name, gender, dateOfBirth, address,
                nationality, healthConditions, dateRegistered);

        this.jobTitle = jobTitle;
        this.department = department;
        this.salary = salary;
        this.livesInHalls = livesInHalls;
        this.assignedHall = "";
        this.roomNumber = "";
    }

    // Getters
    public String getJobTitle()         { return jobTitle; }
    public String getDepartment()       { return department; }
    public double getSalary()           { return salary; }
    public boolean isLivesInHalls()     { return livesInHalls; }
    public String getAssignedHall()     { return assignedHall; }
    public String getRoomNumber()       { return roomNumber; }

    // Setters
    public void setJobTitle(String j)           { this.jobTitle = j; }
    public void setDepartment(String d)         { this.department = d; }
    public void setSalary(double s)             { this.salary = s; }
    public void setLivesInHalls(boolean b)      { this.livesInHalls = b; }
    public void setAssignedHall(String h)       { this.assignedHall = h; }
    public void setRoomNumber(String r)         { this.roomNumber = r; }

    /**
     * Returns a formatted summary of all employee details.
     * @return multi-line summary string
     */
    @Override
    public String getSummary() {
        return "-- EMPLOYEE RECORD --\n"
                + "ID:            " + getId() + "\n"
                + "Name:          " + getName() + "\n"
                + "Gender:        " + getGender() + "\n"
                + "Date of Birth: " + getDateOfBirth() + "\n"
                + "Age:           " + getAge() + "\n"
                + "Address:       " + getAddress() + "\n"
                + "Nationality:   " + getNationality() + "\n"
                + "Health:        " + getHealthConditions() + "\n"
                + "Registered:    " + getDateRegistered() + "\n"
                + "Job Title:     " + jobTitle + "\n"
                + "Department:    " + department + "\n"
                + "Salary:        £" + String.format("%.2f", salary) + "\n"
                + "Lives in Halls:" + (livesInHalls ? "Yes" : "No") + "\n"
                + "Hall:          " + assignedHall + "\n"
                + "Room:          " + roomNumber;
    }

    /**
     * Converts employee data to a pipe-separated line for file storage.
     * @return CSV string starting with EMPLOYEE
     */
    @Override
    public String toCsv() {
        return "EMPLOYEE|" + getId() + "|" + getName() + "|" + getGender()
                + "|" + getDateOfBirth() + "|" + getAddress()
                + "|" + getNationality() + "|" + getHealthConditions()
                + "|" + getDateRegistered() + "|" + jobTitle
                + "|" + department + "|" + salary + "|" + livesInHalls
                + "|" + assignedHall + "|" + roomNumber;
    }
}