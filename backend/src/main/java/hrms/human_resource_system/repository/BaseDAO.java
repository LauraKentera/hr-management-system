package main.java.hrms.human_resource_system.repository;

import java.util.List;

public interface BaseDAO<T> {
    T getById(int id);
    List<T> getAll();
    void insert(T entity);
    void update(T entity);
    void delete(int id);
}
