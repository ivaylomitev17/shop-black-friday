package tu.sofia.shop.black.friday.views;

import javax.swing.*;

public class DialogWindowCreator {

    public void createDialogWindow (String message){
        JDialog d = new JDialog();
        JLabel l = new JLabel(message);
        d.add(l);
        d.setSize(250, 150);
        d.setVisible(true);
        d.setLocationRelativeTo(null);
    }
}
