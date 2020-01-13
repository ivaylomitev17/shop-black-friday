package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.Exceptions.NewPriceLowerThanMinimumException;
import tu.sofia.shop.black.friday.service.ProductService;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductController {

    private PrintStream printStream;

    public ProductController (Socket s){
        try{
            printStream = new PrintStream(s.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void searchProduct (String nameToSearch){
        try{
            ResultSet resultSet = new ProductService().findProductByName(nameToSearch);
            resultSet.beforeFirst();
            while (resultSet.next()){
                String productRecord = String.valueOf(resultSet.getInt(1))
                        .concat("#")
                        .concat(resultSet.getString(2))
                        .concat("#")
                        .concat(String.valueOf(resultSet.getDouble(3)))
                        .concat("#")
                        .concat(String.valueOf(resultSet.getDouble(4)))
                        .concat("#")
                        .concat(String.valueOf(resultSet.getInt(5)))
                        .concat("#")
                        .concat(String.valueOf(resultSet.getString (6)))
                        .concat("#")
                        .concat(String.valueOf(resultSet.getDouble(7)));
                printStream.println(productRecord);
            }
            printStream.println("End");
        }catch (SQLException e){
            printStream.println("Error");
        }
    }
    public void changeProductPrice(String toRun){
        String[] parameters = toRun.split("#");
        try{
            new ProductService().updateProductPrice(parameters[0],Double.parseDouble(parameters[1]));
            printStream.println("Successful");
        }catch (SQLException e){
            e.printStackTrace();
        }catch (NewPriceLowerThanMinimumException e){
            printStream.println(e.getMessage());
        }

    }
}
