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

/**
 * Lớp Room đại diện cho một phòng trong hệ thống quản lý mầm non.
 * Chứa thông tin về ID phòng, số phòng, trạng thái và sức chứa.
 * @author Nguyễn Huy Long
 */
public class Room {

    private int roomId;          // ID của phòng
    private String roomNumber;   // Số phòng
    private int status;          // Trạng thái phòng (0: không hoạt động, 1: hoạt động)
    private int capacity;        // Sức chứa của phòng

    // Constructor không tham số
    public Room() {
    }

    /**
     * Constructor với tất cả các thuộc tính.
     *
     * @param roomId ID của phòng
     * @param roomNumber Số phòng
     * @param status Trạng thái phòng
     * @param capacity Sức chứa của phòng
     */
    public Room(int roomId, String roomNumber, int status, int capacity) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.status = status;
        this.capacity = capacity;
    }
    public Room( String roomNumber, int status, int capacity) {
        this.roomNumber = roomNumber;
        this.status = status;
        this.capacity = capacity;
    }


    // Phương thức getter và setter cho các thuộc tính

    public int getRoomId() {
        return roomId; // Trả về ID của phòng
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId; // Cập nhật ID của phòng
    }

    public String getRoomNumber() {
        return roomNumber; // Trả về số phòng
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber; // Cập nhật số phòng
    }

    public int getStatus() {
        return status; // Trả về trạng thái của phòng
    }

    public void setStatus(int status) {
        this.status = status; // Cập nhật trạng thái của phòng
    }

    public int getCapacity() {
        return capacity; // Trả về sức chứa của phòng
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity; // Cập nhật sức chứa của phòng
    }
}
