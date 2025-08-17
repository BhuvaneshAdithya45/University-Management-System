package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class ExaminationDetails extends JFrame implements ActionListener {

    JTextField search;
    JButton result, back;
    JTable table;

    ExaminationDetails() {
        getContentPane().setBackground(new Color(241, 252, 210));
        setLayout(null);

        JLabel heading = new JLabel("Check Student Result");
        heading.setBounds(350, 15, 400, 50);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(heading);

        search = new JTextField();
        search.setBounds(80, 90, 200, 30);
        search.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(search);

        result = new JButton("Result");
        result.setBounds(300, 90, 120, 30);
        styleButton(result);
        add(result);

        back = new JButton("Back");
        back.setBounds(440, 90, 120, 30);
        styleButton(back);
        add(back);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 130, 1000, 310);
        add(scrollPane);

        loadStudentTable();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                // Ensure the roll_no column exists
                int rollIndex = table.getColumnModel().getColumnIndex("roll_no");
                search.setText(table.getModel().getValueAt(row, rollIndex).toString());
            }
        });

        setSize(1000, 475);
        setLocation(300, 100);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void loadStudentTable() {
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT roll_no, name, course, branch FROM student");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == result) {
            String rollNo = search.getText();
            if (rollNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a roll number.");
                return;
            }
            setVisible(false);
            new Marks(rollNo);
        } else if (e.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ExaminationDetails();
    }
}
