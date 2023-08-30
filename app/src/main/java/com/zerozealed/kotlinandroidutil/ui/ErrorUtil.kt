package com.zerozealed.kotlinandroidutil.ui

import android.widget.EditText
import com.zerozealed.kotlinandroidutil.R

object ErrorUtil {

    /**
     * Check edit text for empty and blank field and give appropriate error message.
     * Returns whether or not the text in the field is approved.
     */
    fun EditText.checkEmptyAndBlank(): Boolean = when {
        this.text.isEmpty() -> {
            this.error = resources.getString(R.string.error_field_empty)
            false
        }
        this.text.isBlank() -> {
            this.error = resources.getString(R.string.error_field_blank)
            false
        }
        else -> true
    }

    /**
     * Same as above but with variable amount of edit text.
     */
    fun checkEmptyAndBlank(vararg ets: EditText): Boolean =
        ets.fold(true) { acc, editText ->
            acc and editText.checkEmptyAndBlank()
        }

    /**
     * Check edit text for value of zero or below. Edit text input type should be number or empty.
     * Returns true if input is a number above zero or the EditText is empty, otherwise false.
     * @throws IllegalStateException if this is called with non-empty text that is not a number.
     */
    fun EditText.checkAboveZeroOrEmpty(): Boolean {
        if (this.text.isEmpty()) return true

        val num = this.text.toString().toIntOrNull()
            ?: throw IllegalStateException("${this.text} could not be parsed to a number")

        return if (num <= 0) {
            this.error = resources.getString(R.string.error_not_above_zero)
            false
        } else true
    }

    fun EditText.checkLength(length: Int): Boolean {
        val ok = this.length() == length
        if (!ok)
            this.error = resources.getString(R.string.error_field_not_of_length, length)

        return ok
    }
}
