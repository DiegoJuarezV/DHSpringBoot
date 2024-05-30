package com.example.proyectoIntegrador11.repository;

import com.example.proyectoIntegrador11.entity.Turno;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TurnoDaoLista implements iDao<Turno> {
    private static final Logger logger=Logger.getLogger(TurnoDaoLista.class);
    private List<Turno> turnos= new ArrayList<>();
    @Override
    public Turno guardar(Turno turno) {
        logger.info("iniciando las operaciones de guardado de un turno");
        //que hacemos para guardar un turno?
        PacienteDAOH2 daoPaciente= new PacienteDAOH2();
        OdontologoDaoH2 daoOdontologo= new OdontologoDaoH2();
        turno.setPaciente(daoPaciente.buscarPorID(turno.getPaciente().getId()));
        turno.setOdontologo(daoOdontologo.buscarPorID(turno.getOdontologo().getId()));
        turnos.add(turno);
        logger.info("turno guardado" + turno.getOdontologo().getApellido() + " " + turno.getPaciente().getApellido());
        return turno;
    }

    @Override
    public Turno buscarPorID(Integer id) {
        for (Turno turno : turnos) {
            if(turno.getId().equals(id)){
                return turno;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Turno turno) {
        for (Turno turnoActualizar: turnos) {
           if (turnoActualizar.getId() != null  && turnoActualizar.getId().equals(turno.getId())) {
               turnoActualizar.setOdontologo(turno.getOdontologo());
               turnoActualizar.setPaciente(turno.getPaciente());
               turnoActualizar.setFecha(turno.getFecha());
               logger.info(("Turno actualizado!"));
               break;
           }
        }
    }

    @Override
    public void eliminar(Integer id) {
        turnos.removeIf(turno -> turno.getId().equals(id));
    }

    @Override
    public List<Turno> buscarTodos() {
        logger.info("Turnos buscados exitosamente!");
        return turnos;
    }

    @Override
    public Turno buscarPorString(String valor) {
        return null;
    }
}
