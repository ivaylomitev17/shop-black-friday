package tu.sofia.shop.black.friday.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DatabaseControlUnit {

    // The class could be named DatabaseConnectionUtil
    // It shall contain only methods for database connection handling
    // SQL statements could be extracted in another utility class

    private Connection connection;
    private static Statement statement;

    public Connection createConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShopDB", "root", "root");
        return connection;
    }
    public Statement createStatement () throws Exception{
        statement = createConnection().createStatement();
        return statement;
    }

    public Connection getConnection() {
        return connection;
    }

}
