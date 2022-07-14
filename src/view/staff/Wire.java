package view.staff;

import view.elements.ConnectionPoint;

import java.awt.*;
import java.awt.geom.Line2D;

public class Wire {
    private final ConnectionPoint _startPoint;
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

    public void drawIt(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(_color);
        g2.setStroke(_stroke);
        g2.draw(getLine());
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
        _endPoint = endPoint;
        _startPoint.setWire(this);
        _endPoint.setWire(this);
    }
}
