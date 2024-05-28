package com.example.proyectoIntegrador11.service;

import com.example.proyectoIntegrador11.repository.PacienteDAOH2;
import com.example.proyectoIntegrador11.repository.iDao;
import com.example.proyectoIntegrador11.entity.Paciente;

import java.util.List;

public class PacienteService {
    //relacion de asociacion directa con el DAO
    private iDao<Paciente> pacienteiDao;

    public PacienteService() {
        pacienteiDao= new PacienteDAOH2();
    }
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteiDao.guardar(paciente);
    }
    public Paciente buscarPaciente(Integer id){
        return pacienteiDao.buscarPorID(id);
    }

    public void actualizarPaciente(Paciente paciente) {
        pacienteiDao.actualizar(paciente);
    }

    public void eliminarPaciente(Integer id) {
        pacienteiDao.eliminar(id);
    }

    public List<Paciente> buscarTodos(){
        return pacienteiDao.buscarTodos();
    }

    public Paciente buscarPorCorreo(String correo){
        return pacienteiDao.buscarPorString(correo);
    }
}