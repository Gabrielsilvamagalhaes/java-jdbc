package dao;

import java.util.List;

import entities.Department;
import entities.Seller;

public interface SellerDao {
    void create(Seller seller);
    void update(Seller seller);
    void delete(Seller seller);

    Department searchById(Integer id);
    List<Seller> searchAll();
}
