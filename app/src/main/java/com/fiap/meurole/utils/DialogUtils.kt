package com.fiap.meurole.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fiap.meurole.R

object DialogUtils {

    fun showSimpleMessage(context: Context, message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(context.getString(android.R.string.ok), null)
            .show()
    }

    fun showSimpleMessageWithTitle(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(android.R.string.ok), null)
            .show()
    }

    fun showErrorMessage(context: Context, errorMessage: String?) {
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.error))
            .setMessage(errorMessage)
            .setPositiveButton(context.getString(android.R.string.ok), null)
            .show()
    }

    fun showToastMessage(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showToastErrorMessage(context: Context, errorMessage: String?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }


}