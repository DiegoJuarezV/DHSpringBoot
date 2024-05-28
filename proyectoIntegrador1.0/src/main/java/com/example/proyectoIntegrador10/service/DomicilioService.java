package com.example.proyectoIntegrador10.service;

import com.example.proyectoIntegrador10.dao.DomicilioDaoH2;
import com.example.proyectoIntegrador10.dao.iDao;
import com.example.proyectoIntegrador10.model.Domicilio;

import java.util.List;
import java.util.Map;

public class DomicilioService {
    private iDao<Domicilio> domicilioiDao;

    public DomicilioService() {
        domicilioiDao = new DomicilioDaoH2();
    }
    public Domicilio guardarDomicilio(Domicilio domicilio){
        return domicilioiDao.guardar(domicilio);
    }
    public Domicilio buscarDomicilio(Integer id){
        return domicilioiDao.buscarPorID(id);
    }

    public void actualizarDomicilio(Map<String, Object> dato, Integer id) {
        domicilioiDao.actualizar(dato, id);
    }

    public void eliminarDomicilio(Integer id) {
        domicilioiDao.eliminar(id);
    }

    public List<Domicilio> buscarTodos(){
        return domicilioiDao.buscarTodos();
    }
}
