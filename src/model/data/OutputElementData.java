package model.data;

import model.exceptions.NullConnectionException;

import java.util.AbstractCollection;

public class OutputElementData extends BaseElementData {
    private final PortData[] _outputPorts;
    private SignalData _outData;

    public OutputElementData(int output){
        _outputPorts = new PortData[output];

        for(int i = 0; i < output; i++) _outputPorts[i] = new PortData(BusType.B1, i, this);
    }

    public void setOutputData(SignalData data){
        _outData = data;
    }
    public void setOutputData(int value){
        _outData.setValue(value);
    }
    public SignalData getOutputData(){
        return _outData;
    }


    @Override
    public void setPort(int index, PortData data, boolean isInput) {
        _outputPorts[index].setAll(data);
    }
    public void setPort(int index, PortData data){ setPort(index, data, true); }

    @Override
    public void execute() {}

    @Override
    protected SignalData getDataFromPort(int index) {
        return _outData;
    }

    @Override
    protected void addNextElements(AbstractCollection<BaseElementData> out) throws NullConnectionException {
        for(var i : _outputPorts) {
            BaseElementData cur = i.getConnectionBase();
            if(cur == null) throw new NullConnectionException(this, i);
            out.add(cur);
        }
    }

    @Override
    public void connect(BaseElementData to, int outputPortIndex, int inputPortIndex) {
        _outputPorts[outputPortIndex].connect(to.getPortData(inputPortIndex, true));
    }

    @Override
    public PortData getPortData(int index, boolean isInput) {
        return _outputPorts[index];
    }
}
