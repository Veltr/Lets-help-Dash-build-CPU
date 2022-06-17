package gfx.panels;

import gfx.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Workspace extends JPanel implements MouseListener, MouseMotionListener {
    private BaseElement curElem;
    Point start;
    public Workspace(){
        super();
        add(new ORElement());
        add(new ANDElement());
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
            curElem = (BaseElement) e.getSource();
            start = SwingUtilities.convertPoint(curElem, e.getPoint(), curElem.getParent());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        start = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point loc = SwingUtilities.convertPoint(curElem, e.getPoint(), curElem.getParent());
        if(curElem.getParent().getBounds().contains(loc)){
            Point newLoc = curElem.getLocation();
            newLoc.translate(loc.x - start.x, loc.y - start.y);
            newLoc.x = Math.max(newLoc.x, 0);
            newLoc.y = Math.max(newLoc.y, 0);
            newLoc.x = Math.min(newLoc.x, curElem.getParent().getWidth() - curElem.getWidth());
            newLoc.y = Math.min(newLoc.y, curElem.getParent().getHeight() - curElem.getHeight());
            curElem.setLocation(newLoc);
            start = loc;
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
