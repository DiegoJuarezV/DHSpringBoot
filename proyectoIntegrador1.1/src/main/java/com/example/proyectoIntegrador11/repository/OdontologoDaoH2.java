package com.example.proyectoIntegrador11.repository;

import com.example.proyectoIntegrador11.entity.Odontologo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OdontologoDaoH2 implements iDao<Odontologo>{
    private static final Logger logger = Logger.getLogger(OdontologoDaoH2.class);
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGOS (NUMERO_MATRICULA, NOMBRE, APELLIDO) VALUES(?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM ODONTOLOGOS WHERE ID = ?";
    private static final String SQL_UPDATE = "UPDATE ODONTOLOGOS SET NUMERO_MATRICULA=?, NOMBRE=?, APELLIDO=? WHERE ID = ?";
    private static final String SQL_DELETE = "DELETE FROM ODONTOLOGOS WHERE ID = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psInsert.setInt(1, odontologo.getNumeroMatricula());
            psInsert.setString(2, odontologo.getNombre());
            psInsert.setString(3, odontologo.getApellido());
            psInsert.execute();
            ResultSet resultSet = psInsert.getGeneratedKeys();
            while (resultSet.next()) {
                odontologo.setId(resultSet.getInt(1));
                logger.info("Odontologo guardado exitosamente");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return odontologo;
    }

    @Override
    public Odontologo buscarPorID(Integer id) {
        logger.info("iniciando la busqueda de un odontologo por id: " + id);
        Connection connection= null;
        Odontologo odontologo= null;
        try{
            connection= BD.getConnection();
            PreparedStatement psSelectOne= connection.prepareStatement(SQL_SELECT_BY_ID);
            psSelectOne.setInt(1,id);
            ResultSet rs= psSelectOne.executeQuery();
            logger.info("Odontologo Buscado exitosamente!");
            while(rs.next()){
                odontologo = new Odontologo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return odontologo;
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setInt(1, odontologo.getNumeroMatricula());
            psUpdate.setString(2, odontologo.getNombre());
            psUpdate.setString(3, odontologo.getApellido());
            psUpdate.setInt(4, odontologo.getId());
            psUpdate.execute();
            logger.warn("Odontologo actualizado");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1, id);
            psDelete.executeUpdate();
            logger.warn("Odontologo eliminado!");
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            connection = BD.getConnection();
            Statement psSearchAll = connection.createStatement();
            ResultSet rs = psSearchAll.executeQuery(SQL_SELECT_ALL);
            logger.info("Odontologos buscados exitosamente!");
            while (rs.next()) {
                Integer id = rs.getInt(1);
                Integer matricula = rs.getInt(2);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                odontologos.add(new Odontologo(id, matricula, nombre, apellido));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return  odontologos;
    }

    @Override
    public Odontologo buscarPorString(String valor) {
        return null;
    }
}
