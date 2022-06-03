package conexion.mysql;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author IBERO
 */
public class ConexionMysql {
    //Declaraciones
    private static Connection con;// Declaramos la conexion a mysql
    private static final String driver = "com.mysql.jdbc.Driver";// JDBC DRIVER Declaramos los datos de conexion a la bd el driver
     //url de la bd y ultimo el nombre de la base de datos y si se utiliza caracteres especiales
    private static final String url = "jdbc:mysql://localhost:3306/iberodb?characterEncoding=UTF-8";
    /*data base credenciales*/
    private static final String user = "root";//nombre de usuario de nuestra db
    private static final String pass = "12345";//contraseña de acceso a nuestra db
   

    //Variable y objetos 
    DriverManager driverManager;
    private static Statement s;
    private static ResultSet rs;
    private static PreparedStatement ps;
    private static final Scanner entrada = new Scanner(System.in);
    private static String name, mail, phone;
    
    public static void main(String[] args) {
        // Reseteamos a null la conexion a la bd
        con = null;
        try {
            //registramos la clase del driver
            Class.forName(driver);
            // abrimos la conexion a la bd indicando la url el usuario y el password
            con = (Connection) DriverManager.getConnection(url, user, pass);
            // Si la conexion fue exitosa mostramos un mensaje de conexion exitosa
            System.out.println("Conexion establecida");
            
            if (con != null) {
                System.out.println("Seleccione una opcion : \n"
                        + "1.-Listar Usuario.\n"
                        + "2.-Crear nuevo Usuario.\n"
                        + "3.-Modificar Datos de Usuario.\n"
                        + "4.-Eliminar usuario");
                System.out.println("Escriba el número de la Opcion :");
                int seleccion = entrada.nextInt();
                switch (seleccion) {
                    case 1: {
                        ListarUsuarios();
                        // Cerramos la conexion a la base de datos.
                        s.close();
                        rs.close();
                        con.close();
                        break;
                    }
                    case 2: {
                        InsertUsuarios();
                        // Cerramos la conexion a la base de datos.
                        s.close();
                        rs.close();
                        con.close();
                        break;
                    }
                    case 3: {
                        System.out.println("Ingrese el ID del usuario a modificar");
                        int id = entrada.nextInt();
                        ModificarUsuarios(id);
                        // Cerramos la conexion a la base de datos.
                        s.close();
                        rs.close();
                        con.close();
                        break;
                    }
                    case 4: {
                        System.out.println("Ingrese el ID del usuario a eliminar");
                        int id = entrada.nextInt();
                        eliminarUsuario(id);
                        // Cerramos la conexion a la base de datos.
                        s.close();
                        rs.close();
                        con.close();
                        break;
                    }

                }
            }
        } catch (ClassNotFoundException | SQLException e) { //si existe algun error de la conexion mostramos el error
            System.out.println("Error de conexion " + e);
        }
    }

    

    public static void ListarUsuarios() throws SQLException {
        // Preparamos la consulta
        s = con.createStatement();
        //mandamos estatement el query y lo almacenamos en resultset
        rs = s.executeQuery("select iduser,nameuser,mailuser,phonouser from usuarios");
        // Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla.
        //obtenermos los datos de un resultset
        while (rs.next()) {
            //obetniendo los valores de las columnas y mostramos los datos obtenidos
            System.out.println("ID : " + rs.getInt(1) 
                    + " Nombre " + rs.getString(2) 
                    + " E-mail : " + rs.getString(3) 
                    + " Phone " + rs.getString(4));
        }

    }

    public static void InsertUsuarios() throws SQLException {
        System.out.println("Creando usuarios, complete los datos solicitado");
        System.out.print("Escriba nombre de usuario :");
        name = entrada.next();
        System.out.println("Escriba E-mail de usuario : ");
        mail = entrada.next();
        System.out.println("Escriba Phone Number de usuario : ");
        phone = entrada.next();
        // Reseteamos a null la conexion a la preparedstatement
        ps = null;
        ps = (PreparedStatement) con.prepareStatement("INSERT INTO iberodb.usuarios(nameuser,mailuser,phonouser) "
                + "VALUES (?,?,?)");
        ps.setString(1, name);
        ps.setString(2, mail);
        ps.setString(3, phone);
        ps.executeUpdate(); // ejecuta la query
        System.out.println("<b>se a insertado los datos</b>");
        ListarUsuarios();

    }

    public static void ModificarUsuarios(int id) throws SQLException {
        // create the java mysql update preparedstatement
        String sql = "update usuarios set nameuser = ?,mailuser=?,phonouser=? where iduser ='" + id + "'";
        ps = (PreparedStatement) con.prepareStatement(sql);
        System.out.println("Modificando datos del Usuario, complete los datos solicitado");
        System.out.print("Escriba nombre de usuario :");
        name = entrada.next();
        System.out.println("Escriba E-mail de usuario : ");
        mail = entrada.next();
        System.out.println("Escriba Phone Number de usuario : ");
        phone = entrada.next();
        ps.setString(1, name);
        ps.setString(2, mail);
        ps.setString(3, phone);
        // execute the java preparedstatement
        ps.executeUpdate();
        //Se actualizo los datos
        System.out.println("Datos Actualizdos del ID" + id);
        //mostramos datos de  la db
        ListarUsuarios();

    }

    public static void eliminarUsuario(int id) throws SQLException {
        // create the java mysql update preparedstatement
        String sql = "delete from usuarios where iduser ='" + id + "'";
        // Preparamos la consulta
        s = con.createStatement();
        s.executeUpdate(sql);
        System.out.println("Usuario Eliminado" + id);
        ListarUsuarios();

    }

}
