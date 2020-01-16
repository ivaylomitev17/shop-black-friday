package tu.sofia.shop.black.friday.controller;
import tu.sofia.shop.black.friday.model.Product;
import tu.sofia.shop.black.friday.service.OrderService;
import tu.sofia.shop.black.friday.service.ProductService;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductController {

    private PrintStream printStream;

    public ProductController(Socket s) {
        try {
            printStream = new PrintStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchProduct(String nameToSearch) {
        try {
            ResultSet resultSet = new ProductService().findProductByName(nameToSearch);
            resultSet.beforeFirst();
            printProductsToClient(resultSet);
        } catch (SQLException e) {
            printStream.println("Error");
        }
    }
    public void changeProductPrice(String toRun) {
        String[] parameters = toRun.split("#");
        try {
            new ProductService().updateProductPrice(parameters[0], parameters[1], parameters[2]);
            printStream.println("Successful");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public void addQuantity (String query){
        String [] received = query.split("#");
        //[0] is quantity, [1] is the productId
        try{
            ResultSet resultSet = new ProductService().findProductById(Integer.parseInt(received[1]));

                new ProductService().updateDatabase("quantity",
                        String.valueOf(Integer.parseInt(received[0]+resultSet.getInt("quantity"))),
                        received[0]);
                printStream.println("Successful");
        }catch (SQLException e){
            printStream.println("Unsuccessful");
        }


    }
    public void addProductToDatabase(String productString){
        try{
            String[]productInfo = productString.split("#");
            Product product = new Product();
            product.setName(productInfo[0]);
            product.setPrice(Double.parseDouble(productInfo[1]));
            product.setPriceMin(Double.parseDouble(productInfo[2]));
            product.setQuantity(Integer.parseInt(productInfo[3]));
            System.out.println(product.getName());
            new ProductService().insertIntoDatabase(product);
            printStream.println("Successful");
        }catch (SQLException e){
            printStream.println("Unsuccessful");
        }
    }
    public void getAllProducts(String toRun){
        try{
            ResultSet resultSet = new ProductService().selectFromDatabaseAll();
           printProductsToClient(resultSet);
        }catch (SQLException e){
            printStream.println("Unsuccessful");
        }

    }
    public void endBlackFriday(String received){
        //First value  is the percentage of discount. Second one is the ids of all products on discount
        String [] commands = received.split("#");
        ProductService productService = new ProductService();

        String [] selectedProducts = commands[1].split(", ");
        try{
                productService.updateDatabaseBlackFriday("0",
                        "BlackFridayOFF",
                        selectedProducts);
        }catch (SQLException e){
            printStream.println("Unsuccessful");
            e.printStackTrace();
        }

    }
    public void startBlackFriday(String received){
        //First value is the percentage of discount. Second one is the ids of all products on discount
        String [] commands = received.split("#");
        ProductService productService = new ProductService();

            String [] selectedProducts = commands[1].split(", ");
            try{
                    productService.updateDatabaseBlackFriday(commands[0],
                            "BlackFridayON",selectedProducts);
            }catch (SQLException e){
                printStream.println("Unsuccessful");
                e.printStackTrace();
            }

    }
    public void printProductsToClient (ResultSet resultSet) throws SQLException{
        while (resultSet.next()) {
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
                    .concat(String.valueOf(resultSet.getString(6)))
                    .concat("#")
                    .concat(String.valueOf(resultSet.getDouble(7)));
            printStream.println(productRecord);
        }
        printStream.println("End");

    }
}
