package com.halls.model;

/**
 * Represents a hall of residence.
 * Each hall has a name, capacity, and a dietary speciality.
 *
 * @author Your Name
 * @version 1.0
 */
public class Hall {

    private String name;
    private int totalRooms;
    private int groundFloorRooms;
    private String dietarySpeciality; // e.g. "None", "Vegetarian", "Vegan"
    private int occupiedRooms;

    /**
     * Constructor for Hall.
     *
     * @param name               hall name
     * @param totalRooms         total number of rooms
     * @param groundFloorRooms   number of ground floor rooms
     * @param dietarySpeciality  dietary speciality of this hall
     */
    public Hall(String name, int totalRooms,
                int groundFloorRooms, String dietarySpeciality) {
        this.name = name;
        this.totalRooms = totalRooms;
        this.groundFloorRooms = groundFloorRooms;
        this.dietarySpeciality = dietarySpeciality;
        this.occupiedRooms = 0;
    }

    // Getters
    public String getName()               { return name; }
    public int getTotalRooms()            { return totalRooms; }
    public int getGroundFloorRooms()      { return groundFloorRooms; }
    public String getDietarySpeciality()  { return dietarySpeciality; }
    public int getOccupiedRooms()         { return occupiedRooms; }

    // Setters
    public void setOccupiedRooms(int n)   { this.occupiedRooms = n; }

    /**
     * Returns the number of rooms still available.
     * @return available rooms
     */
    public int getAvailableRooms() {
        return totalRooms - occupiedRooms;
    }

    /**
     * Returns true if the hall has at least one free room.
     * @return true if space available
     */
    public boolean hasSpace() {
        return occupiedRooms < totalRooms;
    }

    /**
     * Adds one resident to this hall and returns their room number.
     * Ground floor rooms are allocated first if needed.
     *
     * @param needsGroundFloor true if a ground floor room is required
     * @return assigned room number as a string
     */
    public String allocateRoom(boolean needsGroundFloor) {
        occupiedRooms++;
        if (needsGroundFloor) {
            return "G" + String.format("%02d", occupiedRooms);
        }
        return "F" + String.format("%02d", occupiedRooms);
    }

    @Override
    public String toString() {
        return name + " (" + getAvailableRooms() + " rooms free)";
    }
}