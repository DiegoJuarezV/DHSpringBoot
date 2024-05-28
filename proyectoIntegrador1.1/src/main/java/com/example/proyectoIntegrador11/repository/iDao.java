package com.example.proyectoIntegrador11.repository;

import java.util.List;
import java.util.Map;

public interface iDao<T> {
    T guardar(T t);
    T buscarPorID(Integer id);
    void actualizar(T t);
    void eliminar(Integer id);
    List<T> buscarTodos();
    T buscarPorString(String valor);
}
