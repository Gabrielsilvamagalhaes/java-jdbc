package dao;

import dao.impl.SellerDaoImpl;
import db.Connect;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoImpl(Connect.getConnection());
    }
    
}
