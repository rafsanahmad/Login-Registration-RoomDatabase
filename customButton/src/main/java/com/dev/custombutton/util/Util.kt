/*
 * *
 *  * Created by rafsanahmad on 7/10/21, 11:55 PM
 *  * Copyright (c) 2021  rafsanahmad. All rights reserved.
 *  * Last modified 7/10/21, 11:55 PM
 *
 */

package com.dev.custombutton.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * The Custom Button utils
 */

fun dpToPx(dp: Float): Float {
    return TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem()
                .displayMetrics
        )
}

fun pxToDp(px: Float): Float {
    return TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem()
                .displayMetrics
        )
}

fun txtPxToSp(px: Float): Float {
    return TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_SP, px, Resources.getSystem()
                .displayMetrics
        )
}

fun getDensity(): Float {
    return Resources.getSystem().displayMetrics.density
}