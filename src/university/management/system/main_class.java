package university.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

public class main_class extends JFrame implements ActionListener {

    main_class() {
        // Set FlatLaf Dark Theme as default
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/third.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1540, 750, Image.SCALE_SMOOTH);
        JLabel img = new JLabel(new ImageIcon(i2));
        add(img);

        // Menu Bar
        JMenuBar mb = new JMenuBar();

        // ===== New Information =====
        JMenu newInfo = createMenu("New Information", mb);
        addMenuItem(newInfo, "New Faculty Information");
        addMenuItem(newInfo, "New Student Information");

        // ===== View Details =====
        JMenu details = createMenu("View Details", mb);
        addMenuItem(details, "View Faculty Details");
        addMenuItem(details, "View Student Details");

        // ===== Apply Leave =====
        JMenu leave = createMenu("Apply Leave", mb);
        addMenuItem(leave, "Faculty Leave");
        addMenuItem(leave, "Student Leave");

        // ===== Leave Details =====
        JMenu leaveDetails = createMenu("Leave Details", mb);
        addMenuItem(leaveDetails, "Faculty Leave Details");
        addMenuItem(leaveDetails, "Student Leave Details");

        // ===== Examination =====
        JMenu exam = createMenu("Examination", mb);
        addMenuItem(exam, "Enter Marks");
        addMenuItem(exam, "Examination Results");

        // ===== Update Details =====
        JMenu updateInfo = createMenu("Update Details", mb);
        addMenuItem(updateInfo, "Update Faculty Details");
        addMenuItem(updateInfo, "Update Student Details");

        // ===== Fee Details =====
        JMenu fee = createMenu("Fee Details", mb);
        addMenuItem(fee, "Fee Structure");
        addMenuItem(fee, "Student Fee Form");

        // ===== Utility =====
        JMenu utility = createMenu("Utility", mb);
        addMenuItem(utility, "Calculator");
        addMenuItem(utility, "Notepad");
        addMenuItem(utility, "Export Dashboard PDF");

        // ===== Theme Toggle =====
        JMenu themeMenu = createMenu("Theme", mb);
        addThemeMenuItem(themeMenu, "Light Mode");
        addThemeMenuItem(themeMenu, "Dark Mode");

        // ===== About =====
        JMenu about = createMenu("About", mb);
        addMenuItem(about, "About");

        // ===== Exit =====
        JMenu exit = createMenu("Exit", mb);
        addMenuItem(exit, "Exit");

        setJMenuBar(mb);

        // Frame settings
        setSize(1540, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Utility Method to create Menu
    private JMenu createMenu(String title, JMenuBar mb) {
        JMenu menu = new JMenu(title);
        menu.setForeground(Color.BLACK);
        mb.add(menu);
        return menu;
    }

    // Utility Method to add Menu Item
    private void addMenuItem(JMenu menu, String itemName) {
        JMenuItem item = new JMenuItem(itemName);
        item.setBackground(Color.WHITE);
        item.addActionListener(this);
        menu.add(item);
    }

    // Add Theme Menu Items
    private void addThemeMenuItem(JMenu menu, String themeName) {
        JMenuItem item = new JMenuItem(themeName);
        item.setBackground(Color.WHITE);
        item.addActionListener(e -> {
            if (themeName.equals("Light Mode")) setLightTheme();
            else setDarkTheme();
        });
        menu.add(item);
    }

    private void setLightTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDarkTheme() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PDF Export Method
    private void exportDashboardPDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Dashboard_Report.pdf"));
            document.open();
            document.add(new Paragraph("University Management System - Dashboard Report"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Generated from Dashboard Menu"));
            document.close();
            JOptionPane.showMessageDialog(this, "Dashboard_Report.pdf created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            switch (command) {
                case "Exit":
                    System.exit(0);
                    break;
                case "Calculator":
                    Runtime.getRuntime().exec("calc.exe");
                    break;
                case "Notepad":
                    Runtime.getRuntime().exec("notepad.exe");
                    break;
                case "Export Dashboard PDF":
                    exportDashboardPDF();
                    break;
                case "New Faculty Information":
                    new AddFaculty();
                    break;
                case "New Student Information":
                    new AddStudent();
                    break;
                case "View Faculty Details":
                    new TeacherDetails();
                    break;
                case "View Student Details":
                    new StudentDetails();
                    break;
                case "Faculty Leave":
                    new TeacherLeave();
                    break;
                case "Student Leave":
                    new StudentLeave();
                    break;
                case "Faculty Leave Details":
                    new TeacherLeaveDetails();
                    break;
                case "Student Leave Details":
                    new StudentLeaveDetails();
                    break;
                case "Update Faculty Details":
                    new UpdateTeacher();
                    break;
                case "Update Student Details":
                    new UpdateStudent();
                    break;
                case "Enter Marks":
                    new EnterMarks();
                    break;
                case "Examination Results":
                    new ExaminationDetails();
                    break;
                case "Fee Structure":
                    new FeeStructure();
                    break;
                case "Student Fee Form":
                    new StudentFeeForm();
                    break;
                case "About":
                    new About();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Feature not implemented yet!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new main_class();
    }
}
