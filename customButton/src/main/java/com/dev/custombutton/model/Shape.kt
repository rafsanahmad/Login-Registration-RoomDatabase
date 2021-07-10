/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.custombutton.model

/**
 * Kind of shapes Custom Button
 */
enum class Shape(val shape: Int) {
    RECTANGLE(0),
    SQUARE(1),
    CIRCLE(2),
    OVAL(3);

    companion object {
        fun fromInt(value: Int) = Shape.values().first { it.shape == value }
    }
}
