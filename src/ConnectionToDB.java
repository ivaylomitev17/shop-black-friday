import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class ConnectionToDB {

    private Connection connection;
    private Statement statement;


    public ConnectionToDB()  throws Exception{

    }


    public void createConnection() throws Exception{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ShopDB", "root", "root");

    }



    public void updateDB(String table, String set, String update, String where, String condition) throws Exception{

        String query = "UPDATE "+table+"\n SET "+set+" = '"+update+"'\n WHERE "+where+"='" + condition+"'";
        createConnection();
        this.statement = this.connection.createStatement();
        statement.executeUpdate(query);
        this.connection.close();


    }
    public Connection getConnection() {
        return connection;
    }


}
