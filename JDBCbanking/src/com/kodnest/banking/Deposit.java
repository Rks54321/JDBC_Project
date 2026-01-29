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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Deposit extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;   // account no
    private JTextField textField_1; // amount

    // JDBC DETAILS
    private static final String URL =
        "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rakesh143#";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Deposit frame = new Deposit();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Deposit() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 255, 204));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDepositMoney = new JLabel("DEPOSIT MONEY");
        lblDepositMoney.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDepositMoney.setBounds(155, 10, 250, 20);
        contentPane.add(lblDepositMoney);

        JLabel lblEnterId = new JLabel("Enter Account no.:");
        lblEnterId.setBounds(86, 67, 120, 20);
        contentPane.add(lblEnterId);

        JLabel lblEnterAmount = new JLabel("Enter Amount:");
        lblEnterAmount.setBounds(86, 107, 120, 20);
        contentPane.add(lblEnterAmount);

        textField = new JTextField();
        textField.setBounds(243, 68, 96, 18);
        contentPane.add(textField);

        textField_1 = new JTextField();
        textField_1.setBounds(243, 108, 96, 18);
        contentPane.add(textField_1);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(59, 167, 307, 73);
        textArea.setEditable(false);
        contentPane.add(textArea);

        JButton btnDeposit = new JButton("DEPOSIT");
        btnDeposit.setBackground(new Color(204, 153, 51));
        btnDeposit.setBounds(86, 137, 110, 20);
        contentPane.add(btnDeposit);

        JButton btnBack = new JButton("BACK");
        btnBack.setBackground(new Color(204, 153, 51));
        btnBack.setBounds(231, 137, 110, 20);
        contentPane.add(btnBack);

        // 🔙 BACK BUTTON ONLY (ADDED)
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Banking().setVisible(true);
                dispose();
            }
        });

        // 🔥 DEPOSIT LOGIC (UNCHANGED)
        btnDeposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    long accno = Long.parseLong(textField.getText());
                    double amount = Double.parseDouble(textField_1.getText());

                    if (amount <= 0) {
                        textArea.setText("⚠ Deposit amount must be greater than 0");
                        return;
                    }

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String checkSql = "SELECT balance FROM ucucubank WHERE accno=?";
                    PreparedStatement ps1 = con.prepareStatement(checkSql);
                    ps1.setLong(1, accno);

                    ResultSet rs = ps1.executeQuery();

                    if (!rs.next()) {
                        textArea.setText("⚠ Account number does not exist");
                        con.close();
                        return;
                    }

                    double oldBalance = rs.getDouble("balance");

                    String updateSql =
                        "UPDATE ucucubank SET balance = balance + ? WHERE accno=?";
                    PreparedStatement ps2 = con.prepareStatement(updateSql);
                    ps2.setDouble(1, amount);
                    ps2.setLong(2, accno);

                    ps2.executeUpdate();

                    double newBalance = oldBalance + amount;

                    textArea.setText(
                        "✅ Deposit Successful!\n" +
                        "🏦 Account No: " + accno + "\n" +
                        "💵 Deposited Amount: ₹" + amount + "\n" +
                        "💰 Total Balance: ₹" + newBalance
                    );

                    con.close();

                } catch (NumberFormatException ex) {
                    textArea.setText("⚠ Please enter valid account number and amount");
                } catch (Exception ex) {
                    textArea.setText("⚠ Error: " + ex.getMessage());
                }
            }
        });
    }
}
