/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                              DESCRIPTION
 * 10/10/2024       1.1              Nguyễn Huy Long - He160140              Create class Room
 * 10/12/2024       1.2              Nguyễn Huy Long - He160140              Update class Room
 */
package org.example.kindergarten_management_system_g4.model;

public class Room {

    private int roomId;
    private String roomNumber;
    private int status;
    private int capacity;

    public Room() {
    }

    public Room(int roomId, String roomNumber, int status, int capacity) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.status = status;
        this.capacity = capacity;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
