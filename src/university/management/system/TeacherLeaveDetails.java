package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class TeacherLeaveDetails extends JFrame implements ActionListener {

    Choice choiceEmpID;
    JTable table;
    JButton search, cancel, print;

    TeacherLeaveDetails() {
        getContentPane().setBackground(new Color(250, 172, 206));
        setLayout(null);

        JLabel heading = new JLabel("Search by Employee ID");
        heading.setBounds(20, 20, 200, 20);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(heading);

        choiceEmpID = new Choice();
        choiceEmpID.setBounds(220, 20, 150, 25);
        add(choiceEmpID);

        // Load all employee IDs
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT emp_id FROM teacher");
            while (resultSet.next()) {
                choiceEmpID.add(resultSet.getString("emp_id"));
            }
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table for leave details
        table = new JTable();
        loadTable("SELECT * FROM teacherLeave");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 100, 900, 550);
        add(scrollPane);

        // Buttons
        search = createButton("Search", 20, 60);
        print = createButton("Print", 120, 60);
        cancel = createButton("Cancel", 220, 60);

        add(search);
        add(print);
        add(cancel);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 80, 25);
        btn.setBackground(new Color(60, 90, 170));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.addActionListener(this);
        return btn;
    }

    private void loadTable(String query) {
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            loadTable("SELECT * FROM teacherLeave WHERE emp_id = '" + choiceEmpID.getSelectedItem() + "'");
        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherLeaveDetails();
    }
}
