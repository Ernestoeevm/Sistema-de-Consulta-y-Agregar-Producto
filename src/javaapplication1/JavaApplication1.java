package javaapplication1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JavaApplication1 {

    public static void main(String[] args) {
        String prueba = "no ok";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String _url = "jdbc:mysql: //localhost/tienda?user=root&password=mysqladmin";
            Connection connect = DriverManager.getConnection(_url);
            Statement statement = connect.createStatement();
            String _query = "Select * from producto";
            ResultSet resultSet = statement.executeQuery(_query);
            while (resultSet.next()) {
                int idProducto = resultSet.getInt("id_producto");
                String desProd = resultSet.getString("desc_producto");
                int precio = resultSet.getInt("precio");
                System.out.println("Id: " + idProducto);
                System.out.println("desc " + desProd);
                System.out.println("precio " + precio);
                System.out.println("------------------------\n");
            }

            Scanner scan = new Scanner(System.in);
            System.out.println("¿Qué deseas hacer: Insertar / Borrar/ Actualizar / Consultar");
            String accion = scan.nextLine();

            if (accion.equals("Insertar")) {
                scan = new Scanner(System.in);
                System.out.println("Ingresar el id_producto");
                String idProd = scan.nextLine();

                scan = new Scanner(System.in);
                System.out.println("Ingresa el desc_producto");
                String descProd = scan.nextLine();

                scan = new Scanner(System.in);
                System.out.println("Ingresa el precio");
                String precio = scan.nextLine();
                _query = "Insert INTO producto values(?,?,?)";
                PreparedStatement ps = connect.prepareStatement(_query);
                ps.setInt(1, Integer.parseInt(idProd));
                ps.setString(2, descProd);
                ps.setInt(3, Integer.parseInt(precio));
                ps.executeUpdate();

            } else if (accion.equals("Borrar")) {
                scan = new Scanner(System.in);
                System.out.println("Ingresa el id_producto");
                String idProd = scan.nextLine();
                _query = "Delete from Producto where id_Producto = ? ";
                PreparedStatement ps = connect.prepareCall(_query);
                ps.setInt(1, Integer.parseInt(idProd));
                ps.executeUpdate();
            } else if (accion.equals("Actualizar")) {
                scan = new Scanner(System.in);
                System.out.println("Ingresa el id_Producto");
                String idProd = scan.nextLine();

                scan = new Scanner(System.in);
                System.out.println("Ingresa el desc_producto");
                String descProd = scan.nextLine();

                

                scan= new Scanner(System.in);
                System.err.println("1 Ingresa el precio");
                  int precio = scan.nextInt();
                _query= "{call Insert_producto(?, ?)}";
                CallableStatement stmt = connect.prepareCall(_query);
                stmt.setString(1, descProd);
                stmt.setInt(2, precio);
                stmt.execute();
                //               _query = "Update producto set desc_producto = ?,  precio= ? where id_producto=?";
                //             PreparedStatement ps = connect.prepareStatement(_query);
                //           ps.setString(1, descProd);
                //         ps.setInt(2, Integer.parseInt(precio));
                //       ps.setInt(3, Integer.parseInt(idProd));
                //     ps.executeUpdate();
                resultSet.close();
                statement.close();
                connect.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
