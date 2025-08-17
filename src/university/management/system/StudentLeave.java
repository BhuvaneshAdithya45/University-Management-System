package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentLeave extends JFrame implements ActionListener {

    Choice choiceRollNo, choTime;
    JDateChooser selDate;
    JButton submit, cancel;

    StudentLeave() {
        getContentPane().setBackground(new Color(210, 232, 252));
        setLayout(null);

        JLabel heading = new JLabel("Apply Leave (Student)");
        heading.setBounds(120, 30, 300, 30);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(heading);

        JLabel RollNoSE = new JLabel("Select Roll Number");
        RollNoSE.setBounds(60, 90, 200, 20);
        RollNoSE.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(RollNoSE);

        choiceRollNo = new Choice();
        choiceRollNo.setBounds(60, 120, 200, 25);
        add(choiceRollNo);

        // Load student roll numbers
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT roll_no FROM student");
            while (resultSet.next()) {
                choiceRollNo.add(resultSet.getString("roll_no"));
            }
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lbldate = new JLabel("Select Date");
        lbldate.setBounds(60, 170, 200, 20);
        lbldate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(lbldate);

        selDate = new JDateChooser();
        selDate.setBounds(60, 200, 200, 25);
        selDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(selDate);

        JLabel time = new JLabel("Time Duration");
        time.setBounds(60, 250, 200, 20);
        time.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(time);

        choTime = new Choice();
        choTime.setBounds(60, 280, 200, 25);
        choTime.add("Full Day");
        choTime.add("Half Day");
        add(choTime);

        submit = createButton("Submit", 60, 350);
        cancel = createButton("Cancel", 180, 350);

        add(submit);
        add(cancel);

        setSize(400, 450);
        setLocation(550, 150);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 100, 30);
        btn.setBackground(new Color(60, 90, 170));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String rollno = choiceRollNo.getSelectedItem();
            Date selectedDate = selDate.getDate();

            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a date.");
                return;
            }

            // Convert to yyyy-MM-dd format
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            String time = choTime.getSelectedItem();

            // Check if leave already exists for this date
            try {
                Conn c = new Conn();
                String checkQuery = "SELECT * FROM studentLeave WHERE roll_no = ? AND leave_date = ?";
                PreparedStatement checkStmt = c.connection.prepareStatement(checkQuery);
                checkStmt.setString(1, rollno);
                checkStmt.setString(2, date);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Leave already applied for this date!");
                    c.closeConnection();
                    return;
                }

                // Insert leave record
                String Q = "INSERT INTO studentLeave (roll_no, leave_date, time_duration) VALUES (?, ?, ?)";
                PreparedStatement pstmt = c.connection.prepareStatement(Q);
                pstmt.setString(1, rollno);
                pstmt.setString(2, date);
                pstmt.setString(3, time);
                pstmt.executeUpdate();
                c.closeConnection();

                JOptionPane.showMessageDialog(null, "Leave Confirmed");

                // Reset fields after submission
                selDate.setDate(null);
                choTime.select(0);

            } catch (Exception E) {
                E.printStackTrace();
            }
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentLeave();
    }
}
