/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                                      DESCRIPTION
 * 10/5/2024       1.1              Nguyễn Huy Long - He160140                   create interface for change Password
 */

package org.example.kindergarten_management_system_g4.dao.profileDAO;

import java.sql.SQLException;

/**
 * lớp Interface cho Chức năng changePassword giúp code rõ ràng, dễ bảo trì
 *
 * @author Nguyễn Huy Long
 */
public interface IChangePassword {

    /**
     * Tìm kiếm địa chỉ email trong cơ sở dữ liệu.
     *
     * @param "email" địa chỉ email cần tìm kiếm.
     * @return trả về email khi tìm thấy trong cơ sở dữ liệu
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu.
     * @throws ClassNotFoundException nếu không tìm thấy lớp cần thiết cho việc kết nối cơ sở dữ liệu.
     */
    String findEmail(String email)  throws SQLException;


    /**
     * Tìm kiếm mật khẩu dựa trên địa chỉ email đã cho.
     *
     * @param email địa chỉ email cần kiểm tra.
     * @return trả về mật khẩu tương ứng với địa chỉ email, hoặc null nếu không tìm thấy.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình truy vấn cơ sở dữ liệu.
     */
    String findPassword(String email) throws SQLException;


    /**
     * Cập nhật mật khẩu mới cho địa chỉ email đã cho.
     *
     * @param email địa chỉ email mà mật khẩu cần được cập nhật.
     * @param NewPassword mật khẩu mới muốn cập nhật.
     * @return true nếu việc cập nhật thành công; false nếu có lỗi xảy ra.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình cập nhật cơ sở dữ liệu.
     */
    boolean updatePassword(String email, String NewPassword) throws SQLException;
}
