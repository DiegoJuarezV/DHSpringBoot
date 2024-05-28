package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.repository.iDao;
import com.example.proyectoIntegrador11.repository.DomicilioDaoH2;
import com.example.proyectoIntegrador11.entity.Domicilio;

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

    public void actualizarDomicilio(Domicilio domicilio) {
        domicilioiDao.actualizar(domicilio);
    }

    public void eliminarDomicilio(Integer id) {
        domicilioiDao.eliminar(id);
    }

    public List<Domicilio> buscarTodos(){
        return domicilioiDao.buscarTodos();
    }
}
