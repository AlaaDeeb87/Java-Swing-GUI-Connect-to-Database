package com.company;

import java.sql.*;
import java.util.Scanner;

public class Cyklar {
    static Connection cn=null;
    static PreparedStatement pstat=null;
    static ResultSet rs=null;

    static final String DBAddress = "jdbc:mysql://localhost:3306/swing_java";
    static final String user ="root";
    static final String password="8709";

    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Lägg till cykel: type 1");
        System.out.println("Hämta cyklar från databas: type 2");
        int Userinput= scan.nextInt();
        if (Userinput == 1 ){
            try {
                Scanner sca = new Scanner(System.in);

                System.out.println("Ange cykelstatus: ");
                String cykelstatus = sca.nextLine();
                System.out.println("Ange cykeltillverkare: ");
                String cykelfabrik = sca.nextLine();

                cn= DriverManager.getConnection(DBAddress,user,password);

               // pstat = cn.prepareStatement( "INSERT INTO cyklar (cykel_status, cykel_fabrik) VALUES(?, ?)");
                pstat = cn.prepareStatement( "CALL addNewCykel(?,?)");
                pstat.setString(1, cykelstatus);
                pstat.setString(2, cykelfabrik);
                pstat.executeUpdate();

                cn.close();
                System.out.println("Cykeln har registrerats!");
            } catch (SQLException e) {
                System.out.println(e);
            }

        } else if(Userinput ==2) {
            try {

                cn= DriverManager.getConnection(DBAddress,user,password);

                pstat = cn.prepareStatement( "SELECT * FROM cyklar");
               // pstat = cn.prepareStatement( "CALL gitAllCyklar()");

                rs= pstat.executeQuery();


                while (rs.next()) {
                    System.out.println("Cykel ID: "+ rs.getString("id") + " ** Cykelstatus: " + rs.getString("cykel_status") +
                            " ** Cykeltillverkare: " + rs.getString("cykel_fabrik"));
                }
                cn.close();
                System.out.println("Cykeln har blivet hämtad från databasen!");

            } catch (SQLException e) {
                System.out.println(e);
            }


        }

    }
}
