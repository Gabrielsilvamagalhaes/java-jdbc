package dao;

import java.util.List;

import entities.Department;

public interface DepartmentDao {
    void create(Department department);
    void update(Department department);
    void delete(Department department);
    void deleteById(Integer id);
    
    List<Department> searchAll();
    Department searchById(Integer id);
    
}
