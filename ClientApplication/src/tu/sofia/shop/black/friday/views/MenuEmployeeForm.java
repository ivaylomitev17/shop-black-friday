package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class MenuEmployeeForm extends JFrame{

    private JButton findProductButton;
    private JButton newProductButton;
    private JPanel panel;
    private JButton blackFridayButton;

    public MenuEmployeeForm (){
        setSize(400,400);
        setContentPane(panel);
    }


    public JButton getFindProductButton() {
        return findProductButton;
    }

    public JButton getNewProductButton() {
        return newProductButton;
    }

    public JButton getBlackFridayButton() {
        return blackFridayButton;
    }
}
