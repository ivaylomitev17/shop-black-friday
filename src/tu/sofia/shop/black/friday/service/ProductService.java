package tu.sofia.shop.black.friday.service;

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

    public ResultSet findProductById(int id) throws SQLException{

        ResultSet resultSet = selectFromDatabaseById(id);
        resultSet.first();
        return resultSet;
    }


    public void updateProductPrice(String column,String newPrice,String id)throws SQLException{
        ResultSet resultSet = findProductById(Integer.parseInt(id));
        resultSet.first();
        if (Double.compare(resultSet.getDouble(4),Double.parseDouble(newPrice))<0){
            updateDatabase(column,newPrice,id);
        }
    }

    public void updateDatabaseBlackFriday(String discount, String status, String[] id)throws SQLException {
        Connection connection = DatabaseControlUnit.getInstance().getConnection();
        String query = "UPDATE products SET status = ?, blackFridayDiscountPercentage = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < id.length; i++) {
                preparedStatement.setString(1, status);
                preparedStatement.setString(2, discount);
                preparedStatement.setString(3, id[i]);
                preparedStatement.executeUpdate();
            }
        DatabaseControlUnit.getInstance().releaseConnection(connection);
        }

    public ResultSet selectFromDatabaseById(int id) throws SQLException {
        Connection  connection = DatabaseControlUnit.getInstance().getConnection();
        String query  = "SELECT * FROM products WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, Integer.toString(id));
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseControlUnit.getInstance().releaseConnection(connection);
        return resultSet;
    }

    public ResultSet selectFromDatabaseAll() throws SQLException{
        Connection  connection = DatabaseControlUnit.getInstance().getConnection();
        String query  = "SELECT * FROM products";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseControlUnit.getInstance().releaseConnection(connection);
        return resultSet;
    }
    @Override
    public void updateDatabase(String column, String value, String condition) throws SQLException {
        Connection connection = DatabaseControlUnit.getInstance().getConnection();
        String query = "UPDATE products SET "+ column + " = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,value);
        preparedStatement.setString(2,condition);
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
        preparedStatement.setString(5,"BlackFridayOFF");
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
