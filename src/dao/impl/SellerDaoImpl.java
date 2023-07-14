package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.SellerDao;
import db.Connect;
import db.DbException;
import entities.Department;
import entities.Seller;

public class SellerDaoImpl implements SellerDao {
    private Connection conn;

    public SellerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Seller seller) {
        
    }

    @Override
    public void update(Seller seller) {
        
    }

    @Override
    public void delete(Seller seller) {
        
    }

    @Override
    public Seller searchById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT seller.*,department.Name as DepName "
        + "FROM seller INNER JOIN department "
        + "ON seller.DepartmentId = department.Id "
        + "WHERE seller.Id = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if(rs.next()) {
                    return new Seller(rs.getInt("Id"), 
                    rs.getString("Name"), 
                    rs.getString("Email"), 
                    rs.getDate("BirthDate"), 
                    rs.getDouble("BaseSalary"), 
                    new Department(rs.getInt("DepartmentId"),
                    rs.getString("DepName")));
            }
            
        } catch (SQLException e) {
            throw new DbException("Could not find this id");
        }finally {
            Connect.close(rs, ps);
        }
        return null;
    }

    @Override
    public List<Seller> searchAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchAll'");
    }
    
}
