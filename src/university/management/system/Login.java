package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Login extends JFrame implements ActionListener {
    JTextField textFieldName;
    JPasswordField passwordField;
    JButton login, back;

    Login() {
        setTitle("Login");

        JLabel labelName = new JLabel("Username");
        labelName.setBounds(40, 20, 100, 20);
        add(labelName);

        textFieldName = new JTextField();
        textFieldName.setBounds(150, 20, 150, 20);
        add(textFieldName);

        JLabel labelpass = new JLabel("Password");
        labelpass.setBounds(40, 70, 100, 20);
        add(labelpass);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 150, 20);
        add(passwordField);

        login = new JButton("Login");
        login.setBounds(40, 140, 120, 30);
        login.setBackground(Color.black);
        login.setForeground(Color.white);
        login.addActionListener(this);
        add(login);

        back = new JButton("Back");
        back.setBounds(180, 140, 120, 30);
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/second.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel img = new JLabel(new ImageIcon(i2));
        img.setBounds(350, 20, 200, 200);
        add(img);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icon/loginback.png"));
        Image i22 = i11.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i22));
        image.setBounds(0, 0, 600, 300);
        add(image);

        setSize(600, 300);
        setLocation(500, 250);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            String username = textFieldName.getText();
            String password = passwordField.getText();

            // 🔴 FIXED: Typo "fron" → "from"
            String query = "SELECT * FROM login WHERE username='" + username + "' AND password='" + password + "'";

            try {
                Conn c = new Conn();
                ResultSet resultSet = c.statement.executeQuery(query);

                if (resultSet.next()) { // 🔴 FIXED: Added "if" keyword
                    setVisible(false);
                    new main_class(); // Make sure this class exists
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
