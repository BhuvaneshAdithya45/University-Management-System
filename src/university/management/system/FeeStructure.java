package university.management.system;

import net.proteanit.sql.DbUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.ResultSet;

public class FeeStructure extends JFrame implements ActionListener {

    JTable table;
    JComboBox<String> courseBox;
    JButton searchBtn, exportPdfBtn;
    JLabel totalLabel;

    FeeStructure() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Fee Structure");
        heading.setBounds(350, 10, 400, 30);
        heading.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 30));
        add(heading);

        // Search Filter
        JLabel courseLabel = new JLabel("Filter by Course:");
        courseLabel.setBounds(20, 60, 150, 25);
        add(courseLabel);

        String[] courses = {"All", "BE", "B.Tech", "MBA", "BBA", "MCA", "MSC"};
        courseBox = new JComboBox<>(courses);
        courseBox.setBounds(150, 60, 150, 25);
        add(courseBox);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(320, 60, 100, 25);
        searchBtn.addActionListener(this);
        add(searchBtn);

        // Table
        table = new JTable();
        JScrollPane js = new JScrollPane(table);
        js.setBounds(0, 100, 1000, 500);
        add(js);

        // Total Fee Label
        totalLabel = new JLabel("Total Fee: 0");
        totalLabel.setBounds(20, 620, 300, 25);
        totalLabel.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 16));
        add(totalLabel);

        // Load initial table data
        loadTableData("SELECT * FROM fee");

        // Export PDF Button
        exportPdfBtn = new JButton("Export PDF");
        exportPdfBtn.setBounds(800, 620, 150, 30);
        exportPdfBtn.addActionListener(this);
        add(exportPdfBtn);

        setSize(1000, 700);
        setLocation(250, 50);
        setVisible(true);
    }

    private void loadTableData(String query) {
        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

            // Update total fee
            updateTotal(query);

            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTotal(String query) {
        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery(query);

            double total = 0;
            while (rs.next()) {
                // Loop through semester1 to semester8
                for (int i = 2; i <= 9; i++) {
                    String value = rs.getString(i);
                    if (value != null && !value.isEmpty()) {
                        total += Double.parseDouble(value);
                    }
                }
            }

            totalLabel.setText("Total Fee: " + total);
            c.closeConnection();
        } catch (Exception e) {
            totalLabel.setText("Total Fee: 0");
        }
    }

    private void exportToPDF() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                // Title
                com.itextpdf.text.Font titleFont =
                        new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
                Paragraph title = new Paragraph("Fee Structure Report\n\n", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Table
                PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
                for (int i = 0; i < table.getColumnCount(); i++) {
                    pdfTable.addCell(table.getColumnName(i));
                }
                for (int rows = 0; rows < table.getRowCount(); rows++) {
                    for (int cols = 0; cols < table.getColumnCount(); cols++) {
                        pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());
                    }
                }

                // Add Total Fee
                document.add(pdfTable);
                document.add(new Paragraph("\n" + totalLabel.getText()));
                document.close();

                JOptionPane.showMessageDialog(this, "PDF saved successfully at " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            String selectedCourse = courseBox.getSelectedItem().toString();
            String query = "SELECT * FROM fee";
            if (!selectedCourse.equals("All")) {
                query += " WHERE course = '" + selectedCourse + "'";
            }
            loadTableData(query);
        } else if (e.getSource() == exportPdfBtn) {
            exportToPDF();
        }
    }

    public static void main(String[] args) {
        new FeeStructure();
    }
}
