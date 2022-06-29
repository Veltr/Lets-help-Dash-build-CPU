package model.data;

import java.util.ArrayList;

public class OutputElementData extends BaseElementData {
    private final ArrayList<PortData> _outputPorts;
    private WireData _outData;

    public OutputElementData(){
        _outputPorts = new ArrayList<>();
    }

    public void setOutputData(WireData data){
        _outData = data;
    }

    @Override
    public ArrayList<WireData> getOutput() {
        return null;
    }

    @Override
    public void add(PortData data, boolean isInput) {
        _outputPorts.add(data);
    }
}
