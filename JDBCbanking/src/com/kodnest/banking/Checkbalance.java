package com.kodnest.banking;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Checkbalance extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField accField;
    private JTextField passField;

    // JDBC DETAILS
    private static final String URL =
        "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rakesh143#";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Checkbalance frame = new Checkbalance();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Checkbalance() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 153, 153));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCheckBalance = new JLabel("CHECK BALANCE");
        lblCheckBalance.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCheckBalance.setBounds(152, 10, 250, 20);
        contentPane.add(lblCheckBalance);

        JLabel lblEnterYourAccount = new JLabel("Enter Your Account no.:");
        lblEnterYourAccount.setBounds(73, 56, 150, 20);
        contentPane.add(lblEnterYourAccount);

        JLabel lblEnterYourPassword = new JLabel("Enter Your Password :");
        lblEnterYourPassword.setBounds(73, 100, 150, 20);
        contentPane.add(lblEnterYourPassword);

        accField = new JTextField();
        accField.setBounds(244, 57, 96, 18);
        contentPane.add(accField);

        passField = new JTextField();
        passField.setBounds(244, 101, 96, 18);
        contentPane.add(passField);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(72, 172, 268, 69);
        textArea.setEditable(false);
        contentPane.add(textArea);

        JButton btnCheck = new JButton("CHECK");
        btnCheck.setBounds(96, 142, 84, 20);
        contentPane.add(btnCheck);

        JButton btnBack = new JButton("BACK");
        btnBack.setBounds(226, 142, 84, 20);
        contentPane.add(btnBack);

        // 🔙 BACK BUTTON (UNCHANGED)
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Banking().setVisible(true);
                dispose();
            }
        });

        // 🔥 CHECK BUTTON LOGIC (ONLY INPUT VALIDATION ADDED)
        btnCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String accText = accField.getText();
                String pass = passField.getText();

                // ✅ EMPTY INPUT VALIDATION (ADDED)
                if (accText.trim().isEmpty() || pass.trim().isEmpty()) {
                    textArea.setText("⚠ Please enter Account Number and Password");
                    return;
                }

                try {
                    long accno = Long.parseLong(accText);

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String accCheckSql =
                        "SELECT password, username, balance FROM ucucubank WHERE accno=?";
                    PreparedStatement ps1 = con.prepareStatement(accCheckSql);
                    ps1.setLong(1, accno);

                    ResultSet rs1 = ps1.executeQuery();

                    if (!rs1.next()) {
                        textArea.setText("⚠ Account number does not exist");
                        con.close();
                        return;
                    }

                    String dbPassword = rs1.getString("password");

                    if (!dbPassword.equals(pass)) {
                        textArea.setText("⚠ Incorrect password");
                        con.close();
                        return;
                    }

                    String name = rs1.getString("username");
                    double balance = rs1.getDouble("balance");

                    textArea.setText(
                        "👤 Account Holder: " + name +
                        "\n🏦 Account No: " + accno +
                        "\n💰 Available Balance: ₹" + balance
                    );

                    con.close();

                } catch (NumberFormatException ex) {
                    textArea.setText("⚠ Account number must be numeric");
                } catch (Exception ex) {
                    textArea.setText("⚠ Error: " + ex.getMessage());
                }
            }
        });
    }
}
