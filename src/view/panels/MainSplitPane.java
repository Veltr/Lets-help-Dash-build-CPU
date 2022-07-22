package view.panels;

import javax.swing.*;
import java.awt.*;

public class MainSplitPane extends JSplitPane {
    public MainSplitPane(int newOrientation, Workspace workspace, ComponentsArea componentsArea){
        super(newOrientation, workspace, componentsArea);
        setOneTouchExpandable(true);
        setDividerLocation(800-300);
        setResizeWeight(1.0);
        componentsArea._workspace = workspace;
        componentsArea._splitPane = this;
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
    }
}
