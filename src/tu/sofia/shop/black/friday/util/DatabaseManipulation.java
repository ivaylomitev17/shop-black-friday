package tu.sofia.shop.black.friday.util;

import java.sql.Statement;

public class DatabaseManipulation extends DatabaseControlUnit{

    private Statement statement;
    public void updateDatabase(String table, String set, String update, String where, String condition) throws Exception{

        String query = "UPDATE "+table+"\n SET "+set+" = '"+update+"'\n WHERE "+where+"='" + condition+"'";
        this.statement = createStatement();
        statement.executeUpdate(query);
        super.getConnection().close();
    }

    public void insertIntoDatabase(String query)throws Exception{
        this.statement = createStatement();
        statement.executeUpdate(query);
        super.getConnection().close();
    }
}
