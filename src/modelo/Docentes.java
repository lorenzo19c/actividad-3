/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lorenzo
 */
public class Docentes extends Persona {
    private String nit;
    private int id;
    private String codigo_docente;
    private double salario;
    private String fecha_ingreso_laboral;
    private String fecha_ingreso_registro;
    Conexion cn;

    // Constructor vacío
    public Docentes() {}

    // Constructor con parámetros
    public Docentes(int id, String nit, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento,
                    String codigo_docente, double salario, String fecha_ingreso_laboral, String fecha_ingreso_registro) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);  // Heredando los datos de Persona
        this.nit = nit;
        this.id = id;
        this.codigo_docente = codigo_docente;
        this.salario = salario;
        this.fecha_ingreso_laboral = fecha_ingreso_laboral;
        this.fecha_ingreso_registro = fecha_ingreso_registro;
    }

    // Getters y setters para los nuevos campos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCodigo_docente() {
        return codigo_docente;
    }

    public void setCodigo_docente(String codigo_docente) {
        this.codigo_docente = codigo_docente;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getFecha_ingreso_laboral() {
        return fecha_ingreso_laboral;
    }

    public void setFecha_ingreso_laboral(String fecha_ingreso_laboral) {
        this.fecha_ingreso_laboral = fecha_ingreso_laboral;
    }

    public String getFecha_ingreso_registro() {
        return fecha_ingreso_registro;
    }

    public void setFecha_ingreso_registro(String fecha_ingreso_registro) {
        this.fecha_ingreso_registro = fecha_ingreso_registro;
    }

    // Método para leer los docentes desde la base de datos
    public DefaultTableModel leer() {
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "SELECT idDocentes, nit, nombres, apellidos, direccion, telefono, fecha_nacimiento, " +
                           "codigo_docente, salario, fecha_ingreso_laboral, fecha_ingreso_registro FROM docentes;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);

            // Encabezado de la tabla
            String encabezado[] = {"IdDocentes", "Nit", "Nombres", "Apellidos", "Dirección", "Teléfono", "Nacimiento", "Código", "Salario", "Ingreso Laboral", "Ingreso Registro"};
            tabla.setColumnIdentifiers(encabezado);

            // Llenar la tabla con los resultados de la consulta
            String datos[] = new String[11];
            while (consulta.next()) {
                datos[0] = consulta.getString("idDocentes");
                datos[1] = consulta.getString("nit");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("codigo_docente");
                datos[8] = consulta.getString("salario");
                datos[9] = consulta.getString("fecha_ingreso_laboral");
                datos[10] = consulta.getString("fecha_ingreso_registro");
                tabla.addRow(datos);
            }

            cn.cerrar_conexion();

        } catch (SQLException ex) {
            cn.cerrar_conexion();
            System.out.println("Error: " + ex.getMessage());
        }
        return tabla;
    }

    // Método para agregar un docente
    @Override
    public void agregar() {
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "INSERT INTO docentes (nit, nombres, apellidos, direccion, telefono, fecha_nacimiento, codigo_docente, salario, fecha_ingreso_laboral, fecha_ingreso_registro) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getCodigo_docente());
            parametro.setDouble(8, getSalario());
            parametro.setString(9, getFecha_ingreso_laboral());
            parametro.setString(10, getFecha_ingreso_registro());

            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar) + " Registro Ingresado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Método para modificar un docente
    @Override
    public void modificar() {
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "UPDATE docentes SET nit = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, " +
                           "codigo_docente = ?, salario = ?, fecha_ingreso_laboral = ?, fecha_ingreso_registro = ? WHERE idDocentes = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getNit());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getCodigo_docente());
            parametro.setDouble(8, getSalario());
            parametro.setString(9, getFecha_ingreso_laboral());
            parametro.setString(10, getFecha_ingreso_registro());
            parametro.setInt(11, getId());

            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar) + " Registro Actualizado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Método para eliminar un docente
    @Override
    public void eliminar() {
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM docentes WHERE idDocentes = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());

            int ejecutar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(ejecutar) + " Registro Eliminado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
