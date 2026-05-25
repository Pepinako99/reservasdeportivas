package dao;


import conexionBBDD.ConexionBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Instalacion;

public class InstalacionDAO {

    private ConexionBBDD conexionBBDD = new ConexionBBDD();
    private Connection connection;
    private PreparedStatement prstmt;
    private ResultSet resultSet;

    public InstalacionDAO() {
        connection = conexionBBDD.conectarBBDD();
    }

    public List<Instalacion> obtenerTodasInstalaciones() {
        List<Instalacion> lista = new ArrayList<>();
        String query = "select * from instalaciones order by tipo, nombre";
        try {
            prstmt = connection.prepareStatement(query);
            resultSet = prstmt.executeQuery();
            while (resultSet.next()) {
                Instalacion i = new Instalacion();
                i.setIdInstalacion(resultSet.getInt("id_instalacion"));
                i.setNombre(resultSet.getString("nombre"));
                i.setTipo(resultSet.getString("tipo"));
                i.setDescripcion(resultSet.getString("descripcion"));
                i.setPrecioPorHora(Instalacion.getPrecioPorTipo(resultSet.getString("tipo")));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}