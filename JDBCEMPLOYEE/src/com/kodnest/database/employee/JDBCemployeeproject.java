package com.kodnest.database.employee;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class JDBCemployeeproject extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JDBCemployeeproject frame = new JDBCemployeeproject();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JDBCemployeeproject() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(51, 153, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(77, 23, 300, 18);
        contentPane.add(lblNewLabel);

        JButton fetchBtn = new JButton("FETCH EMPLOYEE");
        fetchBtn.setBounds(35, 69, 150, 30);
        styleButton(fetchBtn);
        contentPane.add(fetchBtn);

        JButton btnFetchAll = new JButton("FETCH ALL EMPLOYEE");
        btnFetchAll.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnFetchAll.setBackground(new Color(204, 102, 102));
        btnFetchAll.setBounds(233, 69, 180, 30);
        btnFetchAll.setOpaque(true);
        btnFetchAll.setBorderPainted(false);
        styleButton(btnFetchAll);
        contentPane.add(btnFetchAll);

        JButton btnAddEmployee = new JButton("ADD EMPLOYEE");
        btnAddEmployee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnAddEmployee.setBackground(new Color(204, 102, 102));
        btnAddEmployee.setBounds(35, 124, 150, 30);
        styleButton(btnAddEmployee);
        btnAddEmployee.setOpaque(true);
        btnAddEmployee.setBorderPainted(false);
        contentPane.add(btnAddEmployee);

        JButton btnUpdateEmployee = new JButton("UPDATE EMPLOYEE");
        btnUpdateEmployee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnUpdateEmployee.setBackground(new Color(204, 102, 102));
        btnUpdateEmployee.setBounds(233, 124, 180, 30);
        styleButton(btnUpdateEmployee);
        btnUpdateEmployee.setOpaque(true);
        btnUpdateEmployee.setBorderPainted(false);
        contentPane.add(btnUpdateEmployee);

        JButton btnDeleteEmployee = new JButton("DELETE EMPLOYEE");
        btnDeleteEmployee.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnDeleteEmployee.setBackground(new Color(204, 102, 102));
        btnDeleteEmployee.setBounds(113, 184, 180, 30);
        styleButton(btnDeleteEmployee);
        btnDeleteEmployee.setOpaque(true);
        btnDeleteEmployee.setBorderPainted(false);
        contentPane.add(btnDeleteEmployee);

        // 🔥 ONLY NAVIGATION (BACK BUTTON WILL WORK)

        fetchBtn.addActionListener(e -> {
            new Fetch(this).setVisible(true);
            this.setVisible(false);
        });

        btnFetchAll.addActionListener(e -> {
            new FetchAll(this).setVisible(true);
            this.setVisible(false);
        });

        btnAddEmployee.addActionListener(e -> {
            new insertemployee(this).setVisible(true);
            this.setVisible(false);
        });

        btnUpdateEmployee.addActionListener(e -> {
            new Update(this).setVisible(true);
            this.setVisible(false);
        });

        btnDeleteEmployee.addActionListener(e -> {
            new Delete(this).setVisible(true);
            this.setVisible(false);
        });
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btn.setBackground(new Color(204, 102, 102));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
    }
}
