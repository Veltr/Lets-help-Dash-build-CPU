package gfx.windows;

import gfx.panels.*;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel _workspace;
    private JScrollPane _components;

    public MainWindow() {
        super("God, forgive me");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        _workspace = new Workspace();
        _components = new ComponentsArea();
        add(_workspace);
        //add(_components);
    }
}
