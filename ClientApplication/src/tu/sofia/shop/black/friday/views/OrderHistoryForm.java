package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class OrderHistoryForm extends JFrame{
    private JPanel panel;
    private JList orderHistoryList;
    private JButton backButton;

    public OrderHistoryForm(){
        setSize(400,250);
        setContentPane(panel);
    }

    public JList getOrderHistoryList() {
        return orderHistoryList;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
