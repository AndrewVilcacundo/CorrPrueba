// Librerias
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// Clase Pública
public class Forma_1 {

    /***************************************************************************************************************************************/
    // Componentes
    private JPanel Fondo;
    private JPanel Caja;
    private JLabel Texto_Titulo;
    private JLabel codigoLabel;
    private JLabel cédulaLabel;
    private JLabel nombreLabel;
    private JLabel fechaDeNacimientoLabel;
    private JLabel signoZodiacoLabel;
    private JButton buscarPorCódigoButton;
    private JButton buscarPorNombreButton;
    private JButton buscarPorSignoButton;
    private JTextField Texto_Codigo;
    private JTextField Texto_Cedula;
    private JTextField Texto_Nombre;
    private JTextField Texto_Fecha;
    private JComboBox Eleccion_Zodiaco;
    private JButton Boton_Borrar;
    private JButton Boton_Actualizar;
    private JButton Boton_Ingresar;
    private JButton Boton_Limpiar;

    /***************************************************************************************************************************************/


    /***************************************************************************************************************************************/
    // Variables
    static final String DB_URL = "jdbc:sqlserver://LAPTOP-KR34L9MD\\SQLEXPRESS:55395;database=prueba;encrypt=true;trustServerCertificate=true;";
    static final String USER="andreww";
    static final String PASS="123";
    static String QUERY_SELECT_CODIGO;
    static String QUERY_SELECT_SIGNO;
    static String QUERY_SELECT_NOMBRE;
    static String QUERY_INSERT;
    static String QUERY_DELETE;
    static String QUERY_UPDATE;


    /***************************************************************************************************************************************/
    // Main

    public static void main(String[] args) {
        JFrame frame = new JFrame("Forma_1");
        frame.setContentPane(new Forma_1().Caja);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    /***************************************************************************************************************************************/


    /***************************************************************************************************************************************/
    // Funciones
    // Funcion Crear
    private void Funcion_Insertar(String QUERY_INSERT){
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
        ) {
            int rowsAffected = stmt.executeUpdate(QUERY_INSERT);
            Texto_Insertar();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    // Funcion Borrar
    private void Funcion_Borrar(String QUERY_DELETE){
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
        ) {
            int rowsAffected = stmt.executeUpdate(QUERY_DELETE);
            Texto_Borrar();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    // Funcion Actualizar
    private void Funcion_Actualizar(String QUERY_UPDATE){
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
        ) {
            int rowsAffected = stmt.executeUpdate(QUERY_UPDATE);
            Texto_Actualizar();
        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    // Funcion Mostrar
    private void Funcion_Mostrar_Codigo(String QUERY_SELECT_CODIGO){
        try(
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                Statement stmt= conn.createStatement();
                ResultSet rs= stmt.executeQuery(QUERY_SELECT_CODIGO);
        ){
            while(rs.next()){
                Texto_Mostrar(rs);
            }

        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }
    private void Funcion_Mostrar_Nombre(String QUERY_SELECT_NOMBRE){
        try(
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                Statement stmt= conn.createStatement();
                ResultSet rs= stmt.executeQuery(QUERY_SELECT_NOMBRE);
        ){
            while(rs.next()){
                Texto_Mostrar(rs);
            }

        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }
    private void Funcion_Mostrar_Signo(String QUERY_SELECT_SIGNO){
        try(
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                Statement stmt= conn.createStatement();
                ResultSet rs= stmt.executeQuery(QUERY_SELECT_SIGNO);
        ){
            while(rs.next()){
                Texto_Mostrar(rs);
            }

        } catch (SQLException x) {
            throw new RuntimeException(x);
        }
    }

    /***************************************************************************************************************************************/
    // Funcion Mensaje Insertar
    private void Texto_Insertar(){
        System.out.println("\n*******************");
        System.out.println("Elemento Insertado");
        System.out.println("********************");
    }

    // Funcion Mensaje Borrar
    private void Texto_Borrar(){
        System.out.println("\n*******************");
        System.out.println("Elemento Borrado");
        System.out.println("********************");
    }

    // Funcion Mensaje Actualizar
    private void Texto_Actualizar(){
        System.out.println("\n*******************");
        System.out.println("Elemento Actualizado");
        System.out.println("********************");
    }

    // Funcion Mensaje Mostrar
    private void Texto_Mostrar(ResultSet rs) throws SQLException {
        System.out.println("\n*******************");
        System.out.println("Código:"+rs.getInt("codigo"));
        System.out.println("Cédula:"+rs.getInt("cedula"));
        System.out.println("Nombre:"+rs.getString("nombre"));
        System.out.println("Fecha Nacimiento:"+rs.getString("fechaN"));
        System.out.println("Signo:"+rs.getString("signo"));
        System.out.println("********************");
    }

    /***************************************************************************************************************************************/

    /***************************************************************************************************************************************/
    // Lógica
    public Forma_1() {
        buscarPorCódigoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QUERY_SELECT_CODIGO = "SELECT * FROM personas where codigo = " + Texto_Codigo.getText();
                Funcion_Mostrar_Codigo(QUERY_SELECT_CODIGO);
            }
        });
        buscarPorNombreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Nombre = Texto_Nombre.getText();
                QUERY_SELECT_NOMBRE = "SELECT * FROM personas where nombre = " + "'" + Nombre + "'";
                Funcion_Mostrar_Nombre(QUERY_SELECT_NOMBRE);
            }
        });
        buscarPorSignoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Signo = (String) Eleccion_Zodiaco.getSelectedItem();
                QUERY_SELECT_SIGNO= "SELECT * FROM personas where signo = " + "'" + Signo+ "'";
                Funcion_Mostrar_Signo(QUERY_SELECT_SIGNO);
            }
        });
        Boton_Borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Borrar = Texto_Codigo.getText();
                QUERY_DELETE = "Delete from personas where codigo = " + Borrar;
                Funcion_Borrar(QUERY_DELETE);
            }
        });
        Boton_Actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Actualizar = Texto_Codigo.getText();
                QUERY_UPDATE= "UPDATE personas SET Nombre =" + "'" +  Texto_Nombre.getText() + "'" + " WHERE codigo = " + Actualizar;
                Funcion_Actualizar(QUERY_UPDATE);
            }
        });
        Boton_Ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QUERY_INSERT = "INSERT INTO personas (codigo, nombre, fechaN, cedula, signo) VALUES" + "(" + Texto_Codigo.getText() + "," + "'" +Texto_Nombre.getText() + "'" + "," + "'" + Texto_Fecha.getText() + "'" + "," + "'" + Texto_Cedula.getText() + "'" + "," + "'" + (String) Eleccion_Zodiaco.getSelectedItem() + "'" + ")";
                Funcion_Insertar(QUERY_INSERT);
            }
        });
        Boton_Limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Texto_Codigo.setText("");
                Texto_Nombre.setText("");
                Texto_Cedula.setText("");
                Texto_Fecha.setText("");
            }
        });
    }

}