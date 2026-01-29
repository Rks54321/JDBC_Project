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

public class Withdraw extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;    // account no
    private JTextField textField_1;  // password
    private JTextField textField_2;  // withdraw amount

    // JDBC DETAILS
    private static final String URL =
        "jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Rakesh143#";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Withdraw frame = new Withdraw();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Withdraw() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 330);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 204, 102));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWithdrawMoney = new JLabel("WITHDRAW MONEY");
        lblWithdrawMoney.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblWithdrawMoney.setBounds(135, 10, 250, 20);
        contentPane.add(lblWithdrawMoney);

        JLabel lblEnterAccountNo = new JLabel("Enter Account no.:");
        lblEnterAccountNo.setBounds(85, 65, 120, 20);
        contentPane.add(lblEnterAccountNo);

        JLabel lblEnterPassword = new JLabel("Enter Password:");
        lblEnterPassword.setBounds(85, 105, 120, 20);
        contentPane.add(lblEnterPassword);

        JLabel lblEnterAmount = new JLabel("Withdraw Amount:");
        lblEnterAmount.setBounds(85, 145, 120, 20);
        contentPane.add(lblEnterAmount);

        textField = new JTextField();
        textField.setBounds(253, 66, 96, 18);
        contentPane.add(textField);

        textField_1 = new JTextField();
        textField_1.setBounds(253, 106, 96, 18);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setBounds(253, 146, 96, 18);
        contentPane.add(textField_2);

        JButton btnWithdraw = new JButton("WITHDRAW");
        btnWithdraw.setBounds(95, 180, 110, 20);
        contentPane.add(btnWithdraw);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(61, 210, 324, 70);
        textArea.setEditable(false);
        contentPane.add(textArea);

        JButton btnBack = new JButton("BACK");
        btnBack.setBounds(227, 180, 110, 20);
        contentPane.add(btnBack);

        // 🔙 BACK BUTTON ONLY (ADDED)
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Banking().setVisible(true);
                dispose();
            }
        });

        // 🔥 WITHDRAW LOGIC (UNCHANGED)
        btnWithdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    long accno = Long.parseLong(textField.getText());
                    String pass = textField_1.getText();
                    double withdrawAmount = Double.parseDouble(textField_2.getText());

                    if (withdrawAmount <= 0) {
                        textArea.setText("⚠ Withdraw amount must be greater than 0");
                        return;
                    }

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

                    String sql = "SELECT password, balance FROM ucucubank WHERE accno=?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setLong(1, accno);
                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        textArea.setText("⚠ Account number does not exist");
                        con.close();
                        return;
                    }

                    String dbPass = rs.getString("password");
                    double balance = rs.getDouble("balance");

                    if (!dbPass.equals(pass)) {
                        textArea.setText("⚠ Incorrect password");
                        con.close();
                        return;
                    }

                    if (withdrawAmount > balance) {
                        textArea.setText(
                            "⚠ Insufficient balance\n" +
                            "💰 Available Balance: ₹" + balance
                        );
                        con.close();
                        return;
                    }

                    String updateSql =
                        "UPDATE ucucubank SET balance = balance - ? WHERE accno=?";
                    PreparedStatement ps2 = con.prepareStatement(updateSql);
                    ps2.setDouble(1, withdrawAmount);
                    ps2.setLong(2, accno);
                    ps2.executeUpdate();

                    double newBalance = balance - withdrawAmount;

                    textArea.setText(
                        "✅ Withdrawal Successful!\n" +
                        "🏦 Account No: " + accno + "\n" +
                        "💸 Withdraw Amount: ₹" + withdrawAmount + "\n" +
                        "💰 Remaining Balance: ₹" + newBalance
                    );

                    con.close();

                } catch (NumberFormatException ex) {
                    textArea.setText("⚠ Please enter valid inputs");
                } catch (Exception ex) {
                    textArea.setText("⚠ Error: " + ex.getMessage());
                }
            }
        });
    }
}
