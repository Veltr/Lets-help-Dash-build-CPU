package view.elements;

import model.data.PortData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ConnectionPoint extends JLabel implements MouseListener {
    public PortData data;
    public boolean isInput;

    public ConnectionPoint(PortData data, boolean isInput){
        this.data = data;
        this.isInput = isInput;

        String path;
        if(isInput) path = "src/resources/sprites/Input.png";
        else path = "src/resources/sprites/Output.png";
        setIcon(new ImageIcon(path));
        setFixedSize(10, 10);

        addMouseListener(this);
        //setVisible(false);
    }

    public void setFixedSize(int width, int height){
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

    //region Listener
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ((BaseComponent)((ConnectionPoint)e.getSource()).getParent()).setVisibleAllPorts(true);
        //System.out.println("Enter");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((BaseComponent)((ConnectionPoint)e.getSource()).getParent()).setVisibleAllPorts(false);
        //System.out.println("Exit");
    }
    //endregion
}
