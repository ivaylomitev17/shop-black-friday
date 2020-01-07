package tu.sofia.shop.black.friday.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public  interface  DatabaseManipulation <T>{
    void updateDatabase(String column, String value, String  condition) throws SQLException;
    void insertIntoDatabase(T o) throws SQLException;
    ResultSet selectFromDatabase(String whereCondition) throws SQLException;
    void setConnection(Connection connection);

}
