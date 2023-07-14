package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    return instantiateSeller(rs, instantiateDepartment(rs));
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
        List<Seller> sellers = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT seller.*,department.Name as DepName "
        + "FROM seller INNER JOIN department "
        + "ON seller.DepartmentId = department.Id "
        + "ORDER BY Name";


        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()) {
                sellers.add(instantiateSeller(rs, instantiateDepartment(rs)));
            }

            if(!sellers.isEmpty()) {
                return sellers;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Seller> searchByDepartmentId(Integer id) {
        List<Seller> sellers = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT seller.*,department.Name as DepName "
        + "FROM seller INNER JOIN department "
        + "ON seller.DepartmentId = department.Id "
        + "WHERE DepartmentId = ? "
        + "ORDER BY Name";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while(rs.next()) {
                sellers.add(instantiateSeller(rs, instantiateDepartment(rs)));
            }
            if(!sellers.isEmpty()) {
                return sellers;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.close(rs, ps);
        }
        return null;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
    
}