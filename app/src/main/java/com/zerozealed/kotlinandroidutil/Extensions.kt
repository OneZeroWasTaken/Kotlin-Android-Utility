package com.zerozealed.kotlinandroidutil

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> String.fromJson(): T =
    Gson().fromJson(this, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson(json, object : TypeToken<T>() {}.type)

fun Any.toJson(): String = Gson().toJson(this)

fun Context.toastOnMain(@StringRes resId: Int) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }
}

fun <E> LiveData<E>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<E>) {
    observe(lifecycleOwner, object : Observer<E> {
        override fun onChanged(value: E) {
            removeObserver(this)
            observer.onChanged(value)
        }
    })
}

