package io.github.rhymezxcode.app.util

import android.content.Context
import android.widget.Toast


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG)
        .show()
}
