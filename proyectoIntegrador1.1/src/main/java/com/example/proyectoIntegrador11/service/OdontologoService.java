package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.repository.OdontologoDaoH2;
import com.example.proyectoIntegrador11.repository.iDao;
import com.example.proyectoIntegrador11.entity.Odontologo;

import java.util.List;
import java.util.Map;

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

    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoiDao.actualizar(odontologo);
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
