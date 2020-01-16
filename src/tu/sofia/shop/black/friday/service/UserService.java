package tu.sofia.shop.black.friday.service;
import tu.sofia.shop.black.friday.exceptions.IncorrectPasswordException;
import tu.sofia.shop.black.friday.exceptions.IncorrectUsernameException;
import tu.sofia.shop.black.friday.model.User;
import tu.sofia.shop.black.friday.util.CreateSHA256;
import tu.sofia.shop.black.friday.util.DatabaseControlUnit;
import tu.sofia.shop.black.friday.util.DatabaseManipulation;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService  implements DatabaseManipulation<User>{

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{6,40})";
    User user = new User();

    public ResultSet login(String username,String password)  throws Exception {

        String passwordToCompare = new CreateSHA256().getSHA256(password);
        user.setUsername(username);
        user.setPasswordHash(passwordToCompare);
        ResultSet resultSet = selectFromDatabase(user);
        return resultSet;
    }


    public void userRegistration(String username, String password) throws SQLException, IncorrectUsernameException, IncorrectPasswordException {
        User newUser = new User ();
        if (userVerification(username,USERNAME_PATTERN)){
            newUser.setUsername(username);
        } else throw new IncorrectUsernameException();
        if (userVerification(password,PASSWORD_PATTERN)){
            String hashedPassword = new CreateSHA256().getSHA256(password);;
            newUser.setPasswordHash(hashedPassword);
        } else throw new IncorrectPasswordException();
        insertIntoDatabase(newUser);

    }

    public boolean userVerification(String userdata, String patternToCompare) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(patternToCompare);
        matcher = pattern.matcher(userdata);
        return matcher.matches();
    }
    @Override
    public void updateDatabase(String column, String value, String condition) {

    }

    @Override
    public void insertIntoDatabase(User user) throws SQLException{
        Connection connection = DatabaseControlUnit.getInstance().getConnection();
        String query = "INSERT INTO users(username,passwordHash,isEmployee) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPasswordHash());
        preparedStatement.setBoolean(3,false);
        preparedStatement.executeUpdate();
        DatabaseControlUnit.getInstance().releaseConnection(connection);
    }

    @Override
    public ResultSet selectFromDatabase(User user) throws SQLException {
        Connection connection = DatabaseControlUnit.getInstance().getConnection();
        String query = "SELECT * FROM users WHERE username = ? AND passwordHash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPasswordHash());
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseControlUnit.getInstance().releaseConnection(connection);
        return resultSet;
    }



}

