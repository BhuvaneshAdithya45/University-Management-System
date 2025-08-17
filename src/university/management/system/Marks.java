package university.management.system;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.FontFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

public class Marks extends JFrame implements ActionListener {
    String rollno;
    JButton cancel, downloadPDF;
    JLabel totalLabel, percentLabel, gradeLabel;
    String sem = "";
    String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
    int m1, m2, m3, m4, m5;

    Marks(String rollno) {
        this.rollno = rollno;

        setSize(500, 700);
        setLocation(500, 100);
        setLayout(null);
        getContentPane().setBackground(new Color(210, 252, 248));

        JLabel heading = new JLabel("A.V Technical University");
        heading.setBounds(100, 10, 400, 25);
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(heading);

        JLabel subheading = new JLabel("Result of Examination 2023");
        subheading.setBounds(100, 50, 400, 20);
        subheading.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(subheading);

        JLabel lblrollno = new JLabel("Roll Number: " + rollno);
        lblrollno.setBounds(60, 100, 400, 20);
        lblrollno.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblrollno);

        JLabel lblsemester = new JLabel();
        lblsemester.setBounds(60, 130, 400, 20);
        lblsemester.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(lblsemester);

        JLabel sub1 = createLabel(100, 200);
        JLabel sub2 = createLabel(100, 230);
        JLabel sub3 = createLabel(100, 260);
        JLabel sub4 = createLabel(100, 290);
        JLabel sub5 = createLabel(100, 320);

        totalLabel = createResultLabel("Total Marks: ", 60, 380);
        percentLabel = createResultLabel("Percentage: ", 60, 410);
        gradeLabel = createResultLabel("Grade: ", 60, 440);

        try {
            Conn c = new Conn();

            // Fetch subjects
            ResultSet rs1 = c.statement.executeQuery("SELECT * FROM subject WHERE roll_no = '" + rollno + "'");
            if (rs1.next()) {
                s1 = rs1.getString("sub1");
                s2 = rs1.getString("sub2");
                s3 = rs1.getString("sub3");
                s4 = rs1.getString("sub4");
                s5 = rs1.getString("sub5");

                sub1.setText(s1);
                sub2.setText(s2);
                sub3.setText(s3);
                sub4.setText(s4);
                sub5.setText(s5);
            }

            // Fetch marks
            ResultSet rs2 = c.statement.executeQuery("SELECT * FROM marks WHERE roll_no = '" + rollno + "'");
            if (rs2.next()) {
                m1 = rs2.getInt("mrk1");
                m2 = rs2.getInt("mrk2");
                m3 = rs2.getInt("mrk3");
                m4 = rs2.getInt("mrk4");
                m5 = rs2.getInt("mrk5");
                sem = rs2.getString("semester");

                sub1.setText(s1 + " -------- " + m1);
                sub2.setText(s2 + " -------- " + m2);
                sub3.setText(s3 + " -------- " + m3);
                sub4.setText(s4 + " -------- " + m4);
                sub5.setText(s5 + " -------- " + m5);

                lblsemester.setText("Semester: " + sem);

                int total = m1 + m2 + m3 + m4 + m5;
                double percentage = total / 5.0;

                totalLabel.setText("Total Marks: " + total);
                percentLabel.setText(String.format("Percentage: %.2f%%", percentage));
                gradeLabel.setText("Grade: " + calculateGrade(percentage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cancel = new JButton("Back");
        cancel.setBounds(70, 600, 150, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.addActionListener(this);
        add(cancel);

        downloadPDF = new JButton("Download PDF");
        downloadPDF.setBounds(250, 600, 150, 30);
        downloadPDF.setBackground(Color.BLACK);
        downloadPDF.setForeground(Color.WHITE);
        downloadPDF.setFont(new Font("Tahoma", Font.BOLD, 15));
        downloadPDF.addActionListener(this);
        add(downloadPDF);

        setVisible(true);
    }

    private JLabel createLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 400, 20);
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        add(label);
        return label;
    }

    private JLabel createResultLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 400, 20);
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(label);
        return label;
    }

    private String calculateGrade(double percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 75) return "A";
        else if (percentage >= 60) return "B";
        else if (percentage >= 45) return "C";
        else return "F";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            setVisible(false);
        } else if (e.getSource() == downloadPDF) {
            generatePDF();
        }
    }

    private void generatePDF() {
        try {
            // Open file chooser to select save location
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Marksheet");
            fileChooser.setSelectedFile(new File("Marksheet_" + rollno + ".pdf"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return; // user cancelled
            }

            File saveFile = fileChooser.getSelectedFile();
            String filePath = saveFile.getAbsolutePath();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            com.itextpdf.text.Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);

            document.add(new Paragraph("A.V Technical University", titleFont));
            document.add(new Paragraph("Result of Examination 2023\n\n", subTitleFont));

            document.add(new Paragraph("Roll Number: " + rollno));
            document.add(new Paragraph("Semester: " + sem + "\n\n"));

            PdfPTable table = new PdfPTable(2);
            table.addCell("Subject");
            table.addCell("Marks");
            table.addCell(s1);
            table.addCell(String.valueOf(m1));
            table.addCell(s2);
            table.addCell(String.valueOf(m2));
            table.addCell(s3);
            table.addCell(String.valueOf(m3));
            table.addCell(s4);
            table.addCell(String.valueOf(m4));
            table.addCell(s5);
            table.addCell(String.valueOf(m5));
            document.add(table);

            document.add(new Paragraph("\nTotal Marks: " + (m1 + m2 + m3 + m4 + m5)));
            document.add(new Paragraph("Percentage: " + String.format("%.2f", (m1 + m2 + m3 + m4 + m5) / 5.0) + "%"));
            document.add(new Paragraph("Grade: " + calculateGrade((m1 + m2 + m3 + m4 + m5) / 5.0)));

            document.close();

            JOptionPane.showMessageDialog(this, "PDF Generated: " + filePath);

            // Auto open the PDF
            Desktop.getDesktop().open(saveFile);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating PDF: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Marks("1409001"); // Example roll number
    }
}
