package model.data;

public class WireData {
    private final BusType _type;
    private int _value;

    public WireData(BusType type, int value){
        this._type = type;
        this._value = value;
    }

    public BusType getType(){
        return _type;
    }

    public int getValue(){
        return _value;
    }
    public void setValue(int value){ _value = value; }
}
