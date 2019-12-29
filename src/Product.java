import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Product {
    private int id;
    private String name;
    private double price;
    private double priceMin;
    private int quantity;
    private enum status{
        BlackFridayON,
        BlackFridayOFF
    }
    private double BlackFridayDiscount;
    Scanner sc = new Scanner (System.in);


    public Product(){

    }

    public void addProductToDB (){
        System.out.println("Input Product name:");
        setName(sc.nextLine());
        System.out.println("Input Actual Price:");
        setPrice(Double.parseDouble(sc.nextLine()));
        System.out.println("Input Minimum price");
        setPriceMin(Double.parseDouble(sc.nextLine()));
        System.out.println("Input quantity");
        setQuantity(Integer.parseInt(sc.nextLine()));
        String query = "insert into products(name, price,priceMin,quantity,status,blackFridayDiscountPercentage) values ('" + this.name + "','" + this.price + "','" + this.priceMin+ "','" +this.quantity+ "','BlackFridayOFF','0')";

        try{
            ConnectionToDB connection = new ConnectionToDB();
            connection.createConnection();
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate(query);
            connection.getConnection().close();
        }catch (Exception e){
            System.out.println("A problem occurred connecting to database");
            e.printStackTrace();
        }

    }
    public ResultSet searchProductByName()throws Exception{
        System.out.println("Input product name for search:");
        String query = "SELECT * FROM products WHERE name = '"+sc.nextLine()+ "'";
            ConnectionToDB connection = new ConnectionToDB();
            connection.createConnection();
            Statement statement = connection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.first();
        //System.out.println(resultSet.getString(2));
        return resultSet;
    }


    public void changeProductPrice  () throws Exception{
        ResultSet resultSet = searchProductByName();
        //int id = Integer.parseInt(resultSet.getString (1))
        System.out.println("Current product price:"+resultSet.getString(3)+"    (minimum price:)"+resultSet.getString(4));
        System.out.println("Input new price:");
        double newPrice = Double.parseDouble(sc.nextLine());
        if ((Double.compare(newPrice,Double.parseDouble(resultSet.getString(4))))>0){
            ConnectionToDB connection = new ConnectionToDB();
            connection.updateDB("products","price",Double.toString(newPrice),"id",resultSet.getString(1));
        }
        else System.out.println("Price cannot be lower than minimum price!");
    }








    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(double priceMin) {
        this.priceMin = priceMin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBlackFridayDiscount() {
        return BlackFridayDiscount;
    }

    public void setBlackFridayDiscount(double blackFridayDiscount) {
        BlackFridayDiscount = blackFridayDiscount;
    }
}
