package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class ProductListUserForm extends JFrame{
    private JTextField enterNameOfProductTextField;
    private JButton searchButton;
    private JButton previousOrdersButton;
    private JButton orderButton;
    private JList foundProductsList;
    private JPanel panel;
    private JSpinner quantitySpinner;

    public ProductListUserForm (){
        setSize(500,400);
        setContentPane(panel);
    }

    public JTextField getEnterNameOfProductTextField() {
        return enterNameOfProductTextField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getPreviousOrdersButton() {
        return previousOrdersButton;
    }

    public JButton getOrderButton() {
        return orderButton;
    }

    public JList getFoundProductsList() {
        return foundProductsList;
    }

    public JSpinner getQuantitySpinner() {
        return quantitySpinner;
    }
}
