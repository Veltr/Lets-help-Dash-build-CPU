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
        //add(new Workspace());
        //add(new ComponentsArea());

        var workspace = new Workspace();
        var compArea = new ComponentsArea();

        compArea.setMaximumSize(new Dimension(296, 1080));

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workspace, compArea);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(800-296);

        splitPane.setResizeWeight(1.0);
        add(splitPane);
    }
}
