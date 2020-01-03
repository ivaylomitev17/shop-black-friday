package tu.sofia.shop.black.friday;

import tu.sofia.shop.black.friday.util.ConnectionToDB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class Order {
    public Order (){

    }

    public void createAnOrder (int idBuyer, ResultSet resultSet, int quantity) throws Exception{
        ConnectionToDB newOrder = new ConnectionToDB();
        newOrder.createConnection();
        ResultSet resultSetGetGeneratedKeyOrders = null;
        int generatedOrderId=-1;

        Date date = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        //int rowAffected = statement.executeUpdate();

        String query = "INSERT INTO orders(buyerId,orderDate) "
                + "VALUES(?,?)";

        PreparedStatement preparedStatement = newOrder.getConnection().prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, Integer.toString(idBuyer));
        preparedStatement.setDate(2, date);
        int rowAffected = preparedStatement.executeUpdate();
        System.out.println("row Afected: "+rowAffected);
        if(rowAffected == 1) {
            resultSetGetGeneratedKeyOrders = preparedStatement.getGeneratedKeys();
            if(resultSetGetGeneratedKeyOrders.next())
                generatedOrderId = resultSetGetGeneratedKeyOrders.getInt(1);
        }

        if (resultSetGetGeneratedKeyOrders!=null&&generatedOrderId!=-1){
            query = "insert into orderproducts(count,originalPrice,Discount,Orders_id,Products_id) values ('" + quantity + "','" + resultSet.getDouble(3) + "','"+resultSet.getDouble(7)+ "','"+generatedOrderId +"','"+ resultSet.getInt(1)+ "')";
            Statement statement = newOrder.getConnection().createStatement();
            statement.executeUpdate(query);
            newOrder.updateDB("products","quantity",Integer.toString(resultSet.getInt(1)-quantity),"id",resultSet.getString(1));
        }


    }
}
