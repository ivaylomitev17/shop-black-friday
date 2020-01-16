package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class ChangePriceForm extends JFrame{
    private JTextField currentPriceField;
    private JTextField minimumPriceField;
    private JFormattedTextField inputPriceField;
    private JButton setAsNewCurrentButton;
    private JButton setAsNewMinimumButton;
    private JPanel panel;
    private JButton backButton;


    public ChangePriceForm(){

        setSize(300,250);
        setContentPane(panel);

    }

    public JTextField getCurrentPriceField() {
        return currentPriceField;
    }

    public JTextField getMinimumPriceField() {
        return minimumPriceField;
    }

    public JFormattedTextField getInputPriceField() {
        return inputPriceField;
    }

    public JButton getSetAsNewCurrentButton() {
        return setAsNewCurrentButton;
    }

    public JButton getSetAsNewMinimumButton() {
        return setAsNewMinimumButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
