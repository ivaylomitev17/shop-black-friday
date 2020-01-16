package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.DialogWindowCreator;
import tu.sofia.shop.black.friday.views.ProductListUserForm;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductListUserFormController {
    private JTextField enterNameOfProductTextField;
    private JButton searchButton;
    private JButton previousOrdersButton;
    private JButton orderButton;
    private JList foundProductsList;
    private JSpinner quantitySpinner;
    private String[] userData;
    private ProductListUserForm productListUserForm;
    private DefaultListModel listModel;
    private ArrayList <String> foundProducts;
    private List<Integer> quantityOfFoundProducts;


    public ProductListUserFormController(String[] userData) {
        this.userData = userData;
        initComponents();
        initListeners();
        productListUserForm.setLocationRelativeTo(null);
        productListUserForm.setVisible(true);
        productListUserForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initComponents() {
        productListUserForm = new ProductListUserForm();
        enterNameOfProductTextField = productListUserForm.getEnterNameOfProductTextField();
        listModel = new DefaultListModel();
        foundProducts = new ArrayList<>();
        quantityOfFoundProducts = new ArrayList<>();
        searchButton = productListUserForm.getSearchButton();
        previousOrdersButton = productListUserForm.getPreviousOrdersButton();
        foundProductsList = productListUserForm.getFoundProductsList();
        quantitySpinner = productListUserForm.getQuantitySpinner();
        orderButton = productListUserForm.getOrderButton();
    }

    public void initListeners() {
        orderButton.addActionListener(new OrderButtonListener());
        searchButton.addActionListener(new SearchButtonListener());
        previousOrdersButton.addActionListener(new PreviousOrderButtonListener());
        foundProductsList.addListSelectionListener(new ListSelectionListListener());
        foundProductsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                listModel.clear();
                foundProducts.clear();
                ServerQuery serverQuery = new ServerQuery();
                serverQuery.dataSend("searchProduct");
                serverQuery.dataSend(enterNameOfProductTextField.getText());
                String result;
                int index = 0;
                while (true) {
                    result = serverQuery.dataReceive();
                    if (result.equals("End")) {
                        break;
                    }
                    String[] productInfo = result.split("#");
                    Double price = Double.parseDouble(productInfo[2])- Double.parseDouble(productInfo[2])*(Double.parseDouble(productInfo[6]))/100;
                    foundProducts.add(productInfo[0]);
                    listModel.addElement((productInfo[1])
                            .concat(" Price: ")
                            .concat(price.toString()));
                    quantityOfFoundProducts.add(Integer.parseInt(productInfo[4]));
                    index++;
                }
                if (index == 0) {
                    listModel.addElement("No such product found!");
                }
                foundProductsList.setModel(listModel);
                serverQuery.getSocket().close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ServerQuery serverQuery = new ServerQuery();
                serverQuery.dataSend("newOrder");
                String commandToServer = userData[0]
                        .concat("#")
                        .concat (foundProducts.get(foundProductsList.getSelectedIndex()))
                        .concat("#")
                        .concat (quantitySpinner.getValue().toString());
                serverQuery.dataSend(commandToServer);
                String serverAnswer = serverQuery.dataReceive();
                if (serverAnswer.equals("Unsuccessful")){
                    new DialogWindowCreator().createDialogWindow("Order failed!");
                }
                if (serverAnswer.equals("Successful")){
                    new DialogWindowCreator().createDialogWindow("Successful order!");
                }
                serverQuery.getSocket().close();
            }catch (Exception e1){
                e1.printStackTrace();
                new DialogWindowCreator().createDialogWindow("Order failed!");
            }

        }
    }

    private class ListSelectionListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int min = 1;
            int max =  quantityOfFoundProducts.get(foundProductsList.getSelectedIndex());
            int step = 1;
            int initValue = 1;
            SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
            quantitySpinner.setModel(model);
        }
    }


    private class PreviousOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            productListUserForm.dispose();
            new OrderHistoryFormController(userData);
        }
    }
}

