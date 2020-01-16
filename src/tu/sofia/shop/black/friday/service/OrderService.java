package tu.sofia.shop.black.friday.service;

import tu.sofia.shop.black.friday.model.Order;
import tu.sofia.shop.black.friday.util.DatabaseControlUnit;
import tu.sofia.shop.black.friday.util.DatabaseManipulation;

import java.sql.*;
import java.util.List;

public class OrderService implements DatabaseManipulation<Order>  {

    private int rowAffected;
    private int quantity;
    private int buyerId;
    private int productId;

    public void createNewOrder (Order order, List <String> orderParameters) throws SQLException{
        buyerId = Integer.parseInt(orderParameters.get(0));
        productId = Integer.parseInt(orderParameters.get(1));
        quantity = Integer.parseInt(orderParameters.get(2));
        insertIntoDatabase(order);
    }

    public ResultSet selectFromDatabaseById(int id) throws SQLException {
        Connection  connection = DatabaseControlUnit.getInstance().getConnection();
        String query  = "SELECT o.orderDate,\n" +
                "       op.originalPrice,\n" +
                "       p.name\n" +
                "FROM orders AS o\n" +
                "\n" +
                "       JOIN orderproducts op on o.id = op.Orders_id JOIN products p on op.Products_id = p.id\n" +
                "WHERE o.buyerId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, Integer.toString(id));
        ResultSet resultSet = preparedStatement.executeQuery();
        DatabaseControlUnit.getInstance().releaseConnection(connection);
        return resultSet;
    }
    @Override
    public void updateDatabase(String column, String value, String  condition) throws SQLException {
        new ProductService().updateDatabase(column,value,condition);
    }

    @Override
    public void insertIntoDatabase(Order order)throws SQLException{
        int generatedOrderId;
        Connection connection = DatabaseControlUnit.getInstance().getConnection();
                ResultSet resultSetGetGeneratedKeyOrders;
                String orderQuery = "INSERT INTO orders(buyerId,orderDate) "
                        + "VALUES(?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(orderQuery,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, Integer.toString(order.getBuyerId()));
                preparedStatement.setDate(2, order.getDate());
                rowAffected = preparedStatement.executeUpdate();
                if(rowAffected == 1) {
                    resultSetGetGeneratedKeyOrders = preparedStatement.getGeneratedKeys();
                    if(resultSetGetGeneratedKeyOrders.next()){
                        generatedOrderId = resultSetGetGeneratedKeyOrders.getInt(1);
                        ResultSet orderedProduct = new ProductService().findProductById(productId);
                        String orderproductsQuery = "INSERT INTO orderproducts (count,originalPrice,Discount,Orders_id,Products_id) " +
                                "VALUES(?,?,?,?,?)";
                        preparedStatement = connection.prepareStatement(orderproductsQuery);
                        preparedStatement.setInt(1,quantity);
                        preparedStatement.setDouble (2,Double.parseDouble(Double.toString(orderedProduct.getDouble(3))));
                        preparedStatement.setDouble (3,Double.parseDouble(Double.toString(orderedProduct.getDouble(7))));
                        preparedStatement.setInt(4,generatedOrderId);
                        preparedStatement.setInt(5,productId);
                        preparedStatement.executeUpdate();
                        System.out.println("test");
                        updateDatabase("quantity",Integer.toString(orderedProduct.getInt(5)-quantity),Integer.toString(productId));
                }
            }
        DatabaseControlUnit.getInstance().releaseConnection(connection);
    }



    @Override
    public ResultSet selectFromDatabase(Order o) throws SQLException {
        return null;
    }






}
