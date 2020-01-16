package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class LoginForm extends JFrame{
    private JPanel panel1;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registrationButton;


    public LoginForm(){

        setSize(400,250);
        setContentPane(panel1);
        passwordField.setEchoChar('*');

    }



    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getRegistrationButton() {
        return registrationButton;
    }
}
