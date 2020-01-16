package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.MenuEmployeeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuEmployeeFormController {

    private JButton blackFridayButton;
    private JButton findProductButton;
    private JButton addProductButton;
    private MenuEmployeeForm menuEmployeeForm;
    private String [] userData;
    public MenuEmployeeFormController (String [] userData){
        this.userData = userData;
        initComponents();
        initListeners();
        menuEmployeeForm.setLocationRelativeTo(null);
        menuEmployeeForm.setVisible(true);
        menuEmployeeForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initComponents () {
        menuEmployeeForm = new MenuEmployeeForm();
        findProductButton = menuEmployeeForm.getFindProductButton();
        addProductButton = menuEmployeeForm.getNewProductButton();
        blackFridayButton = menuEmployeeForm.getBlackFridayButton();

    }
    public void initListeners() {
        findProductButton.addActionListener(new FindProductButtonListener());
        addProductButton.addActionListener(new AddProductButtonListener());
        blackFridayButton.addActionListener(new BlackFridayButtonListener());
    }

    private class FindProductButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ProductListFormController(userData);
            menuEmployeeForm.dispose();
        }
    }

    private class AddProductButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddProductFormController(userData);
            menuEmployeeForm.dispose();
        }
    }

    private class BlackFridayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new BlackFridayFormController(userData);
        }
    }
}
