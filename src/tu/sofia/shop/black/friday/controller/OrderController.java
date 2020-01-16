package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.model.Order;
import tu.sofia.shop.black.friday.service.OrderService;
import tu.sofia.shop.black.friday.service.ProductService;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class OrderController {

    private PrintStream printStream;
    public OrderController (Socket socket){
        try{
            printStream = new PrintStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void createNewOrder (String toRun){
        Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        //orderInfo contains only userId, productId and quantity of ordered product
        List <String> orderInfo = Arrays.asList(toRun.split("#"));
        Order order = new Order ();
        order.setBuyerId(Integer.parseInt(orderInfo.get(0)));
        order.setDate(date);
        try{
            //checking if ordered quantity is bigger than actual
            ResultSet resultSet = new ProductService().selectFromDatabaseById(Integer.parseInt(orderInfo.get(1)));
            if (resultSet.first()){
                if (resultSet.getInt("quantity")>=Integer.parseInt(orderInfo.get(2))){
                    new OrderService().createNewOrder(order,orderInfo);
                    printStream.println("Successful");
                }
            }else printStream.println("Unsuccessful");
        }catch (SQLException e){
            printStream.println("Unsuccessful");
            e.printStackTrace();
        }


    }
    public void orderHistory (String id){
        try{
            ResultSet ordersResultSet = new OrderService().selectFromDatabaseById(Integer.parseInt(id));
            ordersResultSet.beforeFirst();
            while (ordersResultSet.next()){
                    printStream.println(ordersResultSet.getDate(1).toString()
                    .concat("#")
                    .concat(Double.toString(ordersResultSet.getDouble(2)))
                    .concat("#")
                    .concat(ordersResultSet.getString(3)));
            }
            printStream.println("End");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
