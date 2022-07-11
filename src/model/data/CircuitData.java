package model.data;

import model.exceptions.NotReadyException;
import model.exceptions.NullConnectionException;

import java.util.ArrayList;
import java.util.LinkedList;

public class CircuitData {
    private final ArrayList<BaseElementData> _elements;
    private final ArrayList<OutputElementData> _generators;
    private final LinkedList<BaseElementData> _bfsQueue;

    public CircuitData(){
        _elements = new ArrayList<>();
        _generators = new ArrayList<>();
        _bfsQueue = new LinkedList<>();
    }

    public void add(BaseElementData data){
        if(data instanceof OutputElementData) _generators.add((OutputElementData) data);
        else _elements.add(data);
    }

    public void start(){
        _bfsQueue.clear();
        _bfsQueue.addAll(_generators);
        bfs();

        for(var i : _elements) if(i instanceof LogicElementData) ((LogicElementData)i).clearForBfs();
    }

    private void bfs(){
        while (_bfsQueue.size() > 0){
            var cur = _bfsQueue.pollFirst();
            try { cur.execute(); }
            catch (NullConnectionException e) {
                System.out.println(e.getMessage());
                return;
            }
            catch (NotReadyException e){
                _bfsQueue.addLast(cur);
                continue;
            }
            cur.addNextElements(_bfsQueue);
        }
    }

    public String toString(){
        return String.format("Generators: %d%nElements: %d", _generators.size(), _elements.size());
    }
}