package uz.ismoil.notes.utils

import android.view.View


fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}


fun View.visibleOrGone(state:Boolean) = if (state) visible() else gone()