package dao;

import java.util.List;

import entities.Department;

public interface DepartmentDao {
    void create(Department department);
    void update(Department department);
    void delete(Department department);
    
    List<Department> searchAll();
    List<Department> searchById(Integer id);
    
}
