package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DepartmentDao;
import db.Connect;
import entities.Department;

public class DepartmentDaoImpl implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Department department) {
        
    }

    @Override
    public void update(Department department) {
        
    }

    @Override
    public void delete(Department department) {
        
    }

    @Override
    public List<Department> searchAll() {
        List<Department> departments = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
            rs = ps.executeQuery();

            
            while(rs.next()) {
                Department department = instantiateDepartment(rs);
                
                if(!departments.contains(department)) {
                    departments.add(department);
                }
            }
                departments.sort((d1, d2) -> {
                    return d1.getId().compareTo(d2.getId());
                });
                return departments;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.close(rs, ps);
        }
        return null;

    }

    @Override
    public Department searchById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if(rs.next()) {
                return instantiateDepartment(rs);

            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.close(rs, ps);
        }
        return null;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}
    
}
