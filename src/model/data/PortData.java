package model.data;

import model.exceptions.NullConnectionException;

public class PortData {
    public int index;
    private PortData _connection;
    private final BusType _type;
    private final BaseElementData _base;

    public PortData(BusType type, BaseElementData base){
        _type = type;
        _base = base;
    }

    public void connect(PortData e){
        if(e.getType() == _type){
            _connection = e;
            if(e._connection != this) e.connect(this);
        }
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
}
