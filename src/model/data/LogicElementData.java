package model.data;

import java.util.ArrayList;

interface ElementAction {
    public ArrayList<WireData> execute(ArrayList<WireData> a);
    public ArrayList<WireData> execute();
}

public class LogicElementData extends BaseElementData {
    private final ArrayList<PortData> _inputPorts;
    private final ArrayList<PortData> _outputPorts;
    protected ArrayList<WireData> _inData;
    protected ArrayList<WireData> _outData;

    public ElementAction action;

    public LogicElementData(){
        _inData = new ArrayList<>();
        _outData = new ArrayList<>();
        _inputPorts = new ArrayList<>();
        _outputPorts = new ArrayList<>();
    }

    @Override
    public ArrayList<WireData> getOutput() {
        return _outData;
    }

    @Override
    public void add(PortData data, boolean isInput) {
        if(isInput) _inputPorts.add(data);
        else _outputPorts.add(data);
    }
}
