package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.DialogWindowCreator;
import tu.sofia.shop.black.friday.views.OrderHistoryForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderHistoryFormController {
    private OrderHistoryForm orderHistoryForm;
    private JList orderHistoryList;
    private JButton backButton;
    private String [] user;
    private DefaultListModel listModel;

    public OrderHistoryFormController(String [] user){
        this.user = user;
        initComponents();
        initListeners();
        orderHistoryForm.setLocationRelativeTo(null);
        orderHistoryForm.setVisible(true);
        orderHistoryForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initComponents (){
        listModel = new DefaultListModel();
        orderHistoryForm = new OrderHistoryForm();
        orderHistoryList = orderHistoryForm.getOrderHistoryList();
        backButton = orderHistoryForm.getBackButton();
        setOrderHistoryList();

    }
    public void initListeners (){
        backButton.addActionListener(new BackButtonListener());
    }

    public void setOrderHistoryList (){
        try{
            ServerQuery serverQuery = new ServerQuery();
            serverQuery.dataSend("orderHistory");
            serverQuery.dataSend(user[0]);
            String result;
            while (true){
                result = serverQuery.dataReceive();
                if(result.equals("End")){
                    break;
                }
                String[] toShow = result.split("#");
                listModel.addElement(toShow[0].concat(" ").concat(toShow[1]).concat(" ").concat(toShow[2]));
            }
            orderHistoryList.setModel(listModel);

        }catch (Exception e){
            new DialogWindowCreator().createDialogWindow("Error occurred");
        }

    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            orderHistoryForm.dispose();
            new ProductListUserFormController(user);
        }
    }
}
