/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dell
 */
public class StudentDAO {
    
    public void loadData(DefaultTableModel model) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM student_java ORDER BY firstName ASC";
            pst = con.prepareStatement(sql);

            rs = pst.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("Email"),
                    rs.getString("Address"),
                    rs.getInt("Age"),
                    rs.getString("Gender"),
                    rs.getString("Status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    public int addStudent(Student student) {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DatabaseConnection.getConnection();
            String query = "INSERT INTO student_java (FirstName, LastName, Email, Address, Age, Gender, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, student.getFirstName());
            pst.setString(2, student.getLastName());
            pst.setString(3, student.getEmail());
            pst.setString(4, student.getAddress());
            pst.setInt(5, student.getAge());
            pst.setString(6, student.getGender());
            pst.setString(7, student.getStatus());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public boolean deleteStudent(int id) {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DatabaseConnection.getConnection();
            String query = "DELETE FROM student_java WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateStudent(Student student) {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DatabaseConnection.getConnection();
            String query = "UPDATE student_java SET FirstName = ?, LastName = ?, Email = ?, Address = ?, Age = ?, Gender = ?, Status = ? WHERE id = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, student.getFirstName());
            pst.setString(2, student.getLastName());
            pst.setString(3, student.getEmail());
            pst.setString(4, student.getAddress());
            pst.setInt(5, student.getAge());
            pst.setString(6, student.getGender());
            pst.setString(7, student.getStatus());
            pst.setInt(8, student.getId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
     public boolean updateStatus(int id, String newStatus) {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DatabaseConnection.getConnection();
            String sql = "UPDATE student_java SET Status = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, newStatus);
            pst.setInt(2, id);

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

     public void searchStudents(String searchText, DefaultTableModel model) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>(); // Collection to store students

        try {
            con = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM student_java WHERE id = ? OR firstName LIKE ? OR lastName LIKE ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, searchText);
            pst.setString(2, "%" + searchText + "%");
            pst.setString(3, "%" + searchText + "%");

            rs = pst.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setEmail(rs.getString("Email"));
                student.setAddress(rs.getString("Address"));
                student.setAge(rs.getInt("Age"));
                student.setGender(rs.getString("Gender"));
                student.setStatus(rs.getString("Status"));
                
                students.add(student); // Add student to the collection
            }

            // Clear the table model before adding new data
            model.setRowCount(0);

            // Populate the table model with the students from the collection
            for (Student student : students) {
                Object[] row = {
                    student.getId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getEmail(),
                    student.getAddress(),
                    student.getAge(),
                    student.getGender(),
                    student.getStatus()
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
