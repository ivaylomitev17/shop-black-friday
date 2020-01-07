package tu.sofia.shop.black.friday.service;

import tu.sofia.shop.black.friday.model.Order;
import tu.sofia.shop.black.friday.model.Product;
import tu.sofia.shop.black.friday.util.DatabaseControlUnit;
import tu.sofia.shop.black.friday.util.DatabaseManipulation;

import java.sql.*;
import java.util.Calendar;

public class OrderService implements DatabaseManipulation<Order> {

    Connection connection;
    private ResultSet resultSet;
    private int rowAffected;
    private int quantity;

    public void createNewOrder ( Order order, ResultSet resultSet,Connection connection, int quantity) throws SQLException{
        this.quantity = quantity;
        this.resultSet = resultSet;
        this.quantity = quantity;
        this.connection = connection;
        insertIntoDatabase(order);

    }

    @Override
    public void updateDatabase(String column, String value, String  condition) throws SQLException {
        new ProductService().updateDatabase(column,value,condition);
    }

    @Override
    public void insertIntoDatabase(Order order)throws SQLException{
        int generatedOrderId;
        ResultSet resultSetGetGeneratedKeyOrders = null;
        String orderQuery = "INSERT INTO orders(buyerId,orderDate) "
                    + "VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(orderQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Integer.toString(order.getBuyerId()));
            preparedStatement.setDate(2, order.getDate());
            rowAffected = preparedStatement.executeUpdate();
            if(rowAffected == 1) {
                resultSetGetGeneratedKeyOrders = preparedStatement.getGeneratedKeys();
                if(resultSetGetGeneratedKeyOrders.next()) {
                    generatedOrderId = resultSetGetGeneratedKeyOrders.getInt(1);
                    String orderproductsQuery = "INSERT INTO orderproducts (count,originalPrice,Discount,Orders_id,Products_id) " +
                            "VALUES(?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(orderproductsQuery);
                    preparedStatement.setInt(1,quantity);
                    preparedStatement.setDouble(2,resultSet.getDouble(3));
                    preparedStatement.setDouble(3,resultSet.getDouble(7));
                    preparedStatement.setInt(4,generatedOrderId);
                    preparedStatement.setInt(5,resultSet.getInt(1));
                    preparedStatement.executeUpdate();
                    String condition = "id =";
                    condition.concat(resultSet.getString(1));
                    updateDatabase("quantity",Integer.toString(resultSet.getInt(1)-quantity),condition);
                }
            }
    }

    @Override
    public ResultSet selectFromDatabase(String whereCondition) throws SQLException {
        return null;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
