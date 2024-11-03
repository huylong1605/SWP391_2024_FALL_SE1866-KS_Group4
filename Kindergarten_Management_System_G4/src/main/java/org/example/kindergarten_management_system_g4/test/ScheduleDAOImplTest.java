package org.example.kindergarten_management_system_g4.test;

import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.Schedule;
import org.example.kindergarten_management_system_g4.model.ScheduleDAL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ScheduleDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private ScheduleDAOImpl scheduleDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        // Tạo lớp con của ScheduleDAOImpl ghi đè getConnection để trả về mock connection
        scheduleDAO = new ScheduleDAOImpl() {
          /*  @Override
            protected Connection getConnection() {
                return connection;
            }*/
        };

        // Cấu hình các hành vi của connection và preparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

   /* @Test*/
   /* void testGetScheduleOfStudent() throws SQLException {
        // Giả lập dữ liệu trả về từ ResultSet
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Chỉ có 1 bản ghi
        when(resultSet.getInt("Schedule_ID")).thenReturn(1);
        when(resultSet.getString("Subject_name")).thenReturn("Math");
        when(resultSet.getString("Term_name")).thenReturn("Fall 2024");
        when(resultSet.getString("Slot_name")).thenReturn("Morning Slot");
        when(resultSet.getString("Start_time")).thenReturn("08:00");
        when(resultSet.getString("end_time")).thenReturn("09:00");
        when(resultSet.getString("day_of_week")).thenReturn("Monday");
        when(resultSet.getString("date")).thenReturn("2024-02-02");
        when(resultSet.getString("Room_number")).thenReturn("Room 101");
        when(resultSet.getString("class_name")).thenReturn("Class A");
        when(resultSet.getString("fullname")).thenReturn("Mr. John");

        // Thực hiện gọi hàm
        List<ScheduleDAL> scheduleList = scheduleDAO.getScheduleOfStudent(7);

        // Kiểm tra kết quả
        assertEquals(1, scheduleList.size());
        ScheduleDAL schedule = scheduleList.get(0);
        assertEquals(1, schedule.getScheduleId());
        assertEquals("Math", schedule.getSubject_name());
        assertEquals("Fall 2024", schedule.getTermName());
        assertEquals("Morning Slot", schedule.getSlotName());
        assertEquals("08:00", schedule.getStartTime());
        assertEquals("09:00", schedule.getEndTime());
        assertEquals("Monday", schedule.getDayOfWeek());
        assertEquals("2024-02-02", schedule.getDateOfDay());
        assertEquals("Room 101", schedule.getRoom());
        assertEquals("Class A", schedule.getClassName());
        assertEquals("Mr. John", schedule.getTeacher());

        // Kiểm tra các tương tác
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
    }*/

    @Test
    void testAddSchedule() throws SQLException {
        // Giả lập dữ liệu đầu vào
        Schedule schedule = new Schedule();
        schedule.setDayOfWeek("Monday");
        schedule.setDateOfDay("2024-02-02");
        schedule.setTermId(1);
        schedule.setClassId(1);
        schedule.setSlotId(1);

        int subjectId = 10; // Giả định Subject ID là 10

        // Giả lập PreparedStatement và ResultSet cho addSchedule
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Giả lập thành công 1 bản ghi
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Có ID mới được tạo
        when(resultSet.getInt(1)).thenReturn(100); // Giả lập Schedule ID vừa thêm là 100

        // Gọi phương thức addSchedule
        Boolean isInserted = scheduleDAO.addSchedule(schedule, subjectId);

        // Kiểm tra kết quả
        assertEquals(true, isInserted);

        // Kiểm tra các tương tác
        verify(preparedStatement, times(1)).executeUpdate();
        verify(preparedStatement, times(1)).getGeneratedKeys();
    }
}
