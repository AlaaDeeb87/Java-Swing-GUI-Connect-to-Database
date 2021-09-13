package com.company;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CyklarGUI {
    private JPanel Main;
    private JTextField txtPrice;
    private JTextField txtStatus;
    private JTextField txtManufacturer;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CyklarGUI");
        frame.setContentPane(new CyklarGUI().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public void connect(){
        try {
            Class.forName("com.company.CyklarGUI");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_java", "root","8709");
            System.out.println("Success connected to database");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from bicycle");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



    public CyklarGUI() {
        connect();
        table_load();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String status, manufacturer, price;

                status = txtStatus.getText();
                manufacturer=txtManufacturer.getText();
                price=txtPrice.getText();

                try {
                    pst = con.prepareStatement("insert into bicycle(status,manufacturer,price)values(?,?,?)");
                    //
                    // pst = con.prepareStatement("CALL addNewBicycle(?,?,?)");
                    pst.setString(1, status);
                    pst.setString(2, manufacturer);
                    pst.setString(3, price);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "bicycle Added!");
                   // table_load();
                    txtStatus.setText("");
                    txtManufacturer.setText("");
                    txtPrice.setText("");
                    txtStatus.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {

                    String bicid = txtid.getText();

                    pst = con.prepareStatement("select status,manufacturer,price from bicycle where id = ?");
                    pst.setString(1, bicid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String status = rs.getString(1);
                        String manufacturer = rs.getString(2);
                        String price = rs.getString(3);

                        txtStatus.setText(status);
                        txtManufacturer.setText(manufacturer);
                        txtPrice.setText(price);

                    }
                    else
                    {
                        txtStatus.setText("");
                        txtManufacturer.setText("");
                        txtPrice.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Bicycle No!");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bicid, status, manufacturer, price;

                status = txtStatus.getText();
                manufacturer=txtManufacturer.getText();
                price=txtPrice.getText();
                bicid=txtid.getText();

                try {
                    pst = con.prepareStatement("update bicycle set status = ?,manufacturer = ?,price = ? where id = ?");
                    pst.setString(1, status);
                    pst.setString(2, manufacturer);
                    pst.setString(3, price);
                    pst.setString(4, bicid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Bicycle is updated!");
                    table_load();
                    txtStatus.setText("");
                    txtManufacturer.setText("");
                    txtPrice.setText("");
                    txtStatus.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bicid;
                bicid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from bicycle  where id = ?");

                    pst.setString(1, bicid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Bicycle is deleted!");
                    table_load();
                    txtStatus.setText("");
                    txtManufacturer.setText("");
                    txtPrice.setText("");
                    txtStatus.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
    }


}
