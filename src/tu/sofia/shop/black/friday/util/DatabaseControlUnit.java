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
    private static DatabaseControlUnit databaseControlUnit = new DatabaseControlUnit( );

    private DatabaseControlUnit (){
        createConnectionPool();
    }
    public static DatabaseControlUnit getInstance( ) {
        return databaseControlUnit;
    }

    private void createConnectionPool(){
        connectionPool = new ArrayList<>(MAX_CONNECTIONS);
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                connectionPool.add(createConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
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