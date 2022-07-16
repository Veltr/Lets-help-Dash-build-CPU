package model.data;

import model.exceptions.NullConnectionException;

public class PortData {
    private int _index;
    private PortData _connection;
    private BusType _type;
    private BaseElementData _base;

    public PortData(BusType type, int index, BaseElementData base){
        _type = type;
        _index = index;
        _base = base;
    }
    public PortData(BusType type, int index){
        _type = type;
        _index = index;
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
        return _base.getDataFromPort(_index);
    }

    public BusType getType(){ return _type; }
    public int getIndex(){ return _index; }
    public BaseElementData getConnectionBase(){ return _connection._base; }
    protected void setAll(PortData other){
        _type = other._type;
        if(other._base != null) _base = other._base;
    }
}
