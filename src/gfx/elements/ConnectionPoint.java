package gfx.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ConnectionPoint extends JLabel {
    public BaseElement.PortData data;
    public boolean isInput;

    public ConnectionPoint(BaseElement.PortData data, boolean isInput){
        this.data = data;
        this.isInput = isInput;

        String path;
        if(isInput) path = "resources/Input.png";
        else path = "resources/Output.png";
        setIcon(new ImageIcon(path));
        setFixedSize(10, 10);

        addMouseListener(new BaseElement.Listener());
    }

    public void setFixedSize(int width, int height){
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

    private static class Listener implements MouseListener {
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

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
