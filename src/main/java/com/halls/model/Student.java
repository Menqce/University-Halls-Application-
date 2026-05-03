package com.halls.model;

import java.time.LocalDate;

/**
 * Represents a student in university halls.
 * Extends Person with student-specific details like course,
 * dietary needs, and accessibility requirements.
 *
 * @author Your Name
 * @version 1.0
 */
public class Student extends Person {

    private String course;
    private int yearOfStudy;
    private String dietaryRequirement; // None, Vegetarian, Vegan, Halal
    private boolean needsGroundFloor;  // true if student has mobility needs
    private boolean seniorStudent;     // true if senior student (reduced rent)
    private double weeklyRent;
    private boolean rentPaid;
    private String assignedHall;
    private String roomNumber;

    /**
     * Constructor for Student.
     *
     * @param id                 unique ID
     * @param name               full name
     * @param gender             gender
     * @param dateOfBirth        date of birth
     * @param address            home address
     * @param nationality        nationality
     * @param healthConditions   health conditions
     * @param dateRegistered     date added to system
     * @param course             course name
     * @param yearOfStudy        year of study
     * @param dietaryRequirement dietary requirement
     * @param needsGroundFloor   true if ground floor room needed
     * @param seniorStudent      true if senior student
     * @param weeklyRent         weekly rent amount
     */
    public Student(String id, String name, String gender,
                   LocalDate dateOfBirth, String address,
                   String nationality, String healthConditions,
                   LocalDate dateRegistered, String course,
                   int yearOfStudy, String dietaryRequirement,
                   boolean needsGroundFloor, boolean seniorStudent,
                   double weeklyRent) {

        super(id, name, gender, dateOfBirth, address,
                nationality, healthConditions, dateRegistered);

        this.course = course;
        this.yearOfStudy = yearOfStudy;
        this.dietaryRequirement = dietaryRequirement;
        this.needsGroundFloor = needsGroundFloor;
        this.seniorStudent = seniorStudent;
        this.weeklyRent = weeklyRent;
        this.rentPaid = false;
        this.assignedHall = "";
        this.roomNumber = "";
    }

    // Getters
    public String getCourse()               { return course; }
    public int getYearOfStudy()             { return yearOfStudy; }
    public String getDietaryRequirement()   { return dietaryRequirement; }
    public boolean isNeedsGroundFloor()     { return needsGroundFloor; }
    public boolean isSeniorStudent()        { return seniorStudent; }
    public double getWeeklyRent()           { return weeklyRent; }
    public boolean isRentPaid()             { return rentPaid; }
    public String getAssignedHall()         { return assignedHall; }
    public String getRoomNumber()           { return roomNumber; }

    // Setters
    public void setCourse(String course)                       { this.course = course; }
    public void setYearOfStudy(int y)                          { this.yearOfStudy = y; }
    public void setDietaryRequirement(String d)                { this.dietaryRequirement = d; }
    public void setNeedsGroundFloor(boolean b)                 { this.needsGroundFloor = b; }
    public void setSeniorStudent(boolean b)                    { this.seniorStudent = b; }
    public void setWeeklyRent(double r)                        { this.weeklyRent = r; }
    public void setRentPaid(boolean b)                         { this.rentPaid = b; }
    public void setAssignedHall(String h)                      { this.assignedHall = h; }
    public void setRoomNumber(String r)                        { this.roomNumber = r; }

    /**
     * Returns a formatted summary of all student details.
     * @return multi-line summary string
     */
    @Override
    public String getSummary() {
        return "-- STUDENT RECORD --\n"
                + "ID:            " + getId() + "\n"
                + "Name:          " + getName() + "\n"
                + "Gender:        " + getGender() + "\n"
                + "Date of Birth: " + getDateOfBirth() + "\n"
                + "Age:           " + getAge() + "\n"
                + "Address:       " + getAddress() + "\n"
                + "Nationality:   " + getNationality() + "\n"
                + "Health:        " + getHealthConditions() + "\n"
                + "Registered:    " + getDateRegistered() + "\n"
                + "Course:        " + course + "\n"
                + "Year:          " + yearOfStudy + "\n"
                + "Diet:          " + dietaryRequirement + "\n"
                + "Ground Floor:  " + (needsGroundFloor ? "Yes" : "No") + "\n"
                + "Senior:        " + (seniorStudent ? "Yes" : "No") + "\n"
                + "Hall:          " + assignedHall + "\n"
                + "Room:          " + roomNumber + "\n"
                + "Weekly Rent:   £" + String.format("%.2f", weeklyRent) + "\n"
                + "Rent Paid:     " + (rentPaid ? "Yes" : "No");
    }

    /**
     * Converts student data to a CSV line for file storage.
     * @return CSV string starting with STUDENT
     */
    @Override
    public String toCsv() {
        return "STUDENT|" + getId() + "|" + getName() + "|" + getGender()
                + "|" + getDateOfBirth() + "|" + getAddress()
                + "|" + getNationality() + "|" + getHealthConditions()
                + "|" + getDateRegistered() + "|" + course
                + "|" + yearOfStudy + "|" + dietaryRequirement
                + "|" + needsGroundFloor + "|" + seniorStudent
                + "|" + weeklyRent + "|" + rentPaid
                + "|" + assignedHall + "|" + roomNumber;
    }
}