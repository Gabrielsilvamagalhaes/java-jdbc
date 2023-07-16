package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DepartmentDao;
import db.Connect;
import db.DbException;
import entities.Department;

public class DepartmentDaoImpl implements DepartmentDao {
    private Connection conn;

    public DepartmentDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Department department) {
        PreparedStatement ps = null;
        String sql = "INSERT INTO department " +
        "(Name) " +
        "VALUES " +
        "(?)";

        List<String> departments = departmentName();
        if(departments.contains(department.getName())) {
            return;
        }

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, department.getName());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeStatement(ps);
        }
        
    }

    @Override
    public void update(Department department) {
        if(department == null || department.getId() == null) {
            return;
        }

        PreparedStatement ps = null;
        String sql = "UPDATE department " +
        "SET Name = ? " +
        "WHERE Id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeStatement(ps);
        }
    }

    @Override
    public void delete(Department department) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            ps.setInt(1, department.getId());

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
        try {
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Connect.closeStatement(ps);
        }
        
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

    private List<String> departmentName() {
        List<Department> departments = searchAll();
        List<String> departmentsName = new ArrayList<>();
        departments.forEach(d -> departmentsName.add(d.getName()));
        return departmentsName;
    }
    
}
