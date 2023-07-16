package application;

import java.util.List;

import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department newDepartment = new Department(null, "Ti");
        departmentDao.create(newDepartment);
        System.out.println("===Create=== + id = " + newDepartment.getId());
        System.out.println("---------------------");
        
        //departmentDao.deleteById(9);
        System.out.println("===Delete===");
        System.out.println("---------------------");

        System.out.println("===SearchAll===");
        List<Department> departments = departmentDao.searchAll();
        departments.forEach(d -> System.out.println(d));
        System.out.println("---------------------");

        System.out.println("===SearchBydId===");
        Department d1 = departmentDao.searchById(3);
        System.out.println("---------------------");
        System.out.println(d1);
        
        System.out.println("===Update===");
        d1.setName("Ti");
        departmentDao.update(d1);


    }
    
}
