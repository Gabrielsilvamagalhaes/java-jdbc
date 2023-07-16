package dao;

import java.util.List;

import entities.Seller;

public interface SellerDao {
    void create(Seller seller);
    void update(Seller seller);
    void delete(Seller seller);
    void deleteById(Integer id);

    Seller searchById(Integer id);
    List<Seller> searchAll();
    List<Seller> searchByDepartmentId(Integer id);
}
