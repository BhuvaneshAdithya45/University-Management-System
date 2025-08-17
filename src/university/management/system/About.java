package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JFrame implements ActionListener {
    JButton closeBtn;

    About() {
        setTitle("About - Nitte Meenakshi Institute of Technology");
        getContentPane().setBackground(new Color(252, 228, 210));
        setLayout(null);

        // Institute Logo/Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/about.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        JLabel img = new JLabel(new ImageIcon(i2));
        img.setBounds(200, 20, 300, 200);
        add(img);

        // Heading
        JLabel heading = new JLabel("Nitte Meenakshi Institute of Technology", JLabel.CENTER);
        heading.setBounds(50, 230, 600, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setForeground(new Color(50, 50, 50));
        add(heading);

        // Scrollable About Description
        String aboutText = "Nitte Meenakshi Institute of Technology (NMIT) is one of the leading institutions in " +
                "engineering and management education in India.\n\n" +
                "Our vision is to foster innovation, research, and practical learning while focusing on " +
                "emerging technologies such as AI, ML, Cybersecurity, Cloud Computing, and Data Analytics.\n\n" +
                "Key Highlights:\n" +
                "• NBA and NAAC Accredited Institution\n" +
                "• Advanced Research Centers\n" +
                "• 100+ Industry Collaborations\n" +
                "• Modern Infrastructure & Labs\n" +
                "• Excellent Placement Record\n\n" +
                "Contact: info@nmit.ac.in";

        JTextArea aboutArea = new JTextArea(aboutText);
        aboutArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        aboutArea.setEditable(false);
        aboutArea.setWrapStyleWord(true);
        aboutArea.setLineWrap(true);
        aboutArea.setBackground(new Color(252, 228, 210));

        JScrollPane scrollPane = new JScrollPane(aboutArea);
        scrollPane.setBounds(100, 280, 500, 150);
        scrollPane.setBorder(BorderFactory.createTitledBorder("About Us"));
        add(scrollPane);

        // Close Button
        closeBtn = new JButton("Close");
        closeBtn.setBounds(300, 450, 100, 30);
        closeBtn.setBackground(new Color(60, 90, 170));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(this);
        add(closeBtn);

        setSize(700, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeBtn) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new About();
    }
}
