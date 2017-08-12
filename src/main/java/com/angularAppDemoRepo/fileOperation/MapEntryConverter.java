package com.angularAppDemoRepo.fileOperation;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MapEntryConverter implements Converter {

    @SuppressWarnings("rawtypes")
    public boolean canConvert(final Class clazz) {
        return AbstractMap.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    public void marshal(final Object value, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        AbstractMap<String, String> map = (AbstractMap<String, String>) value;
        for (Entry<String, String> entry : map.entrySet()) {
            writer.startNode(entry.getKey().toString());
            writer.setValue(entry.getValue().toString());
            writer.endNode();
        }
    }

    public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
        Map<String, String> map = new HashMap<String, String>();

        while (reader.hasMoreChildren()) {
            reader.moveDown();
            map.put(reader.getNodeName(), reader.getValue());
            reader.moveUp();
        }
        return map;
    }
}

