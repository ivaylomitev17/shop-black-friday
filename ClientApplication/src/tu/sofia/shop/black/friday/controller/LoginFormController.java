package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.DialogWindowCreator;
import tu.sofia.shop.black.friday.views.LoginForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFormController {
    private LoginForm loginForm;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registrationButton;


    public LoginFormController (){
        initComponents();
        initListeners();
        loginForm.setLocationRelativeTo(null);
        loginForm.setVisible(true);
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void initComponents (){
        loginForm = new LoginForm();
        loginButton = loginForm.getLoginButton();
        registrationButton = loginForm.getRegistrationButton();
        usernameField = loginForm.getUsernameField();
        passwordField = loginForm.getPasswordField();

    }
    public void initListeners(){
        loginButton.addActionListener(new LoginButtonListener());
        registrationButton.addActionListener(new RegistrationButtonListener());

    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ServerQuery serverQuery = new ServerQuery();
                serverQuery.dataSend("login");
                String command = usernameField.getText().concat("#")
                        .concat(String.valueOf(passwordField.getPassword()));
                serverQuery.dataSend(command);
                String toCmp = serverQuery.dataReceive();

                if (!toCmp.equals("Unsuccessful")) {
                    String [] userData = toCmp.split("#");
                    //checking if the the user is client or employee
                    if (userData[3].equals("false")){

                        new ProductListUserFormController(userData);
                        loginForm.dispose();
                    }else {
                        new MenuEmployeeFormController(userData);
                        loginForm.dispose();
                    }
                }else new DialogWindowCreator().createDialogWindow("Wrong username or password!");
                serverQuery.getSocket().close();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }

    private class RegistrationButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            new RegistrationFormController();
        }
    }

}
