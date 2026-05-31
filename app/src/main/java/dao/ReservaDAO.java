package dao;

import conexionBBDD.ConexionBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Reserva;

public class ReservaDAO {

    private ConexionBBDD conexionBBDD = new ConexionBBDD();
    private Connection connection;
    private PreparedStatement prstmt;
    private ResultSet resultSet;

    public ReservaDAO() {
        connection = conexionBBDD.conectarBBDD();
    }

    public List<String> obtenerHorasOcupadas(int idInstalacion, String fecha) {
        List<String> horas = new ArrayList<>();
        String query = "select hora_inicio from reservas " +
                "where id_instalacion=? and fecha=? and estado='activa'";
        try {
            prstmt = connection.prepareStatement(query);
            prstmt.setInt(1, idInstalacion);
            prstmt.setString(2, fecha);
            resultSet = prstmt.executeQuery();
            while (resultSet.next()) {
                horas.add(resultSet.getString("hora_inicio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horas;
    }

    public int insertarReserva(Reserva reserva) {
        String query = "insert into reservas(id_usuario, id_instalacion, fecha, hora_inicio, hora_fin, importe, nombre_ins, estado) " +
                "values(?,?,?,?,?,?,?,'activa') returning id_reserva;";
        try {
            prstmt = connection.prepareStatement(query);
            prstmt.setInt(1, reserva.getIdUsuario());
            prstmt.setInt(2, reserva.getIdInstalacion());
            prstmt.setString(3, reserva.getFecha());
            prstmt.setString(4, reserva.getHoraInicio());
            prstmt.setString(5, reserva.getHoraFin());
            prstmt.setDouble(6, reserva.getImporte());
            prstmt.setString(7, reserva.getNombreInstalacion());
            resultSet = prstmt.executeQuery();
            if (resultSet.next()) return resultSet.getInt("id_reserva");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Reserva> obtenerReservasUsuario(int idUsuario) {
        List<Reserva> lista = new ArrayList<>();
        String query = "select r.*, i.nombre as nombre_instalacion, i.tipo as tipo_instalacion " +
                "from reservas r join instalaciones i on r.id_instalacion = i.id_instalacion " +
                "WHERE r.id_usuario=? and r.estado='activa' order by r.fecha, r.hora_inicio";
        try {
            prstmt = connection.prepareStatement(query);
            prstmt.setInt(1, idUsuario);
            resultSet = prstmt.executeQuery();
            while (resultSet.next()) {
                Reserva res = new Reserva();
                res.setIdReserva(resultSet.getInt("id_reserva"));
                res.setIdUsuario(resultSet.getInt("id_usuario"));
                res.setIdInstalacion(resultSet.getInt("id_instalacion"));
                res.setNombreInstalacion(resultSet.getString("nombre_instalacion"));
                res.setTipoInstalacion(resultSet.getString("tipo_instalacion"));
                res.setFecha(resultSet.getString("fecha"));
                res.setHoraInicio(resultSet.getString("hora_inicio"));
                res.setHoraFin(resultSet.getString("hora_fin"));
                res.setImporte(resultSet.getDouble("importe"));
                res.setEstado(resultSet.getString("estado"));
                lista.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean cancelarReserva(int idReserva) {
        String query = "update reservas set estado='cancelada' where id_reserva=?";
        try {
            prstmt = connection.prepareStatement(query);
            prstmt.setInt(1, idReserva);
            int rows = prstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}