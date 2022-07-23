package view.panels;

import model.data.CircuitData;
import model.exceptions.NullConnectionException;
import view.elements.*;
import view.elements.input.InputElement;
import view.staff.Wire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Workspace extends JScrollPane {
    protected WorkspaceView _view;

    public Workspace(){
        super();
        _view = new WorkspaceView();
        setViewportView(_view);
        _view.scrollRectToVisible(new Rectangle((getPreferredSize().width - 500) / 2, (getPreferredSize().height - 500) / 2, 1, 1));
        getVerticalScrollBar().setUnitIncrement(16);
        getHorizontalScrollBar().setUnitIncrement(16);
    }

    public void add(BaseElement e) {
        _view.add(e);
        Rectangle r = getViewport().getViewRect();
        setPosition(e, r.x + r.width / 2, r.y + r.height / 2);
    }
    public void setPosition(BaseElement e, int x, int y) { _view.setPosition(e, x, y); }
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
    public void clearAll(){
        _view.clearAll();
        repaint();
    }
}

class WorkspaceView extends JPanel {
    private final CircuitData _circuit;
    private final ElementDrag _elementDrag;
    private final LineDrag _lineDrag;
    private final DragSelect _dragSelect = new DragSelect();

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

        addMouseListener(_dragSelect);
        addMouseMotionListener(_dragSelect);
    }

    protected void add(BaseElement e){
        e.addMouseListener(_elementDrag);
        e.addMouseMotionListener(_elementDrag);

        e.addMouseListener(_lineDrag);
        e.addMouseMotionListener(_lineDrag);
        for(var i : e.getComponents()){
            i.addMouseListener(_lineDrag);
            i.addMouseMotionListener(_lineDrag);
        }

        add((Component)e);
        _circuit.add(e.getElementData());
        setOnTopZ(e);
    }

    protected void setOnTopZ(BaseElement e){
        //var t = getComponents();
        for(var i : getComponents()) {
            if(i == e) { setComponentZOrder(e, 0); return; }
            setComponentZOrder(i, getComponentZOrder(i) + 1);
        }
    }

    protected void setPosition(BaseElement e, int x, int y){
        Dimension d = e.getPreferredSize();
        e.setBounds(x - d.width / 2, y - d.height / 2, d.width, d.height);
    }

    protected void save(String path){
        try(var file = new DataOutputStream(new FileOutputStream(path))){
            HashMap<Wire, Point> wires = new HashMap<>();

            Point p = ((JViewport)getParent()).getViewPosition();
            file.writeInt(p.x);file.writeInt(p.y);

            file.writeInt(getComponentCount());
            var comp = getComponents();
            for(int i = 0; i < comp.length; i++){
                BaseElement cur =((BaseElement)comp[i]);
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
    }

    protected void load(String path){
        try(var file = new DataInputStream(new FileInputStream(path))){
            clearAll();
            String pac = "view.elements.";
            ArrayList<BaseElement> comp = new ArrayList<>();

            ((JViewport)getParent()).setViewPosition(new Point(file.readInt(), file.readInt()));

            int n = file.readInt();
            for(int i = 0; i < n; i++){
                BaseElement cur = (BaseElement) Class.forName(pac + file.readUTF()).getConstructor().newInstance();
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
    }
    protected void run(){
        try {
            _circuit.start();
        }
        catch (NullConnectionException e) {
            BaseElement cur;
            for (var i : getComponents()){
                cur = (BaseElement) i;
                if(cur.getElementData() == e.element){
                    JOptionPane.showMessageDialog(cur, cur.getMessageForError(e.port));
                    return;
                }
            }
        }
        for(var i : getComponents()) if(i instanceof InputElement) ((InputElement)i).setState();
    }
    protected void clearAll(){
        _circuit.clear();
        _lineDrag.getWires().clear();
        removeAll();
    }
    protected void getComponentsInRect(Rectangle rect){
        for(var i : getComponents()){
            if(rect.intersects(i.getBounds())) {
                ((BaseElement) i).setBorder(BorderFactory.createLineBorder(Color.blue));
                _elementDrag._selectedElements.add(((BaseElement) i));
            }
        }
    }

    @Override
    public void remove(Component comp) {
        super.remove(comp);
        if(comp instanceof BaseElement){
            _circuit.remove(((BaseElement)comp).getElementData());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);

        _lineDrag.getWires().removeIf(cur -> !cur.drawIt(g));
        _dragSelect.drawRect(g);
    }

    private static class ElementDrag extends MouseAdapter {
        private BaseElement _curElem;
        private final ArrayList<BaseElement> _selectedElements = new ArrayList<>();
        private Point _start;
        protected Container _base;

        private void clearSelected(){
            for(var i : _selectedElements) i.setBorder(BorderFactory.createLineBorder(Color.black));
            _selectedElements.clear();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getButton() == 3 && e.getSource() instanceof BaseElement) {
                if(_selectedElements.contains((BaseElement)e.getSource())){
                    for(var i : _selectedElements) i.delete();
                }
                else ((BaseElement) e.getSource()).delete();
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getButton() == 1 && e.getSource() instanceof BaseElement) {
                _curElem = (BaseElement) e.getSource();
                if(!_selectedElements.contains(_curElem)) clearSelected();
                _start = SwingUtilities.convertPoint(_curElem, e.getPoint(), _base);
            }
            /*else if(e.getButton() == 3 && e.getSource() instanceof BaseElement)
                ((BaseElement) e.getSource()).delete();*/
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            _start = null;
            _curElem = null;
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(_curElem != null) {
                Point loc = SwingUtilities.convertPoint(_curElem, e.getPoint(), null);
                if (_base.getBounds().contains(loc)) {
                    loc = SwingUtilities.convertPoint(_curElem, e.getPoint(), _base);
                    if(_selectedElements.size() == 0) {
                        Point newLoc = _curElem.getLocation();
                        newLoc.translate(loc.x - _start.x, loc.y - _start.y);
                        newLoc.x = Math.max(newLoc.x, 0);
                        newLoc.y = Math.max(newLoc.y, 0);
                        newLoc.x = Math.min(newLoc.x, _base.getWidth() - _curElem.getWidth());
                        newLoc.y = Math.min(newLoc.y, _base.getHeight() - _curElem.getHeight());
                        _curElem.setLocation(newLoc);
                        ((WorkspaceView)_base).setOnTopZ(_curElem);
                    }

                    for(var i : _selectedElements){
                        Point nLoc = i.getLocation();
                        nLoc.translate(loc.x - _start.x, loc.y - _start.y);
                        nLoc.x = Math.max(nLoc.x, 0);
                        nLoc.y = Math.max(nLoc.y, 0);
                        nLoc.x = Math.min(nLoc.x, _base.getWidth() - _curElem.getWidth());
                        nLoc.y = Math.min(nLoc.y, _base.getHeight() - _curElem.getHeight());
                        i.setLocation(nLoc);
                        ((WorkspaceView)_base).setOnTopZ(i);
                    }
                    _start = loc;
                }
                _base.repaint();
            }
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
            if(e.getButton() == MouseEvent.BUTTON1 && e.getSource() instanceof ConnectionPoint point) {
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
            else if(e.getButton() == MouseEvent.BUTTON3 && e.getSource() instanceof WorkspaceView){
                for(var i : _wires) if(i.isPressed(e.getPoint())) i.disconnect();
            }
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
        private final int _buttonIndex = MouseEvent.BUTTON2;
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getButton() == _buttonIndex) _origin = new Point(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            _origin = null;
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
    private class DragSelect extends MouseAdapter {
        private Point _start;
        private Point _end;
        private Rectangle _curRect;
        private final Color _color = new Color(0, 81, 255, 50);
        private final BasicStroke _stroke = new BasicStroke(2);

        public void drawRect(Graphics g){
            if(_start != null && _end != null){
                g.setColor(_color);
                ((Graphics2D)g).setStroke(_stroke);

                _curRect = new Rectangle(Math.min(_start.x, _end.x), Math.min(_start.y, _end.y),
                        Math.abs(_end.x - _start.x), Math.abs(_end.y - _start.y));
                ((Graphics2D)g).draw(_curRect);
                ((Graphics2D)g).fill(_curRect);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getButton() == 1){
                _start = new Point(e.getPoint());
                _elementDrag.clearSelected();
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            _start = null;
            _end = null;
            if(_curRect != null) {
                getComponentsInRect(_curRect);
                _curRect = null;
            }
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(_start != null) {
                _end = new Point(e.getPoint());
                ((WorkspaceView)e.getSource()).repaint();
            }
        }
    }
}
