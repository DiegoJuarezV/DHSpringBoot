package com.example.proyectoIntegrador10.service;

import java.util.List;
import java.util.Map;
import com.example.proyectoIntegrador10.dao.iDao;
import com.example.proyectoIntegrador10.dao.OdontologoDaoH2;
import com.example.proyectoIntegrador10.model.Odontologo;
import com.example.proyectoIntegrador10.model.Paciente;

public class OdontologoService {
    private iDao<Odontologo> odontologoiDao;

    public OdontologoService() {
        this.odontologoiDao = new OdontologoDaoH2();
    }

    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoiDao.guardar(odontologo);
    }

    public Odontologo buscarOdontologo(Integer id) {
        return odontologoiDao.buscarPorID(id);
    }

    public void actualizarOdontologo(Map<String, Object> dato, Integer id) {
        odontologoiDao.actualizar(dato, id);
    }

    public void eliminarOdontologo(int id) {
        odontologoiDao.eliminar(id);
    }

    public List<Odontologo> listarOdontologos() {
        return odontologoiDao.buscarTodos();
    }

    public Odontologo buscarPorCorreo(String correo){
        return odontologoiDao.buscarPorString(correo);
    }
}
