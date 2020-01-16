package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class AddQuantityForm extends JFrame{
    private JLabel QuantityLabel;
    private JButton addQuantityButton;
    private JButton backButton;
    private JSpinner quantitySpinner;
    private JPanel panel;

    public AddQuantityForm (){
        setSize(400,400);
        setContentPane(panel);
    }

    public JLabel getQuantityLabel() {
        return QuantityLabel;
    }

    public JButton getAddQuantityButton() {
        return addQuantityButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JSpinner getQuantitySpinner() {
        return quantitySpinner;
    }
}
