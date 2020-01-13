package tu.sofia.shop.black.friday.service;

import tu.sofia.shop.black.friday.Exceptions.NewPriceLowerThanMinimumException;
import tu.sofia.shop.black.friday.model.Product;
import tu.sofia.shop.black.friday.util.DatabaseControlUnit;
import tu.sofia.shop.black.friday.util.DatabaseManipulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductService implements DatabaseManipulation <Product>{

    public ResultSet findProductByName(String name) throws SQLException{
        Product productToFind = new Product();
        productToFind.setName(name);
        ResultSet resultSet = selectFromDatabase(productToFind);
        resultSet.first();
        return resultSet;
    }


    public void updateProductPrice(String name, double newPrice)throws NewPriceLowerThanMinimumException,SQLException{
         ResultSet resultSet = findProductByName(name);
        if ((Double.compare(newPrice,Double.parseDouble(resultSet.getString(4))))>0){
            updateDatabase("price",Double.toString(newPrice),"id = "+resultSet.getString(1));
       }
        else throw new NewPriceLowerThanMinimumException();
    }

    @Override
    public void updateDatabase(String column, String value, String condition) throws SQLException {
        Connection connection = DatabaseControlUnit.getInstance().getConnection();
        String query = "UPDATE products SET ? = ? WHERE?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,column);
        preparedStatement.setString(2,value);
        preparedStatement.setString(3,condition);
        preparedStatement.executeUpdate();
        DatabaseControlUnit.getInstance().releaseConnection(connection);

    }

    @Override
    public void insertIntoDatabase(Product product) throws SQLException{
        Connection connection = DatabaseControlUnit.getInstance().getConnection();
        String query = "INSERT INTO products(name, price,priceMin,quantity,status,blackFridayDiscountPercentage) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,product.getName());
        preparedStatement.setDouble(2,product.getPrice());
        preparedStatement.setDouble(3,product.getPriceMin());
        preparedStatement.setInt(4,product.getQuantity());
        preparedStatement.setString(5,"'BlackFridayOFF'");
        preparedStatement.setDouble(6,product.getBlackFridayDiscount());
        preparedStatement.executeUpdate();
        DatabaseControlUnit.getInstance().releaseConnection(connection);
    }

    @Override
    public ResultSet selectFromDatabase(Product product) throws SQLException   {
        Connection  connection = DatabaseControlUnit.getInstance().getConnection();
        String query  = "SELECT * FROM products WHERE name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, product.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseControlUnit.getInstance().releaseConnection(connection);
        return resultSet;
    }



}
