package tu.sofia.shop.black.friday.model;

import java.sql.Date;

public class Order {
    private int buyerId;
    private Date date;

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

