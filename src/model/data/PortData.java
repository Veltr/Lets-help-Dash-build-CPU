package model.data;

import model.exceptions.NullConnectionException;

public class PortData {
    private final int _index;
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
    public void disconnect(){
        if(_connection == null) return;
        var t = _connection;
        _connection = null;
        t.disconnect();
    }

    public SignalData getData() throws NullConnectionException {
        if(_connection == null) throw new NullConnectionException(_base, this);
        return _connection.getOutputData();
    }

    private SignalData getOutputData(){
        return _base.getDataFromPort(_index);
    }

    public BusType getType(){ return _type; }
    public int getIndex(){ return _index; }
    public BaseElementData getConnectionBase(){ if(_connection == null) return null; return _connection._base; }
    protected void setAll(PortData other){
        _type = other._type;
        if(other._base != null) _base = other._base;
    }
}
