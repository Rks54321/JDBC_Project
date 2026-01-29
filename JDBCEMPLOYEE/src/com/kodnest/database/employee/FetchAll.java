package com.kodnest.database.employee;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FetchAll extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnBack;

    private List<Color> rowColors = new ArrayList<>();
    private Random random = new Random();

    private JFrame parentFrame;

    public FetchAll(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setTitle("Fetch All Employees");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 350);

        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 102, 102));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("FETCH ALL EMPLOYEES");
        title.setFont(new Font("Tahoma", Font.BOLD, 16));
        title.setBounds(150, 10, 250, 25);
        contentPane.add(title);

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Gender", "Salary"}, 0);

        table = new JTable(model);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 8));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (row < rowColors.size()) {
                    c.setBackground(rowColors.get(row));
                }

                c.setForeground(Color.WHITE);
                setHorizontalAlignment(CENTER);
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 60, 400, 210);
        contentPane.add(scrollPane);

        btnBack = new JButton("BACK");
        btnBack.setBounds(392, 289, 84, 20);
        contentPane.add(btnBack);

        btnBack.addActionListener(e -> {
            parentFrame.setVisible(true);
            this.dispose();
        });

        SwingUtilities.invokeLater(() -> fetchAllEmployees());
    }

    private void fetchAllEmployees() {

        model.setRowCount(0);
        rowColors.clear();

        String URL =
            "jdbc:mysql://localhost:3306/employeedb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String USER = "root";
        String PASSWORD = "Rakesh143#";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM employee");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getInt("salary")
                });

                rowColors.add(generateRandomColor());
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to fetch employees",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Color generateRandomColor() {
        return new Color(
                random.nextInt(150),
                random.nextInt(150),
                random.nextInt(150)
        );
    }
}
