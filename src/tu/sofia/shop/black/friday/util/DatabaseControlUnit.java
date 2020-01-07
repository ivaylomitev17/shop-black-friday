package tu.sofia.shop.black.friday.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseControlUnit {

    private List<Connection> connectionPool;
    private List<Connection>usedConnections = new ArrayList<Connection>();
    private final int MAX_CONNECTIONS = 10;

    public DatabaseControlUnit (){
        List<Connection> pool = new ArrayList<>(MAX_CONNECTIONS);
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                pool.add(createConnection());
            } catch (SQLException e) {
            }catch (ClassNotFoundException e){

            }


        }
    }

    private Connection createConnection() throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
       return DriverManager.getConnection("jdbc:mysql://localhost:3306/ShopDB",
               "root", "root");
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }


    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}
