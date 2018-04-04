/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jensy
 */
public class Crud {

    private final String tabla = "voto";

    public void guardar(Connection conexion, VotoSchema votoSchema) throws SQLException {
        try {
            PreparedStatement consulta;
            if (votoSchema.getId() == null) {
                consulta = conexion.prepareStatement("INSERT INTO voto (imdb_id, title, cantidad) VALUES(?, ?, ?)");
                consulta.setString(1, votoSchema.getImdbId());
                consulta.setString(2, votoSchema.getTitle());
                consulta.setInt(3, votoSchema.getCantidad());
            } else {
                consulta = conexion.prepareStatement("UPDATE voto SET imdb_id = ?, title = ?, cantidad = ? WHERE id = ?");
                consulta.setString(1, votoSchema.getImdbId());
                consulta.setString(2, votoSchema.getTitle());
                consulta.setInt(3, votoSchema.getCantidad());
                consulta.setInt(4, votoSchema.getId());
            }
            consulta.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    
    public VotoSchema recuperarPorTitulo(Connection conexion, String titulo) throws SQLException {
        VotoSchema votoSchema = null;
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT id, imdb_id, title, cantidad FROM voto WHERE title LIKE ?");
            consulta.setString(1, titulo);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                votoSchema = new VotoSchema(resultado.getInt("id"), resultado.getString("imdb_id"), resultado.getString("title"), resultado.getInt("cantidad"));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return votoSchema;
    }

    public List<VotoSchema> recuperarTodas(Connection conexion) throws SQLException {
        List<VotoSchema> tareas = new ArrayList<>();
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT id, imdb_id, title, cantidad FROM voto ORDER BY cantidad DESC");
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                tareas.add(new VotoSchema(resultado.getInt("id"), resultado.getString("imdb_id"), resultado.getString("title"), resultado.getInt("cantidad")));
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return tareas;
    }
}
