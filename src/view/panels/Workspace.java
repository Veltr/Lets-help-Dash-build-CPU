package view.panels;

import model.data.CircuitData;
import view.elements.*;
import view.elements.input.InputElement;
import view.elements.logic.ANDElement;
import view.staff.Wire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Workspace extends JScrollPane {
    private final WorkspaceView _view;

    public Workspace(){
        super();
        _view = new WorkspaceView();
        setViewportView(_view);
        _view.scrollRectToVisible(new Rectangle((getPreferredSize().width - 500) / 2, (getPreferredSize().height - 500) / 2, 1, 1));
        getVerticalScrollBar().setUnitIncrement(16);
    }

    public void add(BaseComponent e) {
        _view.add(e);
    }
    public void setPosition(BaseComponent e, int x, int y) { _view.setPosition(e, x, y); }
    public void run(){
        _view.run();
    }
    public void save(String path){
        _view.save(path);
    }
    public void load(String path){
        clearAll();
        _view.load(path);
    }
    protected void clearAll(){
        _view.clearAll();
        repaint();
    }
}

class WorkspaceView extends JPanel {
    private final CircuitData _circuit;
    private final ElementDrag _elementDrag;
    private final LineDrag _lineDrag;

    public WorkspaceView(){
        super();
        _circuit = new CircuitData();
        setPreferredSize(new Dimension(2000, 2000));
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(null);
        setVisible(true);

        _elementDrag = new ElementDrag(); _elementDrag._base = this;
        _lineDrag = new LineDrag(); _lineDrag._base = this;

        addMouseListener(_lineDrag);
        addMouseMotionListener(_lineDrag);

        setAutoscrolls(true);
        var m = new DragScroll();
        addMouseListener(m);
        addMouseMotionListener(m);
    }

    protected void add(BaseComponent e){
        e.addMouseListener(_elementDrag);
        e.addMouseMotionListener(_elementDrag);

        e.addMouseListener(_lineDrag);
        e.addMouseMotionListener(_lineDrag);
        for(var i : e.getComponents()){
            i.addMouseListener(_lineDrag);
            i.addMouseMotionListener(_lineDrag);
        }

        add((JComponent)e);
        setPosition(e, getPreferredSize().width / 2, getPreferredSize().height / 2);
        _circuit.add(e.getElementData());
    }

    protected void setPosition(BaseComponent e, int x, int y){
        Dimension d = e.getPreferredSize();
        e.setBounds(x - d.width / 2, y - d.height / 2, d.width, d.height);
    }

    protected void save(String path){
        try(var file = new DataOutputStream(new FileOutputStream(path))){
            HashMap<Wire, Point> wires = new HashMap<>();

            file.writeInt(getComponentCount());
            var comp = getComponents();
            for(int i = 0; i < comp.length; i++){
                BaseComponent cur =((BaseComponent)comp[i]);
                cur.writeToFile(file);
                for(var ii : cur.getAllWires()){
                    if(wires.containsKey(ii)){
                        if(ii.getEndPoint().getParent() == cur) wires.put(ii, new Point(wires.get(ii).x, i));
                        else wires.put(ii, new Point(i, wires.get(ii).y));
                    }
                    else {
                        if (ii.getEndPoint().getParent() == cur) wires.put(ii, new Point(-1, i));
                        else wires.put(ii, new Point(i, -1));
                    }
                }
            }

            file.writeInt(wires.size());
            for (var i : wires.entrySet()){
                file.writeInt(i.getValue().x); file.writeInt(i.getValue().y);
                file.writeInt(i.getKey().getStartPoint().getIndex()); file.writeInt(i.getKey().getEndPoint().getIndex());
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Saved");
    }

    protected void load(String path){
        try(var file = new DataInputStream(new FileInputStream(path))){
            clearAll();
            String pac = "view.elements.";
            ArrayList<BaseComponent> comp = new ArrayList<>();
            int n = file.readInt();
            for(int i = 0; i < n; i++){
                BaseComponent cur = (BaseComponent) Class.forName(pac + file.readUTF()).getConstructor().newInstance();
                add(cur);
                cur.setBounds(new Rectangle(file.readInt(), file.readInt(), file.readInt(), file.readInt()));
                comp.add(cur);
            }

            n = file.readInt();
            for(int i = 0; i < n; i++)
                _lineDrag.getWires().add(comp.get(file.readInt()).connect(comp.get(file.readInt()), file.readInt(), file.readInt()));
            repaint();
        }
        catch (IOException | ClassNotFoundException | InvocationTargetException | InstantiationException |
               IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Loaded");
    }
    protected void run(){
        _circuit.start();
        for(var i : getComponents()) if(i instanceof InputElement) ((InputElement)i).setState();
    }
    protected void clearAll(){
        _circuit.clear();
        _lineDrag.getWires().clear();
        removeAll();
    }
    private void deleteElement(){

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
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == 3 && e.getSource() instanceof BaseComponent)
                ((BaseComponent) e.getSource()).delete();
        }
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
    private class DragScroll extends MouseAdapter {
        private Point _origin;
        @Override
        public void mousePressed(MouseEvent e) {
            _origin = new Point(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (_origin != null) {
                JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, (Component) e.getSource());
                if (viewPort != null) {
                    int deltaX = _origin.x - e.getX();
                    int deltaY = _origin.y - e.getY();

                    Rectangle view = viewPort.getViewRect();
                    view.x += deltaX;
                    view.y += deltaY;

                    scrollRectToVisible(view);
                }
            }
        }
    }
}
