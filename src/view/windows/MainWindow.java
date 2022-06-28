package view.windows;

import view.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("God, forgive me");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridLayout());
        //add(new JButton("Run"));
        add(new Workspace());
        // add(new ComponentsArea());
    }
}
