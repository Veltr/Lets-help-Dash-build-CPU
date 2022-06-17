package gfx.windows;

import gfx.elements.*;
import gfx.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
//    private JPanel _workspace;
//    private JScrollPane _components;

    public MainWindow() {
        super("God, forgive me");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(new Workspace());
        // add(new ANDElement());
        // add(new ComponentsArea());
    }
}
