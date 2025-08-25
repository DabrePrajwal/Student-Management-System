import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Student {
    private int id;
    private String name;
    private String course;
    private double marks;

    public Student(int id, String name, String course, double marks) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }

    public void setName(String name) { this.name = name; }
    public void setCourse(String course) { this.course = course; }
    public void setMarks(double marks) { this.marks = marks; }
}

class StudentManagement {
    private java.util.List<Student> students = new ArrayList<>();

    public void addStudent(Student s) { students.add(s); }
    public java.util.List<Student> getStudents() { return students; }

    public Student searchStudent(int id) {
        for(Student s : students) {
            if(s.getId() == id) return s;
        }
        return null;
    }

    public void deleteStudent(int id) {
        students.removeIf(s -> s.getId() == id);
    }
}

public class StudentManagementGUI extends JFrame {
    private StudentManagement sm = new StudentManagement();
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField idField, nameField, courseField, marksField, searchField;

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for form inputs
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Form"));

        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Course:"));
        courseField = new JTextField();
        formPanel.add(courseField);

        formPanel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        formPanel.add(marksField);

        JButton addBtn = new JButton("Add Student");
        formPanel.add(addBtn);

        JButton updateBtn = new JButton("Update Student");
        formPanel.add(updateBtn);

        // Table
        String[] columns = {"ID", "Name", "Course", "Marks"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Search & Delete
        JPanel actionPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(10);
        JButton searchBtn = new JButton("Search");
        JButton deleteBtn = new JButton("Delete");
        actionPanel.add(new JLabel("Search by ID:"));
        actionPanel.add(searchField);
        actionPanel.add(searchBtn);
        actionPanel.add(deleteBtn);

        // Layout
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Action Listeners
        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        searchBtn.addActionListener(e -> searchStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String course = courseField.getText();
            double marks = Double.parseDouble(marksField.getText());

            Student s = new Student(id, name, course, marks);
            sm.addStudent(s);
            tableModel.addRow(new Object[]{id, name, course, marks});

            JOptionPane.showMessageDialog(this, "Student Added Successfully ✅");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input ❌");
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(idField.getText());
            Student s = sm.searchStudent(id);
            if(s != null) {
                s.setName(nameField.getText());
                s.setCourse(courseField.getText());
                s.setMarks(Double.parseDouble(marksField.getText()));

                // Update table
                for(int i=0; i<tableModel.getRowCount(); i++) {
                    if((int)tableModel.getValueAt(i, 0) == id) {
                        tableModel.setValueAt(s.getName(), i, 1);
                        tableModel.setValueAt(s.getCourse(), i, 2);
                        tableModel.setValueAt(s.getMarks(), i, 3);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Student Updated Successfully ✏️");
            } else {
                JOptionPane.showMessageDialog(this, "Student not found ❌");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input ❌");
        }
    }

    private void searchStudent() {
        try {
            int id = Integer.parseInt(searchField.getText());
            Student s = sm.searchStudent(id);
            if(s != null) {
                JOptionPane.showMessageDialog(this, 
                    "Found: ID=" + s.getId() + ", Name=" + s.getName() +
                    ", Course=" + s.getCourse() + ", Marks=" + s.getMarks());
            } else {
                JOptionPane.showMessageDialog(this, "Student not found ❌");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid ID ❌");
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(searchField.getText());
            sm.deleteStudent(id);

            // Remove from table
            for(int i=0; i<tableModel.getRowCount(); i++) {
                if((int)tableModel.getValueAt(i, 0) == id) {
                    tableModel.removeRow(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Student Deleted Successfully 🗑️");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid ID ❌");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        courseField.setText("");
        marksField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementGUI().setVisible(true);
        });
    }
}
