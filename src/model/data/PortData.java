package model.data;

import model.exceptions.NullConnectionException;

public class PortData {
    public int index;
    private PortData _connection;
    private BusType _type;
    private BaseElementData _base;

    public PortData(BusType type, BaseElementData base){
        _type = type;
        _base = base;
    }
    public PortData(BusType type){
        _type = type;
        _base = null;
    }

    public boolean connect(PortData e){
        if(e.getType() == _type){
            _connection = e;
            if(e._connection != this) e.connect(this);
            return true;
        }
        return false;
    }

    public WireData getData() throws NullConnectionException {
        if(_connection == null) throw new NullConnectionException(_base, this);
        return _connection.getOutputData();
    }

    private WireData getOutputData(){
        return _base.getPortData(index);
    }

    public BusType getType(){ return _type; }
    public BaseElementData getConnectionBase(){ return _connection._base; }
    protected void setAll(PortData other){
        _type = other._type;
        if(other._base != null) _base = other._base;
    }
}
