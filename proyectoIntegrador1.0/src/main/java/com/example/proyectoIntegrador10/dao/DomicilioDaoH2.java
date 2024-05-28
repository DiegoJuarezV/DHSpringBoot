package com.example.proyectoIntegrador10.dao;

import com.example.proyectoIntegrador10.model.Domicilio;
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
    private static final String SQL_UPDATE = "UPDATE DOMICILIO SET %s WHERE ID = ?";
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
    public void actualizar(Map<String, Object> dato, Integer id) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            StringBuilder setClause = new StringBuilder();
            for (Map.Entry<String, Object> entry : dato.entrySet()) {
                setClause.append(entry.getKey()).append(" = ?, ");
            }
            setClause.delete(setClause.length() - 2, setClause.length());
            String sql = String.format(SQL_UPDATE, setClause.toString());
            PreparedStatement psUpdate = connection.prepareStatement(sql);
            int index = 1;
            for (Object value : dato.values()) {
                psUpdate.setObject(index++, value);
            }
            psUpdate.setInt(index, id);
            psUpdate.executeUpdate();
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
