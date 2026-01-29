package com.kodnest.banking;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class Banking extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Banking frame = new Banking();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Banking() {
        setTitle("Banking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(153, 51, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("BANKING MANAGEMENT SYSTEM");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(101, 10, 260, 20);
        contentPane.add(lblNewLabel);

        // CREATE ACCOUNT
        JButton btnCreate = new JButton("CREATE ACCOUNT");
        btnCreate.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCreate.setBounds(51, 72, 150, 20);
        contentPane.add(btnCreate);

        btnCreate.addActionListener(e -> {
            new create_account().setVisible(true);
            this.setVisible(false);
        });

        // CHECK BALANCE
        JButton btnCheck = new JButton("CHECK BALANCE");
        btnCheck.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCheck.setBounds(51, 114, 150, 20);
        contentPane.add(btnCheck);

        btnCheck.addActionListener(e -> {
            new Checkbalance().setVisible(true);
            this.setVisible(false);
        });

        // TRANSFER MONEY
        JButton btnTransfer = new JButton("TRANSFER MONEY");
        btnTransfer.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTransfer.setBounds(51, 158, 150, 20);
        contentPane.add(btnTransfer);

        btnTransfer.addActionListener(e -> {
            new Transfer().setVisible(true);
            this.setVisible(false);
        });

        // DEPOSIT
        JButton btnDeposit = new JButton("DEPOSIT");
        btnDeposit.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnDeposit.setBounds(236, 72, 150, 20);
        contentPane.add(btnDeposit);

        btnDeposit.addActionListener(e -> {
            new Deposit().setVisible(true);
            this.setVisible(false);
        });

        // WITHDRAW
        JButton btnWithdraw = new JButton("WITHDRAW");
        btnWithdraw.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnWithdraw.setBounds(236, 115, 150, 20);
        contentPane.add(btnWithdraw);

        btnWithdraw.addActionListener(e -> {
            new Withdraw().setVisible(true);
            this.setVisible(false);
        });

        // FETCH ALL DATA
        JButton btnFetch = new JButton("FETCH ALL DATA");
        btnFetch.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnFetch.setBounds(236, 159, 150, 20);
        contentPane.add(btnFetch);

        btnFetch.addActionListener(e -> {
            new Fetchalldata().setVisible(true);
            this.setVisible(false);
        });
    }
}
