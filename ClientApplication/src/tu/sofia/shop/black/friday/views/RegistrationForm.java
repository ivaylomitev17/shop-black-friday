package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class RegistrationForm extends JFrame {

    private JButton registrationButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel panel;

    public RegistrationForm (){
        setSize(400,250);
        setContentPane(panel);
    }

    public JButton getRegistrationButton() {
        return registrationButton;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
