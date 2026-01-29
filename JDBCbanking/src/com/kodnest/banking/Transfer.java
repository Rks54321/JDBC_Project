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

public class Transfer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;   // from account
    private JTextField textField_1; // to account
    private JTextField textField_2; // amount
    private JTextField textField_3; // password

    // JDBC DETAILS
    private static final String URL =
        "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rakesh143#";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Transfer frame = new Transfer();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Transfer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 204, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTransferMoney = new JLabel("TRANSFER MONEY");
        lblTransferMoney.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTransferMoney.setBounds(148, 10, 250, 20);
        contentPane.add(lblTransferMoney);

        JLabel lblEnterYourAccount = new JLabel("Enter Your Account no.:");
        lblEnterYourAccount.setBounds(69, 40, 170, 20);
        contentPane.add(lblEnterYourAccount);

        JLabel lblEnterAccountTo = new JLabel("Enter Account no. to Transfer:");
        lblEnterAccountTo.setBounds(69, 70, 190, 20);
        contentPane.add(lblEnterAccountTo);

        JLabel lblEnterAmountPassword = new JLabel("Enter Amount:");
        lblEnterAmountPassword.setBounds(69, 100, 150, 20);
        contentPane.add(lblEnterAmountPassword);

        JLabel lblEnterYourPassword = new JLabel("Enter Your Password:");
        lblEnterYourPassword.setBounds(69, 130, 170, 20);
        contentPane.add(lblEnterYourPassword);

        textField = new JTextField();
        textField.setBounds(260, 40, 96, 18);
        contentPane.add(textField);

        textField_1 = new JTextField();
        textField_1.setBounds(260, 71, 96, 18);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setBounds(260, 101, 96, 18);
        contentPane.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setBounds(260, 131, 96, 18);
        contentPane.add(textField_3);

        JButton btnTransfer = new JButton("TRANSFER");
        btnTransfer.setBounds(260, 189, 100, 20);
        contentPane.add(btnTransfer);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(47, 160, 192, 93);
        textArea.setEditable(false);
        contentPane.add(textArea);

        JButton btnBack = new JButton("BACK");
        btnBack.setBackground(new Color(204, 153, 51));
        btnBack.setBounds(260, 233, 110, 20);
        contentPane.add(btnBack);

        // 🔙 BACK BUTTON ONLY (ADDED)
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Banking().setVisible(true);
                dispose();
            }
        });

        // 🔥 TRANSFER LOGIC (UNCHANGED)
        btnTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    long fromAcc = Long.parseLong(textField.getText());
                    long toAcc = Long.parseLong(textField_1.getText());
                    double amount = Double.parseDouble(textField_2.getText());
                    String pass = textField_3.getText();

                    if (amount <= 0) {
                        textArea.setText("⚠ Transfer amount must be greater than 0");
                        return;
                    }

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    con.setAutoCommit(false);

                    String senderSql =
                        "SELECT password, balance FROM ucucubank WHERE accno=?";
                    PreparedStatement ps1 = con.prepareStatement(senderSql);
                    ps1.setLong(1, fromAcc);
                    ResultSet rs1 = ps1.executeQuery();

                    if (!rs1.next()) {
                        textArea.setText("⚠ Sender account does not exist");
                        con.rollback();
                        con.close();
                        return;
                    }

                    String dbPass = rs1.getString("password");
                    double senderBalance = rs1.getDouble("balance");

                    if (!dbPass.equals(pass)) {
                        textArea.setText("⚠ Incorrect password");
                        con.rollback();
                        con.close();
                        return;
                    }

                    if (amount > senderBalance) {
                        textArea.setText(
                            "⚠ Insufficient balance\n" +
                            "💰 Available Balance: ₹" + senderBalance
                        );
                        con.rollback();
                        con.close();
                        return;
                    }

                    String receiverSql =
                        "SELECT accno FROM ucucubank WHERE accno=?";
                    PreparedStatement ps2 = con.prepareStatement(receiverSql);
                    ps2.setLong(1, toAcc);
                    ResultSet rs2 = ps2.executeQuery();

                    if (!rs2.next()) {
                        textArea.setText("⚠ Receiver account does not exist");
                        con.rollback();
                        con.close();
                        return;
                    }

                    String debitSql =
                        "UPDATE ucucubank SET balance = balance - ? WHERE accno=?";
                    PreparedStatement ps3 = con.prepareStatement(debitSql);
                    ps3.setDouble(1, amount);
                    ps3.setLong(2, fromAcc);
                    ps3.executeUpdate();

                    String creditSql =
                        "UPDATE ucucubank SET balance = balance + ? WHERE accno=?";
                    PreparedStatement ps4 = con.prepareStatement(creditSql);
                    ps4.setDouble(1, amount);
                    ps4.setLong(2, toAcc);
                    ps4.executeUpdate();

                    con.commit();

                    double remainingBalance = senderBalance - amount;

                    textArea.setText(
                        "✅ Transfer Successful!\n" +
                        "➡ From Account: " + fromAcc + "\n" +
                        "⬅ To Account: " + toAcc + "\n" +
                        "💸 Amount Transferred: ₹" + amount + "\n" +
                        "💰 Remaining Balance: ₹" + remainingBalance
                    );

                    con.close();

                } catch (NumberFormatException ex) {
                    textArea.setText("⚠ Please enter valid inputs");
                } catch (Exception ex) {
                    textArea.setText("⚠ Transaction failed. Rolling back...");
                    ex.printStackTrace();
                }
            }
        });
    }
}
