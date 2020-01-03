package tu.sofia.shop.black.friday.service;

import tu.sofia.shop.black.friday.model.Product;
import tu.sofia.shop.black.friday.util.DatabaseControlUnit;
import tu.sofia.shop.black.friday.util.DatabaseManipulation;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class ProductService {

    // Note: methods are dummy, add the needed parameters and implement the functionality
    public void create(Product productToAddToDatabase) {
        String query = "insert into products(name, price,priceMin,quantity,status,blackFridayDiscountPercentage)" +
                " values ('" + productToAddToDatabase.getName() + "','" + productToAddToDatabase.getPrice() + "','" +
                productToAddToDatabase.getPriceMin()+ "','" +productToAddToDatabase.getQuantity()+ "','BlackFridayOFF','0')";

        try{
            DatabaseManipulation insertStatement = new DatabaseManipulation();
            insertStatement.insertIntoDatabase(query);
        }catch (Exception e){
            System.out.println("A problem occurred connecting to database");
            e.printStackTrace();
        }

    }

    public ResultSet findProductByName(String name) throws Exception{
        String query = "SELECT * FROM products WHERE name = '"+name+ "'";
        DatabaseManipulation selectQuery = new DatabaseManipulation();
        ResultSet resultSet = selectQuery.createStatement().executeQuery(query);
        resultSet.first();
        return resultSet;
    }

    public Product updateProductPrice(String name, double newPrice)throws Exception{
        // TODO: update the product price
        ResultSet resultSet = findProductByName(name);
        //System.out.println("Current product price:"+resultSet.getString(3)+"    (minimum price:)"+resultSet.getString(4));
        //double newPrice = Double.parseDouble(sc.nextLine());
        if ((Double.compare(newPrice,Double.parseDouble(resultSet.getString(4))))>0){
            DatabaseManipulation updateStatement = new DatabaseManipulation();
            updateStatement.updateDatabase("products","price",Double.toString(newPrice),"id",resultSet.getString(1));
        }
        else System.out.println("Price cannot be lower than minimum price!");

        return null; // return the updated product
    }
}
