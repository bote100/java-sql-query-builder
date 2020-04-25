package com.github.danfickle.javasqlquerybuilder.tool;

import com.google.common.collect.Maps;

import java.util.HashMap;

/**
 * @author Elias Arndt | bote100
 * Created on 25.04.2020
 */

public class Document {

    private final HashMap<String, Object> saver = Maps.newHashMap();

    public String getString(String key) {
        return saver.get(key).toString();
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public Object getObject(String key) {
        return this.saver.get(key);
    }

    public void append(String key, Object val) {
        this.saver.put(key, val);
    }

}
