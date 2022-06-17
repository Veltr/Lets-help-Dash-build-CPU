package gfx.panels;

import gfx.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Workspace extends JPanel implements MouseListener, MouseMotionListener {
    private BaseElement _curElem;
    private Point _start;
    public Workspace(){
        super();
        add(new ORElement());
        add(new ANDElement());
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setVisible(true);
    }

    public void add(BaseElement e){
        e.addMouseListener(this);
        e.addMouseMotionListener(this);
        add((JComponent)e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() instanceof BaseElement) {
            _curElem = (BaseElement) e.getSource();
            _start = SwingUtilities.convertPoint(_curElem, e.getPoint(), _curElem.getParent());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        _start = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Point loc = SwingUtilities.convertPoint(curElem, e.getPoint(), curElem.getParent());
        Point loc = SwingUtilities.convertPoint(_curElem, e.getPoint(), null);
        if(_curElem.getParent().getBounds().contains(loc)){
            loc = SwingUtilities.convertPoint(_curElem, e.getPoint(), _curElem.getParent());
            Point newLoc = _curElem.getLocation();
            newLoc.translate(loc.x - _start.x, loc.y - _start.y);
            newLoc.x = Math.max(newLoc.x, 0);
            newLoc.y = Math.max(newLoc.y, 0);
            newLoc.x = Math.min(newLoc.x, _curElem.getParent().getWidth() - _curElem.getWidth());
            newLoc.y = Math.min(newLoc.y, _curElem.getParent().getHeight() - _curElem.getHeight());
            _curElem.setLocation(newLoc);
            _start = loc;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
