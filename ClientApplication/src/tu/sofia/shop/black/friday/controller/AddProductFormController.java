package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.AddProductForm;
import tu.sofia.shop.black.friday.views.DialogWindowCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductFormController {
    private JFormattedTextField minPriceField;
    private JTextField nameField;
    private JFormattedTextField priceField;
    private JButton addProductButton;
    private JButton backButton;
    private JFormattedTextField quantityField;
    private String [] userData;
    private AddProductForm addProductForm;


    public AddProductFormController(String [] userData){
        if (userData[3].equals("true")){
            initComponents(userData);
            initListeners();
            addProductForm.setLocationRelativeTo(null);
            addProductForm.setVisible(true);
            addProductForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        else new DialogWindowCreator().createDialogWindow("You do not have rights!");

    }

    private void initComponents(String [] userData) {
        this.userData = userData;
        addProductForm = new AddProductForm();
        minPriceField = addProductForm.getMinPriceField();
        priceField = addProductForm.getPriceField();
        nameField = addProductForm.getNameField();
        addProductButton = addProductForm.getAddProductButton();
        backButton = addProductForm.getBackButton();
        quantityField = addProductForm.getQuantityField();

    }

    private void initListeners() {
        addProductButton.addActionListener(new AddProductButtonListener());
        backButton.addActionListener(new BackButtonListener ());
    }


    private class AddProductButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ServerQuery serverQuery = new ServerQuery();
                serverQuery.dataSend("addProduct");
                serverQuery.dataSend(nameField.getText()
                        .concat("#")
                        .concat(priceField.getText())
                        .concat("#")
                        .concat(minPriceField.getText())
                        .concat("#")
                        .concat(quantityField.getText()));
                if (serverQuery.dataReceive().equals("Successful")){
                    new DialogWindowCreator().createDialogWindow("Product added successfully!");
                }
                serverQuery.getSocket().close();
            }catch (Exception e1){
                new DialogWindowCreator().createDialogWindow("Error occurred!");
            }
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addProductForm.dispose();
            new ProductListFormController(userData);
        }

    }
}
