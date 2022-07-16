package model.data;

import model.exceptions.NotReadyException;
import model.exceptions.NullConnectionException;

import java.util.AbstractCollection;

public abstract class BaseElementData {
    public abstract void setPort(int index, PortData data, boolean isInput);
    public abstract void execute() throws NullConnectionException, NotReadyException;
    protected abstract WireData getDataFromPort(int index);
    protected abstract void addNextElements(AbstractCollection<BaseElementData> out);
    public abstract void connect(BaseElementData to, int outputPortIndex, int inputPortIndex);
    public abstract PortData getPortData(int index, boolean isInput);
}
