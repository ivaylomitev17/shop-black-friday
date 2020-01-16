package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.AddQuantityForm;
import tu.sofia.shop.black.friday.views.DialogWindowCreator;
import tu.sofia.shop.black.friday.views.ProductListForm;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductListFormController {

    private ProductListForm productListForm;
    private JTextField enterNameOfProductTextField;
    private JButton searchButton;
    private JList <String> foundProductsList;
    private JButton changePriceButton;
    private JButton addQuantityButton;
    private JButton backButton;
    private String [] userData;
    private ArrayList<String> foundProductsPrice;
    private ArrayList<String> foundProductsMinimumPrice;
    private ArrayList<String>foundProductsId;
    DefaultListModel listModel;

    public ProductListFormController(String [] userData) {
        this.userData = userData;
        initComponents();
        initListeners();
        productListForm.setLocationRelativeTo(null);
        productListForm.setVisible(true);
        productListForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initComponents() {
        productListForm = new ProductListForm();
        enterNameOfProductTextField = productListForm.getEnterNameOfProductTextField();
        listModel = new DefaultListModel();
        foundProductsPrice = new ArrayList<>();
        foundProductsMinimumPrice = new ArrayList<>();
        foundProductsId = new ArrayList<>();
        searchButton = productListForm.getSearchButton();
        changePriceButton = productListForm.getChangePriceButton();
        addQuantityButton = productListForm.getAddQuantityButton();
        foundProductsList = productListForm.getFoundProductsList();
        backButton = productListForm.getBackButton();
    }

    public void initListeners() {
        searchButton.addActionListener(new SearchButtonListener());
        changePriceButton.addActionListener(new ChangePriceButtonListener());
        addQuantityButton.addActionListener(new AddQuantityButtonListener());
        backButton.addActionListener(new BackButtonListener());
        foundProductsList.addListSelectionListener(new ListSelectionListListener());
        foundProductsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }


    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                listModel.clear();
                foundProductsId.clear();
                foundProductsMinimumPrice.clear();
                foundProductsPrice.clear();
                ServerQuery serverQuery = new ServerQuery();
                serverQuery.dataSend("searchProduct");
                serverQuery.dataSend(enterNameOfProductTextField.getText());
                String result;
                int index = 0;
                while(true) {
                    result = serverQuery.dataReceive();
                    if (result.equals("End")) {
                        break;
                    }
                    String []productInfo = result.split("#");
                    Double price = Double.parseDouble(productInfo[2])- Double.parseDouble(productInfo[2])*(Double.parseDouble(productInfo[6]))/100;
                    listModel.addElement(productInfo[1]
                            .concat(" Price: ")
                            .concat(price.toString())
                            .concat(" Min Price: ")
                            .concat(productInfo[3])
                            .concat(" Quantity: ")
                            .concat(productInfo[4]));
                    index++;
                    foundProductsId.add(productInfo[0]);
                    foundProductsPrice.add(price.toString());
                    foundProductsMinimumPrice.add(productInfo[3]);

                }
                if (index==0){
                    listModel.addElement("No such product found!");
                }
                foundProductsList.setModel(listModel);


            }catch (Exception e1){
                e1.printStackTrace();
            }


        }
    }

    private class ChangePriceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int selectedIndex = foundProductsList.getSelectedIndex();
                new ChangePriceFormController(userData,foundProductsPrice.get(selectedIndex),
                        foundProductsMinimumPrice.get(selectedIndex),
                        foundProductsId.get(selectedIndex));
            }catch (Exception e1){
                e1.printStackTrace();
                new DialogWindowCreator().createDialogWindow("An error occurred");
            }
        }
    }

    private class AddQuantityButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddQuantityFormController(foundProductsId.get(foundProductsList.getSelectedIndex()));
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new MenuEmployeeFormController(userData);
            productListForm.dispose();
        }
    }

    private class ListSelectionListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
        }
    }
}
