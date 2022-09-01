package dat.views;

import dat.controls.*;
import dat.models.Model;
import dat.utils.GoogleMaterialDesignIcons;
import dat.views.components.Function;
import dat.views.components.FunctionBar;
import dat.views.components.TopBar;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MainFrame extends JFrame {
    public static JPanel center;
    private static final TopBar top = new TopBar();
    private static String employee_id;
    private static Model employee;
    public static JDialog windowChild;

    public static String getEmployee_id() {
        return employee_id;
    }

    public static Model getEmployee() {
        return employee;
    }

    public static void setEmployee_id(String employee_id) {
        MainFrame.employee_id = employee_id;
    }

    public static void setEmployee(Model employee) {
        MainFrame.employee = employee;
    }

    public static ArrayList<Function> functions=new ArrayList<>();
    private static final Function[] allFunction = {
            new Function("Bán hàng", GoogleMaterialDesignIcons.STORE, new SellView()),
            new Function("Nhập hàng", GoogleMaterialDesignIcons.SYSTEM_UPDATE_ALT, new ImportProductsView()),
            new Function("Khách Hàng", GoogleMaterialDesignIcons.SUPERVISOR_ACCOUNT, new ManagementView( new CustomerControl())),
            new Function("Nhân viên", GoogleMaterialDesignIcons.GROUP, new ManagementView(new EmployeeControl())),
            new Function("Nhà cung cấp", GoogleMaterialDesignIcons.BUSINESS, new ManagementView( new SupplierControl())),
            new Function("Dòng sản phẩm", GoogleMaterialDesignIcons.STYLE, new ManagementView( new ProductControl())),
            new Function("Sản phẩm", GoogleMaterialDesignIcons.TABLET_ANDROID, new IndividualProductView()),
//            new Function("Khuyến mãi", GoogleMaterialDesignIcons.REDEEM, new ManagementView( new PromotionControl())),
            new Function("Hóa đơn bán", GoogleMaterialDesignIcons.ASSIGNMENT, new BillView()),
            new Function("Hóa đơn nhập", GoogleMaterialDesignIcons.RECEIPT, new ImportView()),
            new Function("Thống kê", GoogleMaterialDesignIcons.INSERT_CHART, new BieuDoView()),
            new Function("Phân quyền", GoogleMaterialDesignIcons.VERIFIED_USER, new ManagementView(new AuthorizationControl())),
    };
    private void activeFunction(){
        functions = new ArrayList<>();
        for(var function:allFunction){
            functions.add(function);
        }
        functions.add(new Function("Cài đặt", GoogleMaterialDesignIcons.SETTINGS, new SettingView()));
    }
    private void activeFunction(String[] active) {
        functions = new ArrayList<>();
        for (var function : allFunction) {
            for (String s : active) {
                if (function.name().equals(s))
                    functions.add(function);
            }
        }
        functions.add(new Function("Cài đặt", GoogleMaterialDesignIcons.SETTINGS, new SettingView()));
    }

    public static void openChild(String title,JPanel view, int width, int height) {
        windowChild.setTitle(title);
        MainFrame.windowChild.setContentPane(view);
        MainFrame.windowChild.setSize(width, height);
        MainFrame.windowChild.setVisible(true);
    }

    public MainFrame(){
        activeFunction();
        init();
    }

    public MainFrame(String[] actives) {
        activeFunction(actives);
        init();
    }
    private void init(){
        windowChild = new JDialog(this);
        windowChild.setModal(true);
        windowChild.setResizable(false);
        windowChild.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        windowChild.setLocation(0, 0);
        windowChild.setSize(700, 700);
        windowChild.setLocationRelativeTo(null);
        setTitle("Hệ Thống Quản Lý Cửa Hàng");
        ImageIcon logo = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img.png")));
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1300, 700));
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(0, 0));
        center = new JPanel();
        center.setLayout(new BorderLayout());
        center.setBorder(new MatteBorder(0, 0, 20, 20, getBackground()));
        var home = new HomeView(functions);
        functions.add(0, new Function("Trang chủ", GoogleMaterialDesignIcons.HOME, home));
        setView("Trang chủ");
        center.add(home, BorderLayout.CENTER);
        getContentPane().add(center, BorderLayout.CENTER);
        FunctionBar functionBar = new FunctionBar(functions, getBackground());
        getContentPane().add(functionBar, BorderLayout.WEST);
        top.setAccount(employee.getData()[0]);
        getContentPane().add(top, BorderLayout.NORTH);
    }
    public static void setView(View view){
        center.removeAll();
        center.add(view);
        center.repaint();
        center.revalidate();
    }

    public static void setView(String name) {
        View view = null;
        for (var i : functions)
            if (i.name().equals(name)) {
                view = i.view().newView();
                break;
            }
        if (view == null) return;
        center.removeAll();
        center.add(view);
        center.repaint();
        center.revalidate();
        top.setFunction(name);

    }
}