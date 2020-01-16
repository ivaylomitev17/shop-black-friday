package tu.sofia.shop.black.friday.controller;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerQuery {
    private Scanner scanInput;
    private PrintStream printout;

    private Socket s;


    public ServerQuery () throws Exception{
        s  = new Socket("localhost", 5000);
        scanInput = new Scanner(s.getInputStream());
        printout = new PrintStream(s.getOutputStream());

    }




    public void dataSend (String stringToSend){
        printout.println(stringToSend);
    }
    public String dataReceive(){
        String dataReceived = scanInput.nextLine();
        return dataReceived;
    }

    public Socket getSocket() {
        return s;
    }
}
