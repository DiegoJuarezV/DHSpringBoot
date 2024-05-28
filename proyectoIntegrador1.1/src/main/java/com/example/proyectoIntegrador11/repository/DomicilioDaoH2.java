package com.example.proyectoIntegrador11.repository;

import com.example.proyectoIntegrador11.entity.Domicilio;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DomicilioDaoH2 implements iDao<Domicilio>{
    private static final Logger logger = Logger.getLogger(DomicilioDaoH2.class);
    private static final String SQL_SELECT_ONE="SELECT * FROM DOMICILIO WHERE ID=?";
    private static final String SQL_INSERT = "INSERT INTO DOMICILIO (CALLE, NUMERO, LOCALIDAD, PROVINCIA) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE DOMICILIO SET CALLE=?, NUMERO=?, LOCALIDAD=?, PROVINCIA=? WHERE ID = ?";
    private static final String SQL_DELETE = "DELETE FROM DOMICILIO WHERE ID = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM DOMICILIO";

    @Override
    public Domicilio guardar(Domicilio domicilio) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, domicilio.getCalle());
            psInsert.setInt(2, domicilio.getNumero());
            psInsert.setString(3, domicilio.getLocalidad());
            psInsert.setString(4, domicilio.getProvincia());
            psInsert.execute();
            ResultSet resultSet = psInsert.getGeneratedKeys();
            while (resultSet.next()) {
                domicilio.setId(resultSet.getInt(1));
                logger.info("Domicilio guardado exitosamente");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return domicilio;
    }

    @Override
    public Domicilio buscarPorID(Integer id) {
        logger.info("iniciando la busqueda de un domicilio por id: " + id);
        Connection connection=null;
        Domicilio domicilio= null;
        try{
            connection= BD.getConnection();
            PreparedStatement psSelect= connection.prepareStatement(SQL_SELECT_ONE);
            psSelect.setInt(1,id);
            ResultSet rs= psSelect.executeQuery();
            while(rs.next()){
                domicilio= new Domicilio(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4), rs.getString(5));
            }
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return domicilio;
    }

    @Override
    public void actualizar(Domicilio domicilio) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, domicilio.getCalle());
            psUpdate.setInt(2, domicilio.getNumero());
            psUpdate.setString(3, domicilio.getLocalidad());
            psUpdate.setString(4, domicilio.getProvincia());
            psUpdate.setInt(5, domicilio.getId());
            logger.warn("Domicilio actualizado!");
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
            logger.warn("Domicilio eliminado!");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Domicilio> buscarTodos() {
        Connection connection = null;
        List<Domicilio> domicilios = new ArrayList<>();
        try {
            connection = BD.getConnection();
            PreparedStatement psSearchAll = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs = psSearchAll.executeQuery();
            logger.info("Domicilios buscados exitosamente!");
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String calle = rs.getString(2);
                Integer numero = rs.getInt(3);
                String localidad = rs.getString(4);
                String provincia = rs.getString(5);
                domicilios.add(new Domicilio(id, calle, numero, localidad, provincia));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return domicilios;
    }

    @Override
    public Domicilio buscarPorString(String valor) {
        return null;
    }
}
