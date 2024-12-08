/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.Student;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Dell
 */
public abstract class BaseController {
    protected DefaultTableModel tableModel;

    public BaseController(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    // Common method to show messages
    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Common method to clear the table
    protected void clearTable() {
        tableModel.setRowCount(0);
    }

    // Common method to remove a row by ID
    protected void removeRowById(int id) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (Integer.parseInt(tableModel.getValueAt(i, 0).toString()) == id) {
                tableModel.removeRow(i);
                break;
            }
        }
    }

    // Abstract methods that must be implemented by subclasses
    public abstract void loadData();
    public abstract void addStudent(Student student);
    public abstract void deleteStudent(int id);
    public abstract void updateStudent(Student student);
    public abstract void searchStudents(String searchText);
    public abstract String validateStudent(Student student);
}
