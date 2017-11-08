package com.braingroom.tutor.model.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by godara on 13/10/17.
 */

public class ListDialogData {
    private LinkedHashMap<String, Integer> items;

    public ListDialogData(LinkedHashMap<String, Integer> items) {
        this.items = items;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(LinkedHashMap<String, Integer> items) {
        this.items = items;
    }
}
