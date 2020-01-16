package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class BlackFridayForm extends JFrame {
    private JPanel panel;
    private JFormattedTextField discountField;
    private JList productsList;
    private JButton AllProductsButton;
    private JButton selectedProductsButton;
    private JButton backButton;
    private JButton stopBlackFridayButton;

    public BlackFridayForm (){

        setSize(500,400);
        setContentPane(panel);
    }

    public JFormattedTextField getDiscountField() {
        return discountField;
    }

    public JList getProductsList() {
        return productsList;
    }

    public JButton getAllProductsButton() {
        return AllProductsButton;
    }

    public JButton getSelectedProductsButton() {
        return selectedProductsButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getStopBlackFridayButton() {
        return stopBlackFridayButton;
    }
}
