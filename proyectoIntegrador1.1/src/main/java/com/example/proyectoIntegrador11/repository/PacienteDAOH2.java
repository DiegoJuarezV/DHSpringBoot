package com.example.proyectoIntegrador11.repository;

import com.example.proyectoIntegrador11.entity.Domicilio;
import com.example.proyectoIntegrador11.entity.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PacienteDAOH2 implements iDao<Paciente> {
    private static final Logger logger= Logger.getLogger(PacienteDAOH2.class);
    private static final String SQL_INSERT="INSERT INTO PACIENTES (NOMBRE, APELLIDO, CEDULA, FECHA_INGRESO, DOMICILIO_ID, EMAIL) VALUES(?,?,?,?,?,?)";
    private static final String SQL_SELECT_ONE="SELECT * FROM PACIENTES WHERE ID=?";
    private static final String SQL_UPDATE = "UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, CEDULA=?, FECHA_INGRESO=?, DOMICILIO_ID=?, EMAIL=? WHERE ID = ?";
    private static final String SQL_DELETE = "DELETE FROM PACIENTES WHERE ID = ?";
    private static final String SQL_SEARCH_ALL = "SELECT * FROM PACIENTES";
    private static final String SQL_SEARCH_BY_EMAIL = "SELECT * FROM PACIENTES WHERE EMAIL = ?";

    @Override
    public Paciente guardar(Paciente paciente) {
        logger.info("iniciando la operacion de guardado");
        Connection connection=null;
        try{
            DomicilioDaoH2 daoAux = new DomicilioDaoH2();
            Domicilio domicilio = daoAux.guardar(paciente.getDomicilio());
            connection= BD.getConnection();
            PreparedStatement psInsert= connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psInsert.setString(1, paciente.getNombre());
            psInsert.setString(2, paciente.getApellido());
            psInsert.setString(3, paciente.getCedula());
            psInsert.setDate(4, paciente.getFechaIngreso());
            psInsert.setInt(5, domicilio.getId());
            psInsert.setString(6, paciente.getEmail());
            psInsert.execute(); //CUANDO SE EJECUTE SE VAN A GENERAR ID
            ResultSet rs= psInsert.getGeneratedKeys();
            while (rs.next()){
                paciente.setId(rs.getInt(1));
                logger.info("Paciente guardado exitosamente!");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return paciente;
    }

    @Override
    public Paciente buscarPorID(Integer id) {
        logger.info("iniciando la busqueda de un paciente por id: " + id);
        Connection connection= null;
        Paciente paciente= null;
        Domicilio domicilio= null;
        try{
            connection= BD.getConnection();
            PreparedStatement psSelectOne= connection.prepareStatement(SQL_SELECT_ONE);
            psSelectOne.setInt(1,id);
            ResultSet rs= psSelectOne.executeQuery();
            DomicilioDaoH2 domAux= new DomicilioDaoH2();
            while(rs.next()){
                domicilio= domAux.buscarPorID(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),domicilio, rs.getString(7));
                logger.info("Paciente encontrado!");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return paciente;
    }

    @Override
    public void actualizar(Paciente paciente) {
        logger.info("Iniciando la actualizacion del paciente: " + paciente.getNombre());
        Connection connection = null;
        try {
            DomicilioDaoH2 domAux = new DomicilioDaoH2();
            domAux.actualizar(paciente.getDomicilio());
            connection = BD.getConnection();
            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, paciente.getNombre());
            psUpdate.setString(2, paciente.getApellido());
            psUpdate.setString(3, paciente.getCedula());
            psUpdate.setDate(4, paciente.getFechaIngreso());
            psUpdate.setInt(5, paciente.getDomicilio().getId());
            psUpdate.setString(6, paciente.getEmail());
            psUpdate.setInt(7, paciente.getDomicilio().getId());
            psUpdate.execute();
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
            logger.warn("Paciente eliminado!");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Paciente> buscarTodos() {
        Connection connection = null;
        Domicilio domicilio = null;
        DomicilioDaoH2 domAux = new DomicilioDaoH2();
        List<Paciente> pacientes = new ArrayList<>();
        try {
            connection = BD.getConnection();
            Statement psSearchAll = connection.createStatement();
            ResultSet rs = psSearchAll.executeQuery(SQL_SEARCH_ALL);
            logger.info("Pacientes buscados exitosamente");
            while (rs.next()) {
                Integer idPaciente = rs.getInt(1);
                String nombrePaciente = rs.getString(2);
                String apellidoPaciente = rs.getString(3);
                String cedulaPaciente = rs.getString(4);
                Date fechaPaciente = rs.getDate(5);
                domicilio = domAux.buscarPorID(rs.getInt(6));
                String emailPaciente = rs.getString(7);
                pacientes.add(new Paciente(idPaciente, nombrePaciente, apellidoPaciente, cedulaPaciente, fechaPaciente, domicilio, emailPaciente));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return pacientes;
    }

    @Override
    public Paciente buscarPorString(String valor) {
        Connection connection = null;
        Domicilio domicilio = null;
        Paciente paciente = null;
        try {
            DomicilioDaoH2 auxDom = new DomicilioDaoH2();
            connection = BD.getConnection();
            PreparedStatement psSelect = connection.prepareStatement(SQL_SEARCH_BY_EMAIL);
            psSelect.setString(1, valor);
            ResultSet rs = psSelect.executeQuery();
            while (rs.next()) {
                domicilio = auxDom.buscarPorID(rs.getInt(6));
                paciente = new Paciente(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),domicilio, rs.getString(7));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return paciente;
    }
}
