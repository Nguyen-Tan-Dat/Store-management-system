package dat.views;
import dat.utils.MyColor;
import dat.views.components.Function;
import dat.views.components.FunctionBar;
import dat.views.components.ItemWrap;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HomeView extends View {
	ArrayList<Function> functions;
	public HomeView(ArrayList<Function> functions) {
		this.functions=functions;
		var foreground = MyColor.text;
		var background = MyColor.white;
		var hoverBackground = MyColor.light_primary;
		var activeBackground= MyColor.primary;
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		for (var i:functions){
			var temp=new ItemWrap(i.iconCode(),i.name(),background,foreground,hoverBackground);
			add(temp);
			temp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for (var i : FunctionBar.items.keySet()) {
						if (FunctionBar.items.get(i).active) {
							FunctionBar.items.get(i).active = false;
							FunctionBar.items.get(i).setBackground(background);
							FunctionBar.items.get(i).setForeground(foreground);
							break;
						}
					}
					FunctionBar.items.get(i.name()).active = true;
					FunctionBar.items.get(i.name()).setBackground(activeBackground);
					FunctionBar.items.get(i.name()).setForeground(background);
					MainFrame.setView(i.name());
				}
			});
		}
	}

	@Override
	public View newView() {
		return new HomeView(functions);
	}
}
