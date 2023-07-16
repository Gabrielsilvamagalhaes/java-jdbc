package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PreparedStatement ps = null;
        String sql = "INSERT INTO seller "
        + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
        + "VALUES "
        + "(?, ?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                Connect.closeResultSet(rs);
            }else {
				throw new DbException("Unexpected error! No rows affected!");
			}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeStatement(ps);
        }
        
        
    }

    @Override
    public void update(Seller seller) {
        if(seller == null || seller.getId() == null) {
            return;
        }
        PreparedStatement ps = null;
        String sql = "UPDATE seller "
        + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
        + "WHERE Id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());
            ps.setInt(6, seller.getId());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeStatement(ps);
        }
        
    }

    @Override
    public void delete(Seller seller) {
        PreparedStatement ps = null;
        String sql = "DELETE FROM seller WHERE Id = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, seller.getId());

            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeStatement(ps);
        }
        
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        String sql = "DELETE FROM seller WHERE Id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeStatement(ps);
        }

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
        Map<Integer, Department> mapDepartment = new HashMap<>();
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
                Department department = mapDepartment.get(rs.getInt("DepartmentId"));

                if(department == null) {
                    department = instantiateDepartment(rs);
                    mapDepartment.put(rs.getInt("DepartmentId"), department);
                }

                sellers.add(instantiateSeller(rs, mapDepartment.get(department.getId())));
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

    @Override
    public List<Seller> searchByDepartmentId(Integer id) {
        List<Seller> sellers = new ArrayList<>();
        Map<Integer,Department> mapDepartment = new HashMap<>();
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
                Department department = mapDepartment.get(rs.getInt("DepartmentId"));

                if(department == null) {
                    department = instantiateDepartment(rs);
                    mapDepartment.put(rs.getInt("DepartmentId"), department);
                }
                sellers.add(instantiateSeller(rs, mapDepartment.get(department.getId())));
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
