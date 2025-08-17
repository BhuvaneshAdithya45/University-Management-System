package university.management.system;

import net.proteanit.sql.DbUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.ResultSet;

public class StudentDetails extends JFrame implements ActionListener {

    Choice choice;
    JTable table;
    JButton searchBtn, printBtn, updateBtn, addBtn, cancelBtn;
    JButton searchFieldBtn, pdfBtn;
    JTextField searchField;

    StudentDetails() {
        getContentPane().setBackground(new Color(210, 252, 218));
        setLayout(null);

        JLabel heading = new JLabel("Search by Roll Number");
        heading.setBounds(20, 20, 250, 25);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(heading);

        choice = new Choice();
        choice.setBounds(250, 20, 150, 25);
        add(choice);

        // Populate roll numbers in choice dropdown
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT roll_no FROM student");
            while (resultSet.next()) {
                choice.add(resultSet.getString("roll_no"));
            }
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table to display student details
        table = new JTable();
        loadTable("SELECT * FROM student");

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

        // Search field and PDF export
        searchField = new JTextField();
        searchField.setBounds(520, 70, 120, 25);
        add(searchField);

        searchFieldBtn = createButton("Search", 650, 70);
        add(searchFieldBtn);

        pdfBtn = createButton("Export PDF", 760, 70);
        add(pdfBtn);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 100, 25);
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

    private void exportToPDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Student_Report.pdf"));
            document.open();
            document.add(new Paragraph("Student Report"));
            document.add(new Paragraph(" "));

            for (int i = 0; i < table.getRowCount(); i++) {
                StringBuilder row = new StringBuilder();
                for (int j = 0; j < table.getColumnCount(); j++) {
                    row.append(table.getValueAt(i, j)).append(" | ");
                }
                document.add(new Paragraph(row.toString()));
            }

            document.close();
            JOptionPane.showMessageDialog(this, "Report saved as Student_Report.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            loadTable("SELECT * FROM student WHERE roll_no = '" + choice.getSelectedItem() + "'");
        } else if (e.getSource() == printBtn) {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == addBtn) {
            setVisible(false);
            new AddStudent();
        } else if (e.getSource() == updateBtn) {
            JOptionPane.showMessageDialog(this, "Update feature will open here!");
            // Future scope: new UpdateStudent(choice.getSelectedItem());
        } else if (e.getSource() == cancelBtn) {
            setVisible(false);
        } else if (e.getSource() == searchFieldBtn) {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                loadTable("SELECT * FROM student WHERE roll_no LIKE '%" + keyword + "%' OR name LIKE '%" + keyword + "%'");
            } else {
                loadTable("SELECT * FROM student");
            }
        } else if (e.getSource() == pdfBtn) {
            exportToPDF();
        }
    }

    public static void main(String[] args) {
        new StudentDetails();
    }
}
