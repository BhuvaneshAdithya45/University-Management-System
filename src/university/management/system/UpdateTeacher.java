package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class UpdateTeacher extends JFrame implements ActionListener {
    JTextField textAddress, textPhone, textEmail, textAadhar, textCourse, textBranch;
    JLabel empText, lblName, lblFather, lblDOB, lblM10, lblM12;
    JButton submit, cancel;
    Choice cEMPID;

    UpdateTeacher() {
        getContentPane().setBackground(new Color(230, 210, 252));
        setLayout(null);

        JLabel heading = new JLabel("Update Teacher Details");
        heading.setBounds(50, 10, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 35));
        add(heading);

        JLabel empIDLabel = new JLabel("Select Employee ID");
        empIDLabel.setBounds(50, 100, 200, 20);
        empIDLabel.setFont(new Font("serif", Font.PLAIN, 20));
        add(empIDLabel);

        cEMPID = new Choice();
        cEMPID.setBounds(250, 100, 200, 20);
        add(cEMPID);

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT emp_id FROM teacher");
            while (rs.next()) {
                cEMPID.add(rs.getString("emp_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Name and Father Name
        lblName = createLabel("Name", 50, 150, 100, 30, true);
        lblFather = createLabel("Father Name", 400, 150, 200, 30, true);
        empText = createLabel("Employee ID", 50, 200, 200, 30, true);
        lblDOB = createLabel("Date of Birth", 400, 200, 200, 30, true);
        lblM10 = createLabel("Class X (%)", 400, 300, 200, 30, true);
        lblM12 = createLabel("Class XII (%)", 50, 350, 200, 30, true);

        // Text Fields
        textAddress = createTextField("Address", 50, 250, 200, 30, 200, 250);
        textPhone = createTextField("Phone", 400, 250, 200, 30, 600, 250);
        textEmail = createTextField("Email", 50, 300, 200, 30, 200, 300);
        textAadhar = createTextField("Aadhar Number", 400, 350, 200, 30, 600, 350);
        textCourse = createTextField("Qualification", 50, 400, 200, 30, 200, 400);
        textBranch = createTextField("Department", 400, 400, 200, 30, 600, 400);

        // Load details
        loadTeacherDetails(cEMPID.getSelectedItem());

        cEMPID.addItemListener(e -> loadTeacherDetails(cEMPID.getSelectedItem()));

        // Buttons
        submit = createButton("Update", 250, 550);
        cancel = createButton("Cancel", 450, 550);
        add(submit);
        add(cancel);

        setSize(900, 700);
        setLocation(350, 50);
        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y, int w, int h, boolean staticLabel) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, w, h);
        label.setFont(new Font("serif", Font.BOLD, 20));
        if (!staticLabel) add(label);
        return label;
    }

    private JTextField createTextField(String labelText, int lx, int ly, int lw, int lh, int tx, int ty) {
        JLabel lbl = new JLabel(labelText);
        lbl.setBounds(lx, ly, lw, lh);
        lbl.setFont(new Font("serif", Font.BOLD, 20));
        add(lbl);

        JTextField field = new JTextField();
        field.setBounds(tx, ty, 150, 30);
        add(field);
        return field;
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 120, 30);
        btn.setBackground(Color.black);
        btn.setForeground(Color.white);
        btn.addActionListener(this);
        return btn;
    }

    private void loadTeacherDetails(String empID) {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM teacher WHERE emp_id = '" + empID + "'";
            ResultSet rs = c.statement.executeQuery(query);
            if (rs.next()) {
                lblName.setText(rs.getString("name"));
                lblFather.setText(rs.getString("father_name"));
                lblDOB.setText(rs.getString("dob"));
                textAddress.setText(rs.getString("address"));
                textPhone.setText(rs.getString("phone"));
                textEmail.setText(rs.getString("email"));
                lblM10.setText(rs.getString("classX"));
                lblM12.setText(rs.getString("classXII"));
                textAadhar.setText(rs.getString("aadhar"));
                empText.setText(rs.getString("emp_id"));
                textCourse.setText(rs.getString("qualification"));
                textBranch.setText(rs.getString("department"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        String phone = textPhone.getText();
        String email = textEmail.getText();
        String address = textAddress.getText();

        if (address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Address cannot be empty!");
            return false;
        }
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Phone must be 10 digits!");
            return false;
        }
        if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email)) {
            JOptionPane.showMessageDialog(this, "Enter a valid email!");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            if (!validateInputs()) return;

            String empid = empText.getText();
            String address = textAddress.getText();
            String phone = textPhone.getText();
            String email = textEmail.getText();
            String course = textCourse.getText();
            String branch = textBranch.getText();

            try {
                String Q = "UPDATE teacher SET address='" + address + "', phone='" + phone + "', email='" + email
                        + "', qualification='" + course + "', department='" + branch + "' WHERE emp_id='" + empid + "'";
                Conn c = new Conn();
                c.statement.executeUpdate(Q);

                JOptionPane.showMessageDialog(null, "Details Updated");
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateTeacher();
    }
}
