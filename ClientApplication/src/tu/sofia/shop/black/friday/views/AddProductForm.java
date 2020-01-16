package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class AddProductForm extends JFrame{
    private JFormattedTextField minPriceField;
    private JTextField nameField;
    private JFormattedTextField priceField;
    private JButton addProductButton;
    private JButton backButton;
    private JPanel panel;
    private JFormattedTextField quantityField;

    public AddProductForm(){

        setSize(400,250);
        setContentPane(panel);


    }

    public JFormattedTextField getMinPriceField() {
        return minPriceField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JFormattedTextField getPriceField() {
        return priceField;
    }

    public JButton getAddProductButton() {
        return addProductButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JFormattedTextField getQuantityField() {
        return quantityField;
    }
}
