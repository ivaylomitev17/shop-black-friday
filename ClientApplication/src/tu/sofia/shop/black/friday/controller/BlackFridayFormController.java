package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.BlackFridayForm;
import tu.sofia.shop.black.friday.views.DialogWindowCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BlackFridayFormController {
    private JFormattedTextField discountField;
    private JList productsList;
    private JButton allProductsButton;
    private JButton selectedProductsButton;
    private JButton backButton;
    private BlackFridayForm blackFridayForm;
    private JButton stopBlackFridayButton;
    private String [] userData;
    private ArrayList <String>productsId;


    public BlackFridayFormController (String []userData){
        if (userData[3].equals("true")){
            initComponents(userData);
            initListeners();
            blackFridayForm.setLocationRelativeTo(null);
            blackFridayForm.setVisible(true);
        }
        else new DialogWindowCreator().createDialogWindow("You do not have rights!");

    }



    public void initComponents (String[]userData){
        blackFridayForm = new BlackFridayForm();
        discountField = blackFridayForm.getDiscountField();
        productsList = blackFridayForm.getProductsList();
        allProductsButton = blackFridayForm.getAllProductsButton();
        selectedProductsButton = blackFridayForm.getSelectedProductsButton();
        backButton = blackFridayForm.getBackButton();
        stopBlackFridayButton = blackFridayForm.getStopBlackFridayButton();
        this.userData = userData;
        productsId = new ArrayList<>();
        initList();
    }



    public void initListeners (){
        allProductsButton.addActionListener(new AllProductsButtonListener());
        selectedProductsButton.addActionListener(new SelectedProductsButtonListener());
        backButton.addActionListener(new BackButtonListener());
        stopBlackFridayButton.addActionListener(new StopBlackFridayButtonListener());
    }



    private void initList(){
        try{
            ServerQuery serverQuery = new ServerQuery();
            serverQuery.dataSend("getAllProducts");
            serverQuery.dataSend("null");
            String product;
            String [] productInfo;
            DefaultListModel listModel = new DefaultListModel();
            while(true){
                product = serverQuery.dataReceive();
                if (product.equals("End")){
                    break;
                }
                productInfo = product.split("#");
                Double price = Double.parseDouble(productInfo[2])- Double.parseDouble(productInfo[2])*(Double.parseDouble(productInfo[6]))/100;
                listModel.addElement(productInfo[1]
                .concat("Price: ")
                .concat (price.toString())
                .concat ("Minimum Price: ")
                .concat (productInfo[3])
                .concat ("Quantity: ")
                .concat (productInfo[4]));
                productsId.add(productInfo[0]);
            }
            productsList.setModel(listModel);
            serverQuery.getSocket().close();
        }catch (Exception e) {
            new DialogWindowCreator().createDialogWindow("Error");
        }
    }



    public void sendToServer( int [] selectedItemsId, String action){
        try{
            ServerQuery serverQuery = new ServerQuery();
            serverQuery.dataSend(action);
            String selectedItemsIdString = new String();
            for (int i = 0; i < selectedItemsId.length; i++) {
                selectedItemsIdString +=productsId.get(selectedItemsId[i]);
                selectedItemsIdString+=", ";
            }
            if (Integer.parseInt(discountField.getText())<100
                &&Integer.parseInt(discountField.getText())>0){

                serverQuery.dataSend(discountField.getText()
                        .concat("#")
                        .concat(selectedItemsIdString));
                serverQuery.getSocket().close();
            }

        }catch (Exception e1){
            new DialogWindowCreator().createDialogWindow("Error!");
        }
    }





    private class AllProductsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int start = 0;
            int end = productsList.getModel().getSize() - 1;
            if (end >= 0) {
                productsList.setSelectionInterval(start, end);
            }
            sendToServer(productsList.getSelectedIndices(), "startBlackFriday");
        }
    }



    private class SelectedProductsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int [] selectedItemsId = productsList.getSelectedIndices();
            sendToServer(selectedItemsId, "startBlackFriday");
        }
    }



    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            blackFridayForm.dispose();
        }
    }

    private class StopBlackFridayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                    int start = 0;
                    int end = productsList.getModel().getSize() - 1;
                    if (end >= 0) {
                        productsList.setSelectionInterval(start, end);
                    }
                    sendToServer(productsList.getSelectedIndices(), "stopBlackFriday");
        }
    }
}
