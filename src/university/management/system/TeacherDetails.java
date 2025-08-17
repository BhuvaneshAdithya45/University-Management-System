package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class TeacherDetails extends JFrame implements ActionListener {

    Choice choice;
    JTable table;
    JButton searchBtn, printBtn, updateBtn, addBtn, cancelBtn;

    TeacherDetails() {
        getContentPane().setBackground(new Color(192, 164, 252));
        setLayout(null);

        JLabel heading = new JLabel("Search by Employee ID");
        heading.setBounds(20, 20, 250, 25);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(heading);

        choice = new Choice();
        choice.setBounds(250, 20, 150, 25);
        add(choice);

        // Load employee IDs
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT emp_id FROM teacher");
            while (resultSet.next()) {
                choice.add(resultSet.getString("emp_id"));
            }
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table to display teacher details
        table = new JTable();
        loadTable("SELECT * FROM teacher");

        JScrollPane js = new JScrollPane(table);
        js.setBounds(0, 100, 900, 550);
        add(js);

        // Buttons
        searchBtn = createButton("Search", 20, 70);
        printBtn = createButton("Print", 120, 70);
        addBtn = createButton("Add", 220, 70);
        updateBtn = createButton("Update", 320, 70);
        cancelBtn = createButton("Cancel", 420, 70);

        add(searchBtn);
        add(printBtn);
        add(addBtn);
        add(updateBtn);
        add(cancelBtn);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 80, 25);
        btn.setBackground(new Color(60, 90, 170));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
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
        if (e.getSource() == searchBtn) {
            loadTable("SELECT * FROM teacher WHERE emp_id = '" + choice.getSelectedItem() + "'");
        } else if (e.getSource() == printBtn) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == addBtn) {
            setVisible(false);
            new AddFaculty();
        } else if (e.getSource() == updateBtn) {
            JOptionPane.showMessageDialog(this, "Update feature will open here!");
            // Future scope: new UpdateTeacher(choice.getSelectedItem());
        } else if (e.getSource() == cancelBtn) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TeacherDetails();
    }
}
