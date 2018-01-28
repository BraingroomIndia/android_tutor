package com.braingroom.tutor.model.data

import java.util.LinkedHashMap

/*
 * Created by godara on 13/10/17.
 */

class ListDialogData(items: LinkedHashMap<Int, String>?) {

    private var items: LinkedHashMap<Int, String>

    init {
        this.items = items ?: LinkedHashMap()
    }

    fun getItems(): LinkedHashMap<Int, String> {
        return items ?: LinkedHashMap()
    }

    fun setItems(items: LinkedHashMap<Int, String>?) {
        this.items = items ?: LinkedHashMap()
    }
}
