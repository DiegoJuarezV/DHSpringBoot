package com.example.proyectoIntegrador10.dao;

import java.util.List;
import java.util.Map;

public interface iDao<T> {
    T guardar(T t);
    T buscarPorID(Integer id);
    void actualizar(Map<String, Object> dato, Integer id);
    void eliminar(Integer id);
    List<T> buscarTodos();
    T buscarPorString(String valor);
}
