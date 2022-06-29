package model.data;

public class PortData {
    private PortData _connection;
    private final BusType _type;
    private BaseElementData _base;

    public PortData(BusType type, BaseElementData base){
        _type = type;
        _base = base;
    }

    public void connect(PortData e){
        if(e.getType() == _type){
            _connection = e;

        }
    }

    public BusType getType(){ return _type; }
    public PortData getConnection(){ return _connection; }

    public enum BusType { B1, B8, B16 }
}
