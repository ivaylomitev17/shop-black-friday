package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.ChangePriceForm;
import tu.sofia.shop.black.friday.views.DialogWindowCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePriceFormController {
    private JTextField currentPriceField;
    private JTextField minimumPriceField;
    private JFormattedTextField inputPriceField;
    private JButton setAsNewCurrentButton;
    private JButton setAsNewMinimumButton;
    private ChangePriceForm changePriceForm;
    private JButton backButton;
    private String [] userData;
    private String productId;

    public ChangePriceFormController (String[] userData, String currentPrice, String minimumPrice, String productId){
        if (userData[3].equals("false")){
            new DialogWindowCreator().createDialogWindow("You do not have rights!");
        }
        else{
            initComponents(currentPrice,minimumPrice, userData, productId);
            initListeners();
            currentPriceField.setEditable(false);
            minimumPriceField.setEditable(false);
            changePriceForm.setLocationRelativeTo(null);
            changePriceForm.setVisible(true);
            changePriceForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public void initComponents(String currentPrice, String minimumPrice, String [] userData, String productId){
        this.userData = userData;
        this.productId = productId;
        changePriceForm = new ChangePriceForm();
        currentPriceField = changePriceForm.getCurrentPriceField();
        currentPriceField.setText(currentPrice);
        minimumPriceField = changePriceForm.getMinimumPriceField();
        minimumPriceField.setText(minimumPrice);
        inputPriceField = changePriceForm.getInputPriceField();
        backButton = changePriceForm.getBackButton();
        setAsNewCurrentButton = changePriceForm.getSetAsNewCurrentButton();
        setAsNewMinimumButton = changePriceForm.getSetAsNewMinimumButton();
    }

    public void initListeners () {
        backButton.addActionListener(new BackButtonListener());
        setAsNewMinimumButton.addActionListener(new SetAsMinimumButtonListener());
        setAsNewCurrentButton.addActionListener(new SetAsNewCurrentButtonListener());
    }
    public void changePrice (String columnToChange){
        try{
            ServerQuery serverQuery = new ServerQuery();
            serverQuery.dataSend("changePrice");
            serverQuery.dataSend(columnToChange.
                    concat("#")
                    .concat(inputPriceField.getText())
                    .concat("#")
                    .concat(this.productId));
            serverQuery.getSocket().close();
        }catch (Exception e){

        }

    }
    private class SetAsNewCurrentButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePrice("price");
        }
    }

    private class SetAsMinimumButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePrice("priceMin");
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            changePriceForm.dispose();
        }
    }
}
