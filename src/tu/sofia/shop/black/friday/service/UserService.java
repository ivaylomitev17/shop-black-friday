package tu.sofia.shop.black.friday.service;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import tu.sofia.shop.black.friday.model.User;
import tu.sofia.shop.black.friday.util.DatabaseControlUnit;
import tu.sofia.shop.black.friday.util.CreateSHA256;
import tu.sofia.shop.black.friday.util.DatabaseManipulation;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService  implements DatabaseManipulation<User>{
    private Connection connection;
    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{6,40})";
    Scanner sc = new Scanner (System.in);


    public boolean login(String username,String password)  throws Exception {

        String passwordToCompare = new CreateSHA256().getSHA256(password);
        String whereCondition = "username = ";
        whereCondition.concat(username);
        if (selectFromDatabase(whereCondition)!=null) {
            return true;
        }
        else return false;
    }


    public void userRegistration(String username, String password) throws IOException,SQLException {
        User newUser = new User ();
        if (userVerification(username,USERNAME_PATTERN)){
            newUser.setUsername(username);
        }
        if (userVerification(password,PASSWORD_PATTERN)){
            String hashedPassword = new CreateSHA256().getSHA256(password);
            newUser.setPasswordHash(hashedPassword);
        }
        insertIntoDatabase(newUser);

    }


    public boolean userVerification(String userdata, String patternToCompare) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(userdata);
        return matcher.matches();
    }


    @Override
    public void updateDatabase(String column, String value, String condition) {

    }

    @Override
    public void insertIntoDatabase(User user) throws SQLException{
        String query = "INSERT INTO users(username,passwordHash,isEmployee) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPasswordHash());
        preparedStatement.setBoolean(3,false);
        preparedStatement.executeUpdate();
    }

    @Override
    public ResultSet selectFromDatabase(String whereCondition) throws SQLException {
        String query = "SELECT username,passwordHash FROM users WHERE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,whereCondition);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}

