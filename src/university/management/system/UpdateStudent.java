package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class UpdateStudent extends JFrame implements ActionListener {
    JTextField textAddress, textPhone, textEmail, textAadhar, textCourse, textBranch;
    JLabel rollText, lblName, lblFather, lblDOB, lblM10, lblM12;
    JButton submit, cancel;
    Choice cRollNo;

    UpdateStudent() {
        getContentPane().setBackground(new Color(230, 210, 252));
        setLayout(null);

        JLabel heading = new JLabel("Update Student Details");
        heading.setBounds(50, 10, 500, 50);
        heading.setFont(new Font("serif", Font.BOLD, 35));
        add(heading);

        JLabel rollLabel = new JLabel("Select Roll Number");
        rollLabel.setBounds(50, 100, 200, 20);
        rollLabel.setFont(new Font("serif", Font.PLAIN, 20));
        add(rollLabel);

        cRollNo = new Choice();
        cRollNo.setBounds(250, 100, 200, 20);
        add(cRollNo);

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT roll_no FROM student");
            while (rs.next()) {
                cRollNo.add(rs.getString("roll_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Static Labels and Data Fields
        lblName = createStaticLabel("Name", 50, 150, 100, 30, 200, 150);
        lblFather = createStaticLabel("Father Name", 400, 150, 200, 30, 600, 150);
        rollText = createStaticLabel("Roll Number", 50, 200, 200, 30, 200, 200);
        lblDOB = createStaticLabel("Date of Birth", 400, 200, 200, 30, 600, 200);
        lblM10 = createStaticLabel("Class X (%)", 400, 300, 200, 30, 600, 300);
        lblM12 = createStaticLabel("Class XII (%)", 50, 350, 200, 30, 200, 350);

        textAddress = createTextField("Address", 50, 250, 200, 30, 200, 250);
        textPhone = createTextField("Phone", 400, 250, 200, 30, 600, 250);
        textEmail = createTextField("Email", 50, 300, 200, 30, 200, 300);
        textAadhar = createTextField("Aadhar Number", 400, 350, 200, 30, 600, 350);
        textCourse = createTextField("Course", 50, 400, 200, 30, 200, 400);
        textBranch = createTextField("Branch", 400, 400, 200, 30, 600, 400);

        // Load initial details
        loadStudentDetails(cRollNo.getSelectedItem());
        cRollNo.addItemListener(e -> loadStudentDetails(cRollNo.getSelectedItem()));

        // Buttons
        submit = createButton("Update", 250, 550);
        cancel = createButton("Cancel", 450, 550);
        add(submit);
        add(cancel);

        setSize(900, 700);
        setLocation(350, 50);
        setVisible(true);
    }

    private JLabel createStaticLabel(String labelText, int lx, int ly, int lw, int lh, int vx, int vy) {
        JLabel lbl = new JLabel(labelText);
        lbl.setBounds(lx, ly, lw, lh);
        lbl.setFont(new Font("serif", Font.BOLD, 20));
        add(lbl);

        JLabel valueLabel = new JLabel();
        valueLabel.setBounds(vx, vy, 150, 30);
        add(valueLabel);
        return valueLabel;
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

    private void loadStudentDetails(String rollNo) {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM student WHERE roll_no = '" + rollNo + "'";
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
                rollText.setText(rs.getString("roll_no"));
                textCourse.setText(rs.getString("course"));
                textBranch.setText(rs.getString("branch"));
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

            String roll = rollText.getText();
            String address = textAddress.getText();
            String phone = textPhone.getText();
            String email = textEmail.getText();
            String course = textCourse.getText();
            String branch = textBranch.getText();

            try {
                String Q = "UPDATE student SET address='" + address + "', phone='" + phone + "', email='" + email
                        + "', course='" + course + "', branch='" + branch + "' WHERE roll_no='" + roll + "'";
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
        new UpdateStudent();
    }
}
