package tu.sofia.shop.black.friday.controller;

import tu.sofia.shop.black.friday.views.DialogWindowCreator;
import tu.sofia.shop.black.friday.views.RegistrationForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFormController {
    private RegistrationForm registrationForm;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registrationButton;

    public RegistrationFormController (){
        initComponents();
        initListeners();
        registrationForm.setLocationRelativeTo(null);
        registrationForm.setVisible(true);

    }

    public void initComponents (){
        registrationForm = new RegistrationForm();
        registrationButton = registrationForm.getRegistrationButton();
        usernameField = registrationForm.getUsernameField();
        passwordField = registrationForm.getPasswordField();

    }
    public void initListeners(){
        registrationButton.addActionListener(new RegistrationFormController.RegistrationButtonListener());
    }


    private class RegistrationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                ServerQuery serverQuery = new ServerQuery();
                serverQuery.dataSend("registration");
                String command = usernameField.getText().concat("#")
                        .concat(String.valueOf(passwordField.getPassword()));
                serverQuery.dataSend(command);
                String serverAnswer = serverQuery.dataReceive();
                if (serverAnswer.equals("Successful")
                        ||serverAnswer.equals("Incorrect username")
                        ||serverAnswer.equals("Incorrect password")){
                    new DialogWindowCreator().createDialogWindow(serverAnswer);
                    if (serverAnswer.equals("Successful")){
                        registrationForm.setVisible(false);
                    }
                }
            }catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }
}
