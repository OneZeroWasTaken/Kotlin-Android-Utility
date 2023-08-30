package com.zerozealed.kotlinandroidutil.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.DialogFragment
import com.zerozealed.kotlinandroidutil.R


/**
 * A BasicDialog only has a positive button
 */
open class BasicDialog(
    private val title: String,
    @StringRes private val positiveTextId: Int,
    @StringRes private val messageId: Int? = null,
    @LayoutRes private val layoutId: Int? = null,
    /**
     * Return if the dialog is finished or not. Reasons for not returning true may be if the
     * layout contains EditTexts that should be filled in certain ways.
     */
    private val onConfirm: (dialog: Dialog) -> Boolean,
) : DialogFragment() {

    open fun buildUpon(builder: AlertDialog.Builder): AlertDialog.Builder = builder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = context?.let {
            // Wrap context to get a common style for every dialog without crashing
            val builder = AlertDialog.Builder(ContextThemeWrapper(it, R.style.CollectionDialog))
            builder.setTitle(title)
                .setPositiveButton(positiveTextId) { _, _ -> }

            messageId?.let { id -> builder.setMessage(id) }
            layoutId?.let { layout -> builder.setView(layout) }

            buildUpon(builder).create()
        } ?: throw IllegalStateException("Context cannot be null")

        dialog.setOnShowListener {
            val button: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                if (onConfirm(requireDialog())) {
                    dismiss()
                }
            }
        }
        return dialog
    }
}

/**
 * A ConfirmDialog has two options: a positive button and a negative button
 */
open class ConfirmDialog(
    title: String,
    @StringRes positiveTextId: Int,
    @StringRes messageId: Int? = null,
    @LayoutRes layoutId: Int? = null,
    onConfirm: (dialog: Dialog) -> Boolean,
) : BasicDialog(
    title,
    positiveTextId,
    messageId,
    layoutId,
    onConfirm
) {
    override fun buildUpon(builder: AlertDialog.Builder): AlertDialog.Builder =
        super.buildUpon(builder)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
}
