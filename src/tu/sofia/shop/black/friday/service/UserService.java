package tu.sofia.shop.black.friday.service;
import tu.sofia.shop.black.friday.model.UserVerification;
import tu.sofia.shop.black.friday.model.User;
import tu.sofia.shop.black.friday.util.DatabaseControlUnit;
import tu.sofia.shop.black.friday.util.CreateSHA256;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService extends User implements UserVerification {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{6,40})";
    private Pattern pattern;
    private Matcher matcher;
    Scanner sc = new Scanner (System.in);


    public boolean login(String username,String password) throws Exception{

        String passwordToCompare = new CreateSHA256().getSHA256(password);
        String query = "SELECT username,passwordHash FROM users WHERE username = '"+username+ "' AND passwordHash = '"+passwordToCompare+"'";
        DatabaseControlUnit connection = new DatabaseControlUnit();
        connection.createConnection();
        Statement statement = connection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()){
            System.out.println("Incorrect username or password");
            return false;
        }
        return true;
    }
    public void userRegistration() throws IOException {
        setUsername();
        setPassword();
        addUser();
    }
    public void setUsername() throws IOException {
        System.out.println("Input username");
        String username = sc.nextLine();
        if (userVerification(username,USERNAME_PATTERN)){
            super.setUsername(username);
        }
        else throw new IOException("Incorrect username!");
    }


    public void setPassword() throws IOException {
        System.out.println("Input password");
        String password = sc.nextLine();
        if (userVerification(password,PASSWORD_PATTERN)){
            String hashedPassword = new CreateSHA256().getSHA256(password);
            super.setPasswordHash(hashedPassword);
        }
        else throw new IOException();
    }
    public void addUser(){
        try{
            // Connection connection = new tu.sofia.shop.black.friday.util.DatabaseControlUnit().createConnection();
            String query = "insert into users(username,passwordHash,isEmployee) values ('" + super.getUsername() + "','" + super.getPasswordHash() + "','" + 0+ "')";
            DatabaseControlUnit connection = new DatabaseControlUnit();
            connection.createConnection();
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate(query);
            connection.getConnection().close();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public boolean userVerification(String userdata, String patternToCompare) {
        pattern = Pattern.compile(USERNAME_PATTERN);
        matcher = pattern.matcher(userdata);
        return matcher.matches();
    }
}

