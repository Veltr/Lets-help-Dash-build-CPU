package model.data;

import model.exceptions.CircuitException;
import model.exceptions.NotReadyException;
import model.exceptions.NullConnectionException;

import java.util.AbstractCollection;

public abstract class BaseElementData {
    public abstract void add(PortData data, boolean isInput);
    public abstract void execute() throws NullConnectionException, NotReadyException;
    protected abstract WireData getPortData(int index);
    protected abstract void addNextElements(AbstractCollection<BaseElementData> out);
    public abstract void connect(BaseElementData to, int outputPortIndex, int inputPortIndex);
    protected abstract PortData getPort(int index, boolean isInput);
}
