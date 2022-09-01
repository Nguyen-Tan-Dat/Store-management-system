package dat;

import dat.utils.CommonUI;
import dat.utils.Database;
import dat.views.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        new CommonUI();
        String message=(new Database(null,null)).connect();
        if(message!=null){
            JOptionPane.showMessageDialog(null, message);
            return;
        }
//        EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
        login();
    }
    private static void login(){
        LoginFrame.login("0775820223","123456".toCharArray());
    }
}
