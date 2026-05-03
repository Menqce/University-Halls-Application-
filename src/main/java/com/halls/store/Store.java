package com.halls.store;

import com.halls.model.Employee;
import com.halls.model.Hall;
import com.halls.model.Person;
import com.halls.model.Student;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all people and halls for the application.
 * Also handles saving and loading data to/from a text file.
 *
 * @author Your Name
 * @version 1.0
 */
public class Store {

    /** List of all people (students and employees) */
    private ArrayList<Person> people;

    /** List of all halls */
    private ArrayList<Hall> halls;

    /** Index used to step through records one at a time */
    private int currentIndex;

    /**
     * Creates a new Store and sets up the default halls.
     */
    public Store() {
        people = new ArrayList<>();
        halls = new ArrayList<>();
        currentIndex = -1;
        setUpHalls();
    }

    // ── Hall setup ────────────────────────────────────────────────────────

    /**
     * Creates the five default halls with different dietary specialities.
     * Called once when the Store is first created.
     */
    private void setUpHalls() {
        halls.add(new Hall("Maple Hall",  80, 20, "None"));
        halls.add(new Hall("Cedar Hall",  60, 10, "Vegetarian"));
        halls.add(new Hall("Birch Hall",  40,  8, "Vegan"));
        halls.add(new Hall("Willow Hall", 60, 12, "Halal"));
        halls.add(new Hall("Oak Hall",    50, 10, "None"));
    }

    // ── Adding people ─────────────────────────────────────────────────────

    /**
     * Adds a person to the store.
     * If the person is a student, they are automatically
     * assigned to the best matching hall.
     *
     * @param person the person to add
     */
    public void addPerson(Person person) {
        people.add(person);
        if (person instanceof Student) {
            assignHall((Student) person);
        }
    }

    // ── Hall assignment ───────────────────────────────────────────────────

    /**
     * Assigns a student to the most suitable hall based on:
     * 1. Matching dietary requirement
     * 2. Ground floor availability if needed
     * 3. Whether the hall has space
     *
     * If no perfect match is found, assigns to any hall with space.
     *
     * @param student the student to assign
     */
    public void assignHall(Student student) {
        // First try to find a hall matching diet requirement
        for (Hall hall : halls) {
            if (hall.getDietarySpeciality().equals(student.getDietaryRequirement())
                    && hall.hasSpace()) {
                // Check ground floor is available if needed
                if (!student.isNeedsGroundFloor()
                        || hall.getGroundFloorRooms() > 0) {
                    String room = hall.allocateRoom(student.isNeedsGroundFloor());
                    student.setAssignedHall(hall.getName());
                    student.setRoomNumber(room);
                    return;
                }
            }
        }

        // Fallback: assign to any hall with space
        for (Hall hall : halls) {
            if (hall.hasSpace()) {
                String room = hall.allocateRoom(student.isNeedsGroundFloor());
                student.setAssignedHall(hall.getName());
                student.setRoomNumber(room);
                return;
            }
        }
    }

    // ── Browsing records ──────────────────────────────────────────────────

    /**
     * Returns the next person in the list.
     * Wraps back to the first record after the last one.
     *
     * @return the next Person, or null if no records exist
     */
    public Person getNextPerson() {
        if (people.isEmpty()) return null;
        currentIndex = (currentIndex + 1) % people.size();
        return people.get(currentIndex);
    }

    /** Resets the browse cursor back to the start. */
    public void resetIndex() {
        currentIndex = -1;
    }

    /** @return current browse position (1-based for display) */
    public int getCurrentIndex() { return currentIndex; }

    // ── Search ────────────────────────────────────────────────────────────

    /**
     * Searches for people whose name contains the search term.
     * Case-insensitive.
     *
     * @param name the name to search for
     * @return list of matching people
     */
    public List<Person> searchByName(String name) {
        List<Person> results = new ArrayList<>();
        for (Person p : people) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    // ── Getters ───────────────────────────────────────────────────────────

    /** @return all people in the store */
    public ArrayList<Person> getPeople()  { return people; }

    /** @return all halls */
    public ArrayList<Hall> getHalls()     { return halls; }

    /** @return total number of records */
    public int getCount()                 { return people.size(); }

    // ── Save to file ──────────────────────────────────────────────────────

    /**
     * Saves all people to a plain text file.
     * Each person is written as one pipe-separated line.
     *
     * @param filePath path of the file to write
     * @throws IOException if the file cannot be written
     */
    public void saveToFile(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Person p : people) {
            writer.write(p.toCsv());
            writer.newLine();
        }
        writer.close();
    }

    // ── Load from file ────────────────────────────────────────────────────

    /**
     * Loads people from a text file into the store.
     * Clears existing people first, then reads each line.
     *
     * @param filePath path of the file to read
     * @throws IOException if the file cannot be read
     */
    public void loadFromFile(String filePath) throws IOException {
        people.clear();
        resetIndex();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            //Limit -1 used to prevent discarding of trailing empty fields in csv/text file
            String[] parts = line.split("\\|", -1);

            if (parts[0].equals("STUDENT")) {
                Student s = parseStudent(parts);
                if (s != null) people.add(s);

            } else if (parts[0].equals("EMPLOYEE")) {
                Employee e = parseEmployee(parts);
                if (e != null) people.add(e);
            }
        }
        reader.close();
    }

    // ── Parsers ───────────────────────────────────────────────────────────

    /**
     * Parses a String array of fields into a Student object.
     *
     * @param p array of field values
     * @return Student, or null if parsing fails
     */
    private Student parseStudent(String[] p) {
        try {
            Student s = new Student(
                    p[1], p[2], p[3],
                    LocalDate.parse(p[4]), p[5], p[6], p[7],
                    LocalDate.parse(p[8]),
                    p[9], Integer.parseInt(p[10]),
                    p[11],
                    Boolean.parseBoolean(p[12]),
                    Boolean.parseBoolean(p[13]),
                    Double.parseDouble(p[14])
            );
            s.setRentPaid(Boolean.parseBoolean(p[15]));
            s.setAssignedHall(p[16]);
            s.setRoomNumber(p[17]);
            return s;
        } catch (Exception e) {
            System.out.println("Could not parse student line: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parses a String array of fields into an Employee object.
     *
     * @param p array of field values
     * @return Employee, or null if parsing fails
     */
    private Employee parseEmployee(String[] p) {
        try {
            Employee e = new Employee(
                    p[1], p[2], p[3],
                    LocalDate.parse(p[4]), p[5], p[6], p[7],
                    LocalDate.parse(p[8]),
                    p[9], p[10],
                    Double.parseDouble(p[11]),
                    Boolean.parseBoolean(p[12])
            );
            e.setAssignedHall(p[13]);
            e.setRoomNumber(p[14]);
            return e;
        } catch (Exception e) {
            System.out.println("Could not parse employee line: " + e.getMessage());
            return null;
        }
    }
}