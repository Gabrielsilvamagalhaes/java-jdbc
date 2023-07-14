package application;

import java.util.List;

import dao.DaoFactory;
import dao.SellerDao;
import entities.Seller;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
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
        System.out.println("===Create===");
        

    }
    
}
