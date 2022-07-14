package view.panels;

import model.data.CircuitData;
import view.elements.*;
import view.elements.logic.ANDElement;
import view.elements.logic.ORElement;
import view.staff.Wire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.AbstractCollection;
import java.util.LinkedList;

public class Workspace extends JPanel {
    private final CircuitData _circuit;
    private final ElementDrag _elementDrag;
    private final LineDrag _lineDrag;

    public Workspace(){
        super();
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(null);
        setVisible(true);

        _circuit = new CircuitData();
        _elementDrag = new ElementDrag(); _elementDrag._base = this;
        _lineDrag = new LineDrag(); _lineDrag._base = this;


        addMouseListener(_lineDrag);
        addMouseMotionListener(_lineDrag);

        add(new ORElement());
        add(new ANDElement());
    }

    public void add(BaseComponent e){
        e.addMouseListener(_elementDrag);
        e.addMouseMotionListener(_elementDrag);

        e.addMouseListener(_lineDrag);
        e.addMouseMotionListener(_lineDrag);
        for(var i : e.getComponents()){
            i.addMouseListener(_lineDrag);
            i.addMouseMotionListener(_lineDrag);
        }

        add((JComponent)e);
        //e.setComponentZOrder(e, 0);
        setPosition(e, 400, 300);
        _circuit.add(e.getElementData());
    }

    public void setPosition(BaseComponent e, int x, int y){
        Dimension d = e.getPreferredSize();
        e.setBounds(x - d.width / 2, y - d.height / 2, d.width, d.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        for(var i : _lineDrag.getWires()) i.drawIt(g);
    }

    private static class ElementDrag extends MouseAdapter {
        private BaseComponent _curElem;
        private Point _start;
        protected Container _base;
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource() instanceof BaseComponent) {
                _curElem = (BaseComponent) e.getSource();
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
                //_curElem.setBounds(newLoc.x, newLoc.y, _curElem.getWidth(), _curElem.getHeight());
                _start = loc;
            }
            _base.repaint();
        }
    }
    private static class LineDrag extends MouseAdapter {
        private final LinkedList<Wire> _wires = new LinkedList<>();
        private Wire _curWire;
        private ConnectionPoint _curPoint;
        private boolean _isDragging = false;
        protected Container _base;

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof ConnectionPoint point) {
                if(_isDragging){
                    if(point == _curPoint || point.getParent() == _curPoint.getParent()) endDrag(false);
                    else {
                        if(!_curPoint.connect(point)) endDrag(false);
                        else { endDrag(true); _curWire.setEndPoint(point); }
                    }
                }
                else if(point.getWire() == null) startDrag(point);
            }
            else if(_isDragging) endDrag(false);
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            if(_isDragging){
                _curWire.moveLine(SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), _base));
                _base.repaint();
            }
        }
        public AbstractCollection<Wire> getWires(){
            return _wires;
        }

        private void startDrag(ConnectionPoint point){
            _isDragging = true;
            _wires.add(new Wire(point));
            _curWire = _wires.getLast();
            _curPoint = point;
        }
        private void endDrag(boolean success){
            _isDragging = false;
            if(!success){
                _wires.pollLast();
                _base.repaint();
            }
        }
    }
}
