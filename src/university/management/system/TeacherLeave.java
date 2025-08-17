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

public class TeacherLeave extends JFrame implements ActionListener {

    Choice choiceEmpId, choTime;
    JDateChooser selDate;
    JButton submit, cancel;

    TeacherLeave() {
        getContentPane().setBackground(new Color(210, 232, 252));
        setLayout(null);

        JLabel heading = new JLabel("Apply Leave (Teacher)");
        heading.setBounds(120, 30, 300, 30);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(heading);

        JLabel empLabel = new JLabel("Select Employee ID");
        empLabel.setBounds(60, 90, 200, 20);
        empLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(empLabel);

        choiceEmpId = new Choice();
        choiceEmpId.setBounds(60, 120, 200, 25);
        add(choiceEmpId);

        // Load employee IDs
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT emp_id FROM teacher");
            while (resultSet.next()) {
                choiceEmpId.add(resultSet.getString("emp_id"));
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
            String empId = choiceEmpId.getSelectedItem();
            Date selectedDate = selDate.getDate();

            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a date.");
                return;
            }

            // Convert to yyyy-MM-dd format
            String date = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
            String time = choTime.getSelectedItem();

            String Q = "INSERT INTO teacherLeave (emp_id, leave_date, time_duration) VALUES (?, ?, ?)";
            try {
                Conn c = new Conn();
                PreparedStatement pstmt = c.connection.prepareStatement(Q);
                pstmt.setString(1, empId);
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
        new TeacherLeave();
    }
}
