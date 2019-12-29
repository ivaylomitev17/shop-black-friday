import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class UserRegistration extends User{
    Scanner sc = new Scanner (System.in);

    public UserRegistration(){
        super();
        setUsername();
        setPassword();
        addUser();

    }
    public void setUsername(){
        System.out.println("Input username");
        super.setUsername( sc.nextLine());
    }


    public void setPassword(){
        System.out.println("Input password");
        String hashedPassword = new CreateSHA256().getSHA256(sc.nextLine());
        super.setPasswordHash(hashedPassword);

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
        //System.out.println("statement: "+connection.getStatement());
       // connection.close();
    }catch (Exception e){
        e.printStackTrace();
    }



    }
}
