package tu.sofia.shop.black.friday;

import tu.sofia.shop.black.friday.model.User;
import tu.sofia.shop.black.friday.util.ConnectionToDB;
import tu.sofia.shop.black.friday.util.CreateSHA256;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserLogin extends User {

    public UserLogin(){
        super();
    }
    public boolean login(String username,String password) throws Exception{

        String passwordToCompare = new CreateSHA256().getSHA256(password);
        String query = "SELECT username,passwordHash FROM users WHERE username = '"+username+ "' AND passwordHash = '"+passwordToCompare+"'";
        ConnectionToDB connection = new ConnectionToDB();
        connection.createConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()){
            System.out.println("Incorrect username or password");
            return false;
        }
        return true;
    }
}
