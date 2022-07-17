package view.staff;

import view.elements.ConnectionPoint;

import java.awt.*;
import java.awt.geom.Line2D;

public class Wire {
    private ConnectionPoint _startPoint;
    private ConnectionPoint _endPoint;
    private final Line2D _line;
    private final Color _color;
    private final BasicStroke _stroke;

    public Wire(ConnectionPoint startPoint){
        _startPoint = startPoint;
        _line = new Line2D.Float();
        _color = new Color(7, 128, 7);
        _stroke = new BasicStroke(5);
    }

    public boolean drawIt(Graphics g){
        if(_startPoint == null) return false;
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(_color);
        g2.setStroke(_stroke);
        g2.draw(getLine());
        return true;
    }

    public void moveLine(Point p){
        _line.setLine(_startPoint.getWorkspaceLocation(), p);
    }
    public Line2D getLine(){
        if(_endPoint == null) return _line;
        _line.setLine(_startPoint.getWorkspaceLocation(), _endPoint.getWorkspaceLocation());
        return _line;
    }

    public void setEndPoint(ConnectionPoint endPoint){
        if(!endPoint.isInput()){
            _endPoint = _startPoint;
            _startPoint = endPoint;
        }
        else _endPoint = endPoint;
        _startPoint.setWire(this);
        _endPoint.setWire(this);
    }
    public ConnectionPoint getStartPoint() { return _startPoint; }
    public ConnectionPoint getEndPoint() { return _endPoint; }
    public void disconnect(){
        _startPoint.setWire(null);
        _endPoint.setWire(null);
        _startPoint = null;
        _endPoint = null;
    }
}
