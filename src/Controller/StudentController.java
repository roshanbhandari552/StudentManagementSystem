/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.Student;
import Model.StudentDAO;
import View.Dashboard;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Dell
 */
public class StudentController extends BaseController {
    private final StudentDAO studentDAO;
    private final Dashboard view;

    public StudentController(Dashboard view) {
        super((DefaultTableModel) view.getTable().getModel());  // Initialize BaseController with table model
        this.view = view;
        this.studentDAO = new StudentDAO();
        loadData();
    }

    @Override
    public void loadData() {
        clearTable();  // Clear existing data in the table before loading new data
        studentDAO.loadData(tableModel);
    }

    @Override
    public void addStudent(Student student) {
        int id = studentDAO.addStudent(student);
        if (id != -1) {
            student.setId(id);
            tableModel.addRow(new Object[]{
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
            showMessage("Student added successfully!");
        } else {
            showMessage("Data insertion failed");
        }
    }

    @Override
    public void deleteStudent(int id) {
        boolean isDeleted = studentDAO.deleteStudent(id);
        if (isDeleted) {
            removeRowById(id);
            showMessage("Student deleted successfully!");
        } else {
            showMessage("Student deletion failed!");
        }
    }

    @Override
    public void updateStudent(Student student) {
        boolean isUpdated = studentDAO.updateStudent(student);
        if (isUpdated) {
            showMessage("Data updated successfully!");
            loadData();
        } else {
            showMessage("Data update failed!");
        }
    }
    
    public void updateStatus(String newStatus) {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a row to update!");
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();

        if (studentDAO.updateStatus(Integer.parseInt(id), newStatus)) {
            tableModel.setValueAt(newStatus, selectedRow, 7);
            view.getStatus().setSelectedItem(newStatus);
            showMessage("Status updated successfully.");
        } else {
            showMessage("Status update failed.");
        }
    }

    @Override
    public void searchStudents(String searchText) {
        clearTable();  // Clear existing data in the table before searching
        studentDAO.searchStudents(searchText, tableModel);
    }
    
//    Validation
    @Override
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
            return "Status is required.";
        }
        return null;
    }
}
