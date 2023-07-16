package application;

import java.util.List;
import java.util.Date;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Department;
import entities.Seller;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        //Department department = new Department(2, null);

        System.out.println("===SearchBydId===");
        Seller s1 = sellerDao.searchById(3);
        System.out.println("---------------------");
        System.out.println(s1);

        List<Seller> sellerByDepartment = sellerDao.searchByDepartmentId(1);
        System.out.println("===SearchBydDepartmentId===");
        sellerByDepartment.forEach(s -> System.out.println(s));
        System.out.println("---------------------");
        
        System.out.println("===SearchAll===");
        List<Seller> sellers = sellerDao.searchAll();
        sellers.forEach(s -> System.out.println(s));

        System.out.println("---------------------");
        //Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        //sellerDao.create(newSeller);
        //System.out.println("===Create=== + id = " + newSeller.getId());

        //sellerDao.deleteById(11);
        //System.out.println("===Delete===");

        System.out.println("===Update===");
        s1.setName("Rafael");
        s1.setEmail("rafael@gmail.com");
        sellerDao.update(s1);

    }
    
}
