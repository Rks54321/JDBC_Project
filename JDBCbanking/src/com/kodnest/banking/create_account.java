package com.kodnest.banking;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class create_account extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField passField;

    // JDBC DETAILS
    private static final String URL =
        "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rakesh143#";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                create_account frame = new create_account();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public create_account() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(102, 153, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAccountCreation = new JLabel("ACCOUNT CREATION");
        lblAccountCreation.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAccountCreation.setBounds(144, 10, 250, 20);
        contentPane.add(lblAccountCreation);

        JLabel lblNewLabel = new JLabel("Enter Name:");
        lblNewLabel.setBounds(77, 62, 100, 20);
        contentPane.add(lblNewLabel);

        JLabel lblEnterPassword = new JLabel("Create Your Password:");
        lblEnterPassword.setBounds(77, 92, 150, 20);
        contentPane.add(lblEnterPassword);

        nameField = new JTextField();
        nameField.setBounds(252, 63, 96, 18);
        contentPane.add(nameField);

        passField = new JTextField();
        passField.setBounds(252, 93, 96, 18);
        contentPane.add(passField);

        JButton btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBounds(103, 132, 84, 20);
        contentPane.add(btnSubmit);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(49, 162, 338, 91);
        textArea.setEditable(false);
        contentPane.add(textArea);

        JButton btnBack = new JButton("BACK");
        btnBack.setBounds(252, 132, 84, 20);
        contentPane.add(btnBack);

        // 🔙 BACK BUTTON (UNCHANGED)
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Banking().setVisible(true);
                dispose();
            }
        });

        // 🔥 ACCOUNT CREATION LOGIC (ONLY INPUT VALIDATION ADDED)
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = nameField.getText();
                String password = passField.getText();

                // ✅ INPUT VALIDATION (ONLY CHANGE)
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    textArea.setText("⚠ Name and Password cannot be empty");
                    return;
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String insertSql =
                        "INSERT INTO ucucubank (username, password, balance) VALUES (?, ?, 0)";
                    PreparedStatement ps =
                        con.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);

                    ps.setString(1, username);
                    ps.setString(2, password);

                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        long accno = rs.getLong(1);

                        textArea.setText(
                            "✅ Account Created Successfully!\n" +
                            "👤 Username: " + username + "\n" +
                            "🔐 Password: " + password + "\n" +
                            "🏦 Account Number: " + accno + "\n" +
                            "💰 Balance: ₹0"
                        );
                    }

                    con.close();

                } catch (Exception ex) {
                    textArea.setText("⚠ Error: " + ex.getMessage());
                }
            }
        });
    }
}
