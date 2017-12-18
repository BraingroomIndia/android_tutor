package com.braingroom.tutor.model.data

import java.util.LinkedHashMap

/*
 * Created by godara on 13/10/17.
 */

class ListDialogData(items: LinkedHashMap<String, Int>?) {

    private var items: LinkedHashMap<String, Int>

    init {
        this.items = items ?: LinkedHashMap()
    }

    fun getItems(): LinkedHashMap<String, Int> {
        return items ?: LinkedHashMap()
    }

    fun setItems(items: LinkedHashMap<String, Int>?) {
        this.items = items ?: LinkedHashMap()
    }
}
