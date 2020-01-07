package tu.sofia.shop.black.friday.service;

import tu.sofia.shop.black.friday.Exceptions.NewPriceLowerThanMinimumException;
import tu.sofia.shop.black.friday.model.Product;
import tu.sofia.shop.black.friday.util.DatabaseManipulation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductService implements DatabaseManipulation <Product>{
    Connection connection;

    public ResultSet findProductByName(String name) throws Exception{
        String whereCondition = "name = ";
        whereCondition.concat(name);
        ResultSet resultSet = selectFromDatabase(whereCondition);
        resultSet.first();
        return resultSet;
    }


    public void updateProductPrice(String name, double newPrice)throws Exception{
         ResultSet resultSet = findProductByName(name);
        if ((Double.compare(newPrice,Double.parseDouble(resultSet.getString(4))))>0){
            updateDatabase("price",Double.toString(newPrice),"id = "+resultSet.getString(1));
       }
        else throw new NewPriceLowerThanMinimumException();
    }

    @Override
    public void updateDatabase(String column, String value, String condition) throws SQLException {

        String query = "UPDATE products SET ? = ? WHERE?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,column);
        preparedStatement.setString(2,value);
        preparedStatement.setString(3,condition);
        preparedStatement.executeUpdate();

    }

    @Override
    public void insertIntoDatabase(Product product) throws SQLException{
        String query = "INSERT INTO products(name, price,priceMin,quantity,status,blackFridayDiscountPercentage) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,product.getName());
        preparedStatement.setDouble(2,product.getPrice());
        preparedStatement.setDouble(3,product.getPriceMin());
        preparedStatement.setInt(4,product.getQuantity());
        preparedStatement.setString(5,"'BlackFridayOFF'");
        preparedStatement.setDouble(6,product.getBlackFridayDiscount());
        preparedStatement.executeUpdate();

    }

    @Override
    public ResultSet selectFromDatabase(String whereCondition) throws SQLException   {
        String query  = "SELECT * FROM products WHERE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,whereCondition);

        return preparedStatement.executeQuery()  ;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
