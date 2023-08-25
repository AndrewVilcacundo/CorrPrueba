import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class datos {
    private JTextField codigo;
    private JTextField cedula;
    private JTextField nombre;
    private JTextField fecha;
    private JButton borrarButton;
    private JButton actualizarButton;
    private JButton ingresarButton;
    private JButton limpiarFormularioButton;
    private JButton buscarPorCodigoButton;
    private JButton bucarPorNombreButton;
    private JButton buscarPorSignoButton;
    private JComboBox sig;
    private JPanel datos;

    //Conectar a la base de datos MYSQL WORKBENCH
    static final String DB_URL="jdbc:mysql://localhost/prueba";
    static final String USER="root";
    static final String PASS="root_bas3";
    static final String QUERY="Select * From datos ;";
    private boolean data = false;


    public static String nomx;
    public static String fechx;
    public static String sigx;

    registro persona = new registro();
    public datos(){
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectar();
                int codx = Integer.parseInt(codigo.getText());
                eliminar(codx);
            }
        });
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectar();
                int codx = Integer.parseInt(codigo.getText());
                int cedx = Integer.parseInt(cedula.getText());
                nomx =nombre.getText().trim();
                fechx = fecha.getText().trim();
                sigx = (String) sig.getSelectedItem();
                ingresar(codx,cedx,nomx,fechx,sigx);
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codx = Integer.parseInt(codigo.getText());
                int cedx = Integer.parseInt(cedula.getText());
                nomx=nombre.getText().trim();
                fechx=fecha.getText().trim();
                sigx =(String) sig.getSelectedItem();
                actualizar(codx,cedx,nomx,fechx,sigx);
            }
        });
        limpiarFormularioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codigo.setText("");
                cedula.setText("");
                nombre.setText("");
                fecha.setText("");
                sig.setSelectedItem("");
            }
        });
        buscarPorCodigoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                persona.setCodigo(codigo.getText());
                try(
                        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                        Statement stat = conn.createStatement();
                        ResultSet rc = stat.executeQuery(QUERY);
                ){
                    while(rc.next()) {
                        if (persona.getCodigo().equals(rc.getString("codigo"))) {
                            nombre.setText(rc.getString("Nombre"));
                            cedula.setText(rc.getString("cedula"));
                            fecha.setText(rc.getString("FechaN"));
                            sig.setSelectedItem(rc.getString("Signo"));
                            data = false;
                            break;
                        }
                        else{
                            data = true;
                        }
                    }
                    if(data == true){
                        JOptionPane.showMessageDialog(null,"USUARIO NO ENCONTRADO","Regitros",JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        bucarPorNombreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                persona.setNombre(nombre.getText());
                try(
                        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                        Statement stat = conn.createStatement();
                        ResultSet rc = stat.executeQuery(QUERY);
                ){
                    while(rc.next()) {
                        if (persona.getNombre().equals(rc.getString("Nombre"))) {
                            codigo.setText(rc.getString("codigo"));
                            cedula.setText(rc.getString("cedula"));
                            fecha.setText(rc.getString("FechaN"));
                            sig.setSelectedItem(rc.getString("Signo"));
                            data = false;
                            break;
                        }
                        else{
                            data = true;
                        }
                    }
                    if(data == true){
                        JOptionPane.showMessageDialog(null,"USUARIO NO ENCONTRADO","Regitros",JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buscarPorSignoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                persona.setSigno((String) sig.getSelectedItem());
                try(
                        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                        Statement stat = conn.createStatement();
                        ResultSet rc = stat.executeQuery(QUERY);
                ){
                    while(rc.next()) {
                        if (persona.getSigno().equals(rc.getString("Signo"))) {
                            codigo.setText(rc.getString("codigo"));
                            cedula.setText(rc.getString("cedula"));
                            fecha.setText(rc.getString("FechaN"));
                            nombre.setText(rc.getString("Nombre"));
                            data = false;
                            break;
                        }
                        else{
                            data = true;
                        }
                    }
                    if(data == true){
                        JOptionPane.showMessageDialog(null,"USUARIO NO ENCONTRADO","Regitros",JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public static void eliminar(int cod){
        String query2 = "DELETE FROM datos where codigo = '"+ cod +"'";
        try(
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS); //Esencial para la conección
                Statement stmt= conn.createStatement();
        ){
            stmt.executeUpdate(query2);
            System.out.println("----------------------------------------------");
            System.out.println("Usuario Eliminado");
            System.out.println("----------------------------------------------");
        }catch (Exception el){
            throw new RuntimeException(el);
        }
    }
    public static void ingresar(int cod, int ced, String nom, String fech,String signo){
        String query3 = " insert into datos values('"+cod+"',+'"+ced+"',+'"+nom+"','"+fech+"','"+signo+"')";
        try(
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS); //Esencial para la conección
                Statement stmt= conn.createStatement();
        ){
            stmt.executeUpdate(query3);
            System.out.println("----------------------------------------------");
            System.out.println("Usuario Ingresado");
            System.out.println("----------------------------------------------");
        }catch (Exception el){
            throw new RuntimeException(el);
        }
    }
    public static void actualizar(int cod, int ced, String nom, String fech,String signo){
        String query4 = "UPDATE datos SET cedula = '" + ced + "', Nombre = '" + nom + "', FechaN = '" + fech + "', Signo = '" + signo + "' WHERE codigo = '" + cod + "'";
        try(
                Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
                Statement stmt= conn.createStatement();
        ){
            stmt.executeUpdate(query4);
            System.out.println("----------------------------------------------");
            System.out.println("Usuario Actualizado");
            System.out.println("----------------------------------------------");
        }catch (Exception el){
            throw new RuntimeException(el);
        }
    }
    public static void conectar(){
        try{
            final String DB_URL="jdbc:mysql://localhost/prueba";
            final String USER="root";
            final String PASS="root_bas3";
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS); //Esencial para la conección
            Statement stmt= conn.createStatement();

        }catch(SQLException S){
            JOptionPane.showMessageDialog(null,S.getMessage(),"ERROR DE CONEXION",JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("datos");
        frame.setContentPane(new datos().datos);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(750, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

