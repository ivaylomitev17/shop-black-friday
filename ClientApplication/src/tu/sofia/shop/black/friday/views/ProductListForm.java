package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class ProductListForm extends JFrame {
    private JPanel panel;
    private JTextField enterNameOfProductTextField;
    private JButton searchButton;
    private JList <String> foundProductsList;
    private JButton changePriceButton;
    private JButton addQuantityButton;
    private JButton backButton;


    public ProductListForm (){
        setSize(500,400);
        setContentPane(panel);
    }

    public JTextField getEnterNameOfProductTextField() {
        return enterNameOfProductTextField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JList getFoundProductsList() {
        return foundProductsList;
    }

    public JButton getChangePriceButton() {
        return changePriceButton;
    }

    public JButton getAddQuantityButton() {
        return addQuantityButton;
    }

    public JButton getBackButton() {
        return backButton;
    }


}
