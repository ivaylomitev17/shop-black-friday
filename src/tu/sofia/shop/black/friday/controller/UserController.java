package tu.sofia.shop.black.friday.controller;
import tu.sofia.shop.black.friday.Exceptions.IncorrectPasswordException;
import tu.sofia.shop.black.friday.Exceptions.IncorrectUsernameException;
import tu.sofia.shop.black.friday.service.UserService;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    private PrintStream printStream;
    public UserController (Socket socket){
        try{
            printStream = new PrintStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void login (String toRun) {
        String[] parameters = toRun.split("#");
        try {
            ResultSet loggedClient = new UserService().login(parameters[0], parameters[1]);
            loggedClient.beforeFirst();
            if (loggedClient.next()){
                String userData = String.valueOf(loggedClient.getInt(1))
                        .concat("#")
                        .concat(loggedClient.getString(2))
                        .concat("#")
                        .concat(loggedClient.getString(3))
                        .concat("#")
                        .concat (String.valueOf(loggedClient.getBoolean(4)));
                printStream.println(userData);
            }else{
                printStream.println("Unsuccessful");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createNewRegistration (String toRun){
        String[] parameters = toRun.split("#");
        try{
            new UserService().userRegistration(parameters[0],parameters[1]);
            printStream.println("Successful");
        }catch (SQLException e){
            e.printStackTrace();
        }catch (IncorrectUsernameException e){
            printStream.println("Incorrect username");
        }catch (IncorrectPasswordException e){
            printStream.println("Incorrect password");
        }


    }



}
