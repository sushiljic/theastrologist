package com.theastrologist.rest.domain;

import org.joda.time.DateTime;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by SAM on 20/07/2015.
 */
public class TransitPeriodMap implements Map<Planet, TransitPeriod> {
    private Map<Planet, TransitPeriod> internalMap = new HashMap<Planet, TransitPeriod>();

    public int size() {
        return internalMap.size();
    }

    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return internalMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return internalMap.containsValue(value);
    }

    public TransitPeriod get(Object key) {
        return internalMap.get(key);
    }

    public TransitPeriod put(Planet key, TransitPeriod value) {
        return internalMap.put(key, value);
    }

    public TransitPeriod remove(Object key) {
        return internalMap.remove(key);
    }

    public void putAll(Map<? extends Planet, ? extends TransitPeriod> m) {
        internalMap.putAll(m);
    }

    public void clear() {
        internalMap.clear();
    }

    public Set<Planet> keySet() {
        return internalMap.keySet();
    }

    public Collection<TransitPeriod> values() {
        return internalMap.values();
    }

    public Set<Entry<Planet, TransitPeriod>> entrySet() {
        return internalMap.entrySet();
    }

    public void appendTransit(Planet planet, AspectPosition aspectPosition, DateTime dateTime) {

    }
}
