package dat.views;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BieuDoView extends View {
    public BieuDoView(){
        setLayout(new BorderLayout());
        JLabel label=new JLabel(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/bieu_do.png"))));
        add(label,BorderLayout.CENTER);
        JLabel note=new JLabel("Chức năng đang bảo trì");
        note.setHorizontalAlignment(SwingConstants.CENTER);
        note.setVerticalAlignment(SwingConstants.CENTER);
        add(note ,BorderLayout.NORTH);
    }

    @Override
    public View newView() {
        return new BieuDoView();
    }
}
