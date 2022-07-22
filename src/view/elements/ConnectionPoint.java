package view.elements;

import model.data.PortData;
import view.staff.Wire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ConnectionPoint extends JLabel implements MouseListener {
    protected PortData _data;
    protected boolean _isInput;
    private Wire _wire;

    public ConnectionPoint(PortData data, boolean isInput){
        _data = data;
        _isInput = isInput;

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
    public Point getWorkspaceLocation(){
        //if(_center != null) return _center;
        var _center = getLocation();
        _center.translate(getPreferredSize().width / 2, getPreferredSize().height / 2);
        _center = SwingUtilities.convertPoint(getParent(), _center, getParent().getParent());
        return _center;
    }
    public boolean connect(ConnectionPoint other){
        return _data.connect(other._data);
    }
    public void setWire(Wire wire){
        _wire = wire;
    }
    public Wire getWire(){
        return _wire;
    }
    public boolean isInput() {
        return _isInput;
    }
    public int getIndex() {
        return _data.getIndex();
    }
    public void disconnect(){
        _data.disconnect();
        if(_wire != null) _wire.disconnect();
        getParent().getParent().repaint();
    }

    //region Listener
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) disconnect();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ((BaseElement)((ConnectionPoint)e.getSource()).getParent()).setVisibleAllPorts(true);
        //System.out.println("Enter");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((BaseElement)((ConnectionPoint)e.getSource()).getParent()).setVisibleAllPorts(false);
        //System.out.println("Exit");
    }
    //endregion
}
