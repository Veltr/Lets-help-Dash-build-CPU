package model.data;

public class PortData {
    public enum BusType { B1, B8, B16 }
    public PortData connect;
    private BusType _type;
    private ElementData _base;

    public PortData(){}

    public PortData(ElementData base){
        _base = base;
    }

    public PortData(BusType type, ElementData base){
        _type = type;
        _base = base;
    }
}
