import javax.jws.soap.SOAPBinding;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistration extends User implements UserVerification {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,15}$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-zA-Z]).{6,40})";
    private Pattern pattern;
    private Matcher matcher;
    Scanner sc = new Scanner (System.in);

    public UserRegistration() throws IOException{
        super();
        setUsername();
        setPassword();
        addUser();

    }
    public void setUsername() throws IOException {
        System.out.println("Input username");
        String username = sc.nextLine();
        System.out.println(username);
        System.out.println(USERNAME_PATTERN);
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
       // Connection connection = new ConnectionToDB().createConnection();
        String query = "insert into users(username,passwordHash,isEmployee) values ('" + super.getUsername() + "','" + super.getPasswordHash() + "','" + 0+ "')";
        ConnectionToDB connection = new ConnectionToDB();
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
        System.out.println(matcher.matches());
        return matcher.matches();
        //return false;
    }
}
