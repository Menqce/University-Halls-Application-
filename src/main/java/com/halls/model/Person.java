package com.halls.model;

import java.time.LocalDate;

/**
 * Abstract base class for all people in the halls system.
 * Contains common personal details shared by students and employees.
 *
 * @author Your Name
 * @version 1.0
 */
public abstract class Person {

    private String id;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;
    private String nationality;
    private String healthConditions;
    private LocalDate dateRegistered;

    /**
     * Constructor for Person.
     *
     * @param id               unique ID
     * @param name             full name
     * @param gender           gender
     * @param dateOfBirth      date of birth
     * @param address          home address
     * @param nationality      nationality
     * @param healthConditions any health conditions
     * @param dateRegistered   date added to system
     */
    public Person(String id, String name, String gender,
                  LocalDate dateOfBirth, String address,
                  String nationality, String healthConditions,
                  LocalDate dateRegistered) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.nationality = nationality;
        this.healthConditions = healthConditions;
        this.dateRegistered = dateRegistered;
    }

    // Getters
    public String getId()                  { return id; }
    public String getName()                { return name; }
    public String getGender()              { return gender; }
    public LocalDate getDateOfBirth()      { return dateOfBirth; }
    public String getAddress()             { return address; }
    public String getNationality()         { return nationality; }
    public String getHealthConditions()    { return healthConditions; }
    public LocalDate getDateRegistered()   { return dateRegistered; }

    // Setters
    public void setId(String id)                            { this.id = id; }
    public void setName(String name)                        { this.name = name; }
    public void setGender(String gender)                    { this.gender = gender; }
    public void setDateOfBirth(LocalDate d)                 { this.dateOfBirth = d; }
    public void setAddress(String address)                  { this.address = address; }
    public void setNationality(String nationality)          { this.nationality = nationality; }
    public void setHealthConditions(String h)               { this.healthConditions = h; }
    public void setDateRegistered(LocalDate dateRegistered) { this.dateRegistered = dateRegistered; }

    /**
     * Calculates age from date of birth.
     * @return age in years
     */
    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    /**
     * Each subclass returns a readable summary of its data.
     * @return formatted summary string
     */
    public abstract String getSummary();

    /**
     * Each subclass returns a CSV line for saving to file.
     * @return CSV string
     */
    public abstract String toCsv();

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}