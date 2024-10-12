/*
 * Copyright(C) 2005,  SWP_G4.
 * KMS :
 * Kindergarten Management System
 *
 * Record of change:
 * DATE           Version                  AUTHOR                                 DESCRIPTION
 * 10/5/2024       1.1              Nguyễn Huy Long - He160140              Create class DAOIpml and method
 * 10/12/2024       1.2              Nguyễn Huy Long - He160140              Update class DAOIpml and method
 */
package org.example.kindergarten_management_system_g4.dao.classDAO.impliment;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.dao.classDAO.IClassDAO;
import org.example.kindergarten_management_system_g4.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lớp ClassDAOImpl kế thừa từ DBConnection và thực hiện các phương thức
 * trong interface IClassDAO. Lớp này chứa các phương thức
 *
 * @author Nguyễn Huy Long
 */
public class ClassDAOImpl extends DBConnection implements IClassDAO {
    public static final String GET_LIST_ROOM = "SELECT r.* \n" +
            "FROM room r\n" +
            "LEFT JOIN class c \n" +
            "ON r.Room_ID = c.Room_ID\n" +
            "WHERE c.Room_ID IS NULL;";
    public static final String GET_LIST_ROOM_2 = "select r.* \n" +
            "FROM room r\n" +
            "LEFT JOIN class c \n" +
            "ON r.Room_ID = c.Room_ID\n" +
            "WHERE c.Room_ID IS NULL OR r.Room_ID = ?;";
    public static final String GET_CLASS_BY_ID = "SELECT * from class where Class_ID = ?";
    public static final String GET_LIST_TEACHER = "SELECT u.* \n" +
            "FROM user u\n" +
            "LEFT JOIN class c \n" +
            "ON u.User_id = c.User_id\n" +
            "WHERE c.User_id IS NULL;";
    public static final String GET_LIST_TEACHER_2 = "SELECT u.* \n" +
            "FROM user u\n" +
            "LEFT JOIN class c \n" +
            "ON u.User_id = c.User_id\n" +
            "WHERE c.User_id IS NULL OR u.User_id = ?;";
    public static final String GET_LIST_CLASS_LEVEL = "SELECT * from class_level";
    public static final String GET_CLASS_NAME = "SELECT Class_name from class where Class_name = ?";
    public static final String GET_CLASS_NAME_UPDATE = "SELECT Class_name from class where Class_name = ? AND Class_ID != ?";
    public static final String GET_CLASS_DETAIL = "SELECT\n" +
            "    c.Class_ID, \n" +
            "    c.Class_name,\n" +
            "    cl.Class_level_name,\n" +
            "    cl.Description,\n" +
            "    u.Fullname,\n" +
            "    u.Email,\n" +
            "    r.Room_number,\n" +
            "    r.capacity\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN\n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "    c.Class_ID = ?;\n";
    public static final String GET_LIST_CLASS = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "    u.Role_id = 2;\n";
    public static final String GET_LIST_CLASS_FILTER = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "    cl.Class_level_ID = ?;\n";
    public static final String GET_LIST_CLASS_SEARCH_FILTER = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "   cl.Class_level_ID = ? AND u.Fullname like ?;\n";
    public static final String GET_LIST_CLASS_SEARCH = "SELECT \n" +
            "    c.Class_ID, \n" +
            "    c.Class_name, \n" +
            "    cl.Class_level_name, \n" +
            "    u.Fullname, \n" +
            "    r.Room_number\n" +
            "FROM \n" +
            "    class c\n" +
            "JOIN \n" +
            "    class_level cl ON c.Class_level_ID = cl.Class_level_ID\n" +
            "JOIN \n" +
            "    user u ON c.User_id = u.User_id\n" +
            "JOIN \n" +
            "    room r ON c.Room_ID = r.Room_ID\n" +
            "WHERE \n" +
            "  u.Fullname like ?";
    public static final String DELETE_CLASS = "DELETE FROM class\n" +
            "WHERE Class_ID = ?\n" +
            "AND NOT EXISTS (\n" +
            "    SELECT 1\n" +
            "    FROM student, schedule \n" +
            "    WHERE student.class_id = class.Class_ID || schedule.class_id = class.Class_ID\n" +
            ");";
    private static final Logger LOGGER = Logger.getLogger(ClassDAOImpl.class.getName());
    private static final String INSERT_CLASS = "INSERT INTO class(Class_name, Class_level_ID, User_id, Room_ID) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CLASS = "UPDATE class\n" +
            "SET Class_name = ?,\n" +
            "    Class_level_ID = ?," +
            "     User_id = ?,  \n" +
            "    Room_ID = ?         \n" +
            "WHERE Class_ID = ?;";


