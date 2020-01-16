package tu.sofia.shop.black.friday;


import tu.sofia.shop.black.friday.util.DatabaseControlUnit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class ServerMain {
    public static void main (String [] args ) throws IOException {

        ServerSocket server = new ServerSocket(5000);

        while (true){
            Socket user = server.accept();
            new Thread(new ClientHandler(user)).start();

        }
    }
}
