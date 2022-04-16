package com.andro.indieschool.cocktailapp.util.extension

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

fun String?.orDefaultField(): String {
    return if (isNullOrBlank()) "-" else this
}

inline fun <reified T : View> View.find(@IdRes resId: Int): T = findViewById<T>(resId)

fun Context?.showAlert(
    @StringRes message: Int,
    builder: MaterialAlertDialogBuilder.() -> AlertDialog.Builder
) {
    this ?: return
    MaterialAlertDialogBuilder(this)
        .setMessage(message)
        .apply { builder() }
        .show()
}

fun View?.showSnackBar(
    @StringRes messageRes: Int,
    duration: Int = Snackbar.LENGTH_SHORT,
    builder: Snackbar.() -> Unit = {}
) {
    this ?: return
    Snackbar.make(this, messageRes, duration).apply {
        builder()
    }.also {
        it.show()
    }
}