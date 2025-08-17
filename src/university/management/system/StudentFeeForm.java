package university.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class StudentFeeForm extends JFrame implements ActionListener {
    Choice cRollNumber;
    JComboBox<String> courseBox, departmentBox, semesterBox;
    JLabel totalAmount;
    JTable paymentTable;
    JButton pay, update, back;

    JLabel textName, textfName;

    StudentFeeForm() {
        getContentPane().setBackground(new Color(210, 252, 251));
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/fee.png"));
        Image i2 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        JLabel img = new JLabel(new ImageIcon(i2));
        img.setBounds(400, 50, 500, 300);
        add(img);

        JLabel rollNumber = new JLabel("Select Roll Number");
        rollNumber.setBounds(40, 60, 150, 20);
        rollNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(rollNumber);

        cRollNumber = new Choice();
        cRollNumber.setBounds(200, 60, 150, 20);
        add(cRollNumber);

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                cRollNumber.add(rs.getString("roll_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel name = new JLabel("Name");
        name.setBounds(40, 100, 150, 20);
        add(name);

        textName = new JLabel();
        textName.setBounds(200, 100, 150, 20);
        add(textName);

        JLabel fname = new JLabel("Father Name");
        fname.setBounds(40, 140, 150, 20);
        add(fname);

        textfName = new JLabel();
        textfName.setBounds(200, 140, 150, 20);
        add(textfName);

        loadStudentDetails();
        cRollNumber.addItemListener(e -> {
            loadStudentDetails();
            loadPaymentHistory();
        });

        JLabel qualification = new JLabel("Course");
        qualification.setBounds(40, 180, 150, 20);
        add(qualification);

        String[] courses = {"BE", "B.Tech", "BBA", "BCA", "BSC", "MSC", "MBA", "MCA", "MCom", "MA", "BA"};
        courseBox = new JComboBox<>(courses);
        courseBox.setBounds(200, 180, 150, 20);
        add(courseBox);

        JLabel department = new JLabel("Branch");
        department.setBounds(40, 220, 150, 20);
        add(department);

        String[] departments = {
                "Computer Science", "Information Science", "Electronics",
                "Electrical", "Mechanical", "Civil", "Aeronautical", "IT"
        };
        departmentBox = new JComboBox<>(departments);
        departmentBox.setBounds(200, 220, 150, 20);
        add(departmentBox);

        JLabel semesterLbl = new JLabel("Semester");
        semesterLbl.setBounds(40, 260, 150, 20);
        add(semesterLbl);

        String[] semesters = {"semester1", "semester2", "semester3", "semester4", "semester5", "semester6", "semester7", "semester8"};
        semesterBox = new JComboBox<>(semesters);
        semesterBox.setBounds(200, 260, 150, 20);
        add(semesterBox);

        JLabel total = new JLabel("Total Payable");
        total.setBounds(40, 300, 150, 20);
        add(total);

        totalAmount = new JLabel("0");
        totalAmount.setBounds(200, 300, 150, 20);
        add(totalAmount);

        update = new JButton("Update");
        update.setBounds(30, 380, 100, 25);
        update.addActionListener(this);
        add(update);

        pay = new JButton("Pay");
        pay.setBounds(150, 380, 100, 25);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setBounds(270, 380, 100, 25);
        back.addActionListener(this);
        add(back);

        // Payment history table
        JLabel historyLbl = new JLabel("Payment History:");
        historyLbl.setBounds(40, 420, 200, 20);
        add(historyLbl);

        paymentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setBounds(40, 450, 800, 150);
        add(scrollPane);

        loadPaymentHistory();

        setSize(900, 650);
        setLocation(300, 100);
        setVisible(true);
    }

    private void loadStudentDetails() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM student WHERE roll_no='" + cRollNumber.getSelectedItem() + "'";
            ResultSet rs = c.statement.executeQuery(query);
            while (rs.next()) {
                textName.setText(rs.getString("name"));
                textfName.setText(rs.getString("father_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPaymentHistory() {
        try {
            Conn c = new Conn();
            String query = "SELECT course, branch, semester, amount FROM feecollege WHERE roll_no='" + cRollNumber.getSelectedItem() + "'";
            ResultSet rs = c.statement.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(new String[]{"Course", "Branch", "Semester", "Amount"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("course"),
                        rs.getString("branch"),
                        rs.getString("semester"),
                        rs.getString("amount")
                });
            }
            paymentTable.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            String course = (String) courseBox.getSelectedItem();
            String semester = (String) semesterBox.getSelectedItem();
            try {
                Conn c = new Conn();
                ResultSet rs = c.statement.executeQuery("SELECT " + semester + " AS fee FROM fee WHERE course='" + course + "'");
                if (rs.next()) {
                    totalAmount.setText(rs.getString("fee"));
                } else {
                    totalAmount.setText("0");
                    JOptionPane.showMessageDialog(this, "No fee data found for this course.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == pay) {
            if (totalAmount.getText().equals("0") || totalAmount.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please click Update to fetch fee before payment.");
                return;
            }
            String rollno = cRollNumber.getSelectedItem();
            String course = (String) courseBox.getSelectedItem();
            String semester = (String) semesterBox.getSelectedItem();
            String branch = (String) departmentBox.getSelectedItem();
            String total = totalAmount.getText();

            try {
                Conn c = new Conn();
                String Q = "INSERT INTO feecollege (roll_no, course, branch, semester, amount) VALUES ('" + rollno + "','" + course + "','" + branch + "','" + semester + "','" + total + "')";
                c.statement.executeUpdate(Q);
                JOptionPane.showMessageDialog(null, "Fee Submitted Successfully!");
                loadPaymentHistory();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}