    /**
     * Lấy danh sách các lớp từ cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để lấy thông tin chi tiết về lớp và ánh xạ kết quả thành một danh sách các
     * đối tượng ClassDAL.
     *
     * @return Danh sách các đối tượng ClassDAL đại diện cho các lớp.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<ClassDAL> getListClass() throws SQLException {
        List<ClassDAL> classList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classList.add(classDAL);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classList;
    }

    /**
     * Lấy danh sách các lớp từ cơ sở dữ liệu với bộ lọc tìm kiếm theo cấp độ lớp.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * với các tham số để lọc các lớp dựa trên cấp độ lớp và từ khóa tìm kiếm.
     *
     * @param classLevelId ID của cấp độ lớp để lọc.
     * @param search từ khóa tìm kiếm để lọc tên lớp.
     * @return Danh sách các đối tượng ClassDAL đại diện cho các lớp phù hợp với bộ lọc.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<ClassDAL> getListClassSearchFilter(int classLevelId, String search) throws SQLException {
        List<ClassDAL> classSearchFilterList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_SEARCH_FILTER);
            preparedStatement.setInt(1, classLevelId);
            preparedStatement.setString(2, "%" + search + "%");
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classSearchFilterList.add(classDAL);

            }

        } catch (SQLException e) {
            throw new SQLException("Error filter list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classSearchFilterList;
    }

    /**
     * Lấy danh sách các phòng từ cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để lấy thông tin chi tiết về phòng và ánh xạ kết quả thành một danh sách các
     * đối tượng Room.
     *
     * @return Danh sách các đối tượng Room đại diện cho các phòng.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<Room> getListRoom() throws SQLException {
        List<Room> roomList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_ROOM);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("Room_ID"));
                room.setRoomNumber(resultSet.getString("Room_number"));
                room.setStatus(resultSet.getInt("Status"));
                room.setCapacity(resultSet.getInt("capacity"));


                // Thêm đối tượng vào danh sách
                roomList.add(room);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list room " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return roomList;
    }

    /**
     * Lấy danh sách các phòng từ cơ sở dữ liệu theo ID phòng.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * với tham số để lấy thông tin chi tiết về phòng dựa trên ID phòng và ánh xạ
     * kết quả thành một danh sách các đối tượng Room.
     *
     * @param roomId ID của phòng cần lấy thông tin.
     * @return Danh sách các đối tượng Room đại diện cho các phòng phù hợp với ID được cung cấp.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<Room> getListRoom(int roomId) throws SQLException {
        List<Room> roomList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_ROOM_2);
            preparedStatement.setInt(1, roomId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomId(resultSet.getInt("Room_ID"));
                room.setRoomNumber(resultSet.getString("Room_number"));
                room.setStatus(resultSet.getInt("Status"));
                room.setCapacity(resultSet.getInt("capacity"));


                // Thêm đối tượng vào danh sách
                roomList.add(room);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list room " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return roomList;
    }

    /**
     * Lấy danh sách các giáo viên từ cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để lấy thông tin chi tiết về giáo viên và ánh xạ kết quả thành một danh sách
     * các đối tượng User.
     *
     * @return Danh sách các đối tượng User đại diện cho các giáo viên.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<User> getListTeacher() throws SQLException {
        List<User> teacherList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_TEACHER);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                User teacher = new User();
                teacher.setUserID(resultSet.getInt("User_id"));
                teacher.setFullname(resultSet.getString("Fullname"));
                teacher.setEmail(resultSet.getString("Email"));
                teacher.setGender(resultSet.getInt("gender"));
                teacher.setPhoneNumber(resultSet.getString("phoneNumber"));


                // Thêm đối tượng vào danh sách
                teacherList.add(teacher);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list teacher " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return teacherList;
    }
    /**
     * Lấy danh sách giáo viên từ cơ sở dữ liệu theo ID người dùng.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * với tham số để lấy thông tin chi tiết về giáo viên dựa trên ID người dùng
     * và ánh xạ kết quả thành một danh sách các đối tượng User.
     *
     * @param userId ID của người dùng cần lấy thông tin giáo viên.
     * @return Danh sách các đối tượng User đại diện cho các giáo viên phù hợp
     *         với ID người dùng được cung cấp.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<User> getListTeacher(int userId) throws SQLException {
        List<User> teacherList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_TEACHER_2);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                User teacher = new User();
                teacher.setUserID(resultSet.getInt("User_id"));
                teacher.setFullname(resultSet.getString("Fullname"));
                teacher.setEmail(resultSet.getString("Email"));
                teacher.setGender(resultSet.getInt("gender"));
                teacher.setPhoneNumber(resultSet.getString("phoneNumber"));


                // Thêm đối tượng vào danh sách
                teacherList.add(teacher);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list teacher " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return teacherList;
    }

    /**
     * Lấy danh sách các cấp lớp từ cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để lấy thông tin về các cấp lớp và ánh xạ kết quả thành một danh sách
     * các đối tượng ClassLevel.
     *
     * @return Danh sách các đối tượng ClassLevel đại diện cho các cấp lớp có
     *         trong cơ sở dữ liệu.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<ClassLevel> getListClassLevel() throws SQLException {
        List<ClassLevel> classLevelList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_LEVEL);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassLevel classLevel = new ClassLevel();
                classLevel.setClassLevelId(resultSet.getInt("Class_level_ID"));
                classLevel.setClassLevelName(resultSet.getString("Class_level_name"));
                classLevel.setDescription(resultSet.getString("Description"));


                // Thêm đối tượng vào danh sách
                classLevelList.add(classLevel);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list class level " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classLevelList;
    }

    /**
     * Lấy danh sách các lớp học theo cấp lớp từ cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để lấy thông tin về các lớp học dựa trên cấp lớp đã chỉ định và ánh xạ
     * kết quả thành một danh sách các đối tượng ClassDAL.
     *
     * @param classLevelId ID của cấp lớp cần lọc danh sách lớp học.
     * @return Danh sách các đối tượng ClassDAL đại diện cho các lớp học
     *         thuộc cấp lớp đã chỉ định trong cơ sở dữ liệu.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<ClassDAL> getListClassFilter(int classLevelId) throws SQLException {
        List<ClassDAL> classFilterList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_FILTER);
            preparedStatement.setInt(1, classLevelId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classFilterList.add(classDAL);

            }

        } catch (SQLException e) {
            throw new SQLException("Error filter list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classFilterList;
    }

    /**
     * Tìm kiếm danh sách các lớp học trong cơ sở dữ liệu theo từ khóa tìm kiếm.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để tìm kiếm thông tin về các lớp học dựa trên từ khóa tìm kiếm được cung cấp.
     * Các kết quả tìm kiếm được ánh xạ thành một danh sách các đối tượng ClassDAL.
     *
     * @param search Từ khóa tìm kiếm để lọc danh sách lớp học.
     * @return Danh sách các đối tượng ClassDAL đại diện cho các lớp học
     *         khớp với từ khóa tìm kiếm trong cơ sở dữ liệu.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public List<ClassDAL> getListClassSearch(String search) throws SQLException {
        List<ClassDAL> classSearchList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_LIST_CLASS_SEARCH);
            preparedStatement.setString(1, "%" + search + "%");
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {
                ClassDAL classDAL = new ClassDAL();
                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));

                // Thêm đối tượng vào danh sách
                classSearchList.add(classDAL);

            }

        } catch (SQLException e) {
            throw new SQLException("Error search list class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classSearchList;
    }

    /**
     * Lấy thông tin lớp học theo ID từ cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để lấy thông tin của lớp học dựa trên ID được cung cấp.
     * Kết quả truy vấn sẽ được ánh xạ thành một đối tượng Classes.
     *
     * @param classId ID của lớp học cần lấy thông tin.
     * @return Đối tượng Classes chứa thông tin về lớp học
     *         tương ứng với ID được cung cấp.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public Classes getClassById(int classId) throws SQLException {
        Classes classes = new Classes();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_CLASS_BY_ID);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {

                classes.setClassId(resultSet.getInt("Class_ID"));
                classes.setClassName(resultSet.getString("Class_name"));
                classes.setClassLevelId(resultSet.getInt("Class_level_ID"));
                classes.setUserId(resultSet.getInt("User_id"));
                classes.setRoomId(resultSet.getInt("Room_ID"));

                // Thêm đối tượng vào danh sách


            }

        } catch (SQLException e) {
            throw new SQLException("Error get class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classes;
    }
    /**
     * Lấy thông tin chi tiết của lớp học theo ID từ cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu, thực hiện truy vấn
     * để lấy thông tin chi tiết của lớp học dựa trên ID được cung cấp.
     * Kết quả truy vấn sẽ được ánh xạ thành một đối tượng ClassDAL.
     *
     * @param classId ID của lớp học cần lấy thông tin chi tiết.
     * @return Đối tượng ClassDAL chứa thông tin chi tiết về lớp học
     *         tương ứng với ID được cung cấp.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện truy vấn.
     */
    @Override
    public ClassDAL getClassByIdDetail(int classId) throws SQLException {
        ClassDAL classDAL = new ClassDAL();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_CLASS_DETAIL);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {

                classDAL.setClassId(resultSet.getInt("Class_ID"));
                classDAL.setClassName(resultSet.getString("Class_name"));
                classDAL.setClassLevelName(resultSet.getString("Class_level_name"));
                classDAL.setFullname(resultSet.getString("Fullname"));
                classDAL.setRoomNumber(resultSet.getString("Room_number"));
                classDAL.setDescription(resultSet.getString("Description"));
                classDAL.setEmail(resultSet.getString("Email"));
                classDAL.setCapacity(resultSet.getString("capacity"));

            }

        } catch (SQLException e) {
            throw new SQLException("Error get class Detail " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return classDAL;
    }

    /**
     * Tạo một lớp học mới trong cơ sở dữ liệu.
     *
     * Phương thức này thiết lập một kết nối tới cơ sở dữ liệu và thực hiện
     * câu lệnh SQL để chèn một lớp học mới dựa trên thông tin được cung cấp
     * trong đối tượng Classes. Nếu quá trình chèn thành công, phương thức sẽ
     * trả về true, ngược lại trả về false.
     *
     * @param classes Đối tượng Classes chứa thông tin lớp học cần tạo.
     * @return true nếu lớp học được chèn thành công, false nếu không.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện câu lệnh chèn.
     */
    @Override
    public Boolean createClass(Classes classes) throws SQLException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(INSERT_CLASS);

            preparedStatement.setString(1, classes.getClassName());
            preparedStatement.setInt(2, classes.getClassLevelId());
            preparedStatement.setInt(3, classes.getUserId());
            preparedStatement.setInt(4, classes.getRoomId());


            LOGGER.log(Level.INFO, "Executing insert class: {0}", preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
                LOGGER.log(Level.INFO, "Class inserted successfully: {0}", classes.getClassName());
            }
        } catch (SQLException e) {
            throw new SQLException("Error insert class " + e.getMessage(), e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }

        return isInserted; // Trả về kết quả kiểm tra
    }

    /**
     * Lấy tên lớp từ cơ sở dữ liệu dựa trên tên lớp được cung cấp.
     *
     * Phương thức này thực hiện một truy vấn để tìm kiếm tên lớp trong
     * cơ sở dữ liệu. Nếu tìm thấy, phương thức sẽ trả về tên lớp,
     * ngược lại sẽ trả về một chuỗi rỗng.
     *
     * @param className Tên lớp cần tìm kiếm trong cơ sở dữ liệu.
     * @return Tên lớp tương ứng nếu tìm thấy, chuỗi rỗng nếu không.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện câu lệnh truy vấn.
     */
    @Override
    public String getClassName(String className) throws SQLException {

        String ClassNamee = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            preparedStatement = connection.prepareStatement(GET_CLASS_NAME);
            preparedStatement.setString(1, className);
            LOGGER.log(Level.INFO, "Executing query: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ClassNamee = resultSet.getString(1);  // Lưu Password ở đây
                LOGGER.log(Level.INFO, "Get class name: {0}", className);  // Ghi log khi lấy được Password
            } else {
                LOGGER.log(Level.WARNING, "No password found for email: {0}", className); // Ghi log không tìm thấy Password
            }

        } catch (SQLException e) {
            throw new SQLException("Error get class name " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return ClassNamee;
    }

    /**
     * Lấy tên lớp từ cơ sở dữ liệu dựa trên tên lớp và ID lớp được cung cấp.
     *
     * Phương thức này thực hiện một truy vấn để tìm kiếm tên lớp trong
     * cơ sở dữ liệu dựa trên tên lớp và ID lớp. Nếu tìm thấy, phương
     * thức sẽ trả về tên lớp, ngược lại sẽ trả về một chuỗi rỗng.
     *
     * @param className Tên lớp cần tìm kiếm trong cơ sở dữ liệu.
     * @param ClassId ID của lớp cần tìm kiếm.
     * @return Tên lớp tương ứng nếu tìm thấy, chuỗi rỗng nếu không.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu hoặc
     *                     thực hiện câu lệnh truy vấn.
     */
    @Override
    public String getClassNameUpdate(String className, int ClassId) throws SQLException {
        String ClassNamee = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            preparedStatement = connection.prepareStatement(GET_CLASS_NAME_UPDATE);
            preparedStatement.setString(1, className);
            preparedStatement.setInt(2, ClassId);
            LOGGER.log(Level.INFO, "Executing query: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ClassNamee = resultSet.getString(1);  // Lưu Password ở đây
                LOGGER.log(Level.INFO, "Get class name: {0}", className);  // Ghi log khi lấy được Password
            } else {
                LOGGER.log(Level.WARNING, "No class name found : {0}", className); // Ghi log không tìm thấy Password
            }

        } catch (SQLException e) {
            throw new SQLException("Error get class name " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return ClassNamee;
    }

    /**
     * Cập nhật thông tin lớp trong cơ sở dữ liệu.
     *
     * Phương thức này thực hiện một câu lệnh SQL để cập nhật thông tin
     * của một lớp dựa trên các thuộc tính của đối tượng Classes được
     * cung cấp. Nếu cập nhật thành công, phương thức sẽ trả về true,
     * ngược lại sẽ trả về false.
     *
     * @param classes Đối tượng Classes chứa thông tin lớp cần cập nhật.
     * @return true nếu cập nhật thành công, false nếu không.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu
     *                     hoặc thực hiện câu lệnh cập nhật.
     */
    @Override
    public Boolean updateClass(Classes classes) throws SQLException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(UPDATE_CLASS);

            preparedStatement.setString(1, classes.getClassName());
            preparedStatement.setInt(2, classes.getClassLevelId());
            preparedStatement.setInt(3, classes.getUserId());
            preparedStatement.setInt(4, classes.getRoomId());
            preparedStatement.setInt(5, classes.getClassId());


            LOGGER.log(Level.INFO, "Executing Update class: {0}", preparedStatement);

            int result = preparedStatement.executeUpdate(); // Thực thi câu lệnh

            if (result > 0) {
                // Nếu số dòng bị ảnh hưởng > 0, thêm thành công
                isInserted = true;
                LOGGER.log(Level.INFO, "Class Update successfully: {0}", classes.getClassName());
            }
        } catch (SQLException e) {
            throw new SQLException("Error Update class " + e.getMessage(), e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }

        return isInserted; // Trả về kết quả kiểm tra
    }

    /**
     * Xóa lớp khỏi cơ sở dữ liệu theo ID lớp.
     *
     * Phương thức này thực hiện một câu lệnh SQL để xóa một lớp
     * dựa trên ID lớp được cung cấp. Nếu xóa thành công, phương thức
     * sẽ trả về true, ngược lại sẽ trả về false.
     *
     * @param classID ID của lớp cần xóa.
     * @return true nếu xóa thành công, false nếu không.
     * @throws SQLException nếu có lỗi xảy ra khi truy cập cơ sở dữ liệu
     *                     hoặc thực hiện câu lệnh xóa.
     */
    @Override
    public Boolean deleteClass(int classID) throws SQLException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(DELETE_CLASS);
            preparedStatement.setInt(1, classID);
            LOGGER.log(Level.INFO, "Executing Delele class: {0}", preparedStatement);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                isInserted = true;
                LOGGER.log(Level.INFO, "Class Update successfully: {0}", classID);
            }
        } catch (SQLException e) {
            throw new SQLException("Error Delete class " + e.getMessage(), e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
        return isInserted;
    }

    /**
     * Đóng các tài nguyên cơ sở dữ liệu (ResultSet, PreparedStatement, Connection)
     *
     * @param resultSet         Đối tượng ResultSet cần đóng
     * @param preparedStatement Đối tượng PreparedStatement cần đóng
     * @param connection        Đối tượng Connection cần đóng
     */
    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        // Đóng ResultSet
        try {
            if (resultSet != null) {
                resultSet.close();
                LOGGER.log(Level.INFO, "ResultSet closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing ResultSet", e);
            printSQLException(e);
        }

        // Đóng PreparedStatement
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
                LOGGER.log(Level.INFO, "PreparedStatement closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing PreparedStatement", e);
            printSQLException(e);
        }

        // Đóng Connection
        try {
            if (connection != null) {
                connection.close();
                LOGGER.log(Level.INFO, "Connection closed");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error closing Connection", e);
            printSQLException(e);
        }
    }

    /**
     * Ghi lại thông tin lỗi SQL
     *
     * @param ex Đối tượng SQLException
     */
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                LOGGER.log(Level.SEVERE, "SQL Exception: {0}", e.getMessage());
                LOGGER.log(Level.SEVERE, "SQLState: {0}", ((SQLException) e).getSQLState());
                LOGGER.log(Level.SEVERE, "Error Code: {0}", ((SQLException) e).getErrorCode());
                Throwable t = ex.getCause();
                while (t != null) {
                    LOGGER.log(Level.SEVERE, "Cause: {0}", t);
                    t = t.getCause();
                }
            }
        }
    }

}
