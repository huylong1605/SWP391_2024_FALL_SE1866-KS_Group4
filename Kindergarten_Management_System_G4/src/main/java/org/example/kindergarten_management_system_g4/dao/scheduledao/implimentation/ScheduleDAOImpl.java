package org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation;

import org.example.kindergarten_management_system_g4.connection.DBConnection;
import org.example.kindergarten_management_system_g4.dao.scheduledao.IScheduleDAO;
import org.example.kindergarten_management_system_g4.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleDAOImpl extends DBConnection implements IScheduleDAO {
    public static final String GET_SCHEDULE_FOR_STUDENT = "SELECT \n" +
            "    ss.Schedule_ID,\n" +
            "    s.Subject_name ,\n" +
            "    cl.Class_name,\n" +
            "    r.Room_number,\n" +
            " teacher.fullname,\n" +
            "    t.Term_name ,\n" +
            "    sl.Slot_name ,\n" +
            "    sl.Start_time ,\n" +
            "    \n" +
            "    sl.end_time ,\n" +
            "    sch.date ,\n" +
            "    \n" +
            "    sch.day_of_week \n" +
            "FROM \n" +
            "    Student st\n" +
            "JOIN \n" +
            "    User u ON st.User_id = u.User_id\n" +
            "JOIN \n" +
            "    Schedule sch ON st.class_id = sch.class_id\n" +
            "JOIN \n" +
            "    subject_Schedule ss ON sch.Schedule_ID = ss.Schedule_ID\n" +
            "JOIN \n" +
            "    Subject s ON ss.Subject_ID = s.Subject_ID\n" +
            "JOIN \n" +
            "    Term t ON sch.Term_ID = t.Term_ID\n" +
            "JOIN \n" +
            "    Slot sl ON sch.SlotId = sl.slot_id\n" +
            "JOIN \n" +
            "     Class cl on  sch.class_id = cl.Class_ID \n" +
            "JOIN \n" +
            "     room r on  cl.Room_ID = r.Room_ID\n" +
            "JOIN \n" +
            "     User teacher on  cl.user_id = teacher.user_id\n" +
            "WHERE \n" +
            "    u.User_id = ?\n" +
            "AND ( " +
            "    (sch.date BETWEEN ? AND ?) " +
            "    OR (? IS NULL AND ? IS NULL) " +
            ") " +
            "ORDER BY sch.date;";
    public static final String GET_ALL_TERM = "SELECT * FROM term;";
    public static final String GET_TERM_BY_ID = "SELECT * FROM term where term_ID = ?;";
    public static final String GET_ALL_SUBJECT = "SELECT * FROM subject where status = 'Active';";
    public static final String GET_ALL_CLASS = "SELECT * FROM class;";
    public static final String GET_ALL_SLOT = "SELECT * FROM slot;";
    public static final String INSERT_SCHEDULE = "INSERT INTO Schedule (day_of_week, date, term_ID, class_id, slotId) VALUES\n" +
            "(?, ?, ?, ?, ?);\n";

    public static final String EDIT_SCHEDULE = "UPDATE Schedule SET day_of_week = ?, date = ?, term_ID = ?" +
            ", class_id = ?, slotId = ? WHERE schedule_ID = ?;\n";

    public static final String CHANGE_SLOT = "UPDATE Schedule SET day_of_week = ?, date = ?, " +
            "slotId = ? WHERE schedule_ID = ?;\n";

    public static final String GET_EXIST_SCHEDULE = "SELECT * FROM schedule where date = ? " +
            "AND class_id = ? AND slotId = ? and schedule_ID != ?;";
    public static final String GET_EXIST_SCHEDULE_2 = "SELECT * FROM schedule where date = ? " +
            "AND slotId = ? and schedule_ID != ?;";

    public static final String GET_SLOT_BY_SCHEDULE_ID = "SELECT * FROM slot s join " +
            "schedule sch on sch.slotId =  s.slot_id where sch.schedule_ID = ? ;\n" +
            "\n";

    public static final String GET_SCHEDULE_BY_ID = "SELECT * FROM schedule where schedule_ID = ?;";
    public static final String GET_SUBJECT_BY_SCHEDULE_ID = "SELECT s.subject_ID ,s.subject_name FROM " +
            "subject s join " +
            "subject_schedule ss on s.subject_ID = ss.subject_ID where ss.schedule_ID = ?;\n";
    public static final String GET_ALL_SCHEDULE_BY_CLASS =
            "SELECT sch.schedule_ID, sch.day_of_week, sch.date, s.subject_name, " +
                    "sl.slot_name, sl.start_time, sl.end_time, u.fullname " +
                    "FROM schedule sch " +
                    "JOIN slot sl ON sl.slot_id = sch.slotId " +
                    "JOIN subject_Schedule ss ON sch.Schedule_ID = ss.Schedule_ID " +
                    "JOIN Subject s ON ss.Subject_ID = s.Subject_ID " +
                    "JOIN class c ON c.class_ID = sch.class_id " +
                    "JOIN user u ON u.user_id = c.user_id" +
                    " " +
                    "WHERE sch.class_id = ? " +
                    "AND ( " +
                    "    (sch.date BETWEEN ? AND ?) " +
                    "    OR (? IS NULL AND ? IS NULL) " +
                    ") " +
                    "ORDER BY sch.date;";

    private static final Logger LOGGER = Logger.getLogger(ScheduleDAOImpl.class.getName());

    @Override
    public List<ScheduleDAL> getScheduleOfStudent(int parentId, String startDate, String endDate) throws SQLException {
        List<ScheduleDAL> listSchedule = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_SCHEDULE_FOR_STUDENT);
            preparedStatement.setInt(1, parentId);
            if (startDate == null || startDate.isEmpty()) {
                preparedStatement.setNull(2, java.sql.Types.DATE); // Nếu startDate là null hoặc rỗng, set NULL
                preparedStatement.setNull(4, java.sql.Types.DATE); // Tương tự cho điều kiện kiểm tra NULL
            } else {
                preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
                preparedStatement.setDate(4, java.sql.Date.valueOf(startDate));
            }


            if (endDate == null || endDate.isEmpty()) {
                preparedStatement.setNull(3, java.sql.Types.DATE); // Nếu endDate là null hoặc rỗng, set NULL
                preparedStatement.setNull(5, java.sql.Types.DATE); // Tương tự cho điều kiện kiểm tra NULL
            } else {
                preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
                preparedStatement.setDate(5, java.sql.Date.valueOf(endDate));
            }
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                ScheduleDAL schedule = new ScheduleDAL();
                schedule.setScheduleId(resultSet.getInt("Schedule_ID"));
                schedule.setSubject_name(resultSet.getString("Subject_name"));
                schedule.setTermName(resultSet.getString("Term_name"));
                schedule.setSlotName(resultSet.getString("Slot_name"));
                schedule.setStartTime(resultSet.getString("Start_time"));
                schedule.setEndTime(resultSet.getString("end_time"));
                schedule.setDayOfWeek(resultSet.getString("day_of_week"));
                schedule.setDateOfDay(resultSet.getString("date"));
                schedule.setRoom(resultSet.getString("Room_number"));
                schedule.setClassName(resultSet.getString("class_name"));
                schedule.setTeacher(resultSet.getString("fullname"));

                // Thêm đối tượng vào danh sách
                listSchedule.add(schedule);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list schedule for student " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return listSchedule;
    }

    @Override
    public List<Term> getListTerm() throws SQLException {
        List<Term> listTerm = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_ALL_TERM);

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {

                Term term = new Term();
                term.setTermId(resultSet.getInt("term_ID"));
                term.setTermName(resultSet.getString("term_name"));
                term.setStartDate(resultSet.getDate("start_Date"));
                term.setEndDate(resultSet.getDate("end_Date"));
                term.setYear(resultSet.getInt("year"));


                // Thêm đối tượng vào danh sách


                listTerm.add(term);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list term  " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return listTerm;
    }

    @Override
    public List<Subject> getListSubject() throws SQLException {
        List<Subject> listSubject = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_ALL_SUBJECT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Subject subject = new Subject();
                subject.setSubjectId(resultSet.getInt("subject_ID"));
                subject.setSubjectName(resultSet.getString("subject_name"));
                subject.setSubjectCode(resultSet.getString("subject_Code"));

                // Thêm đối tượng vào danh sách
                listSubject.add(subject);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list subject  " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return listSubject;
    }

    @Override
    public List<Classes> getListClass() throws SQLException {
        List<Classes> listClass = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_ALL_CLASS);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Classes classes = new Classes();
                classes.setClassId(resultSet.getInt("class_ID"));
                classes.setClassName(resultSet.getString("class_name"));

                // Thêm đối tượng vào danh sách
                listClass.add(classes);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list class  " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return listClass;
    }

    @Override
    public List<Slot> getListSlot() throws SQLException {
        List<Slot> listSlot = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_ALL_SLOT);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Slot slot = new Slot();
                slot.setSlotId(resultSet.getInt("slot_id"));
                slot.setSlotName(resultSet.getString("slot_name"));
                slot.setStartTime(resultSet.getTime("start_time"));
                slot.setEndTime(resultSet.getTime("end_time"));


                // Thêm đối tượng vào danh sách
                listSlot.add(slot);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list Slot  " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return listSlot;
    }

    @Override
    public Boolean addSchedule(Schedule schedule, int subjectId) throws SQLException {
        boolean isInserted = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement scheduleSubjectStatement = null;

        try {
            // Kết nối đến cơ sở dữ liệu
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            // Chuẩn bị câu lệnh SQL để chèn vào bảng schedule
            preparedStatement = connection.prepareStatement(INSERT_SCHEDULE, Statement.RETURN_GENERATED_KEYS);

            // Thiết lập các giá trị cho câu lệnh insert vào bảng schedule
            preparedStatement.setString(1, schedule.getDayOfWeek());
            preparedStatement.setString(2, schedule.getDateOfDay());
            preparedStatement.setInt(3, schedule.getTermId());
            preparedStatement.setInt(4, schedule.getClassId());
            preparedStatement.setInt(5, schedule.getSlotId());

            LOGGER.log(Level.INFO, "Executing insert schedule: {0}", preparedStatement);

            // Thực thi câu lệnh insert
            int result = preparedStatement.executeUpdate();

            // Kiểm tra xem có bản ghi nào được chèn thành công không
            if (result > 0) {
                isInserted = true;
                LOGGER.log(Level.INFO, "Schedule inserted successfully");

                // Lấy ID của bản ghi vừa được chèn vào bảng schedule
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int scheduleId = generatedKeys.getInt(1); // Lấy ID tự động tạo của bảng schedule
                        schedule.setScheduleId(scheduleId); // Thiết lập scheduleId vào đối tượng Schedule

                        // Chuẩn bị câu lệnh SQL để chèn vào bảng schedule_subject
                        String insertScheduleSubjectSQL = "INSERT INTO subject_schedule (schedule_ID, subject_ID) VALUES (?, ?)";
                        scheduleSubjectStatement = connection.prepareStatement(insertScheduleSubjectSQL);
                        scheduleSubjectStatement.setInt(1, scheduleId);
                        scheduleSubjectStatement.setInt(2, subjectId);

                        // Thực thi câu lệnh insert vào bảng schedule_subject
                        scheduleSubjectStatement.executeUpdate();
                        LOGGER.log(Level.INFO, "Schedule_subject relation inserted successfully for schedule_id {0} and subject_id {1}",
                                new Object[]{scheduleId, subjectId});
                    } else {
                        throw new SQLException("Error retrieving schedule ID after insert.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error inserting schedule: " + e.getMessage(), e);
        } finally {
            // Đóng các tài nguyên
            closeResources(null, preparedStatement, connection);
            closeResources(null, scheduleSubjectStatement, null);
        }

        return isInserted;
    }

    @Override
    public Boolean getSchedule(Schedule schedule) throws SQLException {
        boolean isCheck = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            // Giả sử GET_EXIST_SCHEDULE là câu lệnh SELECT
            preparedStatement = connection.prepareStatement(GET_EXIST_SCHEDULE);

            preparedStatement.setString(1, schedule.getDateOfDay());
            preparedStatement.setInt(2, schedule.getClassId());
            preparedStatement.setInt(3, schedule.getSlotId());
            preparedStatement.setInt(4, schedule.getScheduleId());

            LOGGER.log(Level.INFO, "Executing query to check schedule existence: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery(); // Sử dụng executeQuery() cho SELECT

            // Kiểm tra xem có bản ghi nào trả về không
            if (resultSet.next()) {
                isCheck = true; // Nếu có kết quả, bản ghi tồn tại
                LOGGER.log(Level.INFO, "Schedule exists: {0}", schedule);
            } else {
                LOGGER.log(Level.INFO, "Schedule does not exist: {0}", schedule);
            }
        } catch (SQLException e) {
            throw new SQLException("Error checking schedule existence: " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return isCheck;
    }

    @Override
    public Boolean getSchedule2(Schedule schedule) throws SQLException {
        boolean isCheck = false; // Khởi tạo mặc định là false
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            // Giả sử GET_EXIST_SCHEDULE là câu lệnh SELECT
            preparedStatement = connection.prepareStatement(GET_EXIST_SCHEDULE_2);

            preparedStatement.setString(1, schedule.getDateOfDay());

            preparedStatement.setInt(2, schedule.getSlotId());
            preparedStatement.setInt(3, schedule.getScheduleId());

            LOGGER.log(Level.INFO, "Executing query to check schedule (change slot) existence: {0}", preparedStatement);

            resultSet = preparedStatement.executeQuery(); // Sử dụng executeQuery() cho SELECT

            // Kiểm tra xem có bản ghi nào trả về không
            if (resultSet.next()) {
                isCheck = true; // Nếu có kết quả, bản ghi tồn tại
                LOGGER.log(Level.INFO, "Schedule exists: {0}", schedule);
            } else {
                LOGGER.log(Level.INFO, "Schedule does not exist: {0}", schedule);
            }
        } catch (SQLException e) {
            throw new SQLException("Error checking schedule existence: " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return isCheck;
    }

    @Override
    public Term getTermById(int TermId) throws SQLException {
        Term term = new Term();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_TERM_BY_ID);
            preparedStatement.setInt(1, TermId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {

                term.setTermId(resultSet.getInt("term_ID"));
                term.setTermName(resultSet.getString("term_name"));
                term.setStartDate(resultSet.getDate("start_Date"));
                term.setEndDate(resultSet.getDate("end_Date"));
                term.setYear(resultSet.getInt("year"));

                // Thêm đối tượng vào danh sách
            }

        } catch (SQLException e) {
            throw new SQLException("Error get class " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return term;
    }

    @Override
    public List<ScheduleDAL> getListScheduleByClass(int classId, String startDate, String endDate) throws SQLException {
        List<ScheduleDAL> listSchedule = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_ALL_SCHEDULE_BY_CLASS);
            preparedStatement.setInt(1, classId);
            if (startDate == null || startDate.isEmpty()) {
                preparedStatement.setNull(2, java.sql.Types.DATE); // Nếu startDate là null hoặc rỗng, set NULL
                preparedStatement.setNull(4, java.sql.Types.DATE); // Tương tự cho điều kiện kiểm tra NULL
            } else {
                preparedStatement.setDate(2, java.sql.Date.valueOf(startDate));
                preparedStatement.setDate(4, java.sql.Date.valueOf(startDate));
            }


            if (endDate == null || endDate.isEmpty()) {
                preparedStatement.setNull(3, java.sql.Types.DATE); // Nếu endDate là null hoặc rỗng, set NULL
                preparedStatement.setNull(5, java.sql.Types.DATE); // Tương tự cho điều kiện kiểm tra NULL
            } else {
                preparedStatement.setDate(3, java.sql.Date.valueOf(endDate));
                preparedStatement.setDate(5, java.sql.Date.valueOf(endDate));
            }
            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                ScheduleDAL schedule = new ScheduleDAL();
                schedule.setScheduleId(resultSet.getInt("schedule_ID"));
                schedule.setSubject_name(resultSet.getString("subject_name"));
                schedule.setSlotName(resultSet.getString("slot_name"));
                schedule.setStartTime(resultSet.getString("start_time"));
                schedule.setEndTime(resultSet.getString("end_time"));
                schedule.setDayOfWeek(resultSet.getString("day_of_week"));
                schedule.setDateOfDay(resultSet.getString("date"));

                schedule.setTeacher(resultSet.getString("fullname"));

                // Thêm đối tượng vào danh sách
                listSchedule.add(schedule);

            }

        } catch (SQLException e) {
            throw new SQLException("Error list schedule for student " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return listSchedule;
    }

    @Override
    public Schedule getScheduleById(int scheduleId) throws SQLException {
        Schedule schedule = new Schedule();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_SCHEDULE_BY_ID);
            preparedStatement.setInt(1, scheduleId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {

                schedule.setScheduleId(resultSet.getInt("schedule_ID"));
                schedule.setDayOfWeek(resultSet.getString("day_of_week"));
                schedule.setDateOfDay(String.valueOf(resultSet.getDate("date")));
                schedule.setClassId(resultSet.getInt("class_id"));
                schedule.setSlotId(resultSet.getInt("slotId"));
                schedule.setTermId(resultSet.getInt("term_ID"));


                // Thêm đối tượng vào danh sách
            }

        } catch (SQLException e) {
            throw new SQLException("Error get Schedule by Id " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return schedule;
    }

    @Override
    public Subject getSubjectByScheduleId(int scheduleId) throws SQLException {
        Subject subject = new Subject();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_SUBJECT_BY_SCHEDULE_ID);
            preparedStatement.setInt(1, scheduleId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {

                subject.setSubjectId(resultSet.getInt("subject_ID"));
                subject.setSubjectName(resultSet.getString("subject_name"));
                // Thêm đối tượng vào danh sách
            }

        } catch (SQLException e) {
            throw new SQLException("Error get subject by Schedule Id " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return subject;
    }

    @Override
    public Boolean editSchedule(Schedule schedule, int subjectId) throws SQLException {
        Boolean isEdit = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement scheduleSubjectStatement = null;

        try {
            // Kết nối đến cơ sở dữ liệu
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            // Chuẩn bị câu lệnh SQL để cập nhật bảng schedule
            preparedStatement = connection.prepareStatement(EDIT_SCHEDULE);

            // Thiết lập các giá trị cho câu lệnh UPDATE vào bảng schedule
            preparedStatement.setString(1, schedule.getDayOfWeek());
            preparedStatement.setString(2, schedule.getDateOfDay());
            preparedStatement.setInt(3, schedule.getTermId());
            preparedStatement.setInt(4, schedule.getClassId());
            preparedStatement.setInt(5, schedule.getSlotId());
            preparedStatement.setInt(6, schedule.getScheduleId());

            LOGGER.log(Level.INFO, "Executing edit schedule: {0}", preparedStatement);

            // Thực thi câu lệnh update
            int result = preparedStatement.executeUpdate();

            // Kiểm tra xem có bản ghi nào được cập nhật thành công không
            if (result > 0) {
                isEdit = true;
                LOGGER.log(Level.INFO, "Schedule edited successfully");

                // Cập nhật bảng subject_schedule
                String updateScheduleSubjectSQL = "UPDATE subject_schedule SET subject_ID = ? WHERE schedule_ID = ?";
                scheduleSubjectStatement = connection.prepareStatement(updateScheduleSubjectSQL);
                scheduleSubjectStatement.setInt(1, subjectId);
                scheduleSubjectStatement.setInt(2, schedule.getScheduleId());

                // Thực thi câu lệnh update vào bảng subject_schedule
                scheduleSubjectStatement.executeUpdate();
                LOGGER.log(Level.INFO, "Schedule_subject relation updated successfully for schedule_id {0} and subject_id {1}",
                        new Object[]{schedule.getScheduleId(), subjectId});
            }
        } catch (SQLException e) {
            throw new SQLException("Error updating schedule: " + e.getMessage(), e);
        } finally {
            // Đóng các tài nguyên
            closeResources(null, preparedStatement, connection);
            closeResources(null, scheduleSubjectStatement, null);
        }

        return isEdit;
    }

    @Override
    public Slot getSlotByScheduleId(int scheduleId) throws SQLException {
        Slot slot = new Slot();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");
            preparedStatement = connection.prepareStatement(GET_SLOT_BY_SCHEDULE_ID);
            preparedStatement.setInt(1, scheduleId);
            resultSet = preparedStatement.executeQuery();

            // Duyệt qua các kết quả và tạo đối tượng ClassDAL từ mỗi dòng kết quả
            while (resultSet.next()) {

                slot.setSlotId(resultSet.getInt("slot_id"));
                slot.setSlotName(resultSet.getString("slot_name"));
                slot.setStartTime(resultSet.getTime("start_time"));
                slot.setEndTime(resultSet.getTime("end_time"));
                // Thêm đối tượng vào danh sách
            }

        } catch (SQLException e) {
            throw new SQLException("Error get slot by Schedule Id " + e.getMessage(), e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return slot;
    }

    @Override
    public Boolean changeSlot(Schedule schedule) throws SQLException {
        Boolean isChange = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement scheduleSubjectStatement = null;

        try {
            // Kết nối đến cơ sở dữ liệu
            connection = getConnection();
            LOGGER.log(Level.INFO, "Connecting to database...");

            // Chuẩn bị câu lệnh SQL để cập nhật bảng schedule
            preparedStatement = connection.prepareStatement(CHANGE_SLOT);

            // Thiết lập các giá trị cho câu lệnh UPDATE vào bảng schedule
            preparedStatement.setString(1, schedule.getDayOfWeek());
            preparedStatement.setString(2, schedule.getDateOfDay());
            preparedStatement.setInt(3, schedule.getSlotId());
            preparedStatement.setInt(4, schedule.getScheduleId());

            LOGGER.log(Level.INFO, "Executing Change slot schedule: {0}", preparedStatement);

            // Thực thi câu lệnh update
            int result = preparedStatement.executeUpdate();

            // Kiểm tra xem có bản ghi nào được cập nhật thành công không
            if (result > 0) {
                isChange = true;
                LOGGER.log(Level.INFO, "Schedule edited successfully");

            }
        } catch (SQLException e) {
            throw new SQLException("Error updating schedule: " + e.getMessage(), e);
        } finally {
            // Đóng các tài nguyên
            closeResources(null, preparedStatement, connection);
            closeResources(null, scheduleSubjectStatement, null);
        }

        return isChange;
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


