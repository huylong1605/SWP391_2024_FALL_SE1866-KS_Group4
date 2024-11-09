package org.example.kindergarten_management_system_g4.test;

import org.example.kindergarten_management_system_g4.dao.AuthenDAO.LoginDAO;
import org.example.kindergarten_management_system_g4.dao.scheduledao.implimentation.ScheduleDAOImpl;
import org.example.kindergarten_management_system_g4.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

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


    private LoginDAO loginDAO;

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


    @Test
    public void testGetPassword() throws ClassNotFoundException, SQLException {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Giả lập thành công 1 bản ghi
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true); // Có ID mới được tạo
        when(resultSet.getInt(1)).thenReturn(100);
       String pass =    loginDAO.getPassword("ok");
        assertEquals(true, pass);

    }


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
