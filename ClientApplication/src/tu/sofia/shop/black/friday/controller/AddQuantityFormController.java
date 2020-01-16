package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.AddQuantityForm;
import tu.sofia.shop.black.friday.views.DialogWindowCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddQuantityFormController {
    private JLabel QuantityLabel;
    private JButton addQuantityButton;
    private JButton backButton;
    private JSpinner quantitySpinner;
    private AddQuantityForm addQuantityForm;
    private String productId;

    public AddQuantityFormController(String productId){
        initComponents(productId);
        initListeners();
        addQuantityForm.setLocationRelativeTo(null);
        addQuantityForm.setVisible(true);
    }
    private void initComponents (String productId){
        this.productId = productId;
        addQuantityForm = new AddQuantityForm();
        QuantityLabel = addQuantityForm.getQuantityLabel();
        quantitySpinner = addQuantityForm.getQuantitySpinner();
        addQuantityButton = addQuantityForm.getAddQuantityButton();
        backButton = addQuantityForm.getBackButton();

    }
    private void initListeners(){
        addQuantityButton.addActionListener(new AddQuantityButtonListener());
        backButton.addActionListener(new BackButtonListener());
    }

    private class AddQuantityButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ServerQuery serverQuery = new ServerQuery();
                serverQuery.dataSend("addQuantity");
                serverQuery.dataSend(quantitySpinner.getValue().toString().concat("#")
                        .concat(productId));
                String received = serverQuery.dataReceive();
                if (received.equals("Unsuccessful")){
                    new DialogWindowCreator().createDialogWindow("An error occurred!");
                }
                else new DialogWindowCreator().createDialogWindow("Successful!");
            }catch (Exception e1){
                new DialogWindowCreator().createDialogWindow("An error occurred!");
            }

        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            addQuantityForm.dispose();
        }
    }
}
