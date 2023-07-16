package application;

import java.util.List;

import dao.DaoFactory;
import dao.DepartmentDao;
import entities.Department;

public class Program2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("===SearchAll===");
        List<Department> departments = departmentDao.searchAll();
        departments.forEach(d -> System.out.println(d));

        System.out.println("===SearchBydId===");
        Department d1 = departmentDao.searchById(3);
        System.out.println("---------------------");
        System.out.println(d1);


    }
    
}
