/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Dell
 */


import Model.Student;
import Model.StudentDAO;
import View.Dashboard;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JTable;

public class StudentController1 {

    private final StudentDAO studentDAO;
    private final Dashboard view;
    private StudentDAO model;

    public StudentController1(Dashboard view) {
        this.view = view;
        this.studentDAO = new StudentDAO();
        this.model = new StudentDAO();
        loadData();
    }

    public void loadData() {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        studentDAO.loadData(model);
    }

    public void addStudent(Student student) {
        int id = studentDAO.addStudent(student);
        if (id != -1) {
            student.setId(id);
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            model.addRow(new Object[]{
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAddress(),
                student.getAge(),
                student.getGender(),
                student.getStatus()
            });
            view.clearInputFields();
        } else {
            view.showMessage("Data insertion failed");
        }
    }

    public void deleteStudent(int id) {
        boolean isDeleted = studentDAO.deleteStudent(id);
        if (isDeleted) {
            view.showMessage("Student deleted successfully!");
            DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).toString().equals(String.valueOf(id))) {
                    model.removeRow(i);
                    break;
                }
            }
        } else {
            view.showMessage("Student deletion failed!");
        }
    
}

    public void updateStudent(Student student) {
        boolean isUpdated = studentDAO.updateStudent(student);
        if (isUpdated) {
            view.showMessage("Data updated successfully!");
        } else {
            view.showMessage("Data update failed!");
        }
    }
    
    public void updateStatus(String newStatus) {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a row to update!");
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) view.getTable().getModel();
        String id = tableModel.getValueAt(selectedRow, 0).toString();

        if (model.updateStatus(Integer.parseInt(id), newStatus)) {
            tableModel.setValueAt(newStatus, selectedRow, 7);
            view.getStatus().setSelectedItem(newStatus);
            JOptionPane.showMessageDialog(view, "Status updated successfully.");
        } else {
            JOptionPane.showMessageDialog(view, "Status update failed.");
        }
    }
    
    public void searchStudents(String searchText) {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        studentDAO.searchStudents(searchText, model);
    }
    
     public String validateStudent(Student student) {
        if (student.getFirstName().trim().isEmpty() || !student.getFirstName().matches("[a-zA-Z]+")) {
            return "First name is required and should contain only alphabets.";
        }
        if (student.getLastName().trim().isEmpty() || !student.getLastName().matches("[a-zA-Z]+")) {
            return "Last name is required and should contain only alphabets.";
        }
        if (student.getEmail().trim().isEmpty()) {
            return "Email is required.";
        }
        if (!student.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Email format is invalid.";
        }
        if (student.getAddress().trim().isEmpty()) {
            return "Address is required.";
        }
        if (student.getGender().trim().isEmpty()) {
            return "Gender is required.";
        }
        if (student.getStatus().trim().isEmpty()) {
            return " is required.";
        }
        return null;
    }
}
