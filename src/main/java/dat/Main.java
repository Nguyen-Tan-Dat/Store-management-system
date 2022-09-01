package dat;

import dat.controls.AuthorizationControl;
import dat.models.Employee;
import dat.models.Model;
import dat.utils.CommonUI;
import dat.utils.Database;
import dat.views.LoginFrame;
import dat.views.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        new CommonUI();
        String message=(new Database(null,null)).connect();
        if(message!=null){
            JOptionPane.showMessageDialog(null, message);
            return;
        }
        EventQueue.invokeLater(() -> new LoginFrame().setVisible(true));
//        login();
    }
    private static void login(){
        var employees=(new Employee()).login("0775820223","123456");
        if(employees.size()==0)return;
        String id="";
        Model employee=null;
        for (var i: employees.keySet()){
            id=i;employee= employees.get(i);
        }
        MainFrame.setEmployee(employee);
        MainFrame.setEmployee_id(id);
        AuthorizationControl control=new AuthorizationControl();
        String[] actives=control.getList().get(employee.getData()[5]).getData()[1].split(", ");
        EventQueue.invokeLater(() -> new MainFrame(actives).setVisible(true));
    }
}
