package model.data;

import java.util.AbstractCollection;

public class OutputElementData extends BaseElementData {
    private final PortData[] _outputPorts;
    private WireData _outData;

    public OutputElementData(int output){
        _outputPorts = new PortData[output];

        for(int i = 0; i < output; i++) _outputPorts[i] = new PortData(BusType.B1, i, this);
    }

    public void setOutputData(WireData data){
        _outData = data;
    }
    public void setOutputData(int value){
        _outData.setValue(value);
    }
    public WireData getOutputData(){
        return _outData;
    }


    @Override
    public void setPort(int index, PortData data, boolean isInput) {
        _outputPorts[index].setAll(data);
    }
    public void setPort(int index, PortData data){ setPort(index, data, true); }

    @Override
    public void execute(){}

    @Override
    protected WireData getDataFromPort(int index) {
        return _outData;
    }

    @Override
    protected void addNextElements(AbstractCollection<BaseElementData> out) {
        for(var i : _outputPorts) out.add(i.getConnectionBase());
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
