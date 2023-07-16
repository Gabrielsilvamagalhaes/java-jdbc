package dao;

import dao.impl.DepartmentDaoImpl;
import dao.impl.SellerDaoImpl;
import db.Connect;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoImpl(Connect.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoImpl(Connect.getConnection());
    }
    
}
