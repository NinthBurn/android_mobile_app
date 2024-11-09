package com.example.techware.Repository;

import com.example.techware.Model.ComputerComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComputerComponentRepository {
    private final Map<Integer, ComputerComponent> components;
    private int lastId = 1;

    public ComputerComponentRepository() {
        components = new HashMap<>();
    }

    public boolean add(ComputerComponent component) {
        if(component.getId() == -1)
            component.setId(lastId++);

        if(components.containsKey(component.getId()))
            return false;

        components.put(component.getId(), component);
        return true;
    }

    public boolean remove(int id) {
        if(!components.containsKey(id))
            return false;

        components.remove(id);
        return true;
    }

    public boolean update(ComputerComponent component) {
        if(!components.containsKey(component.getId()))
            return false;

        components.put(component.getId(), component);
        return true;
    }

    public ArrayList<ComputerComponent> getAll() {
        return new ArrayList<>(components.values());
    }

    public ComputerComponent get(int id) {
        return components.get(id);
    }

    public int size() {
        return components.size();
    }
}
