package tu.sofia.shop.black.friday;

import tu.sofia.shop.black.friday.controller.OrderController;
import tu.sofia.shop.black.friday.controller.ProductController;
import tu.sofia.shop.black.friday.controller.UserController;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

public class ClientHandler implements Runnable {
    Socket user;
    Scanner scan;


    public ClientHandler(Socket user) throws IOException {
        this.user = user;
        scan = new Scanner(user.getInputStream());
    }

    @Override
    public void run() {
        try{
            String command = scan.nextLine();
            String toRun = scan.nextLine();
            HashMap<String, Consumer<String>> router = new HashMap<>();

            router.put("login",(String query) ->
                    new UserController(user).login(query));
            router.put("registration",(String  query)->
                    new UserController(user).createNewRegistration(query));
            router.put("searchProduct",(String query)->
                    new ProductController(user).searchProduct(query));
            router.put("changePrice",(String query)->
                    new ProductController(user).changeProductPrice(query));
            router.put("newOrder",(String query)->
                    new OrderController(user).createNewOrder(query));
            router.get(command).accept(toRun);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
