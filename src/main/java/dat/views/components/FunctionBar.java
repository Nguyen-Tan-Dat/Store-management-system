package dat.views.components;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FunctionBar extends JPanel {
    public static HashMap<String, Item> items=new HashMap<>();

    public FunctionBar(ArrayList<Function> functions,Color background){
        int with=250;
        int height=700;
        setBackground(background);
        setBorder(new MatteBorder(0, 20, 20, 10, getBackground()));
        setPreferredSize(new Dimension(with,height));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        for(var i:functions){
            Item item=new Item(i.name(),i.iconCode(),with-30,40);
            items.put(i.name(),item);
            add(item);
        }
    }

    public HashMap<String, Item> getItems() {
        return items;
    }
}
