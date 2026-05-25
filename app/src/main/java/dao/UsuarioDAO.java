package dao;

import conexionBBDD.ConexionBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Usuario;

public class UsuarioDAO {

    private ConexionBBDD conexionBBDD = new ConexionBBDD();
    private Connection connection;
    private PreparedStatement prstmt;
    private ResultSet resultSet;

    public UsuarioDAO() {

        connection = conexionBBDD.conectarBBDD();
    }

    public int insertarUsuario(Usuario usuario) {
        String query = "insert into usuarios(nombre, correo, contrasena) VALUES(?,?,?) RETURNING id_usuario;";
        try {
            prstmt = connection.prepareStatement(query);
            prstmt.setString(1, usuario.getNombre());
            prstmt.setString(2, usuario.getCorreo());
            prstmt.setString(3, usuario.getContrasena());
            resultSet = prstmt.executeQuery();
            if (resultSet.next()) return resultSet.getInt("id_usuario");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Usuario login(String login, String contrasena) {
        String query = "select * from usuarios where (correo=? OR nombre=?) and contrasena=?";
        try {
            prstmt = connection.prepareStatement(query);
            prstmt.setString(1, login);
            prstmt.setString(2, login);
            prstmt.setString(3, contrasena);
            resultSet = prstmt.executeQuery();
            if (resultSet.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(resultSet.getInt("id_usuario"));
                u.setNombre(resultSet.getString("nombre"));
                u.setCorreo(resultSet.getString("correo"));
                u.setContrasena(resultSet.getString("contrasena"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}