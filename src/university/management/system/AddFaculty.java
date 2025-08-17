package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AddFaculty extends JFrame implements ActionListener {

    JTextField textName, textfather, textAddress, textPhone, textemail, textM10, textM12, textAadhar;
    JLabel empText;
    JDateChooser cdob;
    JComboBox<String> courseBox, departmentBox;
    JButton submit, cancel, reset;
    JRadioButton male, female, other;

    Random ran = new Random();
    long f4 = Math.abs((ran.nextLong() % 9000L) + 1000L);

    AddFaculty() {
        setModernUI();

        getContentPane().setBackground(new Color(245, 247, 250));
        setLayout(null);

        JLabel heading = new JLabel("New Teacher Details");
        heading.setBounds(280, 30, 400, 50);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(heading);

        // Name
        JLabel name = new JLabel("Name");
        name.setBounds(50, 100, 100, 30);
        name.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(name);

        textName = new JTextField();
        styleTextField(textName, 200, 100);
        add(textName);

        // Father Name
        JLabel fname = new JLabel("Father Name");
        fname.setBounds(450, 100, 150, 30);
        fname.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(fname);

        textfather = new JTextField();
        styleTextField(textfather, 600, 100);
        add(textfather);

        // Gender
        JLabel genderLbl = new JLabel("Gender");
        genderLbl.setBounds(50, 150, 100, 30);
        genderLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(genderLbl);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        other = new JRadioButton("Other");

        styleRadioButton(male, 200, 150);
        styleRadioButton(female, 280, 150);
        styleRadioButton(other, 370, 150);

        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);
        bg.add(other);
        add(male);
        add(female);
        add(other);

        // Employee ID
        JLabel empID = new JLabel("Employee ID");
        empID.setBounds(450, 150, 150, 30);
        empID.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(empID);

        empText = new JLabel("EMP" + f4);
        empText.setBounds(600, 150, 200, 30);
        empText.setFont(new Font("Segoe UI", Font.BOLD, 18));
        empText.setForeground(new Color(60, 90, 170));
        add(empText);

        // DOB
        JLabel dob = new JLabel("Date of Birth");
        dob.setBounds(50, 200, 150, 30);
        dob.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(dob);

        cdob = new JDateChooser();
        cdob.setBounds(200, 200, 200, 30);
        add(cdob);

        // Address
        JLabel address = new JLabel("Address");
        address.setBounds(450, 200, 150, 30);
        address.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(address);

        textAddress = new JTextField();
        styleTextField(textAddress, 600, 200);
        add(textAddress);

        // Phone
        JLabel phone = new JLabel("Phone");
        phone.setBounds(50, 250, 150, 30);
        phone.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(phone);

        textPhone = new JTextField();
        styleTextField(textPhone, 200, 250);
        add(textPhone);

        // Email
        JLabel email = new JLabel("Email");
        email.setBounds(450, 250, 150, 30);
        email.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(email);

        textemail = new JTextField();
        styleTextField(textemail, 600, 250);
        add(textemail);

        // Class X
        JLabel M10 = new JLabel("Class X (%)");
        M10.setBounds(50, 300, 150, 30);
        M10.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(M10);

        textM10 = new JTextField();
        styleTextField(textM10, 200, 300);
        add(textM10);

        // Class XII
        JLabel M12 = new JLabel("Class XII (%)");
        M12.setBounds(450, 300, 150, 30);
        M12.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(M12);

        textM12 = new JTextField();
        styleTextField(textM12, 600, 300);
        add(textM12);

        // Aadhar
        JLabel AadharNo = new JLabel("Aadhar Number");
        AadharNo.setBounds(50, 350, 150, 30);
        AadharNo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(AadharNo);

        textAadhar = new JTextField();
        styleTextField(textAadhar, 200, 350);
        add(textAadhar);

        // Qualification
        JLabel Qualification = new JLabel("Qualification");
        Qualification.setBounds(450, 350, 150, 30);
        Qualification.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(Qualification);

        String course[] = {"BE", "B.Tech", "BBA", "BCA", "BSC", "MSC", "MBA", "MCA", "MCom", "MA", "BA"};
        courseBox = new JComboBox<>(course);
        styleComboBox(courseBox, 600, 350);
        add(courseBox);

        // Department
        JLabel Department = new JLabel("Department");
        Department.setBounds(50, 400, 150, 30);
        Department.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(Department);

        String department[] = {"Computer Science", "Information Science", "Electronics", "Electrical", "Mechanical", "Civil", "Aeronautical", "IT"};
        departmentBox = new JComboBox<>(department);
        styleComboBox(departmentBox, 200, 400);
        add(departmentBox);

        // Buttons
        submit = styleButton("Submit", 150, 500);
        reset = styleButton("Reset", 350, 500);
        cancel = styleButton("Cancel", 550, 500);

        submit.addActionListener(this);
        reset.addActionListener(this);
        cancel.addActionListener(this);

        add(submit);
        add(reset);
        add(cancel);

        setSize(900, 650);
        setLocation(350, 50);
        setVisible(true);
    }

    private JButton styleButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 35);
        button.setBackground(new Color(60, 90, 170));
        button.setForeground(Color.WHITE);
        return button;
    }

    private void styleTextField(JTextField field, int x, int y) {
        field.setBounds(x, y, 200, 30);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }

    private void styleComboBox(JComboBox<String> combo, int x, int y) {
        combo.setBounds(x, y, 200, 30);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }

    private void styleRadioButton(JRadioButton rb, int x, int y) {
        rb.setBounds(x, y, 80, 30);
        rb.setBackground(new Color(245, 247, 250));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            handleSubmit();
        } else if (e.getSource() == reset) {
            clearFields();
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }

    private boolean validateInputs() {
        if (textName.getText().trim().isEmpty() || textfather.getText().trim().isEmpty() || textAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Father Name, and Address cannot be empty!");
            return false;
        }
        if (!textPhone.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone must be exactly 10 digits!");
            return false;
        }
        if (!textemail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!");
            return false;
        }
        if (!textAadhar.getText().matches("\\d{12}")) {
            JOptionPane.showMessageDialog(this, "Aadhar must be exactly 12 digits!");
            return false;
        }
        if (cdob.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Please select Date of Birth!");
            return false;
        }
        return true;
    }

    private void handleSubmit() {
        if (!validateInputs()) return;

        try {
            String name = textName.getText();
            String fname = textfather.getText();
            String empid = empText.getText();
            String dob = new SimpleDateFormat("yyyy-MM-dd").format(cdob.getDate());
            String address = textAddress.getText();
            String phone = textPhone.getText();
            String email = textemail.getText();
            String x = textM10.getText();
            String xii = textM12.getText();
            String aadhar = textAadhar.getText();
            String course = (String) courseBox.getSelectedItem();
            String department = (String) departmentBox.getSelectedItem();
            String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "Other");

            Conn c = new Conn();
            String q = "INSERT INTO teacher (name, father_name, gender, emp_id, dob, address, phone, email, classX, classXII, aadhar, qualification, department) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = c.connection.prepareStatement(q);
            pstmt.setString(1, name);
            pstmt.setString(2, fname);
            pstmt.setString(3, gender);
            pstmt.setString(4, empid);
            pstmt.setString(5, dob);
            pstmt.setString(6, address);
            pstmt.setString(7, phone);
            pstmt.setString(8, email);
            pstmt.setString(9, x);
            pstmt.setString(10, xii);
            pstmt.setString(11, aadhar);
            pstmt.setString(12, course);
            pstmt.setString(13, department);
            pstmt.executeUpdate();
            c.closeConnection();

            JOptionPane.showMessageDialog(null, "Faculty Details Inserted Successfully!");
            setVisible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        textName.setText("");
        textfather.setText("");
        textAddress.setText("");
        textPhone.setText("");
        textemail.setText("");
        textM10.setText("");
        textM12.setText("");
        textAadhar.setText("");
        cdob.setDate(null);
    }

    private void setModernUI() {
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 16));
    }

    public static void main(String[] args) {
        new AddFaculty();
    }
}
